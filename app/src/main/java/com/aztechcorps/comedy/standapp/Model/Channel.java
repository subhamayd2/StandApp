package com.aztechcorps.comedy.standapp.Model;


public class Channel {
    private String ID, title, customUrl, description, published, country, thumbSmall, thumbMed, thumbHigh;

    public Channel() {  }

    public Channel(String ID, String title, String customUrl, String description, String published,
                   String country, String thumbSmall, String thumbMed, String thumbHigh) {

        this.ID = ID;
        this.title = title;
        this.customUrl = customUrl;
        this.description = description;
        this.published = published;
        this.country = country;
        this.thumbSmall = thumbSmall;
        this.thumbMed = thumbMed;
        this.thumbHigh = thumbHigh;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getThumbSmall() {
        return thumbSmall;
    }

    public void setThumbSmall(String thumbSmall) {
        this.thumbSmall = thumbSmall;
    }

    public String getThumbMed() {
        return thumbMed;
    }

    public void setThumbMed(String thumbMed) {
        this.thumbMed = thumbMed;
    }

    public String getThumbHigh() {
        return thumbHigh;
    }

    public void setThumbHigh(String thumbHigh) {
        this.thumbHigh = thumbHigh;
    }
}
