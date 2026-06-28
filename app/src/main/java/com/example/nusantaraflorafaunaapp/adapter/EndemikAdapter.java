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

    private boolean isGridMode = false;
    private static final int VIEW_TYPE_LIST = 0;
    private static final int VIEW_TYPE_GRID = 1;

    public EndemikAdapter(Context context) {
        this.context = context;
    }

    public void setEndemikList(List<Endemik> list) {
        this.originalList = list;
        this.filteredList = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    public void setGridMode(boolean isGrid) {
        this.isGridMode = isGrid;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return isGridMode ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    @NonNull
    @Override
    public EndemikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;
        if (viewType == VIEW_TYPE_GRID) {
            layoutId = R.layout.item_endemik_grid;
        } else {
            layoutId = R.layout.item_endemik;
        }
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
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
        holder.tvAsal.setText(endemik.getAsal());

        if (endemik.getFoto() != null && !endemik.getFoto().isEmpty()) {
            Glide.with(context)
                    .load(endemik.getFoto())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.ivFoto);
        }

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context, com.example.nusantaraflorafaunaapp.DetailActivity.class);

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
    public static class EndemikViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNama, tvNamaLatin, tvAsal;

        public EndemikViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNamaLatin = itemView.findViewById(R.id.tvNamaLatin);
            tvAsal = itemView.findViewById(R.id.tvAsal);
        }
    }
}