package com.example.ridho.tugasakhir_mikan.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ridho.tugasakhir_mikan.Model.Penyewa;
import com.example.ridho.tugasakhir_mikan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class stan_adapter extends RecyclerView.Adapter<stan_adapter.ViewHolder> {

    private Context ctx;
    private List<Penyewa> dataStan;
    private OnItemClickListener monItemClickListener;

    public interface OnItemClickListener{
        public void OnItemClick(View view,int position);
    }
    public stan_adapter(Context context, List<Penyewa> stan, OnItemClickListener onItemClickListener) {
        this.ctx = context;
        this.dataStan = stan;
        this.monItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.tampilan_stan,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.namaStan.setText(dataStan.get(i).getNama());
        Picasso.get().load(dataStan.get(i).getImage()).into(viewHolder.imageStan);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monItemClickListener.OnItemClick(v,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataStan.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaStan;
        ImageView imageStan;
        View container;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaStan = (TextView) itemView.findViewById(R.id.txtnamastan);
            imageStan = (ImageView) itemView.findViewById(R.id.image_stan);
            container = itemView;
        }
    }
}
