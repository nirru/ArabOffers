package arab_offers.lue.com.Models;

/**
 * Created by Fujitsu on 05-01-2017.
 */
public class RegionModel {


    int id;
    String name, english_name;

    public RegionModel(int id, String name, String english_name) {
        this.id = id;
        this.name = name;
        this.english_name = english_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }
}
