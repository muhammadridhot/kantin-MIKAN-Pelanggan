package com.example.ridho.tugasakhir_mikan;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private  static int splashScreen = 1000;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null) {
                    startActivity(new Intent(MainActivity.this, Home.class));
                    finish();
                }else{
                    startActivity(new Intent(MainActivity.this, slide_intro.class));
                    finish();
                }
            }
        },splashScreen);

    }
}
