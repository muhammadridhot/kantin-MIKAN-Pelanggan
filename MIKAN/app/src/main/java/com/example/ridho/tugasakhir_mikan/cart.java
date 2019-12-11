package com.example.ridho.tugasakhir_mikan;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ridho.tugasakhir_mikan.Adapter.cart_adapter;
import com.example.ridho.tugasakhir_mikan.Database.DatabaseOrder;
import com.example.ridho.tugasakhir_mikan.Model.Orders;

import java.util.ArrayList;

public class cart extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<Orders> carts = new ArrayList<>();
    TextView totalharga;
    Button lanjutkan,metodePembayaran;
    cart_adapter adapter;
    String idStan,namaStan,kantin,idUser;
    int total = 0;
    AlertDialog alertDialog;
    CharSequence[] values = {"Dompet Digital","Tunai"};
    int pilihan=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final Intent intent = getIntent();
        idStan = intent.getStringExtra("IDSTAN");
        namaStan = intent.getStringExtra("NAMASTAN");
        kantin = intent.getStringExtra("KANTIN");

        lanjutkan = (Button)findViewById(R.id.btnLanjutkanPembayaran);
        totalharga = (TextView)findViewById(R.id.txttotal);
        recyclerView= (RecyclerView)findViewById(R.id.tampilorder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(cart.this,meja_makan.class);
                intent1.putExtra("IDSTAN",idStan);
                intent1.putExtra("KANTIN",kantin);
                intent1.putExtra("NAMASTAN",namaStan);
                intent1.putExtra("TOTALHARGA",total);
                intent1.putExtra("METODE_PEMBAYARAN",metodePembayaran.getText().toString());
                startActivity(intent1);
            }
        });

        metodePembayaran = (Button)findViewById(R.id.metodePembayaran);
        metodePembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(cart.this);
                builder.setTitle("Pilih metode pembayaran");
                builder.setSingleChoiceItems(values, pilihan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                metodePembayaran.setText("Dompet Digital");
                               pilihan=0;
                                break;
                            case 1:
                                metodePembayaran.setText("Tunai");
                                pilihan=1;
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();


            }
        });
        loaddata();


    }

    private void loaddata() {
        carts = new DatabaseOrder(this).getCart();
        adapter = new cart_adapter(this,carts);
        recyclerView.setAdapter(adapter);


        for (Orders orders:carts){
            total+=orders.getHarga()*orders.getJumlah();
        }
        totalharga.setText("Rp. "+String.valueOf(total));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Sedangpause"," Ture");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Sedangrestart"," Ture");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Sedangresume"," Ture");
    }
}
