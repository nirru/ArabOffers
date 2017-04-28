package arab_offers.lue.com.Models;

import java.util.List;

/**
 * Created by Fujitsu on 09-01-2017.
 */
public class ObjectModel {
    String id, name, publish_date, start_date, end_date, views, description;
    String[] all_images;
    String likes;
    List<Comment_Model> comment_models;
    boolean special_offer;
    boolean is_comment_enabled;

    public ObjectModel(String id, String name, String publish_date, String start_date, String end_date, String views,
                       String description, String[] all_images, String likes, List<Comment_Model> comment_models,
                       boolean special_offer, boolean is_comment_enabled) {
        this.id = id;
        this.name = name;
        this.publish_date = publish_date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.views = views;
        this.description = description;
        this.all_images = all_images;
        this.likes = likes;
        this.comment_models = comment_models;
        this.special_offer = special_offer;
        this.is_comment_enabled = is_comment_enabled;
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

    public String[] getAll_images() {
        return all_images;
    }

    public void setAll_images(String[] all_images) {
        this.all_images = all_images;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public List<Comment_Model> getComment_models() {
        return comment_models;
    }

    public void setComment_models(List<Comment_Model> comment_models) {
        this.comment_models = comment_models;
    }

    public boolean isSpecial_offer() {
        return special_offer;
    }

    public void setSpecial_offer(boolean special_offer) {
        this.special_offer = special_offer;
    }

    public boolean is_comment_enabled() {
        return is_comment_enabled;
    }

    public void setIs_comment_enabled(boolean is_comment_enabled) {
        this.is_comment_enabled = is_comment_enabled;
    }
}
