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
import com.zalaty.lalistadelacompra.database.ProductAdapter;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ProductModel> productModelArrayList;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.productBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.lvProduct);
        databaseHelper = new DatabaseHelper(this);

        productModelArrayList = databaseHelper.getAllProducts();
        //marketModelArrayList = databaseHelper.getAllMarkets();

        productAdapter = new ProductAdapter(this,productModelArrayList);
        listView.setAdapter(productAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductActivity.this, ProductUpdateDeleteActivity.class);
                intent.putExtra("product", (Serializable) productModelArrayList.get(position));
                startActivity(intent);
            }
        });


        btnAdd = (Button) findViewById(R.id.btnAdd);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ProductAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
