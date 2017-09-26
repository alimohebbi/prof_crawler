package crawler;

import entities.Professor;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by ali on 9/22/17.
 */
public class HomePageParser {



    StringBuilder interests = new StringBuilder();
    private Professor professor;
    final static Logger logger = Logger.getLogger(HomePageParser.class);

    public HomePageParser(Professor professor) throws IOException {

        this.professor = professor;
        String homePageUrl = professor.getHomepage();
        if (homePageUrl==null)
            return;
        interestParser(professor, homePageUrl);

    }

    public HomePageParser(Professor professor, String url) throws IOException {

        this.professor = professor;
        String homePageUrl = url;
        if (homePageUrl==null)
            return;
        interestParser(professor, homePageUrl);

    }

    public HomePageParser(Professor professor, Document profile) {
//       just for parse portal as homepage
        this.professor = professor;
        professor.setHomepage(professor.getProfileUrl());
        extractParagraphs(profile);
        professor.setHomepage(null);

    }

    private void interestParser(Professor professor, String url) {
        Document profile;
        try {
            profile = Jsoup.connect(url).timeout(10000).get();
            extractParagraphs(profile);
        } catch (MalformedURLException e) {
            logger.error("page format is not http. professor:" + professor.getName());
        } catch (IOException e) {
            logger.error(e.getMessage() + " professor name :"+professor.getName());
        }
    }

    private void extractParagraphs(Document profile){
        Elements paragraphs = profile.getElementsContainingOwnText("interest");
        if(paragraphs.size() < 300 )
            paragraphs.addAll(profile.getElementsContainingOwnText("research"));
        if(paragraphs.size() < 300 )
            paragraphs.addAll(profile.getElementsContainingOwnText("area"));

        paragraphs.addAll(profile.getElementsByTag("li"));



        for (Element interestElement:paragraphs) {
            String interest = cleanElementContainInterest(interestElement);

            if (!interest.equals(""))
                interests.append(" " + interest);
        }


    }

    private String cleanElementContainInterest(Element interestElement) {
        String paragraph = interestElement.ownText();

        int begin = paragraph.indexOf("research");
        int end = paragraph.indexOf('.');
       /* if (end < 0 || begin < 0)
            return "";*/
        if (end<begin) {

            logger.warn("Anomaly with professor"+ " professor name :" + professor.getHomepage() );
        }
        return paragraph;
    }

    public String getInterests() {
        return interests.toString();
    }

}
