package entities;

/**
 * Created by ali on 9/22/17.
 */
public class Professor {
    String name;
    String interests;
    public String homepage;
    String profileUrl;
    String openPosition;

    public Professor() {
    }

    public Professor(String professorsName, String profileUrl, String homePageUrl) {
        name = professorsName;
        this.profileUrl = profileUrl;
        homepage = homePageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }


    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getOpenPosition() {
        return openPosition;
    }

    public void setOpenPosition(String openPosition) {
        this.openPosition = openPosition;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "name='" + name + '\'' +
                ", interests='" + interests + '\'' +
                ", homepage='" + homepage + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", openPosition='" + openPosition + '\'' +
                '}';
    }
}
