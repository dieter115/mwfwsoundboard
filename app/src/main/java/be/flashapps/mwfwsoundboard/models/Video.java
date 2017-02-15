package be.flashapps.mwfwsoundboard.models;

/**
 * Created by dietervaesen on 28/12/16.
 */

public class Video {
    private int year;
    private String url;
    private int imageId;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Video(int year, String url,int imageId) {
        this.year = year;
        this.url = url;
        this.imageId=imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
