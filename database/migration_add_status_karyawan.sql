-- Migration: Menambahkan kolom status ke tabel karyawan
-- Tujuan: Untuk fitur Aktifkan/Nonaktifkan akun karyawan

-- Tambahkan kolom status ke tabel karyawan
ALTER TABLE `karyawan` 
ADD COLUMN `status` ENUM('Aktif', 'Nonaktif') NOT NULL DEFAULT 'Aktif' 
AFTER `no_rekening`;

-- Update semua karyawan yang sudah ada menjadi status Aktif
UPDATE `karyawan` SET `status` = 'Aktif' WHERE `status` IS NULL;

-- Verifikasi perubahan
SELECT id, nama, jabatan, status FROM karyawan;
