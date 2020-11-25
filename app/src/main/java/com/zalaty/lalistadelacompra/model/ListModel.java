package com.zalaty.lalistadelacompra.model;

import java.io.Serializable;

public class ListModel  implements Serializable {

    int id;
    int num;
    int productId;

    // constructors
    public ListModel(){

    }

    public ListModel(int productId){
        this.productId = productId;
    }

    public ListModel(int num, int productId){
        this.num = num;
        this.productId = productId;
    }

    // setters
    public void setId(int id){
        this.id = id;
    }

    public void setNum(int num){
        this.num = num;
    }

    public void setProductIdId(int productId){
        this.productId = productId;
    }

    // getters
    public long getId(){
        return this.id;
    }

    public int getNum(){
        return this.num;
    }

    public int getProductId(){
        return this.productId;
    }
}
