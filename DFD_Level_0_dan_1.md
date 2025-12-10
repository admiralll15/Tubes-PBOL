# Data Flow Diagram (DFD) Komprehensif
## STAFFLINK - Sistem Manajemen Absensi dan Penggajian Karyawan

**Nama Sistem**: STAFFLINK  
**Tipe Sistem**: Desktop Application (Java Swing + MySQL)  
**Tujuan**: Mengelola data karyawan, absensi, cuti, dan penggajian secara terintegrasi  

---

## DFD LEVEL 0 (Context Diagram)

```
┌─────────────────────────────────────┐
│         EXTERNAL ENTITIES           │
├─────────────────────────────────────┤
│                                     │
│  ┌──────────┐  ┌──────────┐ ┌────────────┐
│  │ Karyawan │  │  Admin   │ │ Karyawan   │
│  │          │  │          │ │ HRD        │
│  │ (Register)│  │ (Manage) │ │(View/Mgmt) │
│  └────┬─────┘  └────┬─────┘ └─────┬──────┘
│       │             │              │
│       └─────────────┼──────────────┘
│                     │
│        ┌────────────▼────────────┐
│        │                         │
│        │ SISTEM MANAJEMEN        │
│        │ ABSENSI DAN PENGGAJIAN  │
│        │                         │
│        └────────────┬────────────┘
│                     │
│       ┌─────────────▼──────────────┐
│       │  DATABASE                  │
│       │  (MySQL - pbol)            │
│       │                            │
│       │  - Users                   │
│       │  - Karyawan                │
│       │  - Absensi                 │
│       │  - Cuti                    │
│       │  - Gaji                    │
│       └────────────────────────────┘
│
└─────────────────────────────────────┘
```

### Data Flow di Level 0:
- **Input dari Karyawan**: Data registrasi, check-in/out, pengajuan cuti
- **Input dari Admin**: Data karyawan, validasi kehadiran, data gaji
- **Input dari HRD**: Persetujuan cuti, perhitungan gaji, approval absensi
- **Output**: Laporan absensi, laporan gaji, slip gaji, status cuti, data karyawan

---

## DFD LEVEL 1 (Process Decomposition)

```
                    ┌────────────────────────────────────────────┐
                    │         EXTERNAL ENTITIES                  │
                    └────┬──────────────┬──────────────┬─────────┘
                         │              │              │
                    Karyawan           Admin          HRD
                         │              │              │
     ┌───────────────┐   │   ┌──────────────┐   ┌─────────────┐
     │   Proses 1    │◄──┼───┤  Proses 1.1  │───┤ P.Autentik. │
     │  AUTENTIKASI  │   │   │  Login/Reg   │   │             │
     │  & REGISTER   │   │   └──────────────┘   └─────────────┘
     └───────┬───────┘   │
             │           │
             ▼           │        ┌──────────────────────────┐
     ┌───────────────┐   │        │ D1: Data Users          │
     │               │◄──┼────────│ - email, password       │
     │  Proses 2     │   │        │ - role (User, Admin,    │
     │  MANAJEMEN    │   │        │   HRD)                  │
     │  KARYAWAN     │   │        └──────────────────────────┘
     │ 2.1-2.4       │   │
     │               │   │        ┌──────────────────────────┐
     │ -CRUD Karyaw. │───┼────────│ D2: Data Karyawan       │
     │               │   │        │ - ID, nama, jabatan     │
     └───────┬───────┘   │        │ - no_hp, no_rekening    │
             │           │        └──────────────────────────┘
             ▼           │
     ┌──────────────────┐ │        ┌──────────────────────────┐
     │   Proses 3      │ │        │ D3: Data Absensi        │
     │   ABSENSI       │◄┼────────│ - id_karyawan, tanggal  │
     │ 3.1-3.2         │ │        │ - jam_masuk, jam_pulang │
     │                 │ │        │ - status                │
     │ -Check-In       │ │        └──────────────────────────┘
     │ -Check-Out      │ │
     │ -Laporan        │ │        ┌──────────────────────────┐
     └───────┬─────────┘ │        │ D4: Data Cuti           │
             │           │        │ - id, id_karyawan       │
             ▼           │        │ - tgl_mulai, tgl_selesai│
     ┌──────────────────┐ │        │ - status (Pending, ✓)   │
     │   Proses 4      │ │        └──────────────────────────┘
     │   PENGAJUAN &   │◄┼────────
     │   PERSETUJUAN   │ │        ┌──────────────────────────┐
     │   CUTI          │ │        │ D5: Data Gaji           │
     │ 4.1-4.2         │ │        │ - id, gapok, lembur     │
     │                 │ │        │ - potongan, bulan, tahun│
     │ -Ajukan         │ │        └──────────────────────────┘
     │ -Approve/Reject │ │
     └───────┬─────────┘ │
             │           │
             ▼           │
     ┌──────────────────┐ │
     │   Proses 5      │ │
     │   PENGGAJIAN    │◄┼────────
     │ 5.1-5.2         │ │
     │                 │ │
     │ -Hitung Gaji    │ │
     │ -Slip Gaji      │ │
     └──────────────────┘ │
             │            │
             └────────────┘
```

---

## DETAIL PROSES LEVEL 1

### **PROSES 1: AUTENTIKASI & REGISTER (P1)**

