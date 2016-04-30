package net.ojava.openkit.excelcomparer.gui;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import net.ojava.openkit.excelcomparer.excel.SourceHead;
import net.ojava.openkit.excelcomparer.res.Resource;

public class SourceHeadTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 4286414737516667690L;
	private ArrayList<SourceHead> headList = new ArrayList<SourceHead>();

	public void addSourceHead(SourceHead head) {
		int index = 0;
		for (; index < headList.size(); index++) {
			if(headList.get(index).getColumn().compareTo(head.getColumn()) >= 0)
				break;
		}
		headList.add(index, head);
	}
	
	public void addAllSourceHead(Collection<SourceHead> heads) {
		for(SourceHead tsh : heads) {
			addSourceHead(tsh);
		}
	}

	public SourceHead removeSourceHead(int index) {
		return headList.remove(index);
	}
	
	public SourceHead getSourceHead(int index) {
		return headList.get(index);
	}
	
	public void redraw() {
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int colIndex) {
		switch (colIndex) {
		case 0:
			return Resource.getInstance().getResourceString(
					Resource.KEY_LABEL_INDEX);
		case 1:
			return Resource.getInstance().getResourceString(
					Resource.KEY_LABEL_TEXT);
		}
		return "";
	}

	@Override
	public int getRowCount() {
		return headList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SourceHead head = headList.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return head.getColumn();
		case 1:
			return head.getText();
		}
		return "";
	}
}
