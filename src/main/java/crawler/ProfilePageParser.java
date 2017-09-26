package crawler;

import config.Config;
import entities.Professor;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.java2d.cmm.Profile;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by ali on 9/22/17.
 */
public class ProfilePageParser {

    StringBuilder interests = new StringBuilder();

     Professor professor;
    final static Logger logger = Logger.getLogger(ProfilePageParser.class);

    public ProfilePageParser(Professor professor) throws IOException {

        String profilePageURL = professor.getProfileUrl();

        if (profilePageURL == null)
            return;

        this.professor = professor;
        interestParser(professor, profilePageURL);
    }

    private void interestParser(Professor professor, String url) throws IOException {
        Document profile = null;
        try {
            profile = Jsoup.connect(url).timeout(10000).get();
            extractParagraphs(profile);
            String homePageUrl = findWebHomePageUrl(profile);
            if (homePageUrl!=null){
                professor.setHomepage(homePageUrl);
                HomePageParser homePageParser = new HomePageParser(professor);
                String interestsInHomePage = homePageParser.interests.toString();
                this.interests.append(" "+ interestsInHomePage);

            }
        } catch (MalformedURLException e) {
            logger.error("page format is not http. professor:" + professor.getName());
        } catch (IOException e) {
            logger.error(e.getMessage() + " professor name :"+professor.getName());
        }

        if(interests.length()<200 && profile!= null)
        {
            HomePageParser profileAsHomePage = new HomePageParser(professor, profile);
            this.interests.append(" "+ profileAsHomePage.getInterests());
        } else if(interests.length()<200){
            HomePageParser profileAsHomePage = new HomePageParser(professor, url);
            this.interests.append(" "+ profileAsHomePage.getInterests());
        }

    }

    private void extractParagraphs(Document profile){

        Elements paragraphs = profile.select(Config.INTERESTS_PROFILE);

        for (Element interestElement:paragraphs) {
            String interest = cleanElementContainInterest(interestElement);

            if (!interest.equals(""))
                interests.append(" " + interest);
        }


    }

    private String cleanElementContainInterest(Element interestElement) {
        String paragrapgh = interestElement.ownText();

        int begin = paragrapgh.indexOf("interests");
        int end = paragrapgh.indexOf('.');
        /*if (end < 0 || begin < 0)
            return "";
        if (end<begin) {
           logger.warn("Anomaly with professor"+ " professor name :" + professor.getProfileUrl() );
        }*/
        return paragrapgh;
    }

    public String getInterests() {
        return interests.toString();
    }

    String findWebHomePageUrl(Document profile) {

        if (professor.getName().equals("Shi Li"))
        {
           Elements e = profile.select("a");
            Element element = e.first();
        }
        Elements elements = profile.select(Config.HOME_PAGE_IN_PROFILE);

        if (elements.size() == 0)
            return null;
        Element element = elements.get(0);

        String url = element.absUrl("href");

        if (url.endsWith("pdf") && elements.size() == 2) {
            element = elements.get(1);
            url = element.absUrl("href");
        } else if(elements.size() == 1 && url.endsWith("pdf")) {
            return null;
        }


        return url;
    }
}
