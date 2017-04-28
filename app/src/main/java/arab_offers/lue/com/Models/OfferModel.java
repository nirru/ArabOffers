package arab_offers.lue.com.Models;

import android.util.Log;

import org.json.JSONArray;

/**
 * Created by Fujitsu on 04-01-2017.
 */
public class OfferModel {
    String id,fb_id, name, publish_date, start_date, end_date, views, description, comments, likes;
    boolean  special_offer;
    String[] imagearray;

    public OfferModel(String id, String name, String publish_date, String start_date,
                      String end_date, String views, String description, String comments,
                      String likes, boolean special_offer, String[] imagearray) {
        this.id = id;
        this.name = name;
        this.publish_date = publish_date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.views = views;
        this.description = description;
        this.comments = comments;
        this.likes = likes;
        this.special_offer = special_offer;
        this.imagearray = imagearray;
    }

    public OfferModel(String id, String fb_id,String name, String publish_date, String start_date,
                      String end_date, String views, String description, String comments,
                      String likes, boolean special_offer, String[] imagearray) {
        this.id = id;
        this.fb_id = fb_id;
        this.name = name;
        this.publish_date = publish_date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.views = views;
        this.description = description;
        this.comments = comments;
        this.likes = likes;
        this.special_offer = special_offer;
        this.imagearray = imagearray;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public boolean isSpecial_offer() {
        return special_offer;
    }

    public void setSpecial_offer(boolean special_offer) {
        this.special_offer = special_offer;
    }

    public String[] getImagearray() {
        return imagearray;
    }

    public void setImagearray(String[] imagearray) {
        this.imagearray = imagearray;
    }
}
