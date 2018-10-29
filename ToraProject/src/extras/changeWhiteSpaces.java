package extras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import ToraApp.Output;

public class changeWhiteSpaces {
	static final Integer ONE = 1;
	static Hashtable<Integer, Integer> charCodeMap = new Hashtable<Integer, Integer>();

	public static void printCharCodes() throws IOException {
		Enumeration<Integer> e = charCodeMap.keys();
		while (e.hasMoreElements()) {
			Integer key = (Integer) e.nextElement();
			Output.printText(key + " : " + charCodeMap.get(key));
		}
	}

	public static void collectCharCode(Integer charCode) {
		Object obj = charCodeMap.get(charCode);
		if (obj == null) {
			charCodeMap.put(charCode, ONE);
		} else {
			int i = ((Integer) obj).intValue() + 1;
			charCodeMap.put(charCode, i);
		}
	}

	static char[] letters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע', 'פ', 'צ',
			'ק', 'ר', 'ש', 'ת', 'ך', 'ם', 'ן', 'ף', 'ץ' };
	static char[] endLetters = {};

	public static String readTextFile(String fileName) {
		String returnValue = "";
		FileReader file;
		String line = "";
		try {
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			try {
				int linecount=1;
				int c;
				boolean foundWhiteSpace=false;
				while ((c = reader.read()) != -1) {
					if (c==10) {
						linecount++;
					}
					if (new String(letters).indexOf((char) c) == -1) {
						if (foundWhiteSpace) {
							System.out.println("Found double whitespace at: " + linecount);
						}
						foundWhiteSpace=true;
						collectCharCode(c);
						// do something
					} else {
						foundWhiteSpace=false;
					}
				}
				printCharCodes();
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occured");
		}
		return returnValue;

	}

	public static void writeTextFile(String fileName, String s) {
		FileWriter output;
		try {
			output = new FileWriter(fileName);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		readTextFile("./src/Lines_2.txt");
	}
}