```
Karyawan ─ Permintaan Login/Register ─►┌─────────────────────┐
                                        │   P1: AUTENTIKASI   │
                                        │  & REGISTER         │
                                        │                     │
                                        │ P1.1: Validasi      │
                                        │       Email/Pass    │
                                        │                     │
                                        │ P1.2: Register      │
                                        │       User Baru     │
                                        │                     │
                                        └────────┬────────────┘
                                                 │
                                    ┌────────────▼───────────┐
                                    │ D1: Data Users         │
                                    │ Simpan/Cek Kredensial  │
                                    └────────────────────────┘
                                                 │
                                    User Valid ─────► Redirect ke Dashboard
                                    User Tidak Valid ─► Perlihatkan Error
```

**Input**: Email, Password, Role (saat register)
**Output**: Authentication Token / Session, Error Message
**Fungsi**: Login, Registrasi User


### **PROSES 2: MANAJEMEN KARYAWAN (P2)**

```
Admin ──────► Permintaan CRUD ──────►┌──────────────────────┐
                                      │ P2: MANAJEMEN        │
                                      │  KARYAWAN            │
                                      │                      │
                                      │ P2.1: CREATE         │
                                      │       Karyawan Baru  │
                                      │                      │
                                      │ P2.2: READ           │
                                      │       Data Karyawan  │
                                      │                      │
                                      │ P2.3: UPDATE         │
                                      │       Edit Data      │
                                      │                      │
                                      │ P2.4: DELETE         │
                                      │       Hapus Data     │
                                      │                      │
                                      └──────────┬───────────┘
                                                 │
                                   ┌─────────────▼───────────┐
                                   │ D2: Data Karyawan       │
                                   │ ID | Nama | Jabatan |..│
                                   └─────────────────────────┘
```

**Input**: Nama, Jabatan, No. HP, No. Rekening
**Output**: Data Karyawan, List Karyawan
**Fungsi**: Tambah, Edit, Lihat, Hapus Data Karyawan


### **PROSES 3: MANAJEMEN ABSENSI (P3)**

```
Karyawan ────────┐
                 │
Admin/HRD ──────►├─► Permintaan Absensi ──►┌──────────────────┐
                 │                          │ P3: MANAJEMEN    │
                 │                          │  ABSENSI         │
                 │                          │                  │
                 │                          │ P3.1: Check-In   │
                 │                          │ Validasi jam     │
                 │                          │                  │
                 │                          │ P3.2: Check-Out  │
                 │                          │ Catat jam pulang │
                 │                          │                  │
                 │                          │ P3.3: Laporan    │
                 │                          │ Generate Report  │
                 │                          └────────┬─────────┘
                 │                                   │
                 │                 ┌─────────────────▼──────────┐
                 │                 │ D3: Data Absensi           │
                 │                 │ ID | ID_Karyawan | Tanggal │
                 │                 │ Jam_Masuk | Jam_Pulang    │
                 │                 └────────────────────────────┘
                 │                                   │
                 └──────────────────────────────────┘
```

**Input**: ID Karyawan, Tanggal, Jam Masuk, Jam Pulang, Status
**Output**: Konfirmasi Check-In/Out, Laporan Absensi
**Fungsi**: Check-In, Check-Out, Lihat Laporan Absensi


### **PROSES 4: PENGAJUAN & PERSETUJUAN CUTI (P4)**

```
Karyawan ───┐                 ┌─────────────────────────────┐
            │                 │ P4: PENGAJUAN & PERSETUJUAN │
            ├──► Request ────►│     CUTI                    │
            │                 │                             │
            │                 │ P4.1: Ajukan Cuti          │
            │                 │ Simpan Request             │
            │                 │                             │
Admin/HRD ──┤                 │ P4.2: Approve/Reject       │
            │                 │ Update Status              │
            │                 │ (Pending→Approve/Reject)   │
            │                 │                             │
Karyawan ───┘                 └──────────┬──────────────────┘
                                         │
                              ┌──────────▼──────────────┐
                              │ D4: Data Cuti          │
                              │ ID | ID_Karyawan      │
                              │ Tgl_Mulai | Tgl_Akhir │
                              │ Status | Keterangan   │
                              └───────────────────────┘
```

**Input**: ID Karyawan, Tanggal Mulai, Tanggal Selesai, Keterangan, Status Approval
**Output**: Konfirmasi Pengajuan, Notifikasi Approval, Status Cuti
**Fungsi**: Ajukan Cuti, Setujui/Tolak Cuti, Lihat Status


### **PROSES 5: MANAJEMEN PENGGAJIAN (P5)**

```
HRD ──────┐
          │
Admin ───►├──► Request Gaji ──►┌────────────────────────┐
          │                    │ P5: MANAJEMEN         │
          │                    │  PENGGAJIAN           │
Karyawan ─┘                    │                       │
                               │ P5.1: Hitung Gaji    │
                               │ Formula:             │
                               │ Gaji = Gapok +       │
                               │ (Jam Lembur *        │
                               │  Upah/Jam) -         │
                               │ Potongan             │
                               │                       │
                               │ P5.2: Generate       │
                               │ Slip Gaji (per user) │
                               │ Laporan Penggajian   │
                               │                       │
                               └────────┬─────────────┘
                                        │
                             ┌──────────▼──────────────┐
                             │ D5: Data Gaji          │
                             │ ID | ID_Karyawan      │
                             │ Gapok | Lembur        │
                             │ Potongan | Bulan/Tahun│
                             │ Gaji Bersih           │
                             └───────────────────────┘
```

**Input**: ID Karyawan, Gapok, Jam Lembur, Upah Lembur/Jam, Potongan, Bulan, Tahun
**Output**: Slip Gaji, Laporan Penggajian, Gaji Bersih
**Fungsi**: Hitung Gaji, Generate Slip Gaji, Lihat Laporan Penggajian


