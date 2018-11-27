package ioManagement;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.IndexedColors;

//String[] results = new String[] {  searchSTR, pBookInstance.getBookName(), pBookInstance.getPerekLetters(),
//	pBookInstance.getPasukLetters(), line } ;

public class LineReport {
	public class Line {
		String[] line;
		ArrayList<Integer[]> indexes;
		Line(String[] r,ArrayList<Integer[]> i){
			line=r;
			indexes=i;
		}
		ArrayList<Integer[]> getIndexesOfLine(){
			return indexes;
		}
		
		String[] getLine() {
			return line;
		}
	}
	
	private ArrayList<Line> results;
	private FontStyle[] cStyle=null;
	private FontStyle   rStyle=null;
	
	public ArrayList<Line> getResults() {
		return results;
	}

	public LineReport(ArrayList<Line> l) {
		results = l;
	}
	
	public LineReport() {
		results = new ArrayList<Line>();
	}
	
	public LineReport(String[] r, ArrayList<Integer[]> i, FontStyle...c) {
		results = new ArrayList<Line>();
		results.add(new Line(r,i));
		if (c!=null) {
			if (c.length>0) {
				rStyle = c[0];
			}
			if (c.length>1) {
				cStyle = Arrays.copyOfRange(c, 1, c.length);
			}
		}
	}
	
	public void add(String[] r, ArrayList<Integer[]>i) {
		results.add(new Line(r,i));
	}
	
	ArrayList<Integer[]> getIndexes(int index) {
		return results.get(index).getIndexesOfLine();
	}
	
	String[] getLine(int index) {
		return results.get(index).getLine();
	}
	
	IndexedColors getColumnColor(int column) {
		if ((cStyle==null) || (cStyle.length<=column)) return null;
		return cStyle[column].getColor();
	}
	
	IndexedColors getRowColor() {
		if (rStyle==null) return null;
		return rStyle.getColor();
	}
	
	int getColumnFontSize(int column) {
		if ((cStyle==null) || (cStyle.length<=column)) return -1;
		return cStyle[column].getFontSize();
	}
	
	int getRowFontSize() {
		if (rStyle==null) return -1;
		return rStyle.getFontSize();
	}
	ArrayList<String[]> getLines() {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		for (Line res:results) {
			lines.add(res.getLine());
		}
		return lines;
	}
}
