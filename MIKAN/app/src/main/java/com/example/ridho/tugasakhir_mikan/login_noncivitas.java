package com.example.ridho.tugasakhir_mikan;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridho.tugasakhir_mikan.Model.Token;
import com.example.ridho.tugasakhir_mikan.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class login_noncivitas extends AppCompatActivity {

    TextView txtRegister;
    EditText txtEmail,txtPass;
    Button btnLoginNonCivitas;
    private FirebaseAuth authLoginNonCivitas;
    private DatabaseReference refLoginNonCivitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_noncivitas);

        txtRegister = (TextView)findViewById(R.id.txtregister);
        txtEmail = (EditText)findViewById(R.id.txtMail);
        txtPass = (EditText)findViewById(R.id.txtPass);
        btnLoginNonCivitas = (Button)findViewById(R.id.btnLogin);


        authLoginNonCivitas = FirebaseAuth.getInstance();
        refLoginNonCivitas = FirebaseDatabase.getInstance().getReference("Users/NonCivitas");

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_noncivitas.this,register.class));
                finish();
            }
        });

        btnLoginNonCivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_loginNonCivitas();
            }
        });
    }

    private void click_loginNonCivitas() {
        final String email = txtEmail.getText().toString().trim();
        final String pass = txtPass.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            if (TextUtils.isEmpty(email)){
                txtEmail.setError("Required");
            }
           if (TextUtils.isEmpty(pass)){
               txtPass.setError("Required");
           }

        }else{

            if (email.contains(".") && email.contains("@")){
                final String emailReg = email.substring(0,(email.indexOf(".")));
                final ProgressDialog pd = new ProgressDialog(login_noncivitas.this);
                pd.setMessage("Please wait......");
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.show();
                Query data = refLoginNonCivitas.orderByKey().equalTo(emailReg);

                data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(emailReg).exists()){
                            final Users users = dataSnapshot.child(emailReg).getValue(Users.class);
                            if (!Boolean.parseBoolean(users.getIsPartner())){
                                authLoginNonCivitas.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                                @Override
                                                public void onSuccess(InstanceIdResult instanceIdResult) {
                                                    String newToken = instanceIdResult.getToken();
                                                    Log.d("newToken",newToken);
                                                    updateToken(newToken);
                                                }
                                            });
                                            startActivity(new Intent(login_noncivitas.this,Home.class));
                                            pd.dismiss();
                                            finish();
                                        }else{
                                            Toast.makeText(login_noncivitas.this,"Password salah",Toast.LENGTH_SHORT).show();
                                            pd.dismiss();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(login_noncivitas.this,"Login dengan email users",Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }else{
                            Toast.makeText(login_noncivitas.this,"Email tidak terdaftar",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else{
               Toast.makeText(login_noncivitas.this,"Masukkan format email yang benar",Toast.LENGTH_SHORT).show();
            }


//            authLoginNonCivitas.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        Query data = refLoginNonCivitas.orderByKey().equalTo(user.getUid());
//                        data.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                String status="Penyewa";
////                                if (status.equals("Penyewa")){
////                                    FirebaseAuth.getInstance().signOut();
////                                    Toast.makeText(login_noncivitas.this,"Login dengan akun pelanggan",Toast.LENGTH_SHORT).show();
////                                    pd.dismiss();
////                                }
//                                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                                    Users users = snapshot.getValue(Users.class);
//                                    if (!Boolean.parseBoolean(users.getIsPartner())){
//                                        status = "Pelanggan";
//                                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                                            @Override
//                                            public void onSuccess(InstanceIdResult instanceIdResult) {
//                                                String newToken = instanceIdResult.getToken();
//                                                Log.d("newToken",newToken);
//                                                updateToken(newToken);
//                                            }
//                                        });
//                                        startActivity(new Intent(login_noncivitas.this,Home.class));
//                                        pd.dismiss();
//                                        finish();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }else {
//                        Toast.makeText(login_noncivitas.this,"Password salah",Toast.LENGTH_SHORT).show();
//                        Log.d("Password ","anda salah");
//                        pd.dismiss();
//                    }
//                }
//            });
//            refLoginNonCivitas.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                        Users users = snapshot.getValue(Users.class);
//                        if (users.getEmail().equals(email)){
//                            if (!Boolean.parseBoolean(users.getIsPartner())){
//                                authLoginNonCivitas.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()){
//                                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                                                @Override
//                                                public void onSuccess(InstanceIdResult instanceIdResult) {
//                                                    String newToken = instanceIdResult.getToken();
//                                                    Log.d("newToken",newToken);
//                                                    updateToken(newToken);
//                                                }
//                                            });
//                                            startActivity(new Intent(login_noncivitas.this,Home.class));
//                                            pd.dismiss();
//                                            finish();
//                                        }else{
//                                                Toast.makeText(login_noncivitas.this,"Password salah",Toast.LENGTH_SHORT).show();
//                                                Log.d("Password ","anda salah");
//                                                pd.dismiss();
//
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
////                    Log.d("Error ","terjadi error");
//                };
//            });

        }
    }

    private void updateToken(String token) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token data = new Token(token, false);//False karena client/user
        reference.child(user.getUid()).setValue(data);
    }
}
