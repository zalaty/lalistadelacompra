package com.zalaty.lalistadelacompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.database.MarketAdapterSpinner;
import com.zalaty.lalistadelacompra.model.MarketModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductUpdateDeleteActivity extends AppCompatActivity {

    private ProductModel product;
    private MarketModel market;
    private EditText etProductName, etProductDescription, etProductPrice;
    private Spinner spProductMarket;
    private Button btnUpdate, btnDelete;;
    private DatabaseHelper databaseHelper;
    List<MarketModel> lstMarkets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update_delete);

        Toolbar toolbar = (Toolbar) findViewById(R.id.productBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        product = (ProductModel) intent.getSerializableExtra("product");

        databaseHelper = new DatabaseHelper(this);

        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductDescription = (EditText) findViewById(R.id.etProductDescription);
        etProductPrice = (EditText) findViewById(R.id.etProductPrice);
        spProductMarket = (Spinner) findViewById(R.id.spProductMarket);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        loadSpinnerData();

        etProductName.setText(product.getName());
        etProductDescription.setText(product.getDescription());
        etProductPrice.setText(product.getPrice().toString());
        spProductMarket.setSelection(getPosition());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product.setName(etProductName.getText().toString().trim());
                product.setDescription(etProductDescription.getText().toString().trim());
                product.setPrice(Double.parseDouble(etProductPrice.getText().toString()));
                product.setMarketId((int) ((MarketModel) spProductMarket.getSelectedItem()).getId());
                databaseHelper.updateProduct(product);
                Intent intent = new Intent(ProductUpdateDeleteActivity.this,ProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductUpdateDeleteActivity.this);

                builder.setTitle(R.string.deleteProduct)
                        .setMessage(R.string.areyousure)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.deleteProduct(product.getId());
                                Intent intent = new Intent(ProductUpdateDeleteActivity.this,ProductActivity.class);
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
