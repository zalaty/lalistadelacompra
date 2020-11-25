package com.zalaty.lalistadelacompra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.database.MarketAdapterSpinner;
import com.zalaty.lalistadelacompra.model.MarketModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.List;

public class ProductAddActivity extends AppCompatActivity {

    private ProductModel product;
    private EditText etProductName, etProductDescription, etProductPrice;
    private Spinner spProductMarket;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    List<MarketModel> lstMarkets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.productBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);

        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductDescription = (EditText) findViewById(R.id.etProductDescription);
        etProductPrice = (EditText) findViewById(R.id.etProductPrice);
        spProductMarket = (Spinner) findViewById(R.id.spProductMarket);
        btnSave = (Button) findViewById(R.id.btnSave);

        loadSpinnerData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etProductName.getText().toString().trim().isEmpty()){
                    ShowMandatory();
                }else {
                    Double price = TextUtils.isEmpty(etProductPrice.getText().toString()) ? 0 : Double.parseDouble(etProductPrice.getText().toString()) ;
                    product = new ProductModel(etProductName.getText().toString().trim(), etProductDescription.getText().toString().trim(), price, (int) ((MarketModel) spProductMarket.getSelectedItem()).getId());
                    databaseHelper.createProduct(product);
                    //Toast.makeText(ProductAddActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductAddActivity.this, ProductActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadSpinnerData(){
        lstMarkets = databaseHelper.getAllMarkets();
        lstMarkets.add(0, new MarketModel(getString(R.string.select)));
        MarketAdapterSpinner marketAdapterSpinner = new MarketAdapterSpinner(this,android.R.layout.simple_spinner_item, lstMarkets);

        marketAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spProductMarket.setAdapter(marketAdapterSpinner);
        spProductMarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Selected User: "+ lstMarkets.get(position).getId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void ShowMandatory(){
        Toast.makeText(this, R.string.nameMandatory, Toast.LENGTH_SHORT).show();
    }
}
