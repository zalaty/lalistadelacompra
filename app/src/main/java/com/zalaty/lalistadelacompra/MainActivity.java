package com.zalaty.lalistadelacompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.adapter.ListAdapter;
import com.zalaty.lalistadelacompra.adapter.MarketAdapterSpinner;
import com.zalaty.lalistadelacompra.model.ListModel;
import com.zalaty.lalistadelacompra.model.MarketModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnMarket, btnProduct;
    Intent intent;
    private Button btnAdd;
    private TextView tvAdd;
    private EditText etTotal;
    private ArrayList<ListModel> listModelArrayList;
    private DatabaseHelper databaseHelper;
    private ListView listView;
    private ListAdapter listAdapter;
    private LinearLayout llHead;
    private RelativeLayout llHeadNoItems;
    private Spinner spMarket;
    List<MarketModel> lstMarkets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMarket = (Button) findViewById(R.id.btnMarket);
        btnProduct = (Button) findViewById(R.id.btnProduct);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        etTotal = (EditText) findViewById(R.id.etTotal);
        listView = (ListView) findViewById(R.id.lvList);
        llHead = (LinearLayout) findViewById(R.id.llHead);
        llHeadNoItems = (RelativeLayout) findViewById(R.id.llHeadNoItems);
        spMarket = (Spinner) findViewById(R.id.spMarket);

        databaseHelper = new DatabaseHelper(this);

        LoadList(0);
        loadSpinnerData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(R.string.deleteProduct)
                        .setMessage(R.string.areyousure)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                databaseHelper.deleteList((listModelArrayList.get(position)).getId());
                                LoadList(0);

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();
            }
        });

        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketActivity();
            }
        });

        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }


    private void setTotal(){
        Double total = 0.0;
        int num;
        Double price;

        if (listModelArrayList.size() > 0){
            for (int i = 0; i < listModelArrayList.size(); i++)
            {
                num = listModelArrayList.get(i).getNum();
                price = databaseHelper.getProduct(listModelArrayList.get(i).getProductId()).getPrice();
                total = total + (price * num);
            }
        }
        etTotal.setText(String.format("%.2f",total));
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
            case R.id.menuProduct:
                ProductActivity();
                break;

            case R.id.menuMarket:
                MarketActivity();
                break;

            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.goToList:
                //MainActivity();
                break;
        }
        //return true;
        return super.onOptionsItemSelected(item);
    }

    private void MainActivity(){
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void MarketActivity(){
        intent = new Intent(this, MarketActivity.class);
        startActivity(intent);
    }

    private void ProductActivity(){
        intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    private void LoadList(int market_id){
        listModelArrayList = databaseHelper.getAllList(market_id);
        listAdapter = new ListAdapter(this, listModelArrayList);
        listView.setAdapter(listAdapter);
        llHead.setVisibility((listModelArrayList.size() > 0) ? View.VISIBLE : View.INVISIBLE);
        llHeadNoItems.setVisibility((listModelArrayList.size() > 0) ? View.INVISIBLE : View.VISIBLE);
        setTotal();
    }

    private void addProduct(){
        if (databaseHelper.getAllProducts().size() > 0){
            Intent intent = new Intent(getApplicationContext(), ListAddActivity.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.nohayproductos);
            builder.setMessage(R.string.debeanadirproductos);
            builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ProductActivity();
                }
            });
            builder.show();
        }
    }

    private void loadSpinnerData(){
        lstMarkets = databaseHelper.getAllMarkets();
        lstMarkets.add(0, new MarketModel(getString(R.string.select)));
        MarketAdapterSpinner marketAdapterSpinner = new MarketAdapterSpinner(this,android.R.layout.simple_spinner_item, lstMarkets);
        marketAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMarket.setAdapter(marketAdapterSpinner);
        spMarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadList((int) ((MarketModel) spMarket.getSelectedItem()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
}