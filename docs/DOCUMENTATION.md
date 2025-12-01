# **1. Introduction**

PomoCat adalah aplikasi desktop berbasis JavaFX yang memadukan metode manajemen waktu Pomodoro dengan mekanisme gamifikasi melalui karakter virtual pet. Dokumen ini menjelaskan desain sistem, arsitektur, alur kerja, serta struktur kode yang digunakan dalam aplikasi. Tujuannya adalah memberikan pemahaman mendalam mengenai bagaimana fitur diimplementasikan dengan prinsip pemrograman berorientasi objek.



# **2. Problem Statement**

Berdasarkan hasil analisis, terdapat beberapa permasalahan utama yang ingin diselesaikan oleh aplikasi PomoCat:

1. **Penurunan fokus dalam jangka waktu panjang** saat mengerjakan tugas atau belajar.
2. **Sulit mempertahankan motivasi**, khususnya bagi pengguna yang mudah bosan saat bekerja dalam durasi lama.
3. **Tidak adanya sistem penghargaan langsung** yang mendorong kebiasaan belajar/kerja yang konsisten.
4. **Kurangnya integrasi antara manajemen tugas dan time-tracking** dalam satu aplikasi sederhana.



# **3. Proposed Solution**

PomoCat dikembangkan untuk menjawab permasalahan di atas melalui:

1. **Pomodoro Timer interaktif** yang membantu pengguna menjaga fokus dalam interval waktu terstruktur.
2. **Task Management System** untuk mencatat, memilih, dan memprioritaskan tugas.
3. **Gamifikasi melalui virtual pet (PomoCat)** yang memberikan:

   * reward berupa coin,
   * peningkatan happiness,
   * interaksi seperti feeding & playing.
4. **Integrasi UI–logic melalui pola MVC**, sehingga pengembangan fitur mudah dilakukan dan struktur kode tetap bersih.



# **4. System Architecture**

## **4.1 Architectural Pattern – MVC**

Aplikasi menggunakan pola **Model–View–Controller (MVC)** untuk memisahkan tampilan, alur kontrol, dan logika data.

### **Model**

Berisi class yang menangani data dan logika inti:

| Class        | Fungsi                                                                           |
| ------------ | -------------------------------------------------------------------------------- |
| `TimerModel` | Mengelola countdown timer, durasi fokus, status running/pause, dan event selesai |
| `Task`       | Representasi entitas tugas (nama, status, data penyimpanan)                      |
| `Pet`        | Representasi karakter PomoCat (coins, happiness, interaksi feed/play)            |

### **View**

Dibangun menggunakan file **FXML**, berfungsi sebagai tampilan UI.

### **Controller**

Menghubungkan user interface dengan model:

| Controller       | Fungsi                                                             |
| ---------------- | ------------------------------------------------------------------ |
| `MainController` | Menghubungkan UI, timer, task, dan pet menjadi alur kerja aplikasi |



# **5. Class Design**

## **5.1 TimerModel**

### **Responsibilities**

* Menyimpan durasi timer (25/50 menit atau trial 10 detik).
* Menjalankan hitung mundur menggunakan thread.
* Mengirimkan event ke controller ketika timer selesai.

### **Key Attributes**

* `int timeRemaining`
* `boolean isRunning`

### **Key Methods**

* `startTimer()`
* `pauseTimer()`
* `resetTimer()`


## **5.2 Task**

### **Responsibilities**

* Menyimpan teks nama task.
* Mengatur status selesai/tidak.
* Disimpan dalam file `tasks.txt`.

### **Key Attributes**

* `String name`
* `boolean isCompleted`

---

## **5.3 Pet**

### **Responsibilities**

* Menyimpan status coin & happiness.
* Memberikan feedback visual/motivasi kepada pengguna.
* Mengatur logika pembelian makanan & permainan.

### **Key Attributes**

* `int coins`
* `int happiness`

### **Key Methods**

* `feed()`
* `play()`
* `addCoins(amount)`

---

## **5.4 MainController**

### **Responsibilities**

* Mengatur alur interaksi seluruh fitur:

  * navigasi antar halaman,
  * pemilihan task,
  * menjalankan timer,
  * memberi reward pet,
  * mengupdate UI.
* Mengelola state Pet, Task List, TimerModel.


# **6. System Workflow**

## **6.1 Overall Flow**

```
[Home] → [Task Selection] → [Choose Duration] → [Timer Page] → [Reward + Pet Interaction]
```

## **6.2 Timer Flow**

1. User memilih task dan durasi fokus.
2. User menekan **Start** → TimerModel mulai menghitung mundur.
3. Tombol **Feed** dan **Play** otomatis dinonaktifkan (anti-distraksi).
4. Ketika waktu habis:

   * PomoCat mendapat coin,
   * happiness bertambah,
   * UI menampilkan hasil sesi.


# **7. Data Persistence**

### **Task Storage**

* Semua task disimpan dalam file `tasks.txt`.
* Format penyimpanan sederhana (text-based), memudahkan akses dan debugging.
* Task otomatis dimuat ulang saat aplikasi dibuka kembali.

### **Pet Data**

* Coin dan happiness tersimpan lokal di memori selama aplikasi berjalan.
* Dapat dikembangkan ke penyimpanan permanen pada versi berikutnya.


# **8. Key Features (Technical View)**

| Fitur          | Deskripsi Teknis                                            |
| -------------- | ----------------------------------------------------------- |
| Pomodoro Timer | Thread countdown, update per detik, event callback          |
| Task List      | Penyimpanan file, parsing text, dynamic list view           |
| Virtual Pet    | State machine sederhana (coin/happiness), interaksi, reward |
| Shop           | Pembelian item (feeding & playing), deduct coin             |
| UI Locking     | Tombol pet disable saat timer running                       |
| MVC Separation | Controller mengatur komunikasi model ↔ view                 |


# **9. Challenges & Opportunities**

## **9.1 Challenges**

* Menjaga sinkronisasi timer dengan UI JavaFX tanpa blocking thread.
* Menghindari “god-class” pada controller karena menangani banyak fitur.
* Membuat interaksi pet terasa natural, bukan sekadar penambahan angka.
* Memastikan Task Persistence aman dari error parsing file.

## **9.2 Opportunities**

* Menambahkan animasi atau sprite PomoCat.
* Mengembangkan sistem leveling pet.
* Integrasi statistik produktivitas (grafik, tracking mingguan).
* Menyimpan data ke database SQLite.
* Menambahkan mode break otomatis (short break / long break).
* Menambahkan notifikasi suara saat timer selesai.


# **10. Conclusion**

Dokumentasi ini menyajikan gambaran teknis lengkap mengenai desain sistem PomoCat, mulai dari analisis kebutuhan, arsitektur, desain class, hingga alur kerja aplikasi. Dengan struktur OOP dan pola MVC, pengembangan aplikasi dapat dilakukan secara modular dan mudah diperluas. Dokumen ini juga diharapkan memperjelas cara kerja aplikasi serta mendukung proses penilaian proyek mata kuliah Pemrograman Berorientasi Objek.
