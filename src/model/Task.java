package model;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    
    private String id;    
    private String message;
    private Date date;
    private Boolean remember;
    private String type;
    private ArrayList<Integer> dayWeek;
    private int interval;
    private int idTable;
    private Date dtAlert;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }    

    public ArrayList<Integer> getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(ArrayList<Integer> dayWeek) {
        this.dayWeek = dayWeek;
    }
}
