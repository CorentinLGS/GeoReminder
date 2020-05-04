package models;


import androidx.annotation.Nullable;

import java.util.Date;

public class Reminder {

    private String title;
    private String content;
    private Date date;
    private Date deadline;

    public Reminder(){};

    public Reminder(String title, String content, Date date, Date deadline){
        this.title = title;
        this.content = content;
        this.date = date;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Date getDeadline() {
        return deadline;
    }

}
