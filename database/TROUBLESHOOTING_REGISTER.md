# Troubleshooting Error Registrasi

## Masalah Umum dan Solusinya

### 1. Error: "Koneksi database gagal"
**Penyebab:**
- MySQL/MariaDB tidak berjalan
- Database `db_absensi` belum dibuat
- Username/password MySQL salah
- MySQL Connector/J belum ditambahkan ke project

**Solusi:**
1. Pastikan MySQL service berjalan:
   - Windows: Services > MySQL > Start
   - Linux: `sudo systemctl start mysql`
2. Import database: `mysql -u root -p < database/db_absensi.sql`
3. Cek file `src/model/koneksi.java`:
   ```java
   String user = "root";
   String pass = "";  // Sesuaikan dengan password MySQL Anda
   ```
4. Pastikan MySQL Connector/J sudah ditambahkan ke Libraries di NetBeans:
   - Right click project > Properties > Libraries > Add JAR/Folder
   - Pilih file `mysql-connector-java-8.x.x.jar`

### 2. Error: "Email atau username sudah terdaftar"
**Penyebab:**
- Email atau username yang digunakan sudah ada di database

**Solusi:**
- Gunakan email lain
- Atau hapus data lama dari database:
  ```sql
  DELETE FROM user WHERE email='email_yang_duplikat';
  ```

### 3. Error: "Format email tidak valid"
**Penyebab:**
- Email tidak mengandung "@" atau "."

**Solusi:**
- Gunakan format email yang benar, contoh: `nama@email.com`

### 4. Error: "Password tidak sama"
**Penyebab:**
- Password dan konfirmasi password berbeda

**Solusi:**
- Pastikan kedua field password sama

### 5. Error: "MySQL Driver tidak ditemukan"
**Penyebab:**
- MySQL Connector/J belum ditambahkan ke project

**Solusi:**
1. Download MySQL Connector/J dari: https://dev.mysql.com/downloads/connector/j/
2. Di NetBeans:
   - Right click project > Properties
   - Libraries > Add JAR/Folder
   - Pilih file JAR yang sudah didownload
   - Klik OK

### 6. Error: "Access denied for user 'root'@'localhost'"
**Penyebab:**
- Password MySQL salah atau tidak diisi

**Solusi:**
- Edit file `src/model/koneksi.java`:
  ```java
  String pass = "password_mysql_anda";
  ```

### 7. Error: "Unknown database 'db_absensi'"
**Penyebab:**
- Database belum dibuat

**Solusi:**
- Import file `database/db_absensi.sql` ke MySQL

### 8. Error saat split email (ArrayIndexOutOfBoundsException)
**Penyebab:**
- Email tidak mengandung "@"

**Solusi:**
- Sudah diperbaiki di kode terbaru dengan validasi email

## Cara Cek Error di NetBeans

1. **Lihat Output Console:**
   - Window > Output > Output
   - Error akan muncul di console

2. **Lihat Stack Trace:**
   - Error biasanya muncul dengan stack trace lengkap
   - Cari baris yang menunjukkan lokasi error

3. **Test Koneksi Database:**
   ```java
   // Tambahkan di main method untuk test
   Connection conn = koneksi.getKoneksi();
   if (conn != null) {
       System.out.println("Koneksi OK!");
   } else {
       System.out.println("Koneksi GAGAL!");
   }
   ```

## Test Manual di MySQL

1. Buka MySQL Command Line atau phpMyAdmin
2. Test koneksi:
   ```sql
   USE db_absensi;
   SELECT * FROM user;
   ```
3. Test insert manual:
   ```sql
   INSERT INTO user (id, username, email, password, role) 
   VALUES ('TEST001', 'testuser', 'test@test.com', 'test123', 'Karyawan');
   ```

## Checklist Sebelum Registrasi

- [ ] MySQL/MariaDB sudah berjalan
- [ ] Database `db_absensi` sudah dibuat
- [ ] Tabel `user` sudah ada
- [ ] MySQL Connector/J sudah ditambahkan ke project
- [ ] Username dan password di `koneksi.java` sudah benar
- [ ] Email yang digunakan valid (mengandung @ dan .)
- [ ] Password minimal 6 karakter
- [ ] Password dan konfirmasi password sama

## Jika Masih Error

1. Cek console output di NetBeans untuk error message lengkap
2. Pastikan semua file sudah di-compile (Build > Clean and Build)
3. Restart NetBeans
4. Restart MySQL service
5. Cek log MySQL untuk error detail

