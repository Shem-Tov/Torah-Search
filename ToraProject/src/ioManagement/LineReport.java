package ioManagement;

import java.util.ArrayList;

public class LineReport {
	String[][] results;
	ArrayList<Integer[]> indexes;
	
	public String[][] getResults() {
		return results;
	}

	public ArrayList<Integer[]> getIndexes() {
		return indexes;
	}

	public LineReport(String[][] r,ArrayList<Integer[]> i ) {
		results = r;
		indexes = i;
	}
}
