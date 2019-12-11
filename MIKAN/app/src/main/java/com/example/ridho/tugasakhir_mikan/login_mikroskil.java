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

public class login_mikroskil extends AppCompatActivity {

    EditText txtIdMiso,txtPass;
    Button btnLogin;
    private FirebaseAuth authLoginMikro;
    private DatabaseReference refLoginMikro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mikroskil);

        txtIdMiso = (EditText)findViewById(R.id.txtIdMiso);
        txtPass = (EditText)findViewById(R.id.txtPassMikro);
        btnLogin = (Button)findViewById(R.id.btnLoginMikro);

        authLoginMikro = FirebaseAuth.getInstance();
        refLoginMikro= FirebaseDatabase.getInstance().getReference("Users/Mikroskil");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_loginMikro();
            }
        });


    }

    private void click_loginMikro() {
        final String idMiso = txtIdMiso.getText().toString().trim();
        final String pass = txtPass.getText().toString();

        if (TextUtils.isEmpty(idMiso) || TextUtils.isEmpty(pass)){
            txtIdMiso.setError("Required");
            txtPass.setError("Required");
        }else{
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Please wait......");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();

            Query data = refLoginMikro.orderByKey().equalTo(idMiso);

            data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(idMiso).exists()){
                        Users users = dataSnapshot.child(idMiso).getValue(Users.class);
                        if(!Boolean.parseBoolean(users.getIsPartner())){
                            String email = users.getEmail();
                            authLoginMikro.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        //Token
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                            @Override
                                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                                String newToken = instanceIdResult.getToken();
                                                Log.d("newToken",newToken);
                                                updateToken(newToken);
                                            }
                                        });

                                        startActivity(new Intent(login_mikroskil.this,Home.class));
                                        pd.dismiss();
                                        finish();
                                    }else{
                                        Toast.makeText(login_mikroskil.this,"Password salah",Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(login_mikroskil.this,"Login dengan akun pemesan",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }else{
                        Toast.makeText(login_mikroskil.this,"Email tidak terdaftar",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateToken(String token) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token data = new Token(token, false);//False karena client/user
        reference.child(user.getUid()).setValue(data);
    }
}
