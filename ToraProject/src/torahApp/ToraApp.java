package torahApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import extras.Console;
import ioManagement.ExcelFunctions;
import ioManagement.Methods;
import ioManagement.Output;
import ioManagement.PropStore;

//import java.util.Formatter;
//import java.util.Locale;

/**
 * 
 */
/**
 * @author shementov777
 *
 */
public class ToraApp {
	// static StringBuffer buffer = new StringBuffer();
	// static Formatter formatter = new Formatter(buffer, new Locale("he", "IL"));

	public static String resourceFolder = "";
	public static final String ToraLineFile = resourceFolder + "Lines.txt";
	public static final String ToraLetterFile = resourceFolder + "NoTevot.txt";
	public static final String ToraTables = resourceFolder + "TorahTables.xls";

	public static String subTorahTableFile = "";
	public static String subTorahLineFile = "";
	public static String subTorahLetterFile = "";

	private static byte guiMode = 0;
	public static final byte id_guiMode_Console = 0;
	public static final byte id_guiMode_Frame = 1;

	private static char[] hLetters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע',
			'פ', 'צ', 'ק', 'ר', 'ש', 'ת' };
	// static char[] endLetters = { 'ך', 'ם', 'ן', 'ף', 'ץ' };
	static char[] otherLetters = { '-' };
	public static String[][] tablePerekBooks;
	static String[][] tablePerekParashot;

	public static String cSpace() {
		return cSpace(1);
	}

	public static String cSpace(int count) {
		String str = "";
		for (int i = 1; i <= count; i++) {
			str += " ";
			//str += "\u00A0";
		}
		return str;
	}

	public static void starter() throws IOException {
		PropStore.load();
		Methods.arrayMethodCreator();
		// There are identical calls like this, one here the another in
		// frame.PopupTextMenu())
		tablePerekBooks = ExcelFunctions
				.readXLS(new String[] { ToraTables, PropStore.map.get(PropStore.subTorahTablesFile) }, 0, 0, 1, 6, 53);
	}

	public static byte getGuiMode() {
		return guiMode;
	}

	public static void setGuiMode(byte i) {
		guiMode = i;
	}

	public static class perekBookInfo {
		private String[] bookNames = { "בראשית", "שמות", "ויקרא", "במדבר", "דברים" };
		private char[][] letters = { { ' ', 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט' },
				{ ' ', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע', 'פ', 'צ' } };
		private String bookName;
		private String perekLetters;
		private String pasukLetters;

		private String findLetters(int num) {
			switch (num) {
			case 15:
				return "טו";
			case 16:
				return "טז";
			default:
				return (Character.toString(letters[1][num / 10]) + Character.toString(letters[0][num % 10]))
						.replaceAll("\\s+", "");
			}
		}

		public perekBookInfo(int bookNum, String pLetters, int pasukNum) {
			perekLetters = pLetters;
			bookName = bookNames[bookNum - 1];
			pasukLetters = findLetters(pasukNum);
		}

		public String getBookName() {
			return bookName;
		}

		public String getPerekLetters() {
			return perekLetters;
		}

		public String getPasukLetters() {
			return pasukLetters;
		}
	}

	public static perekBookInfo findPerekBook(int lineNum) throws NoSuchFieldException {
		int oldI, oldJ;
		if (!ExcelFunctions.tableLoaded) {
			throw new NoSuchFieldException(
					"Tora Tables were not loaded, they should be loaded through ToraApp.starter() function");
		}
		oldI = 1;
		oldJ = 0;
		for (int i = 1; i < tablePerekBooks.length; i++) {
			// second:
			oldI = i;
			oldJ = 1;
			for (int j = 0; j < tablePerekBooks[i].length; j++) {
				if (Integer.parseInt(tablePerekBooks[i][j]) >= lineNum) {
					return new perekBookInfo(oldI, tablePerekBooks[0][oldJ],
							(lineNum - Integer.parseInt(tablePerekBooks[oldI][oldJ])));
					// break second;
				}
				oldI = i;
				oldJ = j;
				if (((j + 1) <= tablePerekBooks[i].length)
						&& (tablePerekBooks[i][j].equals(tablePerekBooks[i][j + 1]))) {
					break;
				}
				// System.out.println("i="+i+" j="+j+" data=" + tablePerekBooks[i][j]);
			}
		}
		return null;
	}

	public static File checkFile(String fileName1, String fileName2) {
		File file = null;
		try {
			file = new File(ClassLoader.getSystemResource(fileName1).toURI());
		} catch (Exception e) {
			// safe to ignore
		}
		if ((file == null) || (!file.exists())) {
			try {
				file = new File(fileName2);
			} catch (Exception e) {
				// safe to ignore
			}
			if ((file == null) || (!file.exists())) {
				// throw new IOException("Could not find file for TorahLetters");
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.clearText();
				}
				Output.printText("Could not find file for TorahLetters", 1);
				return null;
			}
		}
		return file;
	}

	public static BufferedReader getBufferedReader(String fileName1, String fileName2) {
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(
					new InputStreamReader(ToraApp.class.getClassLoader().getResourceAsStream(fileName1)));
		} catch (Exception e) {
			try {
				File file = new File(fileName2);
				bReader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.clearText();
				}
				Output.printText("Could not find file for TorahLetters", 1);
				return null;
			}
			return bReader;
			// safe to ignore
		}
		return bReader;
	}

	public static void main(String[] args) throws IOException {
		// findFirstLetters();
		// findWords();
		ToraApp.setGuiMode(ToraApp.id_guiMode_Console);
		starter();
		Console.menu();
		Output.printText("Program Exited...");

		// printText("One Two Three Four Five Six Seven Eight Nine Ten Eleven Twelve
		// Thirteen Fourteen Fifteen Sixteen Seventeen Eighteen Nineteen Twenty
		// TwentyOne TwentyTwo");
	}

	public static char[] getHLetters() {
		return hLetters;
	}

	public static void setHLetters(char[] hLetters) {
		ToraApp.hLetters = hLetters;
	}
}
