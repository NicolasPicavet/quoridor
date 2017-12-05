package fr.uga.ai.quoridor.gui;

import fr.uga.ai.quoridor.map.Coordinates;
import fr.uga.ai.quoridor.map.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MapGui {

    private static MapGui instance = new MapGui();

    private JTable table;
    private JPanel view;

    private int tableSize = Map.PLAYER_MAP_SIZE + Map.WALL_MAP_SIZE;
    private int sizeUnit = 32;

    private DefaultTableModel tableModel;

    private MapGui() {
        // set column/row number & behaviour
        tableModel = new DefaultTableModel(tableSize, Map.PLAYER_MAP_SIZE + Map.WALL_MAP_SIZE) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setModel(tableModel);
        // set cell renderer
        // set column size
        for (int x = 0; x < tableSize; x += 2) {
            table.getColumnModel().getColumn(x).setMaxWidth(2 * sizeUnit);
            table.getColumnModel().getColumn(x).setMinWidth(2 * sizeUnit);
        }
        for (int x = 1; x < tableSize; x += 2) {
            table.getColumnModel().getColumn(x).setMaxWidth(sizeUnit);
            table.getColumnModel().getColumn(x).setMinWidth(sizeUnit);
        }
        // set row size
        for (int y = 0; y < tableSize; y += 2)
            table.setRowHeight(y, 2 * sizeUnit);
        for (int y = 1; y < tableSize; y += 2)
            table.getColumnModel().getColumn(y).setMinWidth(sizeUnit);


        /*setCell(new Coordinates(0, 0), new PlayerSquareWidget().getView());
        JPanel panel = (JPanel) table.getModel().getValueAt(0, 0);
        panel.setBackground(new Color(50, 100, 200));*/
    }

    public boolean setCell(Coordinates coordinates, JComponent component) {
        table.setValueAt(component, coordinates.y,  coordinates.x);
        return false;
    }

    public boolean setCellString(int x, int y, String text) {
        tableModel.setValueAt(text, y, x);
        return false;
    }

    public JPanel getView() {
        return view;
    }

    public static MapGui getInstance() {
        return instance;
    }
}
