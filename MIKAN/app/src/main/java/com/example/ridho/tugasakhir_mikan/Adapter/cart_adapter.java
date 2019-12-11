package com.example.ridho.tugasakhir_mikan.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ridho.tugasakhir_mikan.Model.Orders;
import com.example.ridho.tugasakhir_mikan.R;

import java.util.ArrayList;

public class cart_adapter extends RecyclerView.Adapter<cart_adapter.ViewHolder> {
    Context ct;
    ArrayList<Orders> data;

    public cart_adapter(Context ct, ArrayList<Orders> data) {
        this.ct = ct;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(ct).inflate(R.layout.tampilan_cart,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nama.setText(data.get(i).getNama());
        viewHolder.harga.setText(String.valueOf(data.get(i).getHarga()*data.get(i).getJumlah()));
        viewHolder.jlh.setText(String.valueOf(data.get(i).getJumlah()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama,harga,jlh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = (TextView)itemView.findViewById(R.id.namapesanancart);
            harga = (TextView)itemView.findViewById(R.id.hargapesanancart);
            jlh = (TextView)itemView.findViewById(R.id.jlhpesanancart);
        }
    }
}
