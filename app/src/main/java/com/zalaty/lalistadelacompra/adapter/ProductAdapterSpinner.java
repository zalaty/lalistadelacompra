package com.zalaty.lalistadelacompra.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.List;

public class ProductAdapterSpinner extends ArrayAdapter<ProductModel> {

    private Context context;
    private List<ProductModel> productList;

    public ProductAdapterSpinner(Context context, int textViewResourceId, List<ProductModel> productList) {
        super(context, textViewResourceId, productList);
        this.context = context;
        this.productList = productList;
    }

    public int getCount(){
        return productList.size();
    }

    public ProductModel getItem(int position){
        return productList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        view.setTextSize(16);
        view.setText(productList.get(position).getName());
        view.setPadding(5,5,5,5);
        return view;
    }

    //View of Spinner on dropdown Popping

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        view.setText(productList.get(position).getName());
        view.setTextSize(20);
        view.setPadding(10,10,10,10);
        return view;
    }


}
