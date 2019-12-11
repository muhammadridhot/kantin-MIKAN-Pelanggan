package com.example.ridho.tugasakhir_mikan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ridho.tugasakhir_mikan.Adapter.stan_adapter;
import com.example.ridho.tugasakhir_mikan.Adapter.stan_adapter_kantina;
import com.example.ridho.tugasakhir_mikan.Model.Penyewa;
import com.example.ridho.tugasakhir_mikan.R;
import com.example.ridho.tugasakhir_mikan.daftar_menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView lstdaftarStanKampusA,lstdaftarStanKampusB;
    List<Penyewa> lstdataUserA,lstdataUserB;
    private DatabaseReference refStan;
    String saldo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        lstdaftarStanKampusA = (RecyclerView)view.findViewById(R.id.lstdaftarstankampusa);
        lstdaftarStanKampusA.setLayoutManager(new LinearLayoutManager(getContext()));
        lstdaftarStanKampusB = (RecyclerView)view.findViewById(R.id.lstdaftarstankampusb);
        lstdaftarStanKampusB.setLayoutManager(new LinearLayoutManager(getContext()));

        refStan = FirebaseDatabase.getInstance().getReference("Users/Penyewa");
        lstdataUserA = new ArrayList<>();
        lstdataUserB = new ArrayList<>();


        refStan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstdataUserA.clear();
                lstdataUserB.clear();
                for (DataSnapshot snapshotData : dataSnapshot.getChildren()){
                    Penyewa stan = snapshotData.getValue(Penyewa.class);
                    if (stan.getKantin().equals("Kantin B")){
                        if (stan.getStatus().equals("Aktif")){
                            lstdataUserB.add(stan);
                        }
                    }else if(stan.getKantin().equals("Kantin A")){
                        if (stan.getStatus().equals("Aktif")) {
                            lstdataUserA.add(stan);
                        }
                    }
                }
                if (lstdataUserB.size()>0){
                    LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.layoutkampusb);
                    linearLayout.setVisibility(View.VISIBLE);
                    stan_adapter adapter = new stan_adapter(getActivity(), lstdataUserB, new stan_adapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, int position) {
                            //Users user = lstdataUser.get(position);
                            Penyewa stan = lstdataUserB.get(position);
                            //Log.d("Test data",user.getUserid() + user.getNama());
                            Intent intent = new Intent(getActivity(), daftar_menu.class);
                            intent.putExtra("IDSTAN",stan.getUserid());
                            intent.putExtra("NAMASTAN",stan.getNama());
                            intent.putExtra("KANTIN",stan.getKantin());
                            startActivity(intent);
                        }
                    });
                    lstdaftarStanKampusB.setAdapter(adapter);
                }

                if (lstdataUserA.size() >0){
                    LinearLayout linearLayout1 = (LinearLayout)view.findViewById(R.id.layoutkampusa);
                    linearLayout1.setVisibility(View.VISIBLE);
                    stan_adapter_kantina adapter1 = new stan_adapter_kantina(getActivity(), lstdataUserA, new stan_adapter_kantina.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, int position) {
                            Penyewa stan = lstdataUserA.get(position);

                            Intent intent = new Intent(getActivity(), daftar_menu.class);
                            intent.putExtra("IDSTAN",stan.getUserid());
                            intent.putExtra("NAMASTAN",stan.getNama());
                            intent.putExtra("KANTIN",stan.getKantin());
                            startActivity(intent);
                        }
                    });
                    lstdaftarStanKampusA.setAdapter(adapter1);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

}
