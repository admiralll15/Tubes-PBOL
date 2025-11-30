# Database db_absensi

## Instalasi Database

### Cara 1: Menggunakan phpMyAdmin
1. Buka phpMyAdmin di browser
2. Klik tab "Import"
3. Pilih file `db_absensi.sql`
4. Klik "Go" untuk mengimpor

### Cara 2: Menggunakan Command Line
```bash
mysql -u root -p < db_absensi.sql
```

### Cara 3: Menggunakan MySQL Workbench
1. Buka MySQL Workbench
2. File > Open SQL Script
3. Pilih file `db_absensi.sql`
4. Klik tombol Execute (⚡)

## Struktur Database

### Tabel yang Dibuat:
1. **user** - Data pengguna untuk login/register
2. **karyawan** - Data master karyawan
3. **cuti** - Pengajuan dan persetujuan cuti
4. **absensi_karyawan** - Data absensi detail dengan jam
5. **absensi** - Data absensi sederhana (backup)
6. **penggajian** - Data penggajian karyawan

### Data Sample
Database sudah termasuk data sample untuk testing:
- 3 user (Admin, HRD, Karyawan)
- 4 karyawan sample

**Login Testing:**
- Admin: username=`admin`, password=`admin123`
- HRD: username=`hrd`, password=`hrd123`
- Karyawan: username=`karyawan1`, password=`karyawan123`

## Konfigurasi Koneksi

Pastikan file `src/model/koneksi.java` sesuai dengan konfigurasi MySQL Anda:

```java
String url = "jdbc:mysql://localhost:3306/db_absensi";
String user = "root";
String pass = "";  // Sesuaikan dengan password MySQL Anda
```

## Catatan Penting

1. **Password MySQL**: Jika MySQL Anda menggunakan password, ubah di file `koneksi.java`
2. **Port MySQL**: Default port adalah 3306, sesuaikan jika berbeda
3. **Driver JDBC**: Pastikan MySQL Connector/J sudah ditambahkan ke project
4. **Foreign Keys**: Semua foreign key menggunakan `ON DELETE CASCADE` untuk menjaga integritas data

## Troubleshooting

### Error: "Access denied for user"
- Pastikan username dan password MySQL benar
- Pastikan user memiliki hak akses untuk membuat database

### Error: "Unknown database"
- Pastikan database `db_absensi` sudah dibuat
- Import ulang file SQL

### Error: "Table already exists"
- Hapus database lama: `DROP DATABASE db_absensi;`
- Import ulang file SQL

## Backup Database

Untuk backup database:
```bash
mysqldump -u root -p db_absensi > backup_db_absensi.sql
```

## Restore Database

Untuk restore database:
```bash
mysql -u root -p db_absensi < backup_db_absensi.sql
```

