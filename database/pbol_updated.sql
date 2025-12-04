-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 04, 2025 at 12:51 AM
-- Server version: 10.11.10-MariaDB-log
-- PHP Version: 8.3.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbol`
--

-- --------------------------------------------------------

--
-- Table structure for table `absensi`
--

CREATE TABLE `absensi` (
  `id` int(11) NOT NULL,
  `id_karyawan` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `jam_masuk` time DEFAULT NULL,
  `jam_pulang` time DEFAULT NULL,
  `status` enum('Hadir','Izin','Sakit','Alpha') DEFAULT 'Hadir',
  `created_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `absensi`
--

INSERT INTO `absensi` (`id`, `id_karyawan`, `tanggal`, `jam_masuk`, `jam_pulang`, `status`, `created_at`) VALUES
(1, 'K001', '2025-11-28', '13:53:04', '20:53:04', 'Hadir', '2025-11-28 06:53:23');

-- --------------------------------------------------------

--
-- Table structure for table `cuti`
--

CREATE TABLE `cuti` (
  `id` int(11) NOT NULL,
  `id_karyawan` varchar(50) NOT NULL,
  `tanggal_mulai` date NOT NULL,
  `tanggal_selesai` date NOT NULL,
  `keterangan` text DEFAULT NULL,
  `status` enum('Pending','Disetujui','Ditolak') DEFAULT 'Pending',
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cuti`
--

INSERT INTO `cuti` (`id`, `id_karyawan`, `tanggal_mulai`, `tanggal_selesai`, `keterangan`, `status`, `created_at`, `updated_at`) VALUES
(1, 'K001', '2025-11-02', '2025-11-09', 'Honeymoon', 'Disetujui', '2025-11-25 17:22:36', '2025-12-02 16:22:10'),
(2, 'K001', '2025-11-15', '2025-11-20', 'jangenam', 'Disetujui', '2025-11-26 03:10:55', '2025-12-02 16:52:21');

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
-- UPDATED: Menghapus no_hp dan no_rekening, menambahkan status
--

CREATE TABLE `karyawan` (
  `id` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `status` enum('Aktif','Nonaktif') NOT NULL DEFAULT 'Aktif',
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `karyawan`
-- UPDATED: Menghapus kolom no_hp dan no_rekening, menambahkan status
--

INSERT INTO `karyawan` (`id`, `nama`, `jabatan`, `status`, `created_at`, `updated_at`) VALUES
('K001', 'Agustinus Pasaribu', 'Karyawan', 'Aktif', '2025-11-25 17:15:49', '2025-11-25 17:15:49'),
('K002', 'Admin1', 'Admin', 'Aktif', '2025-11-25 17:30:45', '2025-11-25 17:30:45'),
('K003', 'Kevin', 'HRD', 'Aktif', '2025-11-28 07:23:57', '2025-11-28 07:23:57'),
('K004', 'Doly Bukit', 'Karyawan', 'Aktif', '2025-12-02 07:59:29', '2025-12-02 07:59:29'),
('K005', '', 'Admin', 'Aktif', '2025-12-02 08:01:16', '2025-12-02 08:01:16'),
('K006', 'Leondo Admiral', 'Karyawan', 'Aktif', '2025-12-02 16:25:23', '2025-12-02 16:25:23');

-- --------------------------------------------------------

--
-- Table structure for table `lembur`
-- NEW: Tabel untuk mencatat lembur karyawan
--

CREATE TABLE `lembur` (
  `id` int(11) NOT NULL,
  `id_karyawan` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `jam_lembur` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lembur`
--

INSERT INTO `lembur` (`id`, `id_karyawan`, `nama`, `jabatan`, `tanggal`, `jam_lembur`, `created_at`) VALUES
(1, 'K001', 'Agustinus Pasaribu', 'Karyawan', '2025-11-28', 3, '2025-11-28 10:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `penggajian`
--

CREATE TABLE `penggajian` (
  `id` int(11) NOT NULL,
  `id_karyawan` varchar(50) NOT NULL,
  `bulan` varchar(20) NOT NULL,
  `tahun` varchar(4) NOT NULL,
  `gaji_pokok` int(11) NOT NULL DEFAULT 0,
  `jam_lembur` int(11) DEFAULT 0,
  `upah_lembur_per_jam` int(11) DEFAULT 0,
  `lembur` int(11) DEFAULT 0,
  `potongan` int(11) DEFAULT 0,
  `total` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penggajian`
--

INSERT INTO `penggajian` (`id`, `id_karyawan`, `bulan`, `tahun`, `gaji_pokok`, `jam_lembur`, `upah_lembur_per_jam`, `lembur`, `potongan`, `total`, `created_at`) VALUES
(1, 'K001', 'Januari', '2025', 2000000, 2, 200000, 400000, 400000, 2000000, '2025-11-26 14:56:53');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `id_karyawan` varchar(50) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','HRD','Karyawan') NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `id_karyawan`, `username`, `email`, `password`, `role`, `created_at`) VALUES
('USR001', 'K001', 'Agustinus Pasaribu', 'agustinus@gmail.com', 'agustinus123', 'Karyawan', '2025-11-25 17:15:49'),
('USR002', 'K002', 'Admin1', 'admin1@gamil.com', 'admin1', 'Admin', '2025-11-25 17:30:45'),
('USR003', 'K003', 'Kevin', 'kevin@gmail.com', 'kevin123', 'HRD', '2025-11-28 07:23:57'),
('USR004', 'K004', 'Doly Bukit', 'doly@gmail.com', 'doly123', 'Karyawan', '2025-12-02 07:59:29'),
('USR005', 'K005', '', '', '', 'Admin', '2025-12-02 08:01:16'),
('USR006', 'K006', 'Leondo Admiral', 'leondo@gmail.com', 'Mandiri2024', 'Karyawan', '2025-12-02 16:25:23');

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_laporan_absensi`
-- (See below for the actual view)
--
CREATE TABLE `v_laporan_absensi` (
`id` int(11)
,`id_karyawan` varchar(50)
,`nama` varchar(100)
,`jabatan` varchar(50)
,`tanggal` date
,`jam_masuk` time
,`jam_pulang` time
,`status` enum('Hadir','Izin','Sakit','Alpha')
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_laporan_lembur`
-- NEW: View untuk laporan lembur
--
CREATE TABLE `v_laporan_lembur` (
`id` int(11)
,`id_karyawan` varchar(50)
,`nama` varchar(100)
,`jabatan` varchar(50)
,`tanggal` date
,`jam_lembur` int(11)
,`created_at` timestamp
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_laporan_penggajian`
-- (See below for the actual view)
--
CREATE TABLE `v_laporan_penggajian` (
`id` int(11)
,`id_karyawan` varchar(50)
,`nama` varchar(100)
,`jabatan` varchar(50)
,`bulan` varchar(20)
,`tahun` varchar(4)
,`gaji_pokok` int(11)
,`jam_lembur` int(11)
,`lembur` int(11)
,`potongan` int(11)
,`total` int(11)
,`created_at` timestamp
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_status_cuti`
-- (See below for the actual view)
--
CREATE TABLE `v_status_cuti` (
`id` int(11)
,`id_karyawan` varchar(50)
,`nama` varchar(100)
,`jabatan` varchar(50)
,`tanggal_mulai` date
,`tanggal_selesai` date
,`jumlah_hari` int(9)
,`keterangan` text
,`status` enum('Pending','Disetujui','Ditolak')
,`created_at` timestamp
);

-- --------------------------------------------------------

--
-- Structure for view `v_laporan_absensi`
--
DROP TABLE IF EXISTS `v_laporan_absensi`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_laporan_absensi`  AS SELECT `a`.`id` AS `id`, `a`.`id_karyawan` AS `id_karyawan`, `k`.`nama` AS `nama`, `k`.`jabatan` AS `jabatan`, `a`.`tanggal` AS `tanggal`, `a`.`jam_masuk` AS `jam_masuk`, `a`.`jam_pulang` AS `jam_pulang`, `a`.`status` AS `status` FROM (`absensi` `a` join `karyawan` `k` on(`a`.`id_karyawan` = `k`.`id`))  ;

-- --------------------------------------------------------

--
-- Structure for view `v_laporan_lembur`
-- NEW: View untuk laporan lembur
--
DROP TABLE IF EXISTS `v_laporan_lembur`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_laporan_lembur`  AS SELECT `l`.`id` AS `id`, `l`.`id_karyawan` AS `id_karyawan`, `l`.`nama` AS `nama`, `l`.`jabatan` AS `jabatan`, `l`.`tanggal` AS `tanggal`, `l`.`jam_lembur` AS `jam_lembur`, `l`.`created_at` AS `created_at` FROM `lembur` `l` ORDER BY `l`.`tanggal` DESC, `l`.`id_karyawan` ASC ;

-- --------------------------------------------------------

--
-- Structure for view `v_laporan_penggajian`
--
DROP TABLE IF EXISTS `v_laporan_penggajian`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_laporan_penggajian`  AS SELECT `p`.`id` AS `id`, `p`.`id_karyawan` AS `id_karyawan`, `k`.`nama` AS `nama`, `k`.`jabatan` AS `jabatan`, `p`.`bulan` AS `bulan`, `p`.`tahun` AS `tahun`, `p`.`gaji_pokok` AS `gaji_pokok`, `p`.`jam_lembur` AS `jam_lembur`, `p`.`lembur` AS `lembur`, `p`.`potongan` AS `potongan`, `p`.`total` AS `total`, `p`.`created_at` AS `created_at` FROM (`penggajian` `p` join `karyawan` `k` on(`p`.`id_karyawan` = `k`.`id`))  ;

-- --------------------------------------------------------

--
-- Structure for view `v_status_cuti`
--
DROP TABLE IF EXISTS `v_status_cuti`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_status_cuti`  AS SELECT `c`.`id` AS `id`, `c`.`id_karyawan` AS `id_karyawan`, `k`.`nama` AS `nama`, `k`.`jabatan` AS `jabatan`, `c`.`tanggal_mulai` AS `tanggal_mulai`, `c`.`tanggal_selesai` AS `tanggal_selesai`, to_days(`c`.`tanggal_selesai`) - to_days(`c`.`tanggal_mulai`) + 1 AS `jumlah_hari`, `c`.`keterangan` AS `keterangan`, `c`.`status` AS `status`, `c`.`created_at` AS `created_at` FROM (`cuti` `c` join `karyawan` `k` on(`c`.`id_karyawan` = `k`.`id`))  ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_absensi` (`id_karyawan`,`tanggal`),
  ADD KEY `fk_absensi_karyawan` (`id_karyawan`);

--
-- Indexes for table `cuti`
--
ALTER TABLE `cuti`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cuti_karyawan` (`id_karyawan`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lembur`
-- NEW: Index untuk tabel lembur
--
ALTER TABLE `lembur`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_lembur_karyawan` (`id_karyawan`);

--
-- Indexes for table `penggajian`
--
ALTER TABLE `penggajian`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_gaji` (`id_karyawan`,`bulan`,`tahun`),
  ADD KEY `fk_gaji_karyawan` (`id_karyawan`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `fk_user_karyawan` (`id_karyawan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absensi`
--
ALTER TABLE `absensi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cuti`
--
ALTER TABLE `cuti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `lembur`
-- NEW: Auto increment untuk tabel lembur
--
ALTER TABLE `lembur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `penggajian`
--
ALTER TABLE `penggajian`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absensi`
--
ALTER TABLE `absensi`
  ADD CONSTRAINT `fk_absensi_karyawan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cuti`
--
ALTER TABLE `cuti`
  ADD CONSTRAINT `fk_cuti_karyawan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `lembur`
-- NEW: Foreign key untuk tabel lembur
--
ALTER TABLE `lembur`
  ADD CONSTRAINT `fk_lembur_karyawan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `penggajian`
--
ALTER TABLE `penggajian`
  ADD CONSTRAINT `fk_gaji_karyawan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_karyawan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
