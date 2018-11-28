package model;

import java.util.Date;

public class Task {
    
    private String id;
    private String title;
    private String message;
    private Date date;
    private Boolean remember;
    private int interval;
    private int idTable;
    private Date dtAlert;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public Date getDtAlert() {
        return dtAlert;
    }

    public void setDtAlert(Date dtAlert) {
        this.dtAlert = dtAlert;
    }

     
    
}
