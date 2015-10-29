package com.diplomado.adrian.findjobs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SERVER on 14/10/2015.
 */
public class Job {
    private int id;
    private String title;
    private String description;
    private String posted_date;

    private ArrayList<String> contacts;

    public Job(){

    }

    public Job(int id, String title, String description, String posted_date, ArrayList<String> contacts) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.posted_date = posted_date;
        this.contacts = contacts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    public ArrayList<String>  getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", posted_date='" + posted_date + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
