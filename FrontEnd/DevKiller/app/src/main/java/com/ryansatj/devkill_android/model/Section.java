package com.ryansatj.devkill_android.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Section {

    public int id;
    public String projectrepo;
    public String title;
    public String description;
    public String deadline;
    public String resources;
    public String alerts;

    public Section(int id, String projectrepo, String title, String description,
                   String deadline, String resources, String alerts) {
        if (deadline != null && deadline.length() >= 10) {
            this.deadline = deadline.substring(0, 10);
        } else {
            this.deadline = deadline;
        }

        this.id = id;
        this.projectrepo = projectrepo;
        this.title = title;
        this.description = description;
        this.resources = resources;
        this.alerts = alerts;
    }
}
