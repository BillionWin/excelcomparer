package net.ojava.openkit.excelcomparer.gui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.ojava.openkit.excelcomparer.res.Resource;


public class HeadTable extends JTable {
	private static final long serialVersionUID = 7093167771122105617L;

	public HeadTable(AbstractTableModel model, int[] widths) {
		super(model);
		
		this.setFillsViewportHeight(true);
		this.setGridColor(Color.LIGHT_GRAY);
		this.setRowHeight(Resource.TABLE_ROW_HEIGHT);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		GuiUtil.updateTableColumnWidth(this, widths);
	}
}
