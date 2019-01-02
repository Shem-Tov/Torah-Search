package ioManagement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ioManagement.LineReport.Line;

public class LastSearchClass {
	Map<Integer,LastSearchRecord> lastRecord;
	static ArrayList<LastSearchRecord> storedRecord;
	static LastSearchClass instance;
	
	public LastSearchClass(){
		instance = this;
		lastRecord = new HashMap<Integer,LastSearchRecord>();
	}
	
	public static LastSearchClass getInstance() {
		return instance;
	}

	public void add(int lineNum, Line line) {
		// TODO Auto-generated constructor stub
		add(lineNum, line.getLine()[ExcelFunctions.id_torahLine], line.getIndexesOfLine());
	}
	
	public void add(int lineNum, String text, ArrayList<Integer[]> indexes) {
		// TODO Auto-generated constructor stub
		LastSearchRecord lRecord = lastRecord.get(lineNum);
		if (lRecord==null) {
			lastRecord.put(lineNum,new LastSearchRecord(lineNum, text, indexes));
		} else {
			ArrayList<Integer[]> indexes1 = lRecord.getMarkIndexes();
			indexes1 = Output.mergeMarkIndexes(indexes1, indexes);
			lastRecord.put(lineNum,new LastSearchRecord(lineNum, text, indexes1));
		}
	}
	
	public LastSearchRecord get(int index) {
		return lastRecord.get(index);
	}

	public static int getStoredLineNum(int index) {
		return storedRecord.get(index).getLineNum();
	}
	
	public static BufferedReader getStoredBufferedReader() {
		// Assume the ArrayList is named stringList
		StringBuilder buffer = new StringBuilder();
		for(LastSearchRecord current : storedRecord) {
		    buffer.append(current.getLineText()).append("\n");
		}
		return new BufferedReader(new StringReader(buffer.toString()));
	}
	
	public static int getStoredSize() {
		return storedRecord.size();
	}
	
	public static final String lastSearchFolder_HardCoded = "etc/";
	public static final String lastSearchFileExtension_HardCoded = ".tsa";

	public Boolean storeCurrent() {
		try {
			storedRecord = new ArrayList<LastSearchRecord>(lastRecord.values());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void store(String fName) {
		try {
			//sortMap();
			String fileName = ExcelFunctions.getData_Folder_Location()+lastSearchFolder_HardCoded+fName+lastSearchFileExtension_HardCoded;
			FileOutputStream fos = new FileOutputStream(fileName);			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lastRecord);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void load(String fName) {
		String fileName = ExcelFunctions.getData_Folder_Location()+lastSearchFolder_HardCoded+fName+lastSearchFileExtension_HardCoded;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			storedRecord = (ArrayList<LastSearchRecord>) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Could not find lastSearch file: "+fileName);
		}
	}

}
