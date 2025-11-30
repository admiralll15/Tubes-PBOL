-- ============================================
-- DATABASE: db_absensi
-- Sistem Manajemen Absensi dan Penggajian
-- ============================================

-- Hapus database jika sudah ada (opsional, hati-hati!)
-- DROP DATABASE IF EXISTS db_absensi;

-- Buat database baru
CREATE DATABASE IF NOT EXISTS db_absensi;
USE db_absensi;

-- ============================================
-- TABEL: user
-- Untuk login dan registrasi
-- ============================================
CREATE TABLE IF NOT EXISTS user (
    id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'HRD', 'Karyawan') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- TABEL: karyawan
-- Data master karyawan
-- ============================================
CREATE TABLE IF NOT EXISTS karyawan (
    id VARCHAR(50) PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    jabatan VARCHAR(50) NOT NULL,
    no_hp VARCHAR(20),
    no_rekening VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- TABEL: cuti
-- Pengajuan dan persetujuan cuti
-- ============================================
CREATE TABLE IF NOT EXISTS cuti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_karyawan VARCHAR(50) NOT NULL,
    tanggal_mulai DATE NOT NULL,
    tanggal_selesai DATE NOT NULL,
    keterangan TEXT,
    status ENUM('Pending', 'Disetujui', 'Ditolak') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_karyawan) REFERENCES karyawan(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- TABEL: absensi_karyawan
-- Data absensi detail dengan jam masuk/pulang
-- ============================================
CREATE TABLE IF NOT EXISTS absensi_karyawan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    id_karyawan VARCHAR(50) NOT NULL,
    jabatan VARCHAR(50),
    tanggal DATE NOT NULL,
    jam_masuk TIME,
    jam_pulang TIME,
    status ENUM('Hadir', 'Izin', 'Sakit') DEFAULT 'Hadir',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_karyawan) REFERENCES karyawan(id) ON DELETE CASCADE,
    UNIQUE KEY unique_absensi (id_karyawan, tanggal)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- TABEL: absensi
-- Data absensi sederhana (backup/legacy)
-- ============================================
CREATE TABLE IF NOT EXISTS absensi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_karyawan VARCHAR(50) NOT NULL,
    tanggal DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_karyawan) REFERENCES karyawan(id) ON DELETE CASCADE,
    UNIQUE KEY unique_absensi_simple (id_karyawan, tanggal)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- TABEL: penggajian
-- Data penggajian karyawan
-- ============================================
CREATE TABLE IF NOT EXISTS penggajian (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_karyawan VARCHAR(50) NOT NULL,
    gaji_pokok INT NOT NULL DEFAULT 0,
    jam_lembur INT DEFAULT 0,
    upah_lembur_per_jam INT DEFAULT 0,
    lembur INT DEFAULT 0,
    potongan INT DEFAULT 0,
    total INT NOT NULL DEFAULT 0,
    bulan VARCHAR(20) NOT NULL,
    tahun VARCHAR(4) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_karyawan) REFERENCES karyawan(id) ON DELETE CASCADE,
    UNIQUE KEY unique_penggajian (id_karyawan, bulan, tahun)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- DATA SAMPLE (Optional)
-- ============================================

-- Insert sample user untuk testing
INSERT INTO user (id, username, email, password, role) VALUES
('USR001', 'admin', 'admin@stafflink.com', 'admin123', 'Admin'),
('USR002', 'hrd', 'hrd@stafflink.com', 'hrd123', 'HRD'),
('USR003', 'karyawan1', 'karyawan1@stafflink.com', 'karyawan123', 'Karyawan');

-- Insert sample karyawan
INSERT INTO karyawan (id, nama, jabatan, no_hp, no_rekening) VALUES
('K001', 'Budi Santoso', 'Manager', '081234567890', '1234567890'),
('K002', 'Siti Nurhaliza', 'HRD', '081234567891', '1234567891'),
('K003', 'Ahmad Fauzi', 'Karyawan', '081234567892', '1234567892'),
('K004', 'Dewi Sartika', 'Karyawan', '081234567893', '1234567893');

-- ============================================
-- INDEXES untuk performa
-- ============================================
CREATE INDEX idx_karyawan_nama ON karyawan(nama);
CREATE INDEX idx_cuti_karyawan ON cuti(id_karyawan);
CREATE INDEX idx_cuti_status ON cuti(status);
CREATE INDEX idx_absensi_tanggal ON absensi_karyawan(tanggal);
CREATE INDEX idx_penggajian_periode ON penggajian(bulan, tahun);

-- ============================================
-- VIEWS (Optional - untuk reporting)
-- ============================================

-- View untuk laporan absensi lengkap
CREATE OR REPLACE VIEW v_laporan_absensi AS
SELECT 
    a.id,
    a.id_karyawan,
    k.nama,
    k.jabatan,
    a.tanggal,
    a.jam_masuk,
    a.jam_pulang,
    a.status,
    TIMESTAMPDIFF(HOUR, a.jam_masuk, a.jam_pulang) AS jam_kerja
FROM absensi_karyawan a
JOIN karyawan k ON a.id_karyawan = k.id;

-- View untuk laporan penggajian lengkap
CREATE OR REPLACE VIEW v_laporan_penggajian AS
SELECT 
    p.id,
    p.id_karyawan,
    k.nama,
    k.jabatan,
    p.gaji_pokok,
    p.jam_lembur,
    p.upah_lembur_per_jam,
    p.lembur,
    p.potongan,
    p.total,
    p.bulan,
    p.tahun,
    p.created_at
FROM penggajian p
JOIN karyawan k ON p.id_karyawan = k.id;

-- View untuk status cuti karyawan
CREATE OR REPLACE VIEW v_status_cuti AS
SELECT 
    c.id,
    c.id_karyawan,
    k.nama,
    k.jabatan,
    c.tanggal_mulai,
    c.tanggal_selesai,
    DATEDIFF(c.tanggal_selesai, c.tanggal_mulai) + 1 AS jumlah_hari,
    c.keterangan,
    c.status,
    c.created_at
FROM cuti c
JOIN karyawan k ON c.id_karyawan = k.id;

-- ============================================
-- SELESAI
-- ============================================
-- Database berhasil dibuat!
-- Pastikan MySQL/MariaDB sudah berjalan
-- Import file ini melalui phpMyAdmin atau command line:
-- mysql -u root -p < db_absensi.sql

