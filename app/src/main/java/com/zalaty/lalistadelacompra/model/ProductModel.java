package com.zalaty.lalistadelacompra.model;

import java.io.Serializable;

public class ProductModel implements Serializable {

    int id;
    String name;
    String description;
    Double price;
    int marketId;

    // constructors
    public ProductModel(){

    }

    public ProductModel(String name){
        this.name = name;
    }

    public ProductModel(String name, String description, Double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductModel(String name, String description, Double price, int marketId){
        this.name = name;
        this.description = description;
        this.price = price;
        this.marketId = marketId;
    }

    // setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public void setMarketId(int marketId){
        this.marketId = marketId;
    }

    // getters
    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public Double getPrice(){
        return this.price;
    }

    public long getMarketId(){
        return this.marketId;
    }
}