---

## DATA STORE STRUCTURE

### **D1: DATA USERS**
| Field | Type | Deskripsi |
|-------|------|-----------|
| email | VARCHAR(100) | Email user (PK) |
| password | VARCHAR(255) | Password (hashed) |
| role | ENUM | Admin, HRD, Karyawan |
| id_karyawan | VARCHAR(50) | FK ke karyawan |
| created_at | TIMESTAMP | Waktu pembuatan |

### **D2: DATA KARYAWAN**
| Field | Type | Deskripsi |
|-------|------|-----------|
| id | VARCHAR(50) | ID Karyawan (PK) |
| nama | VARCHAR(100) | Nama lengkap |
| jabatan | VARCHAR(50) | Posisi/Jabatan |
| no_hp | VARCHAR(20) | Nomor handphone |
| no_rekening | VARCHAR(50) | No. rekening |
| created_at | TIMESTAMP | Waktu pembuatan |

### **D3: DATA ABSENSI**
| Field | Type | Deskripsi |
|-------|------|-----------|
| id | INT | ID Absensi (PK) |
| id_karyawan | VARCHAR(50) | FK ke karyawan |
| tanggal | DATE | Tanggal absensi |
| jam_masuk | TIME | Waktu check-in |
| jam_pulang | TIME | Waktu check-out |
| status | ENUM | Hadir, Izin, Sakit, Alpha |
| created_at | TIMESTAMP | Waktu pembuatan |

### **D4: DATA CUTI**
| Field | Type | Deskripsi |
|-------|------|-----------|
| id | INT | ID Cuti (PK) |
| id_karyawan | VARCHAR(50) | FK ke karyawan |
| tanggal_mulai | DATE | Awal cuti |
| tanggal_selesai | DATE | Akhir cuti |
| keterangan | TEXT | Alasan cuti |
| status | ENUM | Pending, Disetujui, Ditolak |
| created_at | TIMESTAMP | Waktu pembuatan |

### **D5: DATA GAJI**
| Field | Type | Deskripsi |
|-------|------|-----------|
| id | INT | ID Gaji (PK) |
| id_karyawan | VARCHAR(50) | FK ke karyawan |
| gapok | INT | Gaji pokok |
| jam_lembur | INT | Jumlah jam lembur |
| upah_lembur_per_jam | INT | Upah per jam |
| potongan | INT | Potongan gaji |
| gaji_bersih | INT | Total gaji bersih |
| bulan | VARCHAR(10) | Bulan payroll |
| tahun | VARCHAR(4) | Tahun payroll |
| created_at | TIMESTAMP | Waktu pembuatan |

---

## RINGKASAN ALUR SISTEM

```
┌─────────────────────────────────────────────────────────────────┐
│              ALUR PROSES SISTEM SECARA KESELURUHAN              │
└─────────────────────────────────────────────────────────────────┘

1. REGISTRASI & LOGIN
   └─► User mendaftar/login via Form
       └─► Sistem validasi di P1
           └─► Redirect ke Dashboard sesuai role

2. ADMIN MENGELOLA KARYAWAN (P2)
   └─► Tambah/Edit/Hapus data karyawan
       └─► Simpan ke D2
           └─► Update list karyawan

3. KARYAWAN MELAKUKAN ABSENSI (P3)
   └─► Karyawan check-in di pagi hari
       └─► Sistem catat jam masuk di D3
           └─► Karyawan check-out di sore hari
               └─► Sistem catat jam pulang di D3
                   └─► Admin/HRD lihat laporan absensi

4. KARYAWAN MENGAJUKAN CUTI (P4)
   └─► Karyawan ajukan cuti via form
       └─► Sistem simpan request di D4 (status: Pending)
           └─► HRD review dan approve/reject
               └─► Status berubah di D4
                   └─► Notifikasi ke karyawan

5. HRD MENGHITUNG GAJI (P5)
   └─► HRD input data gaji (gapok, lembur, potongan)
       └─► Sistem hitung gaji bersih
           └─► Simpan ke D5
               └─► Generate slip gaji per karyawan
                   └─► Karyawan dapat lihat slip gaji
```

---

## ROLE DAN AKSES

| Role | Akses Proses | Deskripsi |
|------|--------------|-----------|
| **KARYAWAN** | P1, P3 (check-in/out), P4 (ajukan cuti), P5 (lihat slip) | Absensi & pengajuan cuti |
| **ADMIN** | P1, P2 (full CRUD), P3 (laporan), P4 (review), P5 (laporan) | Manajemen data & laporan |
| **HRD** | P1, P3 (laporan), P4 (approve/reject), P5 (hitung gaji) | Payroll & cuti management |

---

# DFD LEVEL 2 - DETAIL PROSES UTAMA

## PROSES 1: AUTENTIKASI & REGISTER

