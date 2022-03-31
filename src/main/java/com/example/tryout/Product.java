package com.example.tryout;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private SimpleStringProperty  title, category, expdate;
    private SimpleIntegerProperty id, units;

    public Product(Integer id, String title, String category, Integer units, String expdate) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.category = new SimpleStringProperty(category);
        this.units = new SimpleIntegerProperty(units);
        this.expdate = new SimpleStringProperty(expdate);
    }
    public int getID(){return this.id.get();}
    public String getTitle(){return this.title.get();}
    public String getCategory(){return this.category.get();}
    public int getUnits(){return this.units.get();}
    public String getExpDate(){return this.expdate.get();}
}
