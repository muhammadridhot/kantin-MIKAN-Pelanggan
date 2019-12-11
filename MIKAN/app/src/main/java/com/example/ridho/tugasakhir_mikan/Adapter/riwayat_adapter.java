package com.example.ridho.tugasakhir_mikan.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ridho.tugasakhir_mikan.Model.Riwayat_Pesanan;
import com.example.ridho.tugasakhir_mikan.R;

import java.util.ArrayList;

public class riwayat_adapter extends RecyclerView.Adapter<riwayat_adapter.ViewHolder> {
    private Context context;
    private ArrayList<Riwayat_Pesanan> data;
    private OnItemClickListener monItemClickListener;

    public interface OnItemClickListener{
        public void OnItemClick(View view,int position);
    }

    public riwayat_adapter(Context context, ArrayList<Riwayat_Pesanan> data, OnItemClickListener monItemClickListener) {
        this.context = context;
        this.data = data;
        this.monItemClickListener = monItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tampilan_riwayat_pesanan,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.idORder.setText(data.get(i).getIdOrder());
        viewHolder.totalHarga.setText("Rp. "+String.valueOf(data.get(i).getTotal_harga()));
        viewHolder.StatusPesanan.setText(data.get(i).getStatus());
        viewHolder.tglPesanan.setText(data.get(i).getTgl_pesan());
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        monItemClickListener.OnItemClick(v,i);
//                    }
//                });
                monItemClickListener.OnItemClick(v,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView idORder,totalHarga,StatusPesanan,tglPesanan;
        Button btnDetail;
        View container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idORder= (TextView)itemView.findViewById(R.id.txtRiwayatIdOrder);
            totalHarga = (TextView)itemView.findViewById(R.id.txtRiwayatTotalHarga);
            StatusPesanan = (TextView)itemView.findViewById(R.id.txtRiwayatStatus);
            tglPesanan = (TextView)itemView.findViewById(R.id.RiwayattglOrder);
//            btnDetail = (Button)itemView.findViewById(R.id.btnDetailPesanan);
            container = itemView;
        }

    }
}
