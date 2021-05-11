package com.example.newpr;

public class DataModel {
    String name;
    String kolvo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKolvo() {
        return kolvo;
    }

    public void setKolvo(String kolvo) {
        this.kolvo = kolvo;
    }

    public DataModel(String name, String kolvo) {
        this.name = name;
        this.kolvo = kolvo;
    }
}
