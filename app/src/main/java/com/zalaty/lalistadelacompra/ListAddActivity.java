package com.zalaty.lalistadelacompra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.database.ProductAdapterSpinner;
import com.zalaty.lalistadelacompra.model.ListModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.List;

public class ListAddActivity extends AppCompatActivity {

    private ListModel list;
    private EditText etListNum;
    private Spinner spListProduct;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    List<ProductModel> lstProducts;
    private Button btnGoToList;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.listBackButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);

        etListNum = (EditText) findViewById(R.id.etListNum);
        spListProduct = (Spinner) findViewById(R.id.spListProduct);
        btnSave = (Button) findViewById(R.id.btnSave);

        loadSpinnerData();

        btnSave.setEnabled(lstProducts.size() > 1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer num = TextUtils.isEmpty(etListNum.getText().toString()) ? 0 : Integer.parseInt(etListNum.getText().toString());
                list = new ListModel(num, (int) ((ProductModel) spListProduct.getSelectedItem()).getId());
                databaseHelper.createList(list);
                //Toast.makeText(ListAddActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListAddActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private void loadSpinnerData(){
        lstProducts = databaseHelper.getAllProducts();
        lstProducts.add(0, new ProductModel(getString(R.string.select)));
        ProductAdapterSpinner productAdapterSpinner = new ProductAdapterSpinner(this,android.R.layout.simple_spinner_item, lstProducts);

        productAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spListProduct.setAdapter(productAdapterSpinner);
        spListProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Selected User: "+ lstMarkets.get(position).getId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
