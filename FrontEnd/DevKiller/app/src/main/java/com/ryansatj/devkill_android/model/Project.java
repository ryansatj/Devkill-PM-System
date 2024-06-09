package com.ryansatj.devkill_android.model;

public class Project {
    public int id;
    public String repository;
    public int userid;
    public String title;
    public String descriptions;

    public Project(int id, String repository, int userid, String title, String descriptions){
        this.repository =repository;
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.descriptions = descriptions;
    }
    public String toString() {
        return "Project{id=" + id + "repository=" + repository + ", userid=" + userid + ", title='" + title + "', descriptions='" + descriptions + "'}";
    }
}