```
External Entity: PENGGUNA
    │
    ├─────► EMAIL, PASSWORD ────────────────────────┐
    │                                               │
    │       ┌────────────────────────────────────┐  │
    │       │  P1.0: AUTENTIKASI & REGISTER     │  │
    │       │                                    │  │
    │       │  ┌──────────────────────────────┐ │  │
    │       │  │ P1.1: FORM LOGIN             │ │  │
    │       │  │ Input: email, password       │ │  │
    │       │  │ Output: Validasi input       │ │  │
    │       │  └───────────┬──────────────────┘ │  │
    │       │              │                    │  │
    │       │  ┌───────────▼──────────────────┐ │  │
    │       │  │ P1.2: CEK DATABASE           │ │  │
    │       │  │ Query: SELECT user WHERE..   │ │  │
    │       │  │ Output: User data / Not Found│ │  │
    │       │  └───────────┬──────────────────┘ │  │
    │       │              │                    │  │
    │       │      ┌───────▼────────┐           │  │
    │       │      │ USER DITEMUKAN? │           │  │
    │       │      └───────┬────────┘           │  │
    │       │              │                    │  │
    │       │    ┌─────────┴─────────┐          │  │
    │       │    │                   │          │  │
    │       │    ▼                   ▼          │  │
    │       │  ┌────────┐        ┌──────────┐ │  │
    │       │  │YA:P1.3 │        │TIDAK:P1.4│ │  │
    │       │  │CEK PASS │        │REGISTER  │ │  │
    │       │  └────────┘        └──────────┘ │  │
    │       │      │                   │      │  │
    │       │      └────────┬──────────┘      │  │
    │       │               ▼                 │  │
    │       │  ┌─────────────────────────┐   │  │
    │       │  │ P1.5: SIMPAN SESSION    │   │  │
    │       │  │ - user_id               │   │  │
    │       │  │ - role (K/A/H)          │   │  │
    │       │  │ - id_karyawan           │   │  │
    │       │  │ - nama, jabatan         │   │  │
    │       │  └─────────────────────────┘   │  │
    │       │               │                 │  │
    │       └───────────────┼─────────────────┘  │
    │                       │                    │
    └─────────────────────────────────────────────┘
            │
            └───► REDIRECT TO DASHBOARD (by Role)
                  - Karyawan -> Karyawan Dashboard
                  - Admin -> Admin Dashboard
                  - HRD -> HRD Dashboard

DATA STORES:
D1.1: user (email, password, role, id_karyawan)
D1.2: karyawan (id, nama, jabatan)
```

---

## PROSES 2: MANAJEMEN DATA KARYAWAN (Admin Only)

```
ADMIN
  │
  ├─ Request: GET DAFTAR KARYAWAN ──────────────────────┐
  │                                                      │
  │  ┌────────────────────────────────────────────────┐ │
  │  │ P2.0: MANAJEMEN KARYAWAN                       │ │
  │  │                                                 │ │
  │  │ ┌──────────────────────────────────────────┐  │ │
  │  │ │ P2.1: TAMPILKAN TABEL KARYAWAN           │  │ │
  │  │ │ SELECT * FROM karyawan                   │  │ │
  │  │ │ Output: Tabel (ID, Nama, Jabatan, HP)    │  │ │
  │  │ └──────────────────────────────────────────┘  │ │
  │  │              │                                 │ │
  │  │  ┌───────────┼───────────┬──────────┐         │ │
  │  │  │           │           │          │         │ │
  │  │  ▼           ▼           ▼          ▼         │ │
  │  │ ┌────────┐ ┌───────┐ ┌───────┐ ┌───────┐    │ │
  │  │ │P2.2:   │ │P2.3:  │ │P2.4:  │ │P2.5:  │    │ │
  │  │ │ADD     │ │VIEW   │ │EDIT   │ │DELETE │    │ │
  │  │ │KARYAW. │ │DETAIL │ │DATA   │ │DATA   │    │ │
  │  │ └───┬────┘ └───┬───┘ └───┬───┘ └───┬───┘    │ │
  │  │     │          │         │        │         │ │
  │  │ ┌───▼──────────▼────────▼────────▼────┐    │ │
  │  │ │ P2.6: VALIDASI & SIMPAN KE DATABASE  │    │ │
  │  │ │ Cek: nama, jabatan tidak kosong      │    │ │
  │  │ │ INSERT/UPDATE/DELETE karyawan        │    │ │
  │  │ │ Output: Konfirmasi berhasil/gagal    │    │ │
  │  │ └────────────────────────────────────┘    │ │
  │  │                 │                          │ │
  │  └─────────────────┼──────────────────────────┘ │
  │                    │                             │
  └────────────────────┼─────────────────────────────┘
                       ▼
                  Response: Success/Error Message

DATA STORES:
D2.1: karyawan (id, nama, jabatan, no_hp, no_rekening)
```

---

## PROSES 3: MANAJEMEN ABSENSI (Karyawan + Admin/HRD)

