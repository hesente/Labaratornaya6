package com.example.laba6s;

public class Reminder {
    private int id;
    private String title;
    private String description;
    private long dateTime;

    public Reminder(int id, String title, String description, long dateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getDateTime() {
        return dateTime;
    }
}
