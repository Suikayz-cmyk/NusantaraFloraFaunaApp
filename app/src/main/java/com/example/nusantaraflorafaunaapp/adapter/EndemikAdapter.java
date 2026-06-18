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
    private List<Endemik> originalList = new ArrayList<>();
    private List<Endemik> filteredList = new ArrayList<>();

    public EndemikAdapter(Context context) {
        this.context = context;
    }

    public void setEndemikList(List<Endemik> list) {
        this.originalList = list;
        this.filteredList = new ArrayList<>(list); // Copy data asli ke filtered
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EndemikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_endemik, parent, false);
        return new EndemikViewHolder(view);
    }

    public void filter(String query, String region) {
        filteredList.clear();
        String safeQuery = query != null ? query.toLowerCase() : "";
        String safeRegion = region != null ? region.toLowerCase() : "semua region";

        if (safeQuery.isEmpty() && safeRegion.equals("semua region")) {
            filteredList.addAll(originalList);
        } else {
            for (Endemik item : originalList) {
                boolean matchQuery = item.getNama().toLowerCase().contains(safeQuery) ||
                        item.getNama_latin().toLowerCase().contains(safeQuery);

                boolean matchRegion = safeRegion.equals("semua region") ||
                        (item.getAsal() != null && item.getAsal().toLowerCase().contains(safeRegion)) ||
                        (item.getSebaran() != null && item.getSebaran().toLowerCase().contains(safeRegion));

                if (matchQuery && matchRegion) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull EndemikViewHolder holder, int position) {
        Endemik endemik = filteredList.get(position);

        holder.tvNama.setText(endemik.getNama());
        holder.tvNamaLatin.setText(endemik.getNama_latin());
        holder.tvTipe.setText(endemik.getTipe());

        if (endemik.getFoto() != null && !endemik.getFoto().isEmpty()) {
            Glide.with(context)
                    .load(endemik.getFoto())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.ivFoto);
        }

        // TAMBAHKAN KODE INI: Aksi ketika satu item (baris) diklik
        holder.itemView.setOnClickListener(v -> {
            // Intent untuk berpindah dari halaman saat ini ke DetailActivity
            android.content.Intent intent = new android.content.Intent(context, com.example.nusantaraflorafaunaapp.DetailActivity.class);

            // Membawa bekal data ke halaman detail
            intent.putExtra("EXTRA_FOTO", endemik.getFoto());
            intent.putExtra("EXTRA_TIPE", endemik.getTipe());
            intent.putExtra("EXTRA_NAMA", endemik.getNama());
            intent.putExtra("EXTRA_NAMA_LATIN", endemik.getNama_latin());
            intent.putExtra("EXTRA_STATUS", endemik.getStatus());
            intent.putExtra("EXTRA_ASAL", endemik.getAsal());
            intent.putExtra("EXTRA_SEBARAN", endemik.getSebaran());
            intent.putExtra("EXTRA_DESKRIPSI", endemik.getDeskripsi());
            intent.putExtra("EXTRA_ID", endemik.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
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