package com.example.saracruz.alertnotification;




import java.util.Date;

public class Item {
    private String title;
    private String descr;
    private Date fecha;


    private int tipo;




    public Item(String title, Date fecha, int image, String descr) {
        this.title = title;
        this.tipo = image;
        this.fecha = fecha;
        this.descr = descr;
    }

    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescr() {
        return descr;
    }
    public void setDescr(String descr) {
        this.descr = descr;
    }


    public Date getDay() {
        return fecha;
    }
    public void setDay(Date fecha) {
        this.fecha = fecha;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


}

