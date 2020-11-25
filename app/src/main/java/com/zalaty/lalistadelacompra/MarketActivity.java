package com.zalaty.lalistadelacompra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.database.MarketAdapter;
import com.zalaty.lalistadelacompra.model.MarketModel;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MarketModel> marketModelArrayList;
    private MarketAdapter marketAdapter;
    private DatabaseHelper databaseHelper;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        Toolbar toolbar = (Toolbar) findViewById(R.id.marketBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.lvMarket);
        databaseHelper = new DatabaseHelper(this);

/*
        MarketModel mk1 = new MarketModel("Mercadona", "Los gallos");
        databaseHelper.createMarket(mk1);
        MarketModel mk2 = new MarketModel("Supeco", "Urbisur");
        databaseHelper.createMarket(mk2);
*/

        marketModelArrayList = databaseHelper.getAllMarkets();

        marketAdapter = new MarketAdapter(this,marketModelArrayList);
        listView.setAdapter(marketAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MarketActivity.this, MarketUpdateDeleteActivity.class);
                intent.putExtra("market", (Serializable) marketModelArrayList.get(position));
                startActivity(intent);
            }
        });


        btnAdd = (Button) findViewById(R.id.btnAdd);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketActivity.this, MarketAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
