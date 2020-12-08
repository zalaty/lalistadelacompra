package com.zalaty.lalistadelacompra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.adapter.MarketAdapterSpinner;
import com.zalaty.lalistadelacompra.model.MarketModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.List;

public class ProductAddActivity extends Activity {

    private ProductModel product;
    private EditText etProductName, etProductDescription, etProductPrice;
    private Spinner spProductMarket;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    List<MarketModel> lstMarkets;
    private ImageView ivExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_add);
        this.setFinishOnTouchOutside(true);

        databaseHelper = new DatabaseHelper(this);

        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductDescription = (EditText) findViewById(R.id.etProductDescription);
        etProductPrice = (EditText) findViewById(R.id.etProductPrice);
        spProductMarket = (Spinner) findViewById(R.id.spProductMarket);
        btnSave = (Button) findViewById(R.id.btnSave);
        ivExit = (ImageView) findViewById(R.id.ivExit);

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
                    Intent intent = new Intent(ProductAddActivity.this, MainActivity.class);
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
