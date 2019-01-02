package ioManagement;

import java.util.ArrayList;

public class LineHtmlReport {
	String lineHtml;
	String line;
	ArrayList<Integer[]> indexes;
	
	public String getLineHtml(Boolean htmlReady) {
		if (htmlReady) {
			return lineHtml;
		} 
		else {
			return line;
		}
	}
	
	public String getLine() {
		return line;
	}

	public ArrayList<Integer[]> getIndexes() {
		return indexes;
	}

	public LineHtmlReport(String lhtml,String l, ArrayList<Integer[]> i ) {
		lineHtml = lhtml;
		line = l;
		indexes = i;
	}
}