```
┌─ KARYAWAN                    ADMIN/HRD ──────────┐
│      │                           │                 │
│      │ Check-In Request          │ Laporan Request │
│      │ (Pagi)                    │                 │
│      │                           │                 │
│      └───────────────┬───────────┴────────────────┐
│                      │                             │
│         ┌────────────▼──────────────────────────┐ │
│         │ P3.0: MANAJEMEN ABSENSI               │ │
│         │                                        │ │
│         │ ┌──────────────────────────────────┐  │ │
│         │ │ P3.1: CHECK-IN (JAM MASUK)       │  │ │
│         │ │                                   │  │ │
│         │ │ Input:                            │  │ │
│         │ │ - id_karyawan (dari session)      │  │ │
│         │ │ - tanggal (hari ini)              │  │ │
│         │ │ - jam_masuk (waktu saat ini)      │  │ │
│         │ │                                   │  │ │
│         │ │ Process:                          │  │ │
│         │ │ 1. Cek apakah sudah ada record    │  │ │
│         │ │    untuk tanggal hari ini         │  │ │
│         │ │ 2. Jika belum: INSERT baru        │  │ │
│         │ │ 3. Jika sudah: Error (sudah cek) │  │ │
│         │ │                                   │  │ │
│         │ │ Output: Konfirmasi check-in OK    │  │ │
│         │ └──────────────────────────────────┘  │ │
│         │              │                        │ │
│         │ ┌────────────▼──────────────────────┐  │ │
│         │ │ P3.2: CHECK-OUT (JAM PULANG)      │  │ │
│         │ │                                   │  │ │
│         │ │ Input:                            │  │ │
│         │ │ - id_karyawan (dari session)      │  │ │
│         │ │ - tanggal (hari ini)              │  │ │
│         │ │ - jam_pulang (waktu saat ini)     │  │ │
│         │ │                                   │  │ │
│         │ │ Process:                          │  │ │
│         │ │ 1. Cari record yg sudah check-in │  │ │
│         │ │ 2. UPDATE jam_pulang              │  │ │
│         │ │ 3. Jika belum check-in: Error     │  │ │
│         │ │                                   │  │ │
│         │ │ Output: Konfirmasi check-out OK   │  │ │
│         │ └──────────────────────────────────┘  │ │
│         │              │                        │ │
│         │ ┌────────────▼──────────────────────┐  │ │
│         │ │ P3.3: LAPORAN ABSENSI             │  │ │
│         │ │                                   │  │ │
│         │ │ Query:                            │  │ │
│         │ │ SELECT * FROM absensi             │  │ │
│         │ │ WHERE tanggal BETWEEN X AND Y     │  │ │
│         │ │                                   │  │ │
│         │ │ Output:                           │  │ │
│         │ │ - Tabel absensi (ID, Nama,       │  │ │
│         │ │   Jam Masuk, Jam Pulang, Status) │  │ │
│         │ │ - Total Hadir/Izin/Sakit/Alpha   │  │ │
│         │ │                                   │  │ │
│         │ └──────────────────────────────────┘  │ │
│         │              │                        │ │
│         └──────────────┼────────────────────────┘ │
│                        ▼                          │
│              Response: Konfirmasi / Laporan        │
└─────────────────────────────────────────────────────┘

DATA STORES:
D3.1: absensi (id, id_karyawan, tanggal, jam_masuk, jam_pulang, status)
```

---

## PROSES 4: PENGAJUAN & PERSETUJUAN CUTI

```
┌─ KARYAWAN              HRD/ADMIN ──────────────┐
│      │                  │                       │
│      │ Ajukan Cuti      │ Review Request       │
│      │                  │                       │
│      └─────────┬────────┴──────────────────────┐
│                │                                │
│    ┌───────────▼────────────────────────────┐  │
│    │ P4.0: PENGAJUAN & PERSETUJUAN CUTI     │  │
│    │                                         │  │
│    │ ┌───────────────────────────────────┐  │  │
│    │ │ P4.1: FORM PENGAJUAN CUTI         │  │  │
│    │ │                                    │  │  │
│    │ │ Input (dari Karyawan):             │  │  │
│    │ │ - tanggal_mulai                    │  │  │
│    │ │ - tanggal_selesai                  │  │  │
│    │ │ - keterangan (alasan cuti)         │  │  │
│    │ │                                    │  │  │
│    │ │ Validasi:                          │  │  │
│    │ │ - Tanggal_mulai < Tanggal_selesai │  │  │
│    │ │ - Cek saldo cuti tersedia          │  │  │
│    │ │ - Tidak ada cuti ganda             │  │  │
│    │ │                                    │  │  │
│    │ │ Output: Validasi berhasil/gagal    │  │  │
│    │ └───────────────┬────────────────────┘  │  │
│    │                 │                        │  │
│    │ ┌───────────────▼────────────────────┐  │  │
│    │ │ P4.2: SIMPAN REQUEST CUTI          │  │  │
│    │ │                                    │  │  │
│    │ │ INSERT INTO cuti                   │  │  │
│    │ │ (id_karyawan, tgl_mulai,           │  │  │
│    │ │  tgl_selesai, keterangan,          │  │  │
│    │ │  status='Pending')                 │  │  │
│    │ │                                    │  │  │
│    │ │ Output: ID Cuti (reference)        │  │  │
│    │ └───────────────┬────────────────────┘  │  │
│    │                 │                        │  │
│    │ ┌───────────────▼────────────────────┐  │  │
│    │ │ P4.3: NOTIFIKASI KE HRD/ADMIN      │  │  │
│    │ │ Kirim notifikasi request           │  │  │
│    │ └───────────────┬────────────────────┘  │  │
│    │                 │                        │  │
│    │ ┌───────────────▼────────────────────┐  │  │
│    │ │ P4.4: REVIEW REQUEST (HRD/Admin)   │  │  │
│    │ │                                    │  │  │
│    │ │ Tampilkan:                         │  │  │
│    │ │ - Daftar request cuti (Pending)    │  │  │
│    │ │ - Detail pemohon                   │  │  │
│    │ │ - Alasan cuti                      │  │  │
│    │ │                                    │  │  │
│    │ │ HRD Action:                        │  │  │
│    │ │ - Klik [Setujui] / [Tolak]         │  │  │
│    │ │                                    │  │  │
│    │ │ Validasi:                          │  │  │
│    │ │ - Cek kehadiran karyawan           │  │  │
│    │ │ - Cek saldo cuti                   │  │  │
│    │ │                                    │  │  │
│    │ │ Output: Status decision            │  │  │
│    │ └───────────────┬────────────────────┘  │  │
│    │                 │                        │  │
│    │ ┌───────────────▼────────────────────┐  │  │
│    │ │ P4.5: UPDATE STATUS CUTI           │  │  │
│    │ │                                    │  │  │
│    │ │ UPDATE cuti                        │  │  │
│    │ │ SET status = 'Disetujui' / 'Ditolak'│ │  │
│    │ │ WHERE id = id_cuti                 │  │  │
│    │ │                                    │  │  │
│    │ │ Output: Konfirmasi update          │  │  │
│    │ └───────────────┬────────────────────┘  │  │
│    │                 │                        │  │
│    │ ┌───────────────▼────────────────────┐  │  │
│    │ │ P4.6: NOTIFIKASI KE KARYAWAN       │  │  │
│    │ │ Kirim status persetujuan/penolakan │  │  │
│    │ │ Karyawan bisa lihat di dashboard    │  │  │
│    │ └────────────────────────────────────┘  │  │
│    │                 │                        │  │
│    └─────────────────┼────────────────────────┘  │
│                      ▼                           │
│            Response: Status Update                │
└──────────────────────────────────────────────────┘

DATA STORES:
D4.1: cuti (id, id_karyawan, tgl_mulai, tgl_selesai, 
            keterangan, status, created_at, updated_at)
```

