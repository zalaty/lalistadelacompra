package com.zalaty.lalistadelacompra;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zalaty.lalistadelacompra.database.DatabaseHelper;
import com.zalaty.lalistadelacompra.database.ListAdapter;
import com.zalaty.lalistadelacompra.database.ProductAdapter;
import com.zalaty.lalistadelacompra.model.ListModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnMarket, btnProduct;
    Intent intent;
    private Button btnAdd;
    private ArrayList<ListModel> listModelArrayList;
    private DatabaseHelper databaseHelper;
    private ListView listView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMarket = (Button) findViewById(R.id.btnMarket);
        btnProduct = (Button) findViewById(R.id.btnProduct);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        listView = (ListView) findViewById(R.id.lvList);

        databaseHelper = new DatabaseHelper(this);

        LoadList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
/*                Intent intent = new Intent(ProductActivity.this, ProductUpdateDeleteActivity.class);
                intent.putExtra("product", (Serializable) productModelArrayList.get(position));
                startActivity(intent);*/

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(R.string.deleteProduct)
                        .setMessage(R.string.areyousure)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
/*                                databaseHelper.deleteProduct(product.getId());
                                Intent intent = new Intent(ProductUpdateDeleteActivity.this,ProductActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/

                                databaseHelper.deleteList((listModelArrayList.get(position)).getId());
                                LoadList();

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

        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketActivity();
            }
        });

        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHelper.getAllProducts().size() > 0){
                    Intent intent = new Intent(MainActivity.this, ListAddActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.nohayproductos);
                    builder.setMessage(R.string.debeanadirproductos);
                    //builder.setIcon(R.drawable.)
                    builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
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
                //MainActivity();
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

    private void LoadList(){
        databaseHelper.deleteList(2);
        databaseHelper.deleteList(3);
        databaseHelper.deleteList(4);
        databaseHelper.deleteList(5);

        listModelArrayList = databaseHelper.getAllList();
        listAdapter = new ListAdapter(this, listModelArrayList);
        listView.setAdapter(listAdapter);
    }
}