package arab_offers.lue.com.Models;

/**
 * Created by Fujitsu on 09-01-2017.
 */
public class Comment_Model {

    String comment_id, text, time, user_id, user_name, user_mobile, user_city;

    public Comment_Model(String comment_id, String text, String time, String user_id, String user_name, String user_mobile, String user_city) {
        this.comment_id = comment_id;
        this.text = text;
        this.time = time;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.user_city = user_city;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }
}
