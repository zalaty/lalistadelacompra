package com.zalaty.lalistadelacompra;

import android.app.Activity;
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
import android.view.Window;
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

public class ProductUpdateDeleteActivity extends Activity {

    private ProductModel product;
    private MarketModel market;
    private EditText etProductName, etProductDescription, etProductPrice;
    private Spinner spProductMarket;
    private Button btnUpdate, btnDelete;;
    private DatabaseHelper databaseHelper;
    private ImageView ivExit;
    List<MarketModel> lstMarkets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_update_delete);
        this.setFinishOnTouchOutside(true);

        Intent intent = getIntent();
        product = (ProductModel) intent.getSerializableExtra("product");

        databaseHelper = new DatabaseHelper(this);

        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductDescription = (EditText) findViewById(R.id.etProductDescription);
        etProductPrice = (EditText) findViewById(R.id.etProductPrice);
        spProductMarket = (Spinner) findViewById(R.id.spProductMarket);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        ivExit = (ImageView) findViewById(R.id.ivExit);

        loadSpinnerData();

        etProductName.setText(product.getName());
        etProductDescription.setText(product.getDescription());
        etProductPrice.setText(product.getPrice().toString());
        spProductMarket.setSelection(getPosition());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etProductName.getText().toString().trim().isEmpty()){
                    ShowMandatory();
                }else {
                    product.setName(etProductName.getText().toString().trim());
                    product.setDescription(etProductDescription.getText().toString().trim());
                    product.setPrice(Double.parseDouble(etProductPrice.getText().toString()));
                    product.setMarketId((int) ((MarketModel) spProductMarket.getSelectedItem()).getId());
                    databaseHelper.updateProduct(product);
                    Intent intent = new Intent(ProductUpdateDeleteActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteProduct(product.getId());
                Intent intent = new Intent(ProductUpdateDeleteActivity.this, MainActivity.class);
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

    private void loadSpinnerData(){
        lstMarkets = databaseHelper.getAllMarkets();
        lstMarkets.add(0, new MarketModel(getString(R.string.select)));
        MarketAdapterSpinner marketAdapterSpinner = new MarketAdapterSpinner(this,android.R.layout.simple_spinner_dropdown_item, lstMarkets);
        marketAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProductMarket.setAdapter(marketAdapterSpinner);
    }

    private Integer getPosition(){
        market = databaseHelper.getMarKet(product.getMarketId());
        Integer position = 1;
        for (int i = 0; i < lstMarkets.size(); i++){
            if(lstMarkets.get(i).getId() == (market.getId())){
                position = i;
                break;
            }
        }
        return position;
    }

}
