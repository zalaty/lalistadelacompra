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
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.adapter.MarketAdapter;
import com.zalaty.lalistadelacompra.model.MarketModel;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MarketModel> marketModelArrayList;
    private MarketAdapter marketAdapter;
    private DatabaseHelper databaseHelper;
    private Button btnAdd;
    private Button btnGoToList;
    Intent intent;

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
                Intent intent = new Intent(getApplicationContext(), MarketAddActivity.class);
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
/*            case R.id.menuProduct:
                ProductActivity();
                break;

            case R.id.menuMarket:
                MarketActivity();
                break;

            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.goToList:
                MainActivity();
                break;
            case R.id.info:
                info();
                break;
        }
        //return true;
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

/*    private void MarketActivity(){
        intent = new Intent(this, MarketActivity.class);
        startActivity(intent);
    }

    private void ProductActivity(){
        intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }*/
}
