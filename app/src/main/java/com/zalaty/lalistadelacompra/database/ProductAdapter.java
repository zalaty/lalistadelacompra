package com.zalaty.lalistadelacompra.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zalaty.lalistadelacompra.R;
import com.zalaty.lalistadelacompra.model.MarketModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductModel> productArrayList;
    private ArrayList<MarketModel> marketModelArrayList;
    private DatabaseHelper databaseHelper;

    public ProductAdapter(Context context, ArrayList<ProductModel> productArrayList){
        this.context = context;
        this.productArrayList = productArrayList;
        databaseHelper = new DatabaseHelper(context);
        marketModelArrayList = databaseHelper.getAllMarkets();
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.producto_item, null, true);

            holder.tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);
            holder.tvProductMarket = (TextView) convertView.findViewById(R.id.tvProductMarket);
            holder.tvProductPrice = (TextView) convertView.findViewById(R.id.tvProductPrice);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvProductName.setText(productArrayList.get(position).getName());
        if (databaseHelper.existMarket(productArrayList.get(position).getMarketId())){
            holder.tvProductMarket.setText(databaseHelper.getMarKet(productArrayList.get(position).getMarketId()).getName());
        }else{
            holder.tvProductMarket.setText("");
        }

        holder.tvProductPrice.setText(productArrayList.get(position).getPrice().toString());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvProductName, tvProductMarket, tvProductPrice;
    }
}
