package com.zalaty.lalistadelacompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.R;
import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.model.MarketModel;

public class MarketUpdateDeleteActivity extends AppCompatActivity {

    private MarketModel market;
    private EditText etMarketName, etMarketZone;
    private Button btnUpdate, btnDelete;
    private DatabaseHelper databaseHelper;
    private Button btnGoToList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_update_delete);

        Toolbar toolbar = (Toolbar) findViewById(R.id.marketBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        market = (MarketModel) intent.getSerializableExtra("market");

        databaseHelper = new DatabaseHelper(this);


        etMarketName = (EditText) findViewById(R.id.etMarketName);
        etMarketZone = (EditText) findViewById(R.id.etMarketZone);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        etMarketName.setText(market.getName());
        etMarketZone.setText(market.getZone());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMarketName.getText().toString().trim().isEmpty()){
                    ShowMandatory();
                }else {
                    market.setName(etMarketName.getText().toString().trim());
                    market.setZone(etMarketZone.getText().toString().trim());

                    databaseHelper.updateMarket(market);
                    //Toast.makeText(MarketUpdateDeleteActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MarketUpdateDeleteActivity.this, MarketActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MarketUpdateDeleteActivity.this);

                builder.setTitle(R.string.deleteMarket)
                        .setMessage(R.string.areyousure)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.deleteMarket(market.getId());
                                Intent intent = new Intent(MarketUpdateDeleteActivity.this,MarketActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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

        btnGoToList = (Button) findViewById(R.id.btnGoToList);

        btnGoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity();
            }
        });
    }

    private void ShowMandatory(){
        Toast.makeText(this, R.string.nameMandatory, Toast.LENGTH_SHORT).show();
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
                MainActivity();
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
}
