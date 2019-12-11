package com.example.ridho.tugasakhir_mikan;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class topup extends AppCompatActivity {

    CardView kantin,atm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);

        kantin = (CardView)findViewById(R.id.cardKantin);
        atm = (CardView)findViewById(R.id.cardATM);

        kantin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(topup.this,topUp_kantin.class));
                Toast.makeText(topup.this,"Masih dalam tahap pengembangan",Toast.LENGTH_SHORT).show();
            }
        });
        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(topup.this,topUp_atm.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
