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

public class MarketUpdateDeleteActivity extends Activity {

    private MarketModel market;
    private EditText etMarketName, etMarketZone;
    private Button btnUpdate, btnDelete;
    private DatabaseHelper databaseHelper;
    private ImageView ivExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_market_update_delete);
        this.setFinishOnTouchOutside(true);

        Intent intent = getIntent();
        market = (MarketModel) intent.getSerializableExtra("market");

        databaseHelper = new DatabaseHelper(this);


        etMarketName = (EditText) findViewById(R.id.etMarketName);
        etMarketZone = (EditText) findViewById(R.id.etMarketZone);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        ivExit = (ImageView) findViewById(R.id.ivExit);

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
                    Intent intent = new Intent(MarketUpdateDeleteActivity.this, MarketActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteMarket(market.getId());
                Intent intent = new Intent(MarketUpdateDeleteActivity.this, MarketActivity.class);
                startActivity(intent);
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
