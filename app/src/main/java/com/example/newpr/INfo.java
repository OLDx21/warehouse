package com.example.newpr;

public class INfo {
    String name;
    String date;
    String kode;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public INfo(String name, String date, String kode) {
        this.name = name;
        this.date = date;
        this.kode = kode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }


}
