package com.example.nusantaraflorafaunaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivFoto = findViewById(R.id.ivDetailFoto);
        TextView tvTipe = findViewById(R.id.tvDetailTipe);
        TextView tvNama = findViewById(R.id.tvDetailNama);
        TextView tvNamaLatin = findViewById(R.id.tvDetailNamaLatin);
        TextView tvStatus = findViewById(R.id.tvDetailStatus);
        TextView tvAsal = findViewById(R.id.tvDetailAsal);
        TextView tvDeskripsi = findViewById(R.id.tvDetailDeskripsi);

        // Menangkap data dari Intent
        String foto = getIntent().getStringExtra("EXTRA_FOTO");
        String tipe = getIntent().getStringExtra("EXTRA_TIPE");
        String nama = getIntent().getStringExtra("EXTRA_NAMA");
        String namaLatin = getIntent().getStringExtra("EXTRA_NAMA_LATIN");
        String status = getIntent().getStringExtra("EXTRA_STATUS");
        String asal = getIntent().getStringExtra("EXTRA_ASAL");
        String sebaran = getIntent().getStringExtra("EXTRA_SEBARAN");
        String deskripsi = getIntent().getStringExtra("EXTRA_DESKRIPSI");

        // Menampilkan data ke UI
        tvTipe.setText(tipe);
        tvNama.setText(nama);
        tvNamaLatin.setText(namaLatin);
        tvStatus.setText(status != null && !status.isEmpty() ? status : "Tidak ada informasi");

        String asalSebaran = (asal != null ? asal : "") + " " + (sebaran != null ? sebaran : "");
        tvAsal.setText(asalSebaran.trim().isEmpty() ? "Tidak ada informasi" : asalSebaran);

        tvDeskripsi.setText(deskripsi != null && !deskripsi.isEmpty() ? deskripsi : "Belum ada deskripsi.");

        // Load gambar menggunakan Glide
        if (foto != null && !foto.isEmpty()) {
            Glide.with(this)
                    .load(foto)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(ivFoto);
        }
    }
}