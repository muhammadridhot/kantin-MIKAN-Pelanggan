package com.example.ridho.tugasakhir_mikan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ridho.tugasakhir_mikan.Database.DatabaseOrder;
import com.example.ridho.tugasakhir_mikan.Model.Notification;
import com.example.ridho.tugasakhir_mikan.Model.Orders;
import com.example.ridho.tugasakhir_mikan.Model.Response;
import com.example.ridho.tugasakhir_mikan.Model.Riwayat_Pesanan;
import com.example.ridho.tugasakhir_mikan.Model.Sender;
import com.example.ridho.tugasakhir_mikan.Model.Token;
import com.example.ridho.tugasakhir_mikan.Model.Users;
import com.example.ridho.tugasakhir_mikan.Model.laporan_saldo_dompet_digital;
import com.example.ridho.tugasakhir_mikan.Model.notifikasi_penyewa;
import com.example.ridho.tugasakhir_mikan.Remote.APIService;
import com.example.ridho.tugasakhir_mikan.Remote.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class meja_makan extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog pd;
    private static final String CHANNEL_ID ="Order";
    private static final String BASE_URL = "https://fcm.googleapis.com/";
    APIService mService;
    String kantin,namaStan,idStan,idPelanggan,metodePembayaran,idUser,jenisUser,idMiso,email,namaPemesan,image;
    Integer totalharga=0,saldo=0;
    Button pembayaran,btnMJ01,btnMJ02,btnMJ03,btnMJ04,btnMJ05,btnMJ06,btnMJ07,btnMJ08,btnMJ09,
            btnMJ10,btnMJ11,btnMJ12,btnMJ13,btnMJ14,btnMJ15,btnMJ16,btnMJ17,btnMJ18,btnMJ19,btnMJ20;
    Button btnMJA01;
    boolean statusMeja01=false,statusMeja02=false,statusMeja03=false,statusMeja04=false,
            statusMeja05=false,statusMeja06=false,statusMeja07=false,statusMeja08=false
            ,statusMeja09=false,statusMeja10=false,statusMeja11=false,statusMeja12=false
            ,statusMeja13=false,statusMeja14=false,statusMeja15=false,statusMeja16=false
            ,statusMeja17=false,statusMeja18=false,statusMeja19=false,statusMeja20=false;

    LinearLayout kantinA,kantinB;
    ArrayList<Users> dataUserMikroskil,dataUserNonCivitas;
//    TextView DaftarMejaMakan;
    String meja="";
    private DatabaseReference refRiwayatPesanan,refTokens,refDataUserMikroskil,refDataUserNonCivitas,refNotifikasiPenyewa,refDompetDigital;
    private ArrayList<Orders> menu = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meja_makan);

        mService = getFCMService();

        Intent intent = getIntent();
        idStan = intent.getStringExtra("IDSTAN");
        kantin = intent.getStringExtra("KANTIN");
        namaStan = intent.getStringExtra("NAMASTAN");
        totalharga = intent.getIntExtra("TOTALHARGA",0);
        metodePembayaran = intent.getStringExtra("METODE_PEMBAYARAN");
        getSupportActionBar().setTitle(kantin);


        kantinA = (LinearLayout)findViewById(R.id.LayoutKantinA);
        kantinB = (LinearLayout)findViewById(R.id.LayoutKantinB);
        if (kantin != null){
            if(kantin.equals(("Kantin A"))){
                kantinA.setVisibility(View.VISIBLE);
            }else if (kantin.equals("Kantin B")){
                kantinB.setVisibility(View.VISIBLE);
            }
        }

        refRiwayatPesanan = FirebaseDatabase.getInstance().getReference("Riwayat_Pesanan");
        refNotifikasiPenyewa = FirebaseDatabase.getInstance().getReference("NotifikasiPenyewa");
        refTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        refDompetDigital = FirebaseDatabase.getInstance().getReference("LaporanSaldoDompetDigital/Pelanggan");


        dataUserMikroskil = new ArrayList<>();
        dataUserNonCivitas = new ArrayList<>();

