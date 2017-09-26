package crawler;

import config.Config;
import entities.Professor;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ali on 9/22/17.
 */
public class FacultyParser {


    Document facultyPage;
    String base ;
    HashMap<String, Professor> professors  = new HashMap<String, Professor>();
    final static Logger logger = Logger.getLogger(FacultyParser.class);

    public FacultyParser(String base) throws IOException {
        this.base = base;
        facultyPage = Jsoup.connect(base).timeout(50000).get();
        extractProfessorsData(facultyPage, Config.FACULTY_LIST_CSS);

    }

    private void extractProfessorsData(Document facultyPage, String elementClass) {
        Elements facultySections = facultyPage.select(elementClass);
        extractsUrls(facultySections);
    }

    private String extractName(Element professorData, String cssQuery){
        Element nameElement = professorData.select(cssQuery).first();
        String name;
        if (nameElement == null) {
            logger.error("Name could not be found " + professorData.ownText());
            return "";
        }
        name = nameElement.ownText();
        return name;
    }

    private void extractsUrls(Elements facultySections) {
        for (Element section:facultySections) {

            String profileUrl = extractElementUrl(section, Config.PROFILE_URL_CSS);
            String homePageUrl = extractElementUrl(section, Config.HOME_PAGE_URL_CSS);
            String professorsName = extractName(section,Config.NAME_CSS);


            if (professorsName.length()<3)
                logger.error("Unusual name: "+ professorsName +" " + profileUrl+ " " + homePageUrl);

            Professor professor = new Professor(professorsName, profileUrl,homePageUrl);
            professors.put(professorsName, professor);
        }
    }


    String extractElementUrl(Element element, String cssQuery){
        Element profileElement = element.select(cssQuery).first();

        if (profileElement==null)
            return null;
        String profileUrl= profileElement.absUrl("href");
        return profileUrl;

    }

    public HashMap<String, Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(HashMap<String, Professor> professors) {
        this.professors = professors;
    }

}
