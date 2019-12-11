package com.example.ridho.tugasakhir_mikan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ridho.tugasakhir_mikan.Model.laporan_saldo_dompet_digital;
import com.example.ridho.tugasakhir_mikan.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class laporan_saldo_adapter extends RecyclerView.Adapter<laporan_saldo_adapter.ViewHolder> {

    Context ctx;
    ArrayList<laporan_saldo_dompet_digital> data;

    public laporan_saldo_adapter(Context ctx, ArrayList<laporan_saldo_dompet_digital> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.tampilan_laporan_saldo_dompet_digital,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.heading.setText(data.get(position).getHeading());
        holder.deskripsi.setText(data.get(position).getDeskripsi());
        if (data.get(position).getHeading().equals("Pesanan")){
            holder.saldo.setText(" - "+String.valueOf(data.get(position).getSaldo()));
        }else{
            holder.saldo.setText(String.valueOf(data.get(position).getSaldo()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView heading,deskripsi,saldo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = (TextView)itemView.findViewById(R.id.txtHeading);
            deskripsi = (TextView)itemView.findViewById(R.id.txtDeskripsi);
            saldo = (TextView)itemView.findViewById(R.id.txtSaldo);
        }
    }
}
