package com.example.saracruz.alertnotification;


import java.util.Date;

public class Item {
    private String title;
    private Date date;

    private int tipo;


    public Item(String title, Date date, int image) {
        this.title = title;
        this.tipo = image;
        this.date = date;
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

