package models;


import java.util.Date;

public class Reminder {

    private String uid;
    private String title;
    private String content;
    private Date date;
    private Date deadline;

    public Reminder(String uid, String title, String content, Date date, Date deadline){
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }
}
