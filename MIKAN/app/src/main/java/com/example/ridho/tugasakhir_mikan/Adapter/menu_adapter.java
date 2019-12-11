package com.example.ridho.tugasakhir_mikan.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ridho.tugasakhir_mikan.Database.DatabaseOrder;
import com.example.ridho.tugasakhir_mikan.Model.Orders;
import com.example.ridho.tugasakhir_mikan.Model.menu;
import com.example.ridho.tugasakhir_mikan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class menu_adapter extends RecyclerView.Adapter<menu_adapter.MyViewHolder> {

    private Context ct;
    private ArrayList<menu> datamenu;

    public menu_adapter(Context ct, ArrayList<menu> datamenu) {
        this.ct = ct;
        this.datamenu = datamenu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(ct).inflate(R.layout.tampilan_menu,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.nama.setText(datamenu.get(i).getNama());
        myViewHolder.harga.setText(String.valueOf(datamenu.get(i).getHarga()));
        Picasso.get().load(datamenu.get(i).getImage()).into(myViewHolder.image);
        myViewHolder.onClick(i,datamenu.get(i).getNama(),datamenu.get(i).getHarga());
    }

    @Override
    public int getItemCount() {
        return datamenu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama,harga,jlhPesanan;
        ImageView image;
        Button btnTambah,btnKurang,btnTambahJlhMenu;
        LinearLayout layoutJlh;
        String idUser,idOrder,namaPemesan;
        Integer jlh = 0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = (TextView)itemView.findViewById(R.id.txtNamaDataMenu);
            harga = (TextView)itemView.findViewById(R.id.txtHargaDataMenu);
            image = (ImageView)itemView.findViewById(R.id.imgDataMenu);
            btnTambahJlhMenu = (Button)itemView.findViewById(R.id.btnTambahMenu);
            btnTambah = (Button)itemView.findViewById(R.id.btnPlus);
            btnKurang = (Button)itemView.findViewById(R.id.btnMinus);
            jlhPesanan = (TextView)itemView.findViewById(R.id.txtjlhmenu);
            layoutJlh = (LinearLayout)itemView.findViewById(R.id.layoutJlh);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference refOrder = FirebaseDatabase.getInstance().getReference();
            idOrder = refOrder.push().getKey();
            idUser = user.getUid();
            namaPemesan = user.getDisplayName();
        }

        public void onClick(int position,final String nama, final Integer harga){
            btnTambahJlhMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jlh++;
                    btnTambahJlhMenu.setVisibility(View.GONE);
                    layoutJlh.setVisibility(View.VISIBLE);
                    jlhPesanan.setText(String.valueOf(jlh));
                    if (jlh==1){
                        new DatabaseOrder(ct).clearCart_idOrder(idOrder);
                        new DatabaseOrder(ct).addToCart(new Orders(idOrder,idUser,nama,harga,jlh));
                    }else {
                        new DatabaseOrder(ct).updateCartJlh(idOrder,jlh);
                    }
                }
            });

            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jlh++;
                    jlhPesanan.setText(String.valueOf(jlh));
                    new DatabaseOrder(ct).updateCartJlh(idOrder,jlh);

                }
            });

            btnKurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(jlh>1){
                        jlh--;
                        jlhPesanan.setText(String.valueOf(jlh));
                        new DatabaseOrder(ct).updateCartJlh(idOrder,jlh);
                    }else {
                        jlh = 0;
                        layoutJlh.setVisibility(View.GONE);
                        btnTambahJlhMenu.setVisibility(View.VISIBLE);
                        new DatabaseOrder(ct).clearCart_idOrder(idOrder);
                    }
                }
            });
        }
    }
}
