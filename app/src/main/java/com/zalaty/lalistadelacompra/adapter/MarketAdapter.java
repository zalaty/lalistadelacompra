package com.zalaty.lalistadelacompra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zalaty.lalistadelacompra.R;
import com.zalaty.lalistadelacompra.model.MarketModel;

import java.util.ArrayList;

public class MarketAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MarketModel> marketArrayList;

    public MarketAdapter(Context context, ArrayList<MarketModel> marketArrayList){
        this.context = context;
        this.marketArrayList = marketArrayList;
    }

    @Override
    public int getCount() {
        return marketArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return marketArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return marketArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.market_item, null, true);

            holder.tvMarketName = (TextView) convertView.findViewById(R.id.tvMarketName);
            holder.tvMarketZone = (TextView) convertView.findViewById(R.id.tvMarketZone);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvMarketName.setText(marketArrayList.get(position).getName());
        holder.tvMarketZone.setText(marketArrayList.get(position).getZone());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvMarketName, tvMarketZone;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setText(marketArrayList.get(position).getName());
        return view;
    }
}
