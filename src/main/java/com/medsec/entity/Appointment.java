package com.medsec.entity;

import org.json.simple.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    private Integer id;
    private String title;
    private Timestamp date_create;
    private Timestamp date_change;
    private Date date;
    private String detail;
    private String note;
    private boolean status;
    private Integer duration;
    private boolean is_cancelled;
    private String pid;
    private boolean is_confirmed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate_create() {
        return date_create;
    }

    public void setDate_create(Timestamp date_create) {
        this.date_create = date_create;
    }

    public Timestamp getDate_change() {
        return date_change;
    }

    public void setDate_change(Timestamp date_change) {
        this.date_change = date_change;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public boolean isIs_cancelled() {
        return is_cancelled;
    }

    public void setIs_cancelled(boolean is_cancelled) {
        this.is_cancelled = is_cancelled;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isIs_confirmed() {
        return is_confirmed;
    }

    public void setIs_confirmed(boolean is_confirmed) {
        this.is_confirmed = is_confirmed;
    }


    @SuppressWarnings("unchecked")
    public JSONObject toJson() {
        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("title", title);
        jo.put("date_create", date_create.toString());
        jo.put("date_change", date_change.toString());
        jo.put("date", date.toString());
        jo.put("detail", detail);
        jo.put("note", note);
        jo.put("status", status);
        jo.put("duration", duration);
        jo.put("is_cancelled", is_cancelled);
        jo.put("pid", pid);
        jo.put("is_confirmed", is_confirmed);
        return jo;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}