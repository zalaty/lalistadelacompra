package com.zalaty.lalistadelacompra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.adapter.ProductAdapter;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ProductModel> productModelArrayList;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;
    private Button btnAdd;
    private Button btnGoToList;
    Intent intent;

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
                Intent intent = new Intent(getApplicationContext(), ProductAddActivity.class);
                startActivity(intent);
            }
        });

        btnGoToList = (Button) findViewById(R.id.btnGoToList);

        btnGoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Intent intent;
        switch(item.getItemId()){
            case R.id.goToList:
                MainActivity();
                break;
            case R.id.info:
                info();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void MainActivity(){
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void info(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View infoDialogView = factory.inflate(R.layout.custom_dialog, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(this).create();
        infoDialog.setView(infoDialogView);
        infoDialogView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

}
