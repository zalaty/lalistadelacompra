package com.zalaty.lalistadelacompra.model;

import java.io.Serializable;

public class MarketModel implements Serializable {
    int id;
    String name;
    String zone;

    // constructors
    public MarketModel(){

    }

    public MarketModel(String name){
        this.name=name;
    }

    public MarketModel(String name, String zone){
        this.name = name;
        this.zone = zone;
    }

    // setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setZone(String zone){
        this.zone = zone;
    }

    // getters
    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getZone(){
        return this.zone;
    }
}
