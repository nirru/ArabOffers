package arab_offers.lue.com.Models;

/**
 * Created by Fujitsu on 05-01-2017.
 */
public class CountryModel {
    int id;
    String name , phonecode, englishname;

    public CountryModel(int id, String name, String phonecode, String englishname) {
        this.id = id;
        this.name = name;
        this.phonecode = phonecode;
        this.englishname = englishname;
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

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }
}