//        DaftarMejaMakan = (TextView)findViewById(R.id.DaftarMejaMakan);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        idUser = user.getUid();
        refDataUserMikroskil = FirebaseDatabase.getInstance().getReference("Users/Mikroskil");
        refDataUserMikroskil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataUserMikroskil.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Users users = snapshot.getValue(Users.class);
                    dataUserMikroskil.add(users);
                    if (users.getUserid().equals(idUser)){
                        saldo = users.getSaldo();
                        jenisUser = "Mikroskil";
                        namaPemesan = users.getNama();
                        idMiso = users.getIdMiso();
                        image = users.getImage();
                        idPelanggan = users.getUserid();
                        Log.d("saldo anda(Mikroskil)",String.valueOf(users.getSaldo()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        refDataUserNonCivitas = FirebaseDatabase.getInstance().getReference("Users/NonCivitas");
        refDataUserNonCivitas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataUserNonCivitas.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Users users = snapshot.getValue(Users.class);
                    dataUserNonCivitas.add(users);
                    if (users.getUserid().equals(idUser)){
                        saldo = users.getSaldo();
                        jenisUser = "NonCivitas";
                        namaPemesan = users.getNama();
                        image = users.getImage();
                        idPelanggan = users.getUserid();
                        email = users.getEmail().substring(0,users.getEmail().indexOf("."));
                        Log.d("saldo anda(NonCivitas)",String.valueOf(users.getSaldo()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnMJA01 = (Button)findViewById(R.id.btnMJA01);
        btnMJA01.setOnClickListener(this);
        btnMJ01 = (Button)findViewById(R.id.btnMJ01);
        btnMJ01.setOnClickListener(this);
        btnMJ02 = (Button)findViewById(R.id.btnMJ02);
        btnMJ02.setOnClickListener(this);
        btnMJ03 = (Button)findViewById(R.id.btnMJ03);
        btnMJ03.setOnClickListener(this);
        btnMJ04 = (Button)findViewById(R.id.btnMJ04);
        btnMJ04.setOnClickListener(this);
        btnMJ05 = (Button)findViewById(R.id.btnMJ05);
        btnMJ05.setOnClickListener(this);
        btnMJ06 = (Button)findViewById(R.id.btnMJ06);
        btnMJ06.setOnClickListener(this);
        btnMJ07 = (Button)findViewById(R.id.btnMJ07);
        btnMJ07.setOnClickListener(this);
        btnMJ08 = (Button)findViewById(R.id.btnMJ08);
        btnMJ08.setOnClickListener(this);
        btnMJ09 = (Button)findViewById(R.id.btnMJ09);
        btnMJ09.setOnClickListener(this);
        btnMJ10 = (Button)findViewById(R.id.btnMJ10);
        btnMJ10.setOnClickListener(this);
        btnMJ11 = (Button)findViewById(R.id.btnMJ11);
        btnMJ11.setOnClickListener(this);
        btnMJ12 = (Button)findViewById(R.id.btnMJ12);
        btnMJ12.setOnClickListener(this);
        btnMJ13 = (Button)findViewById(R.id.btnMJ13);
        btnMJ13.setOnClickListener(this);
        btnMJ14 = (Button)findViewById(R.id.btnMJ14);
        btnMJ14.setOnClickListener(this);
        btnMJ15 = (Button)findViewById(R.id.btnMJ15);
        btnMJ15.setOnClickListener(this);
        btnMJ16 = (Button)findViewById(R.id.btnMJ16);
        btnMJ16.setOnClickListener(this);
        btnMJ17 = (Button)findViewById(R.id.btnMJ17);
        btnMJ17.setOnClickListener(this);
        btnMJ18 = (Button)findViewById(R.id.btnMJ18);
        btnMJ18.setOnClickListener(this);
        btnMJ19 = (Button)findViewById(R.id.btnMJ19);
        btnMJ19.setOnClickListener(this);
        btnMJ20 = (Button)findViewById(R.id.btnMJ20);
        btnMJ20.setOnClickListener(this);

        pembayaran = (Button)findViewById(R.id.btnPembayaran);
        pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusMeja01){ meja += "MJ01 ";}
                if (statusMeja02){ meja += "MJ02 "; }
                if (statusMeja03){ meja += "MJ03 "; }
                if (statusMeja04){ meja += "MJ04 "; }
                if (statusMeja05){ meja += "MJ05 "; }
                if (statusMeja06){ meja += "MJ06 "; }
                if (statusMeja07){ meja += "MJ07 "; }
                if (statusMeja08){ meja += "MJ08 "; }
                if (statusMeja09){ meja += "MJ09 "; }
                if (statusMeja10){ meja += "MJ10 "; }
                if (statusMeja11){ meja += "MJ11 "; }
                if (statusMeja12){ meja += "MJ12 "; }
                if (statusMeja13){ meja += "MJ13 "; }
                if (statusMeja14){ meja += "MJ14 "; }
                if (statusMeja15){ meja += "MJ15 "; }
                if (statusMeja16){ meja += "MJ16 "; }
                if (statusMeja17){ meja += "MJ17 "; }
                if (statusMeja18){ meja += "MJ18 "; }
                if (statusMeja19){ meja += "MJ19 "; }
                if (statusMeja20){ meja += "MJ20 "; }


                    pesanan();

            }
        });

    }

    private void showProgressDialog(){
        if (pd == null){
            pd = new ProgressDialog(this);
            pd.setMessage("Please wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        pd.show();
    }
    private void dismissProgressDialog(){
        if (pd != null && pd.isShowing()){
            pd.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    private void sendNotificationToStan() {
        Query data = refTokens.orderByKey().equalTo(idStan);

        showProgressDialog();

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                   Token serverToken = snapshot.getValue(Token.class);
                   Notification notification = new Notification("MIKAN","Kamu mempunyai pesanan");
                   Sender content = new Sender(serverToken.getToken(),notification);
                   mService.sendNotification(content)
                           .enqueue(new Callback<Response>() {
                               @Override
                               public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                   if (response.body().success == 1){
                                       Toast.makeText(meja_makan.this,"Terima kasih,Pesanan anda berhasil dibuat",Toast.LENGTH_SHORT).show();
                                       dismissProgressDialog();
                                   }else {
                                       Toast.makeText(meja_makan.this,"Gagal memesan!",Toast.LENGTH_SHORT).show();
                                       dismissProgressDialog();
                                   }
                               }

                               @Override
                               public void onFailure(Call<Response> call, Throwable t) {
                                   Log.e("ERROR",t.getMessage());
                                  dismissProgressDialog();
                               }
                           });

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void notificationOrder() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"Chanel Name",importance);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Intent intent = new Intent(this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_mikan)
                .setContentTitle("Pesanan berhasil dibuat")
                .setContentText("Silahkan tunggu konfirmasi pemilik stan")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0,mBuilder.build());
    }

    private void pesanan() {
        String idOrder = refRiwayatPesanan.push().getKey();
        String statusPesanan = "Menunggu";

        if (metodePembayaran.equals("Dompet Digital")){
            if (saldo < totalharga){
                Toast.makeText(meja_makan.this,"Saldo anda tidak cukup",Toast.LENGTH_SHORT).show();
            }else{
                Date date = new Date();
                menu = new DatabaseOrder(this).getCart();

                Riwayat_Pesanan riwayat_pesanan = new Riwayat_Pesanan(idOrder,idUser,idStan,namaStan,meja,metodePembayaran,statusPesanan,totalharga, DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM, Locale.ENGLISH).format(date),menu);

                refRiwayatPesanan.child(idUser).child(idOrder).setValue(riwayat_pesanan);

                notifikasi_penyewa notifikasiPenyewa = new notifikasi_penyewa(idStan,idOrder,idPelanggan,namaPemesan,meja,DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM, Locale.ENGLISH).format(date),totalharga,menu,image,statusPesanan,metodePembayaran);
                refNotifikasiPenyewa.child(idStan).child(idOrder).setValue(notifikasiPenyewa);

                laporan_saldo_dompet_digital laporanSaldoDompetDigital = new laporan_saldo_dompet_digital("Pesanan",namaStan,idOrder,idPelanggan,totalharga);
                refDompetDigital.child(idPelanggan).child(idOrder).setValue(laporanSaldoDompetDigital);

                new DatabaseOrder(meja_makan.this).clearCart();

                Intent intent = new Intent(this,Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                notificationOrder();
                sendNotificationToStan();

                if (jenisUser.equals("Mikroskil")){
                    refDataUserMikroskil.child(idMiso).child("saldo").setValue(saldo-totalharga);
                }else if(jenisUser.equals("NonCivitas")){
                    refDataUserNonCivitas.child(email).child("saldo").setValue(saldo-totalharga);
                }
            }
            Log.d("Ket Saldo :",String.valueOf(saldo));
            Log.d("Ket Total Harga :",String.valueOf(totalharga));
        }else if (metodePembayaran.equals("Tunai")){
            Date date = new Date();
            menu = new DatabaseOrder(this).getCart();

            Riwayat_Pesanan riwayat_pesanan = new Riwayat_Pesanan(idOrder,idUser,idStan,namaStan,meja,metodePembayaran,statusPesanan,totalharga, DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM, Locale.ENGLISH).format(date),menu);

            refRiwayatPesanan.child(idUser).child(idOrder).setValue(riwayat_pesanan);
            notifikasi_penyewa notifikasiPenyewa = new notifikasi_penyewa(idStan,idOrder,idPelanggan,namaPemesan,meja,DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM, Locale.ENGLISH).format(date),totalharga,menu,image,statusPesanan,metodePembayaran);
            refNotifikasiPenyewa.child(idStan).child(idOrder).setValue(notifikasiPenyewa);
            new DatabaseOrder(meja_makan.this).clearCart();

            Intent intent = new Intent(this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //notificationOrder();
            sendNotificationToStan();
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMJA01:
                Log.d("ID STAN ADALAH : ",idStan);
                if (statusMeja01){
                    btnMJA01.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJA01.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja01 = false;

                }else {
                    btnMJA01.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJA01.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja01 = true;
                }
                break;
            case R.id.btnMJ01:
                Log.d("ID STAN ADALAH : ",idStan);
                if (statusMeja01){
                    btnMJ01.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ01.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja01 = false;

                }else {
                    btnMJ01.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ01.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja01 = true;
                }
                break;
            case R.id.btnMJ02:
                if (statusMeja02){
                    btnMJ02.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ02.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja02 = false;
                }else {
                    btnMJ02.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ02.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja02 = true;
                }
                break;
            case R.id.btnMJ03:
                if (statusMeja03){
                    btnMJ03.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ03.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja03 = false;
                }else {
                    btnMJ03.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ03.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja03 = true;
                }
                break;
            case R.id.btnMJ04:
                if (statusMeja04){
                    btnMJ04.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ04.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja04 = false;
                }else {
                    btnMJ04.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ04.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja04 = true;
                }
                break;
            case R.id.btnMJ05:
                if (statusMeja05){
                    btnMJ05.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ05.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja05 = false;
                }else {
                    btnMJ05.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ05.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja05 = true;
                }
                break;
            case R.id.btnMJ06:
                if (statusMeja06){
                    btnMJ06.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ06.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja06 = false;
                }else {
                    btnMJ06.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ06.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja06 = true;
                }
                break;
            case R.id.btnMJ07:
                if (statusMeja07){
                    btnMJ07.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ07.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja07 = false;
                }else {
                    btnMJ07.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ07.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja07 = true;
                }
                break;
            case R.id.btnMJ08:
                if (statusMeja08){
                    btnMJ08.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ08.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja08 = false;
                }else {
                    btnMJ08.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ08.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja08 = true;
                }
                break;
            case R.id.btnMJ09:
                if (statusMeja09){
                    btnMJ09.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ09.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja09 = false;
                }else {
                    btnMJ09.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ09.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja09 = true;
                }
                break;
            case R.id.btnMJ10:
                if (statusMeja10){
                    btnMJ10.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ10.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja10 = false;
                }else {
                    btnMJ10.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ10.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja10 = true;
                }
                break;
            case R.id.btnMJ11:
                if (statusMeja11){
                    btnMJ11.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ11.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja11 = false;
                }else {
                    btnMJ11.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ11.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja11 = true;
                }
                break;
            case R.id.btnMJ12:
                if (statusMeja12){
                    btnMJ12.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ12.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja12 = false;
                }else {
                    btnMJ12.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ12.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja12 = true;
                }
                break;
            case R.id.btnMJ13:
                if (statusMeja13){
                    btnMJ13.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ13.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja13 = false;
                }else {
                    btnMJ13.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ13.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja13 = true;
                }
                break;
            case R.id.btnMJ14:
                if (statusMeja14){
                    btnMJ14.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ14.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja14 = false;
                }else {
                    btnMJ14.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ14.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja14 = true;
                }
                break;
            case R.id.btnMJ15:
                if (statusMeja15){
                    btnMJ15.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ15.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja15 = false;
                }else {
                    btnMJ15.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ15.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja15 = true;
                }
                break;
            case R.id.btnMJ16:
                if (statusMeja16){
                    btnMJ16.setBackground(getDrawable(R.drawable.button_meja_makan_tersedia));
                    btnMJ16.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.secondary_text_material_dark));
                    statusMeja16 = false;
                }else {
                    btnMJ16.setBackground(getDrawable(R.drawable.button_meja_makan_terpilih));
                    btnMJ16.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary_text_material_dark));
                    statusMeja16 = true;
                }
                break;
                default:
                    break;
        }
    }

    public static APIService getFCMService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
