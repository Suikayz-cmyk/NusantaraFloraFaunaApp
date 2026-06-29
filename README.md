# EndemikID

## Informasi Mahasiswa
* **Nama:** Muhammad Prayogo Pangestu
* **NIM:** 2410501046
* **Kelas:** B
* **Program Studi:** D3 Sistem Informasi

## Deskripsi App
**EndemikID** adalah aplikasi ensiklopedia interaktif berbasis Android yang dirancang khusus untuk menyajikan informasi mengenai flora dan fauna endemik khas Nusantara. Aplikasi ini bertujuan untuk mengedukasi masyarakat sekaligus meningkatkan kesadaran akan pentingnya pelestarian keanekaragaman hayati Indonesia yang unik dan langka.

Aplikasi ini mendukung navigasi yang dinamis, pencarian yang responsif, serta penyesuaian visual dan bahasa demi kenyamanan pengguna dalam menjelajahi kekayaan alam Indonesia.

## Tech Stack & Features
Aplikasi ini dibangun menggunakan arsitektur modern Android dengan komponen-komponen berikut:
* **Core Language:** Android SDK (Java)
* **Database Architecture:** Room Database (SQLite) untuk penyimpanan data lokal yang efisien.
* **Architecture Pattern:** MVVM (Model-View-ViewModel) dengan LiveData untuk memisahkan logika bisnis dan UI secara reaktif.
* **Image Loading:** Glide Library untuk pemuatan gambar yang cepat dan optimal dari internet/lokal.
* **UI Components:** Material Design 3 (CardView, ShapeableImageView, BottomNavigationView, ScrollView).
* **Fitur Utama:**
  * **Dynamic Grid/List View:** Mengubah tampilan beranda antara mode *Grid* atau *List* secara *real-time*.
  * **Bilingual Support (Localization):** Mendukung pergantian bahasa dinamis antara Bahasa Indonesia dan Bahasa Inggris tanpa merusak tata letak atau judul halaman.
  * **Modern Light/Dark Mode:** Perpindahan tema malam dan siang yang menyesuaikan preferensi pengguna secara instan.
  * **Filter & Search:** Fitur pencarian teks dan filter berdasarkan wilayah/region (Sumatera, Jawa, Kalimantan, dll.) dengan mengklik judul halaman.
  * **Bookmark/Favorit:** Menyimpan flora atau fauna pilihan ke dalam daftar lokal.

## Screenshot App
| Splash Screen & Init Data | Main Screen Grid & List (with filter region) |
| :---: | :---: |
| <img width="150" src="https://github.com/user-attachments/assets/ff165248-9648-4acb-8c6b-01177ef52150" /> <img width="150" src="https://github.com/user-attachments/assets/eb89c75a-3e9c-4eea-9c94-e973b9cefc96" /> | <img width="150" src="https://github.com/user-attachments/assets/04c60408-c5c5-4740-9785-9ac112a8b822" /> <img width="150" src="https://github.com/user-attachments/assets/4fe64f9e-83d5-43a4-b235-6a448ee06d02" />|

| Detail & Search | Favorite & Account |
| :---: | :---: |
| <img width="150" src="https://github.com/user-attachments/assets/7ea9c74f-150a-4ad7-b9b0-84b30948da92" /> <img width="150" src="https://github.com/user-attachments/assets/8205d15e-fe0c-4557-82c5-ed5de7425fb7" /> | <img width="150" src="https://github.com/user-attachments/assets/a7fc7689-c708-43f0-acad-5aee2cffec3e" /> <img width="150" src="https://github.com/user-attachments/assets/93a7cb39-7c32-49b2-9023-06306b89e3c4" />|

## Cara Menjalankan
1. **Clone Repositori**
   ```
   git clone https://github.com/Suikayz-cmyk/NusantaraFloraFaunaApp.git
   ```
2. Buka di Android Studio
* Jalankan Android Studio.
* Pilih File > Open, lalu arahkan ke folder hasil clone aplikasi ini.
* Tunggu hingga proses Gradle Sync selesai sepenuhnya.

3. Konfigurasi Database
* Pastikan perangkat terhubung ke internet saat pertama kali menjalankan aplikasi agar data awal (initial data) dapat dipersiapkan ke dalam Room Database.

4. Jalankan Aplikasi
* Hubungkan perangkat Android fisik atau gunakan Emulator.
* Klik tombol Run 'app' (ikon play hijau) di Android Studio.