---

## PROSES 5: MANAJEMEN PENGGAJIAN (HRD)

```
HRD/ADMIN
   │
   │ Request: INPUT DATA GAJI
   │
   └──────────────┬─────────────────────────────────┐
                  │                                  │
         ┌────────▼────────────────────────────────┐│
         │ P5.0: MANAJEMEN PENGGAJIAN               ││
         │                                          ││
         │ ┌─────────────────────────────────────┐ ││
         │ │ P5.1: FORM INPUT DATA GAJI           │ ││
         │ │                                      │ ││
         │ │ Input (dari HRD):                    │ ││
         │ │ - Pilih karyawan (dropdown)          │ ││
         │ │ - Gapok (gaji pokok)                 │ ││
         │ │ - Jam lembur                         │ ││
         │ │ - Upah lembur per jam                │ ││
         │ │ - Potongan (pajak, cicilan, dll)     │ ││
         │ │ - Bulan payroll                      │ ││
         │ │ - Tahun payroll                      │ ││
         │ │                                      │ ││
         │ │ Validasi:                            │ ││
         │ │ - Semua field tidak kosong           │ ││
         │ │ - Nilai numerik valid                │ ││
         │ │                                      │ ││
         │ │ Output: Data tersimpan di form       │ ││
         │ └──────────────┬──────────────────────┘ ││
         │                │                        ││
         │ ┌──────────────▼──────────────────────┐ ││
         │ │ P5.2: HITUNG GAJI BERSIH             │ ││
         │ │                                      │ ││
         │ │ Formula:                             │ ││
         │ │ Gaji Bruto = Gapok +                 │ ││
         │ │             (Jam Lembur × UphPerJam) │ ││
         │ │                                      │ ││
         │ │ Gaji Bersih = Gaji Bruto - Potongan │ ││
         │ │                                      │ ││
         │ │ Output: Total gaji bersih            │ ││
         │ └──────────────┬──────────────────────┘ ││
         │                │                        ││
         │ ┌──────────────▼──────────────────────┐ ││
         │ │ P5.3: SIMPAN DATA GAJI KE DATABASE  │ ││
         │ │                                      │ ││
         │ │ INSERT INTO gaji                     │ ││
         │ │ (id_karyawan, gapok,                 │ ││
         │ │  jam_lembur, upah_lembur_per_jam,    │ ││
         │ │  potongan, gaji_bersih,              │ ││
         │ │  bulan, tahun)                       │ ││
         │ │                                      │ ││
         │ │ Output: Konfirmasi simpan            │ ││
         │ └──────────────┬──────────────────────┘ ││
         │                │                        ││
         │ ┌──────────────▼──────────────────────┐ ││
         │ │ P5.4: GENERATE SLIP GAJI             │ ││
         │ │                                      │ ││
         │ │ Proses:                              │ ││
         │ │ 1. Query data karyawan + data gaji   │ ││
         │ │ 2. Format slip (header, detail)      │ ││
         │ │ 3. Output: Slip (PDF/Print)          │ ││
         │ │                                      │ ││
         │ │ Isi Slip:                            │ ││
         │ │ - Nama karyawan                      │ ││
         │ │ - ID karyawan                        │ ││
         │ │ - Jabatan                            │ ││
         │ │ - Periode bulan/tahun                │ ││
         │ │ - Gapok                              │ ││
         │ │ - Tunjangan lembur                   │ ││
         │ │ - Potongan                           │ ││
         │ │ - Gaji bersih                        │ ││
         │ │ - TTD HRD & Karyawan                 │ ││
         │ │                                      │ ││
         │ │ Output: Slip Gaji (untuk dicetak)    │ ││
         │ └──────────────┬──────────────────────┘ ││
         │                │                        ││
         │ ┌──────────────▼──────────────────────┐ ││
         │ │ P5.5: LAPORAN PENGGAJIAN KESELURUHAN │ ││
         │ │                                      │ ││
         │ │ Query:                               │ ││
         │ │ SELECT * FROM gaji                   │ ││
         │ │ WHERE bulan = ? AND tahun = ?        │ ││
         │ │ JOIN karyawan ON gaji.id_karyawan    │ ││
         │ │                                      │ ││
         │ │ Output:                              │ ││
         │ │ - Tabel gaji semua karyawan          │ ││
         │ │ - Kolom: Nama, Gapok, Lembur,        │ ││
         │ │          Potongan, Gaji Bersih       │ ││
         │ │ - Total penggajian bulan ini         │ ││
         │ │                                      │ ││
         │ │ Bisa diexport ke PDF/Excel           │ ││
         │ └─────────────────────────────────────┘ ││
         │                                          ││
         └──────────────────────────────────────────┘│
                        │                            │
        ┌───────────────▼─────────────┐             │
        │ Karyawan                    │             │
        │ Bisa lihat slip gaji mereka │             │
        │ di dashboard karyawan       │             │
        └────────────────────────────┘             │
                                                    │
         Response: Slip Gaji / Laporan Penggajian  │
         ─────────────────────────────────────────┘

DATA STORES:
D5.1: gaji (id, id_karyawan, gapok, jam_lembur, 
           upah_lembur_per_jam, potongan, gaji_bersih, 
           bulan, tahun, created_at)
```

