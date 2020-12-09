package com.zalaty.lalistadelacompra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.model.MarketModel;

public class MarketAddActivity extends Activity {

    private MarketModel market;
    private EditText etMarketName, etMarketZone;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private ImageView ivExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_market_add);
        this.setFinishOnTouchOutside(true);

        databaseHelper = new DatabaseHelper(this);

        etMarketName = (EditText) findViewById(R.id.etMarketName);
        etMarketZone = (EditText) findViewById(R.id.etMarketZone);
        btnSave = (Button) findViewById(R.id.btnSave);
        ivExit = (ImageView) findViewById(R.id.ivExit);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMarketName.getText().toString().trim().isEmpty()){
                    ShowMandatory();
                }else{
                    market = new MarketModel(etMarketName.getText().toString().trim(), etMarketZone.getText().toString().trim());
                    databaseHelper.createMarket(market);
                    Intent intent = new Intent(MarketAddActivity.this, MarketActivity.class);
                    startActivity(intent);
                }
            }
        });

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void ShowMandatory(){
        Toast.makeText(this, R.string.nameMandatory, Toast.LENGTH_SHORT).show();
    }
}
