package net.ojava.openkit.excelcomparer.excel;

public class SourceHead {
	private String column;
	private String text;

	public SourceHead() {
		
	}
	
	public SourceHead(String column, String text) {
		this.column = column;
		this.text = text;
	}
	
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