---

# DIAGRAM ALIRAN DATA KESELURUHAN SISTEM

```
┌──────────────────────────────────────────────────────────────────┐
│                     STAFFLINK - SISTEM LENGKAP                    │
└──────────────────────────────────────────────────────────────────┘

                        ┌──────────────────┐
                        │   USER / ACTOR   │
                        ├──────────────────┤
                        │ - Karyawan       │
                        │ - Admin          │
                        │ - HRD            │
                        └────────┬─────────┘
                                 │
                    ┌────────────▼────────────┐
                    │   DASHBOARD AWAL        │
                    │ Login / Register Screen │
                    └────────────┬────────────┘
                                 │
                          P1: AUTENTIKASI
                                 │
                    ┌────────────▼──────────────────┐
                    │  REDIRECT KE DASHBOARD SESUAI │
                    │  ROLE USER                    │
                    └────┬──────────┬───────┬───────┘
                         │          │       │
        ┌────────────────┘          │       └────────────────┐
        │                           │                        │
        ▼                           ▼                        ▼
    KARYAWAN               ADMIN DASHBOARD          HRD DASHBOARD
    DASHBOARD               (Admin Portal)           (HRD Portal)
        │                       │                        │
        ├─ P3: CHECK-IN/OUT     ├─ P2: CRUD Karyawan     ├─ P4: APPROVE CUTI
        │                       │                        │
        ├─ P4: AJUKAN CUTI      ├─ P3: LAPORAN ABSENSI   ├─ P3: LAPORAN ABSENSI
        │                       │                        │
        ├─ P5: LIHAT SLIP GAJI  ├─ P4: LIHAT REQUEST     ├─ P5: HITUNG GAJI
        │                       │       CUTI             │
        └─ P4: STATUS CUTI      └─ P5: LAPORAN GAJI      └─ P5: SLIP GAJI


                          ┌──────────────────┐
                          │  DATABASE: PBOL  │
                          ├──────────────────┤
                          │ - user           │
                          │ - karyawan       │
                          │ - absensi        │
                          │ - cuti           │
                          │ - gaji           │
                          └──────────────────┘
                                   ▲
                                   │
                          Semua proses terhubung
                          ke database ini
```

---

# TABEL REFERENSI STRUKTUR DATABASE

## D1: TABLE `user`
```
+------------------+---------------------+
| Field            | Type                |
+------------------+---------------------+
| id               | VARCHAR(50) PRIMARY |
| email            | VARCHAR(100) UNIQUE |
| password         | VARCHAR(255)        |
| role             | ENUM(K/A/H)         |
| id_karyawan      | VARCHAR(50) FK      |
| created_at       | TIMESTAMP           |
+------------------+---------------------+
```

## D2: TABLE `karyawan`
```
+------------------+---------------------+
| Field            | Type                |
+------------------+---------------------+
| id               | VARCHAR(50) PRIMARY |
| nama             | VARCHAR(100)        |
| jabatan          | VARCHAR(50)         |
| no_hp            | VARCHAR(20)         |
| no_rekening      | VARCHAR(50)         |
| created_at       | TIMESTAMP           |
| updated_at       | TIMESTAMP           |
+------------------+---------------------+
```

## D3: TABLE `absensi`
```
+------------------+---------------------+
| Field            | Type                |
+------------------+---------------------+
| id               | INT PRIMARY AUTO_INC|
| id_karyawan      | VARCHAR(50) FK      |
| tanggal          | DATE                |
| jam_masuk        | TIME                |
| jam_pulang       | TIME                |
| status           | ENUM(H/I/S/A)       |
| created_at       | TIMESTAMP           |
+------------------+---------------------+
```

## D4: TABLE `cuti`
```
+------------------+---------------------+
| Field            | Type                |
+------------------+---------------------+
| id               | INT PRIMARY AUTO_INC|
| id_karyawan      | VARCHAR(50) FK      |
| tanggal_mulai    | DATE                |
| tanggal_selesai  | DATE                |
| keterangan       | TEXT                |
| status           | ENUM(P/D/T)         |
| created_at       | TIMESTAMP           |
| updated_at       | TIMESTAMP           |
+------------------+---------------------+
```

## D5: TABLE `gaji`
```
+---------------------+---------------------+
| Field               | Type                |
+---------------------+---------------------+
| id                  | INT PRIMARY AUTO_INC|
| id_karyawan         | VARCHAR(50) FK      |
| gapok               | INT                 |
| jam_lembur          | INT                 |
| upah_lembur_per_jam | INT                 |
| potongan            | INT                 |
| gaji_bersih         | INT (CALCULATED)    |
| bulan               | VARCHAR(10)         |
| tahun               | VARCHAR(4)          |
| created_at          | TIMESTAMP           |
+---------------------+---------------------+
```

