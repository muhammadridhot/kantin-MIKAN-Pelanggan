package com.example.ridho.tugasakhir_mikan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ridho.tugasakhir_mikan.Adapter.riwayat_adapter;
import com.example.ridho.tugasakhir_mikan.Model.Riwayat_Pesanan;
import com.example.ridho.tugasakhir_mikan.R;
import com.example.ridho.tugasakhir_mikan.detail_pesanan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class RiwayatFragment extends Fragment {

    RecyclerView recyclerView;
    private DatabaseReference refRiwayatPesanan;
    ArrayList<Riwayat_Pesanan> lstDataRiwayatPesanan;
    riwayat_adapter adapter;
    Button detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.lstriwayat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refRiwayatPesanan = FirebaseDatabase.getInstance().getReference("Riwayat_Pesanan");
        lstDataRiwayatPesanan = new ArrayList<>();

        loadData();
        return view;
    }

    private void loadData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String idUser = user.getUid();

        refRiwayatPesanan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstDataRiwayatPesanan.clear();
                for (DataSnapshot snapshot:dataSnapshot.child(idUser).getChildren()){
                    Riwayat_Pesanan data = snapshot.getValue(Riwayat_Pesanan.class);
                    lstDataRiwayatPesanan.add(data);
                    Log.d("Data Riwayat Pesanan" ,data.toString());
                }
               adapter = new riwayat_adapter(getActivity(), lstDataRiwayatPesanan, new riwayat_adapter.OnItemClickListener() {
                   @Override
                   public void OnItemClick(View view, int position) {
                       Riwayat_Pesanan detail = lstDataRiwayatPesanan.get(position);
                       Intent intent = new Intent(getActivity(), detail_pesanan.class);
                       intent.putExtra("IDORDER",detail.getIdOrder());
                       intent.putExtra("IDPELANGGAN",detail.getidPelanggan());
                       startActivity(intent);
                   }
               });
                recyclerView.setAdapter(adapter);
                Collections.reverse(lstDataRiwayatPesanan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
