package com.zalaty.lalistadelacompra.database;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zalaty.lalistadelacompra.model.MarketModel;

import java.util.List;

public class MarketAdapterSpinner extends ArrayAdapter<MarketModel> {

    private Context context;
    private List<MarketModel> marketList;

    public MarketAdapterSpinner(Context context, int textViewResourceId, List<MarketModel> marketList) {
        super(context, textViewResourceId, marketList);
        this.context = context;
        this.marketList = marketList;
    }

    public int getCount(){
        return marketList.size();
    }

    public MarketModel getItem(int position){
        return marketList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setGravity(Gravity.LEFT);
        view.setTextSize(20);
        view.setText(marketList.get(position).getName());
        view.setPadding(10,10,10,10);
        return view;
    }

    //View of Spinner on dropdown Popping

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setText(marketList.get(position).getName());
        view.setTextSize(20);
        view.setPadding(10,10,10,10);
        return view;
    }
}
