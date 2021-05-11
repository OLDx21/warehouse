package com.example.newpr;

public class HistoryClas {

    private String fruitImg;
    private String fruitName;
    private String calories;


    public HistoryClas(String fruitImg, String fruitName,String calories) {
        super();
        this.setFruitImg(fruitImg);
        this.setFruitName(fruitName);
        this.setCalories(calories);
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitImg() {
        return fruitImg;
    }

    public void setFruitImg(String fruitImg) {
        this.fruitImg = fruitImg;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
