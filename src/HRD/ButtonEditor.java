package HRD;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.Cutimodel;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton btnTerima;
    protected JButton btnTolak;
    private Boolean isPushed;
    private JTable table;
    private int idCuti; // Menyimpan ID Cuti baris yang diklik
    private Cutimodel cutiModel = new Cutimodel();

    public ButtonEditor(JTable table) {
        super(new JCheckBox());
        this.table = table;
        
        btnTerima = new JButton();
        btnTolak = new JButton();
        
        // LOGIKA TOMBOL TERIMA
        btnTerima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Stop edit mode
                prosesCuti("Disetujui");
            }
        });

        // LOGIKA TOMBOL TOLAK
        btnTolak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); 
                prosesCuti("Ditolak");
            }
        });
    }

    private void prosesCuti(String status) {
        int confirm = JOptionPane.showConfirmDialog(null, 
                "Yakin ingin mengubah status menjadi " + status + "?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean sukses = cutiModel.updateStatusCuti(idCuti, status);
            if (sukses) {
                JOptionPane.showMessageDialog(null, "Berhasil " + status + "!");
                // Hapus baris dari tabel karena sudah tidak 'Pending' lagi
                // (Ini akan otomatis refresh saat tabel di-reload di FormIzinCuti)
            } else {
                JOptionPane.showMessageDialog(null, "Gagal update database.");
            }
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        
        // Ambil ID Cuti dari kolom ke-0 (Hidden/Visible)
        // Pastikan urutan kolom di FormIzinCuti: ID Cuti ada di indeks 0
        idCuti = Integer.parseInt(table.getValueAt(row, 0).toString());
        
        return null; // Editor ini tidak menampilkan komponen, ButtonRenderer yang menampilkan
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}