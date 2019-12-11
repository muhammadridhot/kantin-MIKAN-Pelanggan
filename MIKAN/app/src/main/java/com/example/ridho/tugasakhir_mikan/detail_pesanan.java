package com.example.ridho.tugasakhir_mikan;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.ridho.tugasakhir_mikan.Adapter.cart_adapter;
import com.example.ridho.tugasakhir_mikan.Model.Riwayat_Pesanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class detail_pesanan extends AppCompatActivity {

    String idOrder,idPelanggan,idPenyewa,tgl_pesan,meja_makan,totalHarga,statusPesanan;
    private DatabaseReference refRiwayat,refNotifPenyewa;
    TextView txtTotalHarga,txtMeja,txtIdOrder;
    RecyclerView recyclerView;
    cart_adapter adapter;
    Button btnSelesai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        Intent intent = getIntent();
        idOrder = intent.getStringExtra("IDORDER");
        idPelanggan = intent.getStringExtra("IDPELANGGAN");
        refRiwayat = FirebaseDatabase.getInstance().getReference("Riwayat_Pesanan");
        refNotifPenyewa = FirebaseDatabase.getInstance().getReference("NotifikasiPenyewa");

        btnSelesai = (Button)findViewById(R.id.btnSelesai);
        txtTotalHarga = (TextView)findViewById(R.id.txtDetailTotalHarga);
        txtMeja = (TextView)findViewById(R.id.txtDetailMejaMakan);
        txtIdOrder = (TextView)findViewById(R.id.txtDetailIdOrder);
        recyclerView = (RecyclerView)findViewById(R.id.lstTampilanDetailMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refRiwayat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.child(idPelanggan).getChildren()){
                    Riwayat_Pesanan riwayat_pesanan = snapshot.getValue(Riwayat_Pesanan.class);
                    if (riwayat_pesanan.getIdOrder().equals(idOrder)){
                        idPenyewa = riwayat_pesanan.getIdPenyewa();
                        tgl_pesan = riwayat_pesanan.getTgl_pesan();
                        meja_makan = riwayat_pesanan.getNo_meja();
                        statusPesanan = riwayat_pesanan.getStatus();
                        totalHarga = String.valueOf(riwayat_pesanan.getTotal_harga());
                        Log.d("Data menu anda :",String.valueOf(riwayat_pesanan.getMenu()));
                        adapter = new cart_adapter(detail_pesanan.this,riwayat_pesanan.getMenu());
                        recyclerView.setAdapter(adapter);
                    }
                }
                getSupportActionBar().setTitle(tgl_pesan);
                txtIdOrder.setText(idOrder);
                txtTotalHarga.setText(totalHarga);
                if (meja_makan.equals("")){
                    txtMeja.setText("-");
                }else {
                    txtMeja.setText(meja_makan);
                }
                Log.d("StatusAnda",statusPesanan);
                //StepInView
                HorizontalStepView setpview5 = (HorizontalStepView) findViewById(R.id.step_view);
                List<StepBean> stepsBeanList = new ArrayList<>();
                if (statusPesanan.equals("Menunggu")){
                    StepBean stepBean0 = new StepBean("Pesan",1);
                    StepBean stepBean1 = new StepBean("Diproses",0);
                    StepBean stepBean2 = new StepBean("Selesai",-1);
                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);
                }else if(statusPesanan.equals("Diterima")){
                    StepBean stepBean0 = new StepBean("Pesan",1);
                    StepBean stepBean1 = new StepBean("Diproses",1);
                    StepBean stepBean2 = new StepBean("Selesai",0);
                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);
                    btnSelesai.setEnabled(true);
                }else if (statusPesanan.equals("Ditolak")){
                    StepBean stepBean0 = new StepBean("Pesan",1);
                    StepBean stepBean1 = new StepBean("Ditolak",1);
                    StepBean stepBean2 = new StepBean("Selesai",-1);
                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);
                }else{
                    StepBean stepBean0 = new StepBean("Pesan",1);
                    StepBean stepBean1 = new StepBean("Diproses",1);
                    StepBean stepBean2 = new StepBean("Selesai",1);
                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);
                    btnSelesai.setVisibility(View.GONE);
                }

                setpview5
                        .setStepViewTexts(stepsBeanList)
                        .setTextSize(12)
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(detail_pesanan.this,R.color.colorAccent))
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(detail_pesanan.this, R.color.secondary_text_material_dark))
                        .setStepViewComplectedTextColor(ContextCompat.getColor(detail_pesanan.this,R.color.colorAccent))
                        .setStepViewUnComplectedTextColor(Color.parseColor("#9e9e9e"))
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(detail_pesanan.this, R.drawable.ic_check_circle_black_24dp))
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(detail_pesanan.this, R.drawable.default_icon))
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(detail_pesanan.this, R.drawable.ic_autorenew_black_24dp));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refRiwayat.child(idPelanggan).child(idOrder).child("status").setValue("Selesai");
                refNotifPenyewa.child(idPenyewa).child(idOrder).child("status").setValue("Selesai");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
