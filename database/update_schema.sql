-- ============================================
-- QUERY UPDATE SCHEMA DATABASE PBOL
-- Paste langsung ke phpMyAdmin (tab SQL)
-- ============================================

-- 1. UPDATE TABEL KARYAWAN
-- Hapus kolom no_hp dan no_rekening, tambahkan status
ALTER TABLE `karyawan` DROP COLUMN `no_hp`;
ALTER TABLE `karyawan` DROP COLUMN `no_rekening`;
ALTER TABLE `karyawan` ADD COLUMN `status` ENUM('Aktif','Nonaktif') NOT NULL DEFAULT 'Aktif' AFTER `jabatan`;

-- Update semua karyawan existing menjadi Aktif
UPDATE `karyawan` SET `status` = 'Aktif';

-- ============================================

-- 2. BUAT TABEL LEMBUR
CREATE TABLE `lembur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_karyawan` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `jam_lembur` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `fk_lembur_karyawan` (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tambahkan foreign key constraint
ALTER TABLE `lembur`
  ADD CONSTRAINT `fk_lembur_karyawan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

-- ============================================

-- 3. BUAT VIEW LAPORAN LEMBUR
DROP VIEW IF EXISTS `v_laporan_lembur`;

CREATE VIEW `v_laporan_lembur` AS 
SELECT 
  `l`.`id` AS `id`,
  `l`.`id_karyawan` AS `id_karyawan`,
  `l`.`nama` AS `nama`,
  `l`.`jabatan` AS `jabatan`,
  `l`.`tanggal` AS `tanggal`,
  `l`.`jam_lembur` AS `jam_lembur`,
  `l`.`created_at` AS `created_at`
FROM `lembur` `l` 
ORDER BY `l`.`tanggal` DESC, `l`.`id_karyawan` ASC;

-- ============================================

-- 4. INSERT DATA SAMPLE LEMBUR (OPSIONAL - hapus jika tidak perlu)
INSERT INTO `lembur` (`id_karyawan`, `nama`, `jabatan`, `tanggal`, `jam_lembur`) VALUES
('K001', 'Agustinus Pasaribu', 'Karyawan', '2025-11-28', 3);

-- ============================================
-- SELESAI! Semua perubahan schema sudah diterapkan
-- ============================================
