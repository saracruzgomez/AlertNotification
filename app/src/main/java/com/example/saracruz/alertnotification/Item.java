package com.example.saracruz.alertnotification;


import java.util.Date;

public class Item {
    private String title;
    private String descr;
    private Date date;

    private int tipo;


    public Item(String title, Date date, int image, String descr) {
        this.title = title;
        this.tipo = image;
        this.date = date;
        this.descr = descr;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

