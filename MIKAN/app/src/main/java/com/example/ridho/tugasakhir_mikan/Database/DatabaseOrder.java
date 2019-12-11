package com.example.ridho.tugasakhir_mikan.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;


import com.example.ridho.tugasakhir_mikan.Model.Orders;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DatabaseOrder extends SQLiteAssetHelper {

    private  static final String DB_NAME="OrdersDb.db";
    private static final int DB_VER =1;
    public DatabaseOrder(Context context) {
        super(context,DB_NAME,null,DB_VER);
    }

    public ArrayList<Orders> getCart(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"IdOrder","IdUser","Nama","Harga","Jumlah"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final ArrayList<Orders> result = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                result.add(new Orders(
                        c.getString(c.getColumnIndex("IdOrder")),
                        c.getString(c.getColumnIndex("IdUser")),
                        c.getString(c.getColumnIndex("Nama")),
                        c.getInt(c.getColumnIndex("Harga")),
                        c.getInt(c.getColumnIndex("Jumlah"))));
            }while (c.moveToNext());
            db.close();
        }
        return result;
    }

    public void addToCart(Orders orders){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(IdOrder,IdUser,Nama,Harga,Jumlah) VALUES('%s','%s','%s','%s','%s');",
                orders.getIdOrder(),
                orders.getIdUser(),
                orders.getNama(),
                orders.getHarga(),
                orders.getJumlah(),
                orders.getHarga()*orders.getJumlah());
        db.execSQL(query);
        Log.d("Jumlah total",String.valueOf(orders.getHarga()*orders.getJumlah()));
        db.close();
    }

    public void clearCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM OrderDetail";
        db.execSQL(query);
        db.close();
    }

    public void clearCart_idOrder(String idOrder){
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM OrderDetail WHERE IdOrder" + "=" + "'" + idOrder + "'";
        db.execSQL(query);
        db.close();
    }

    public void updateCartJlh(String idOrder,Integer jumlah){
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE OrderDetail SET Jumlah=" + "'" + jumlah + "'" + " WHERE IdOrder" + "=" + "'" + idOrder+ "'";
        db.execSQL(query);
        db.close();
    }

    public int jumlahOrder(){
        int jumlah = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT SUM(Jumlah) FROM OrderDetail";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                jumlah= cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        db.close();
        return jumlah;
    }

   /* public int totalHarga(){
        int total = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT SUM(Total) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                total = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return total;
    }

    public void updateTotal(String idOrder,Integer total){
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE OrderDetail SET Total=" + "'" + total + "'" + " WHERE IdOrder" + "=" + "'" + idOrder+ "'";
        db.execSQL(query);
    }*/
}
