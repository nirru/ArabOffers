package arab_offers.lue.com.Models;

/**
 * Created by Fujitsu on 02-01-2017.
 */
public class ModelObj {

    int imageid;
    String datedfrom, dateduntil,views, likes, company, message;
    boolean special;

    public ModelObj(int imageid, String dateduntil, String datedfrom, String views, String likes, String company, String message, boolean special) {
        this.imageid = imageid;
        this.dateduntil = dateduntil;
        this.datedfrom = datedfrom;
        this.views = views;
        this.likes = likes;
        this.company = company;
        this.message = message;
        this.special = special;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getDatedfrom() {
        return datedfrom;
    }

    public void setDatedfrom(String datedfrom) {
        this.datedfrom = datedfrom;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getDateduntil() {
        return dateduntil;
    }

    public void setDateduntil(String dateduntil) {
        this.dateduntil = dateduntil;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }
}
