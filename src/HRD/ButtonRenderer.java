package HRD;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class ButtonRenderer extends JPanel implements TableCellRenderer {

    private final JButton btnSetuju;
    private final JButton btnTolak;

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        setOpaque(true);

        btnSetuju = new JButton("Setuju");
        btnTolak  = new JButton("Tolak");

        btnSetuju.setPreferredSize(new Dimension(90, 30));
        btnTolak.setPreferredSize(new Dimension(90, 30));

        add(btnSetuju);
        add(btnTolak);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
