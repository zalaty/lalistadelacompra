package com.zalaty.lalistadelacompra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.adapter.ProductAdapterSpinner;
import com.zalaty.lalistadelacompra.model.ListModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.List;

public class ListAddActivity extends Activity {

    private ListModel list;
    private EditText etListNum;
    private Spinner spListProduct;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    List<ProductModel> lstProducts;
    private ImageView ivExit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_add);
        this.setFinishOnTouchOutside(true);

        databaseHelper = new DatabaseHelper(this);

        etListNum = (EditText) findViewById(R.id.etListNum);
        spListProduct = (Spinner) findViewById(R.id.spListProduct);
        btnSave = (Button) findViewById(R.id.btnSave);
        ivExit = (ImageView) findViewById(R.id.ivExit);

        loadSpinnerData();

        btnSave.setEnabled(lstProducts.size() > 1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ProductModel)spListProduct.getSelectedItem()).getId() == 0){
                    ShowMandatory();
                }else {

                    Integer num = TextUtils.isEmpty(etListNum.getText().toString()) ? 0 : Integer.parseInt(etListNum.getText().toString());
                    list = new ListModel(num, (int) ((ProductModel) spListProduct.getSelectedItem()).getId());
                    databaseHelper.createList(list);
                    Intent intent = new Intent(ListAddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private void ShowMandatory(){
        Toast.makeText(this, R.string.productMandatory, Toast.LENGTH_SHORT).show();
    }
}
