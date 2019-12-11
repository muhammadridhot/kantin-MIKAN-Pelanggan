package com.example.ridho.tugasakhir_mikan.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ridho.tugasakhir_mikan.Model.Users;
import com.example.ridho.tugasakhir_mikan.R;
import com.example.ridho.tugasakhir_mikan.login;
import com.example.ridho.tugasakhir_mikan.pengaturan;
import com.example.ridho.tugasakhir_mikan.topup;
import com.example.ridho.tugasakhir_mikan.laporan_transaksi_dompet_digital;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private Button topUp,btnUpdateProfile,btnKeluar,btnHistoryTopUp;
    private ImageButton setting;
    private ImageView imageView;
    private TextView nama,saldo;
    private FirebaseUser user;
    private DatabaseReference refUser;
//    private ArrayList<laporan_saldo_dompet_digital> lst;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        imageView = (CircleImageView)view.findViewById(R.id.profile_image);
        nama = (TextView)view.findViewById(R.id.username);
        saldo = (TextView)view.findViewById(R.id.txtsaldouser);
        topUp = (Button)view.findViewById(R.id.btnTopUp);
        btnKeluar = (Button)view.findViewById(R.id.btnKeluar);
        btnUpdateProfile = (Button)view.findViewById(R.id.btnUpdateProfile);
        btnHistoryTopUp = (Button)view.findViewById(R.id.btnHistoryTopUp);

        btnHistoryTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), laporan_transaksi_dompet_digital.class));
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickUpdate();
            }
        });
        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity(),R.style.AlertDialogDelete);
                myAlert.setTitle("Keluar");
                myAlert.setMessage("Apakah kamu yakin ingin keluar ?");
                myAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        click_keluar();
                    }
                });
                myAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                myAlert.show().getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
//        lst = new ArrayList<>();


        refUser = FirebaseDatabase.getInstance().getReference("Users/Mikroskil");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null){
            if (user.getPhotoUrl() != null && user.getDisplayName() != null){
                Picasso.get().load(user.getPhotoUrl()).into(imageView);
                nama.setText(user.getDisplayName());
            }else {
                nama.setText("MIKAN");
                Picasso.get().load(R.drawable.logo_mikan).into(imageView);
            }
        }

//        refLaporanSaldo.child(user.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lst.clear();
//                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    laporan_saldo_dompet_digital laporan = snapshot.getValue(laporan_saldo_dompet_digital.class);
//                    lst.add(laporan);
//                }
////                adapter = new laporan_saldo_adapter(getActivity(),lst);
////                lstLaporanSaldo.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Users users = snapshot.getValue(Users.class);
                    if (users.getUserid().equals(user.getUid())){
                        saldo.setText(users.getSaldo().toString());
                    }else{
                        DatabaseReference refNonCivitas = FirebaseDatabase.getInstance().getReference("Users/NonCivitas");
                        refNonCivitas.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot1:dataSnapshot.getChildren()){
                                    Users users1 = snapshot1.getValue(Users.class);
                                    Log.d("Test",users1.getUserid() + user.getUid());
                                    if (users1.getUserid().equals(user.getUid())){
                                        saldo.setText(String.valueOf(users1.getSaldo()));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), topup.class));
            }
        });
        return view;
    }

    private void clickUpdate() {
        startActivity(new Intent(getActivity(),pengaturan.class));
    }

    private void click_keluar() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), login.class));
//        Intent intent = new Intent(this,login.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        getActivity().finish();
    }
}
