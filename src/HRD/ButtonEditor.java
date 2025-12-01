/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HRD;

/**
 *
 * @author PUTRI SAHARA T
 */
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel panel;
    private JButton btnSetuju;
    private JButton btnTolak;
    private JTable table;

    public ButtonEditor(JTable table) {
        this.table = table;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));

        btnSetuju = new JButton("Setuju");
        btnTolak  = new JButton("Tolak");

        btnSetuju.setPreferredSize(new Dimension(90, 30));
        btnTolak.setPreferredSize(new Dimension(90, 30));

        panel.add(btnSetuju);
        panel.add(btnTolak);

        btnSetuju.addActionListener(e -> {
            int row = table.getEditingRow();
            table.setValueAt("Disetujui", row, 6);
            fireEditingStopped();
        });

        btnTolak.addActionListener(e -> {
            int row = table.getEditingRow();
            table.setValueAt("Ditolak", row, 6);
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