---

# RINGKASAN PROSES SISTEM

| Proses | Nama | Actor | Input | Output | DB Involved |
|--------|------|-------|-------|--------|-------------|
| P1 | Autentikasi & Register | Semua | Email, Pass, Role | Token/Session | D1 |
| P2 | Manajemen Karyawan | Admin | CRUD Data | List Karyawan | D2 |
| P3 | Absensi | Karyawan+Admin | Check-in/out | Laporan Absensi | D3 |
| P4 | Cuti | Karyawan+HRD | Request/Approval | Status Cuti | D4 |
| P5 | Penggajian | HRD | Gaji Data | Slip Gaji | D5 |

---

# FLOW TRANSAKSI UTAMA

## Transaksi 1: Karyawan Masuk Kantor (Pagi)
```
Karyawan (09:00)
    │
    ├─► Login sistem (P1)
    │
    ├─► Masuk Karyawan Dashboard
    │
    ├─► Klik "Check-In" (P3.1)
    │
    ├─► Sistem catat: jam masuk = 09:00
    │
    ├─► INSERT ke absensi: (K001, 2025-12-04, 09:00, NULL, Hadir)
    │
    └─► Tampil: "Selamat Datang! Check-in berhasil pukul 09:00"
```

## Transaksi 2: Karyawan Pulang (Sore)
```
Karyawan (17:00)
    │
    ├─► Klik "Check-Out" (P3.2)
    │
    ├─► Sistem catat: jam pulang = 17:00
    │
    ├─► UPDATE absensi: jam_pulang = 17:00 WHERE id_karyawan=K001 AND tanggal=2025-12-04
    │
    └─► Tampil: "Check-out berhasil pukul 17:00"
```

## Transaksi 3: Karyawan Mengajukan Cuti
```
Karyawan
    │
    ├─► Menu: Pengajuan Cuti (P4.1)
    │
    ├─► Input: Tanggal mulai, tanggal selesai, alasan
    │
    ├─► Validasi: Cek saldo cuti, tanggal tidak overlap (P4.2)
    │
    ├─► INSERT ke cuti: (K001, 2025-12-10, 2025-12-15, "Liburan", Pending)
    │
    ├─► Sistem kirim notifikasi ke HRD
    │
    └─► Tampil: "Pengajuan cuti telah dikirim. Tunggu approval HRD"

                        ⏳ WAITING STATE ⏳

HRD
    │
    ├─► Menu: Review Cuti Pending (P4.4)
    │
    ├─► Lihat daftar request: Karyawan K001, Periode 10-15 Des
    │
    ├─► Review & Klik [Setujui] (P4.5)
    │
    ├─► UPDATE cuti: status = Disetujui WHERE id = [ID_CUTI]
    │
    ├─► Sistem kirim notifikasi ke Karyawan
    │
    └─► Tampil: "Approval berhasil"

                        KARYAWAN NOTIFIED

Karyawan
    │
    └─► Lihat dashboard: Status Cuti = "Disetujui"
```

## Transaksi 4: HRD Menghitung Gaji Bulan Ini
```
HRD (Akhir bulan)
    │
    ├─► Menu: Hitung Gaji (P5)
    │
    ├─► Input data:
    │   - Karyawan: K001 (Agustinus)
    │   - Gapok: 5.000.000
    │   - Jam lembur: 10
    │   - Upah lembur/jam: 150.000
    │   - Potongan: 500.000
    │   - Bulan: 12
    │   - Tahun: 2025
    │
    ├─► Sistem hitung:
    │   - Gaji Bruto = 5.000.000 + (10 × 150.000) = 6.500.000
    │   - Gaji Bersih = 6.500.000 - 500.000 = 6.000.000
    │
    ├─► INSERT ke gaji: (K001, 5M, 10, 150K, 500K, 6M, "12", "2025")
    │
    ├─► Generate Slip Gaji (P5.4)
    │
    └─► Tampil: Slip PDF (siap print & tanda tangan)

                        KARYAWAN NOTIFICATIONS

Karyawan
    │
    └─► Dashboard: Slip gaji bulan 12 tersedia
        ├─► Lihat detail
        ├─► Print
        └─► Download PDF
```

---

# CATATAN TEKNIS

## Use Case Flowchart Legend:
```
┌─────────┐     = Process/Proses
├─────────┤     
│ Nama    │
└─────────┘

(   O    )     = Data Store/Database

█████████       = Pipe/Aliran Data

│
├─ Kondisi
│
└─ Hasil
```

## Security Measures:
- ✅ Password hashing (tidak disimpan plaintext)
- ✅ Role-based access control (RBAC)
- ✅ Session management (tracking login user)
- ✅ Input validation di setiap form
- ✅ Database connection pooling

## Performance Optimization:
- Query dengan INDEX di foreign keys
- Pagination untuk laporan besar
- Cache session untuk reduce DB query

---

# KESIMPULAN

STAFFLINK adalah sistem terintegrasi yang mengelola 5 proses utama:

1. **Autentikasi** - Kontrol akses pengguna
2. **Manajemen Karyawan** - CRUD data pegawai  
3. **Absensi** - Tracking kehadiran real-time
4. **Cuti** - Pengajuan & approval cuti
5. **Penggajian** - Perhitungan & slip gaji otomatis

Semua proses terhubung ke database terpusat MySQL (pbol) dan dapat diakses via desktop GUI berbasis Java Swing dengan role-based interface.



