package com.zalaty.lalistadelacompra.adapter;

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
        view.setTextColor(Color.BLACK);
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        view.setTextSize(20);
        view.setText(marketList.get(position).getName());
        view.setPadding(10,10,10,10);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        view.setText(marketList.get(position).getName());
        view.setTextSize(20);
        view.setPadding(10,10,10,10);
        return view;
    }
}
