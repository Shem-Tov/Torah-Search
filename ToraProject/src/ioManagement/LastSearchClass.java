package ioManagement;

import java.io.BufferedReader;
import java.io.File;
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
			String newText = lRecord.getLineText();
			String largeText = (newText.length()>text.length())? newText:text;
			lastRecord.put(lineNum,new LastSearchRecord(lineNum, largeText, indexes1));
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
		if (storedRecord != null) {
			return storedRecord.size();
		} else {
			return -1;
		}
	}
	
	public static ArrayList<Integer[]> getStoredLineIndexes(int index) {
		return storedRecord.get(index).getMarkIndexes();
	}
	
	public static final String lastSearchFolder_HardCoded = "etc/";
	public static final String lastSearchFileExtension_HardCoded = ".tsa";

	public static String getLastSearchFolder() {
		return ExcelFunctions.getData_Folder_Location()+lastSearchFolder_HardCoded;
	}
	
	public Boolean storeCurrent() {
		try {
			storedRecord = new ArrayList<LastSearchRecord>(lastRecord.values());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void store(File file) {
		try {
			//String fileName = ExcelFunctions.getData_Folder_Location()+lastSearchFolder_HardCoded+fName+lastSearchFileExtension_HardCoded;
			FileOutputStream fos = new FileOutputStream(file);			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(storedRecord);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void load(File file) {
		//String fileName = ExcelFunctions.getData_Folder_Location()+lastSearchFolder_HardCoded+fName+lastSearchFileExtension_HardCoded;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			storedRecord = (ArrayList<LastSearchRecord>) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Could not find lastSearch file: "+file.getName());
		}
	}

}
