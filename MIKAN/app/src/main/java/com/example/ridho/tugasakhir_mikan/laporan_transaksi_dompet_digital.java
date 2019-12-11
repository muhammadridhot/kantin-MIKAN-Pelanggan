package com.example.ridho.tugasakhir_mikan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ridho.tugasakhir_mikan.Adapter.laporan_saldo_adapter;
import com.example.ridho.tugasakhir_mikan.Model.laporan_saldo_dompet_digital;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class laporan_transaksi_dompet_digital extends AppCompatActivity {

    private DatabaseReference refLaporanSaldo;
    private ArrayList<laporan_saldo_dompet_digital> lst;
    RecyclerView lstLaporanSaldo;
    laporan_saldo_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_topup);

        lstLaporanSaldo = (RecyclerView)findViewById(R.id.lstDataTransaksiTopUp);
        lstLaporanSaldo.setHasFixedSize(true);
        lstLaporanSaldo.setLayoutManager(new LinearLayoutManager(this));
        refLaporanSaldo = FirebaseDatabase.getInstance().getReference("LaporanSaldoDompetDigital/Pelanggan");
        lst = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        refLaporanSaldo.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lst.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    laporan_saldo_dompet_digital laporan = snapshot.getValue(laporan_saldo_dompet_digital.class);
                    lst.add(laporan);
                }
                adapter = new laporan_saldo_adapter(laporan_transaksi_dompet_digital.this,lst);
                lstLaporanSaldo.setAdapter(adapter);
                Collections.reverse(lst);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
