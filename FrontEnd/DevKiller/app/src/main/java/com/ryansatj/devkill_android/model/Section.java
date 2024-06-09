package com.ryansatj.devkill_android.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Section {

    public int id;
    public int projectid;
    public String title;
    public String descriptions;
    public String deadline;
    public String resources;
    public String alerts;

    public Section(int id, int projectid, String title, String descriptions,
                   String deadline, String resources, String alerts) {
        if (deadline != null && deadline.length() >= 10) {
            this.deadline = deadline.substring(0, 10);
        } else {
            this.deadline = deadline;
        }

        this.id = id;
        this.projectid = projectid;
        this.title = title;
        this.descriptions = descriptions;
        this.resources = resources;
        this.alerts = alerts;
    }

    // Getters and setters can be added here if needed

    public static void main(String[] args) {
        Section section = new Section(1, 1, "Title", "Descriptions",
                "2025-10-10T17:00:00.000Z", "Resources", "Alerts");
        System.out.println("Section deadline: " + section.deadline);  // Output: 2025-10-10
    }
}
