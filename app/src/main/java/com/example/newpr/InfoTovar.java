package com.example.newpr;

public class InfoTovar {
    String name;
    String recept;

    public InfoTovar(String name, String recept) {
        this.name = name;
        this.recept = recept;
    }

    public String getNam() {
        return name;
    }

    public void setNam(String name) {
        this.name = name;
    }

    public String getRecept() {
        return recept;
    }

    public void setRecept(String recept) {
        this.recept = recept;
    }
}
