package com.example.cars_app;

public class Cars {

    //These attribute are private and I will deal with them by setter && getter methods
    private int id;
    private String model;
    private String color;
    private String description;
    private String image;
    private double dpl;

    // I do this constructor for any insert process to any table in database
    public Cars(String model, String color, String description, String image, double dpl) {
        this.model = model;
        this.color = color;
        this.description = description;
        this.image = image;
        this.dpl = dpl;
    }//end constructor(without id)

    //I do this constructor for any select, update, delete any data from database
    public Cars(int id, String model, String color, String description, String image, double dpl) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.description = description;
        this.image = image;
        this.dpl = dpl;
    }//end constructor(with all parameter)

    public int getId() {
        return id;
    }//end getId()

    public void setId(int id) {
        this.id = id;
    }//end setId()

    public String getModel() {
        return model;
    }//end getModel()

    public void setModel(String model) {
        this.model = model;
    }//end setModel()

    public String getColor() {
        return color;
    }//end getColor()

    public void setColor(String color) {
        this.color = color;
    }//end setColor()

    public String getDescription() {
        return description;
    }//end getDescription()

    public void setDescription(String description) {
        this.description = description;
    }//end setDescription()

    public String getImage() {
        return image;
    }//end getImage()

    public void setImage(String image) {
        this.image = image;
    }//end setImage()

    public double getDpl() {
        return dpl;
    }//end getDpl()

    public void setDpl(double dpl) {
        this.dpl = dpl;
    }//end setDpl()

}//end class