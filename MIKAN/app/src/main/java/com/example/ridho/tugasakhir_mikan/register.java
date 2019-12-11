package com.example.ridho.tugasakhir_mikan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ridho.tugasakhir_mikan.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {


    Button btnRegister;
    EditText txtEmail,txtNama,txtPass;
    private FirebaseAuth authRegister;
    private DatabaseReference refRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtEmail = (EditText)findViewById(R.id.txtEmailRegister);
        txtNama = (EditText)findViewById(R.id.txtnamaRegister);
        txtPass = (EditText)findViewById(R.id.txtPassRegister);

        authRegister = FirebaseAuth.getInstance();
        refRegister = FirebaseDatabase.getInstance().getReference("Users/NonCivitas");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_register();
            }
        });
    }

    private void click_register() {
        final String idMiso = "";
        final String email = txtEmail.getText().toString().trim();
        final String nama = txtNama.getText().toString();
        final String pass = txtPass.getText().toString();
        final String image = "https://firebasestorage.googleapis.com/v0/b/kantin-online-mikroskil-9ee2c.appspot.com/o/logo_mikan.jpg?alt=media&token=2a1c3c85-1972-4d3a-8902-61a7575f4203";

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(pass)){
                if (TextUtils.isEmpty(email)){
                    txtEmail.setError("Required");
                }
                if (TextUtils.isEmpty(pass)){
                    txtPass.setError("Required");
                }
                if (TextUtils.isEmpty(nama)){
                    txtNama.setError("Required");
                }
        }else{
            final String emailReg = email.substring(0,email.indexOf("."));

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Please wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            authRegister.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = authRegister.getCurrentUser();
                        if (firebaseUser != null){
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nama)
                                    .setPhotoUri(Uri.parse(image)).build();

                            firebaseUser.updateProfile(profileChangeRequest);

                            String userid = firebaseUser.getUid();
                            Users users = new Users(userid,idMiso,pass,nama,email,image);
                            refRegister.child(emailReg).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(register.this,"Register berhasil",Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(register.this, login_noncivitas.class));
                                    finish();
                                    pd.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(register.this,"Register gagal,email sudah terdaftar",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                }
            });
        }
    }
}
