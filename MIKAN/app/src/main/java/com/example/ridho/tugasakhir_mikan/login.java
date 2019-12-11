package com.example.ridho.tugasakhir_mikan;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CardView mikro = (CardView)findViewById(R.id.login_mikro);
        CardView noncivitas =(CardView)findViewById(R.id.login_noncivitas);

        mikro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_mikro();
            }
        });
        noncivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_noncivitas();
            }
        });
    }

    private void click_noncivitas() {
        startActivity(new Intent(login.this,login_noncivitas.class));
        finish();
    }

    private void click_mikro() {
        startActivity(new Intent(login.this,login_mikroskil.class));
        finish();
    }
}
