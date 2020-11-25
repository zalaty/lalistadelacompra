package com.zalaty.lalistadelacompra.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zalaty.lalistadelacompra.model.ListModel;
import com.zalaty.lalistadelacompra.model.MarketModel;
import com.zalaty.lalistadelacompra.model.ProductModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 15;

    // Database Name
    private static final String DATABASE_NAME = "laListaDeLaCompraManager";

    // Table Names
    private static final String TABLE_MARKET = "market";
    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_LIST = "list";

    private static final String KEY_MARKET_ID = "id";
    private static final String KEY_MARKET_NAME = "name";
    private static final String KEY_MARKET_ZONE = "zone";
    private static final String KEY_PRODUCT_ID = "id";
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_PRODUCT_DESCRIPTION = "description";
    private static final String KEY_PRODUCT_PRICE = "price";
    private static final String KEY_PRODUCT_MARKETID = "marketid";
    private static final String KEY_LIST_ID = "id";
    private static final String KEY_LIST_PRODUCTID = "productid";
    private static final String KEY_LIST_NUM = "num";



    private static final String CREATE_TABLE_MARKET = "CREATE TABLE "
            + TABLE_MARKET + "(" + KEY_MARKET_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MARKET_NAME + " TEXT, " + KEY_MARKET_ZONE + " TEXT );";


    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
            + TABLE_PRODUCT + "(" + KEY_PRODUCT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT_NAME + " TEXT, " + KEY_PRODUCT_DESCRIPTION + " TEXT, " +  KEY_PRODUCT_PRICE + " DOUBLE, "
            + KEY_PRODUCT_MARKETID + " INTEGER, FOREIGN KEY (" + KEY_PRODUCT_MARKETID + ") REFERENCES " + TABLE_MARKET  + " (" + KEY_MARKET_ID + "));";


    private static final String CREATE_TABLE_LIST = "CREATE TABLE "
            + TABLE_LIST + "(" + KEY_LIST_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_LIST_NUM + " INTEGER, "
            + KEY_LIST_PRODUCTID + " INTEGER, FOREIGN KEY (" + KEY_LIST_PRODUCTID + ") REFERENCES " + TABLE_PRODUCT  + " (" + KEY_PRODUCT_ID + "));";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_MARKET);
        Log.d("table", CREATE_TABLE_PRODUCT);
        Log.d("table", CREATE_TABLE_LIST);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MARKET);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_LIST);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);

        onCreate(db);
    }

    // ------------------------ "market" table methods ----------------//
    public long createMarket(MarketModel market) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MARKET_NAME, market.getName());
        values.put(KEY_MARKET_ZONE, market.getZone());

        long todo_id = db.insert(TABLE_MARKET, null, values);

        return todo_id;
    }

    public MarketModel getMarKet(long market_id) {
        MarketModel mk = new MarketModel();
        if (!existMarket(market_id)){
            return mk;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_MARKET + " WHERE "
                + KEY_MARKET_ID + " = " + market_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        mk.setId(c.getInt(c.getColumnIndex(KEY_MARKET_ID)));
        mk.setName((c.getString(c.getColumnIndex(KEY_MARKET_NAME))));
        mk.setZone(c.getString(c.getColumnIndex(KEY_MARKET_ZONE)));

        return mk;
    }

    public boolean existMarket(long market_id){
        String countQuery = "SELECT  * FROM " + TABLE_MARKET + " WHERE ID = " + market_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(countQuery, null);

        if (c.getCount() > 0){
            return true;
        }
        return false;
    }

    public ArrayList<MarketModel> getAllMarkets() {
        ArrayList<MarketModel> markets = new ArrayList<MarketModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_MARKET;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MarketModel mk = new MarketModel();
                mk.setId(c.getInt(c.getColumnIndex(KEY_MARKET_ID)));
                mk.setName((c.getString(c.getColumnIndex(KEY_MARKET_NAME))));
                mk.setZone(c.getString(c.getColumnIndex(KEY_MARKET_ZONE)));

                // adding to todo list
                markets.add(mk);
            } while (c.moveToNext());
        }

        return markets;
    }

    public int getMarketCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MARKET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updateMarket(MarketModel market) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MARKET_NAME, market.getName());
        values.put(KEY_MARKET_ZONE, market.getZone());

        // updating row
        return db.update(TABLE_MARKET, values, KEY_MARKET_ID + " = ?",
                new String[] { String.valueOf(market.getId()) });
    }

    public void deleteMarket(long market_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_MARKETID, 0);
        db.update(TABLE_PRODUCT, values, KEY_PRODUCT_MARKETID + " =  ?",
                new String[] { String.valueOf(market_id) });


        db.delete(TABLE_MARKET, KEY_MARKET_ID + " = ?",
                new String[] { String.valueOf(market_id) });
    }


    // ------------------------ "product" table methods ----------------//
    public long createProduct(ProductModel product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, product.getName());
        values.put(KEY_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(KEY_PRODUCT_PRICE, product.getPrice());
        values.put(KEY_PRODUCT_MARKETID, product.getMarketId());

        long todo_id = db.insert(TABLE_PRODUCT, null, values);

        return todo_id;
    }

    public ProductModel getProduct(long product_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE "
                + KEY_PRODUCT_ID + " = " + product_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ProductModel p = new ProductModel();
        p.setId(c.getInt(c.getColumnIndex(KEY_PRODUCT_ID)));
        p.setName((c.getString(c.getColumnIndex(KEY_PRODUCT_NAME))));
        p.setDescription(c.getString(c.getColumnIndex(KEY_PRODUCT_DESCRIPTION)));
        p.setPrice((c.getDouble(c.getColumnIndex(KEY_PRODUCT_PRICE))));
        p.setMarketId(c.getInt(c.getColumnIndex(KEY_PRODUCT_MARKETID)));

        return p;
    }

    public boolean existProduct(long product_id){
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE ID = " + product_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(countQuery, null);

        if (c.getCount() > 0){
            return true;
        }
        return false;
    }

    public ArrayList<ProductModel> getAllProducts() {
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ProductModel p = new ProductModel();
                p.setId(c.getInt(c.getColumnIndex(KEY_PRODUCT_ID)));
                p.setName((c.getString(c.getColumnIndex(KEY_PRODUCT_NAME))));
                p.setDescription(c.getString(c.getColumnIndex(KEY_PRODUCT_DESCRIPTION)));
                p.setPrice(c.getDouble(c.getColumnIndex(KEY_PRODUCT_PRICE)));
                p.setMarketId(c.getInt(c.getColumnIndex(KEY_PRODUCT_MARKETID)));

                // adding to todo list
                products.add(p);
            } while (c.moveToNext());
        }

        return products;
    }

    public int getProductCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updateProduct(ProductModel product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, product.getName());
        values.put(KEY_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(KEY_PRODUCT_PRICE, product.getPrice());
        values.put(KEY_PRODUCT_MARKETID, product.getMarketId());

        // updating row
        return db.update(TABLE_PRODUCT, values, KEY_PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
    }

    public void deleteProduct(long product_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_LIST, KEY_LIST_PRODUCTID + " = ?",
                new String[] { String.valueOf(product_id) });

        db.delete(TABLE_PRODUCT, KEY_PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product_id) });
    }


    // ------------------------ "list" table methods ----------------//
    public long createList(ListModel list) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Integer num = list.getNum();
        Integer productId = list.getProductId();

        values.put(KEY_LIST_NUM, num);
        values.put(KEY_LIST_PRODUCTID, productId);

        long todo_id = db.insert(TABLE_LIST, null, values);

        return todo_id;
    }

    public ListModel getList(long list_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_LIST + " WHERE "
                + KEY_LIST_ID + " = " + list_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ListModel ls = new ListModel();
        ls.setId(c.getInt(c.getColumnIndex(KEY_LIST_ID)));
        ls.setProductIdId((c.getInt(c.getColumnIndex(KEY_LIST_PRODUCTID))));
        ls.setNum(c.getInt(c.getColumnIndex(KEY_LIST_NUM)));

        return ls;
    }

    public ArrayList<ListModel> getAllList() {
        ArrayList<ListModel> lists = new ArrayList<ListModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_LIST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ListModel ls = new ListModel();
                ls.setId(c.getInt(c.getColumnIndex(KEY_LIST_ID)));
                ls.setProductIdId(c.getInt(c.getColumnIndex(KEY_LIST_PRODUCTID)));
                ls.setNum(c.getInt(c.getColumnIndex(KEY_LIST_NUM)));

                // adding to todo list
                lists.add(ls);
            } while (c.moveToNext());
        }

        return lists;
    }

    public int updateProduct(ListModel ls) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LIST_NUM, ls.getNum());

        // updating row
        return db.update(TABLE_LIST, values, KEY_LIST_ID + " = ?",
                new String[] { String.valueOf(ls.getId()) });
    }

    public void deleteList(long list_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIST, KEY_LIST_ID + " = ?",
                new String[] { String.valueOf(list_id) });
    }

}
