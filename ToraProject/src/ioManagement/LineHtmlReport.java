package ioManagement;

import java.util.ArrayList;

public class LineHtmlReport {
	String lineHtml;
	ArrayList<Integer[]> indexes;
	
	public String getLineHtml() {
		return lineHtml;
	}

	public ArrayList<Integer[]> getIndexes() {
		return indexes;
	}

	public LineHtmlReport(String l,ArrayList<Integer[]> i ) {
		lineHtml = l;
		indexes = i;
	}
}
