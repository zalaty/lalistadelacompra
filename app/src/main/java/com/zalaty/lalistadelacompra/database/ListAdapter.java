package com.zalaty.lalistadelacompra.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zalaty.lalistadelacompra.R;
import com.zalaty.lalistadelacompra.model.ListModel;
import com.zalaty.lalistadelacompra.model.MarketModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListModel> listModelArrayList;
    //private ArrayList<ProductModel> productArrayList;
    //private ArrayList<MarketModel> marketModelArrayList;
    private DatabaseHelper databaseHelper;

    public ListAdapter(Context context, ArrayList<ListModel> listModelArrayList){
        this.context = context;
        this.listModelArrayList = listModelArrayList;
        databaseHelper = new DatabaseHelper(context);
        //productArrayList = databaseHelper.getAllProducts();
        //marketModelArrayList = databaseHelper.getAllMarkets();
    }

    @Override
    public int getCount() {
        return listModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.listModelArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int num;
        Double price;
        ProductModel product;
        MarketModel market;

        if (convertView == null) {
            holder = new ListAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null, true);

            holder.tvListProduct = (TextView) convertView.findViewById(R.id.tvListProduct);
            holder.tvListProductMarket = (TextView) convertView.findViewById(R.id.tvListProductMarket);
            holder.tvListNum = (TextView) convertView.findViewById(R.id.tvListNum);
            holder.tvListPrice = (TextView) convertView.findViewById(R.id.tvListPrice);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ListAdapter.ViewHolder)convertView.getTag();
        }


        num = listModelArrayList.get(position).getNum();

        //long test = listModelArrayList.get(position).getProductId();
        //ArrayList<ProductModel> test2 = databaseHelper.getAllProducts();

        product = databaseHelper.getProduct(listModelArrayList.get(position).getProductId());

        if (databaseHelper.existMarket(product.getMarketId())){
            market = databaseHelper.getMarKet(product.getMarketId());
            holder.tvListProductMarket.setText(market.getName());
        }else{
            holder.tvListProductMarket.setText("");
        }

        holder.tvListNum.setText(Integer.toString(num));
        holder.tvListProduct.setText(product.getName());
        price = databaseHelper.getProduct(listModelArrayList.get(position).getProductId()).getPrice();
        holder.tvListPrice.setText(Double.toString(num * price));


        return convertView;
    }

    private class ViewHolder {

        protected TextView tvListProduct, tvListProductMarket, tvListNum, tvListPrice;
    }
}
