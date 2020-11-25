package com.zalaty.lalistadelacompra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.model.MarketModel;

public class MarketAddActivity extends AppCompatActivity {

    private MarketModel market;
    private EditText etMarketName, etMarketZone;
    private Button btnSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.marketBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);

        etMarketName = (EditText) findViewById(R.id.etMarketName);
        etMarketZone = (EditText) findViewById(R.id.etMarketZone);
        btnSave = (Button) findViewById(R.id.btnSave);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMarketName.getText().toString().trim().isEmpty()){
                    ShowMandatory();
                }else{
                    market = new MarketModel(etMarketName.getText().toString().trim(), etMarketZone.getText().toString().trim());
                    databaseHelper.createMarket(market);
                    //Toast.makeText(MarketAddActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MarketAddActivity.this,MarketActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void ShowMandatory(){
        Toast.makeText(this, R.string.nameMandatory, Toast.LENGTH_SHORT).show();
    }
}
