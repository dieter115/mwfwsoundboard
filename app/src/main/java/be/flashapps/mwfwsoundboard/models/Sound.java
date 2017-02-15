package be.flashapps.mwfwsoundboard.models;

/**
 * Created by dietervaesen on 25/01/16.
 */
public class Sound {
    String title;
    String name;

    public Sound(String title,String name){
        this.title=title;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}
