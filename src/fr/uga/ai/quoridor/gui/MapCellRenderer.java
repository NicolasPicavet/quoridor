package fr.uga.ai.quoridor.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MapCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof WallSquareWidget)
            return ((WallSquareWidget) value).getView();
        if (value instanceof PlayerSquareWidget)
            return ((PlayerSquareWidget) value).getView();
        return null;
    }
}
