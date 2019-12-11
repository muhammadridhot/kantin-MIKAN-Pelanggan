package com.example.ridho.tugasakhir_mikan;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.andremion.counterfab.CounterFab;
import com.example.ridho.tugasakhir_mikan.Adapter.menu_adapter;
import com.example.ridho.tugasakhir_mikan.Database.DatabaseOrder;
import com.example.ridho.tugasakhir_mikan.Model.menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class daftar_menu extends AppCompatActivity {

    private DatabaseReference refMenu;
    RecyclerView view;
    CounterFab btnCart;
    String idStan,namaStan,kantin;
    public static int testing=0;
    ArrayList<menu> list;
    menu_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_menu);

        btnCart = (CounterFab)findViewById(R.id.floatCart);
        btnCart.setCount(testing);

        refMenu = FirebaseDatabase.getInstance().getReference("Menu");

        view = (RecyclerView)findViewById(R.id.viewmenu);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        Intent intent = getIntent();
        idStan = intent.getStringExtra("IDSTAN");
        namaStan = intent.getStringExtra("NAMASTAN");
        kantin = intent.getStringExtra("KANTIN");
        adapter = new menu_adapter(daftar_menu.this,list);
        view.setAdapter(adapter);

        getSupportActionBar().setTitle(namaStan);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int test = new DatabaseOrder(daftar_menu.this).jumlahOrder();
                if (test != 0){
                    Intent cart = new Intent(daftar_menu.this,cart.class);
                    cart.putExtra("IDSTAN",idStan);
                    cart.putExtra("KANTIN",kantin);
                    cart.putExtra("NAMASTAN",namaStan);
                    startActivity(cart);
                }
            }
        });

        //loadData(idStan);
        if (idStan != null){
            loadData(idStan);
        }else{
            Intent intent1 = getIntent();
            idStan = intent1.getStringExtra("IDSTAN");
            namaStan = intent1.getStringExtra("NAMASTAN");
            kantin = intent1.getStringExtra("KANTIN");
            if (idStan != null) {
                loadData(idStan);
            }
        }
    }

    private void loadData(final String idStan) {
        refMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data:dataSnapshot.child(idStan).getChildren()){
                    menu datamenu = data.getValue(menu.class);
                    list.add(datamenu);
                }
                adapter = new menu_adapter(daftar_menu.this,list);
                view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        ArrayList<Orders> carts = new DatabaseOrder(this).getCart();
////        cart_adapter adapter = new cart_adapter(this,carts);
//
//        int total = 0;
//        for (Orders orders:carts){
//            total+=orders.getHarga()*orders.getJumlah();
//        }
//        if (total==0){
//            finish();
//        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        new DatabaseOrder(daftar_menu.this).clearCart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
