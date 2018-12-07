package com.example.saracruz.alertnotification;


public class Item {
    private String title;
    private String day;
    private String hour;


    private int tipo;


    public Item(String title, String day, String hour, int image) {
        this.title = title;
        this.day = day;
        this.hour = hour;
        this.tipo = image;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }



 }
