package com.example.nusantaraflorafaunaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nusantaraflorafaunaapp.R;
import com.example.nusantaraflorafaunaapp.database.Endemik;

import java.util.ArrayList;
import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.EndemikViewHolder> {

    private Context context;
    private List<Endemik> endemikList = new ArrayList<>();

    public EndemikAdapter(Context context) {
        this.context = context;
    }

    // Method untuk memperbarui data dari MainActivity nanti
    public void setEndemikList(List<Endemik> list) {
        this.endemikList = list;
        notifyDataSetChanged(); // Memberitahu adapter bahwa data berubah
    }

    @NonNull
    @Override
    public EndemikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_endemik, parent, false);
        return new EndemikViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EndemikViewHolder holder, int position) {
        Endemik endemik = endemikList.get(position);

        holder.tvNama.setText(endemik.getNama());
        holder.tvNamaLatin.setText(endemik.getNama_latin());
        holder.tvTipe.setText(endemik.getTipe());

        // Menggunakan Glide untuk memuat gambar dari URL
        if (endemik.getFoto() != null && !endemik.getFoto().isEmpty()) {
            Glide.with(context)
                    .load(endemik.getFoto())
                    .placeholder(android.R.drawable.ic_menu_gallery) // Gambar sementara saat loading
                    .into(holder.ivFoto);
        }
    }

    @Override
    public int getItemCount() {
        return endemikList.size();
    }

    // ViewHolder: Berfungsi mencari dan menyimpan referensi komponen UI
    public static class EndemikViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNama, tvNamaLatin, tvTipe;

        public EndemikViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNamaLatin = itemView.findViewById(R.id.tvNamaLatin);
            tvTipe = itemView.findViewById(R.id.tvTipe);
        }
    }
}