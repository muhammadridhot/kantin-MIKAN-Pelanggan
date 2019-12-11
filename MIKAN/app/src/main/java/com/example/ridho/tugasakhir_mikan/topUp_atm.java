package com.example.ridho.tugasakhir_mikan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ridho.tugasakhir_mikan.Model.laporan_saldo_dompet_digital;
import com.example.ridho.tugasakhir_mikan.Model.topup_saldo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class topUp_atm extends AppCompatActivity {

    EditText txtNominal;
    Button btnLanjutkan,btnCaraTfMaybank;
    private DatabaseReference refTopUpATM;
    String idTopUp,idPelanggan,tgl_topUp,status,jenisAtm;
    Integer nominal;
    Boolean statusButton=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_atm);

        txtNominal = (EditText)findViewById(R.id.txtIsiNominal);
        btnLanjutkan = (Button)findViewById(R.id.btnLanjutkanTopUpATM);
        btnCaraTfMaybank = (Button)findViewById(R.id.btnCaraTFMayBank);
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layoutCaraTF);

        refTopUpATM = FirebaseDatabase.getInstance().getReference("TopupSaldo");


        btnLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jenisAtm == null){
                    Toast.makeText(topUp_atm.this,"Pilih jenis ATM",Toast.LENGTH_SHORT).show();
                }else {
                    clickLanjutkan();

                }
            }
        });

        btnCaraTfMaybank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusButton){
                    btnCaraTfMaybank.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_black_24dp,0);
                    statusButton = false;
                    linearLayout.setVisibility(View.GONE);
                }else {
                    btnCaraTfMaybank.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_up_black_24dp,0);
                    statusButton = true;
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void clickLanjutkan() {
        if (txtNominal.getText().toString().equals("")){
            Toast.makeText(topUp_atm.this,"Isi Nominal Top Up",Toast.LENGTH_SHORT).show();
        }else {
            nominal = Integer.valueOf(txtNominal.getText().toString());
            idTopUp =  refTopUpATM.push().getKey();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            idPelanggan = user.getUid();
            status = "Menunggu Konfirmasi";
            Date date = new Date();
            tgl_topUp = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(date);
            topup_saldo topup_saldo = new topup_saldo(idTopUp,idPelanggan,tgl_topUp,status,nominal,jenisAtm);
            refTopUpATM.child(idPelanggan).child(idTopUp).setValue(topup_saldo);

            DatabaseReference refLaporanTransaksiSaldo = FirebaseDatabase.getInstance().getReference("LaporanSaldoDompetDigital/Pelanggan");
            String heading = "Top Up";
            String deskriptsi = "Menunggu Konfirmasi";
            laporan_saldo_dompet_digital laporan = new laporan_saldo_dompet_digital(heading,deskriptsi,idTopUp,idPelanggan,nominal);
            refLaporanTransaksiSaldo.child(idPelanggan).child(idTopUp).setValue(laporan);
            Intent intent = new Intent(topUp_atm.this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Toast.makeText(topUp_atm.this,"Top Up berhasil, silahkan menunggu konfirmasi",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void clickJenisAtm(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.rdbMaybank:
                if(checked)
                    jenisAtm = "Maybank";
        }
    }
}
