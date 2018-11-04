package ToraApp;
import java.io.IOException;

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
	//static StringBuffer buffer = new StringBuffer();
	//static Formatter formatter = new Formatter(buffer, new Locale("he", "IL"));
	
	public static final String ToraLineFile = "Lines.txt";
	public static final String ToraLetterFile = "NoTevot.txt";
	
	private static byte guiMode = 0;
	static char[] hLetters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע', 'פ', 'צ',
			'ק', 'ר', 'ש', 'ת' };
	//static char[] endLetters = { 'ך', 'ם', 'ן', 'ף', 'ץ' };
	static char[] otherLetters = { '-' };
	static String[][] tablePerekBooks;
	static String[][] tablePerekParashot;
	private static boolean tableLoaded=false; 
	
	public static String cSpace() {
		return cSpace(1);
	}
	public static String cSpace(int count) {
		String str = "";
		for (int i=1; i<=count; i++) {
			str += "\u00A0";
		}
		return str;
	}
	public static void starter() throws IOException{
		PropStore.load();
		Methods.arrayMethodCreator();
		tablePerekBooks = ExcelFunctions.readXLS("TorahTables2.xls", 0, 0, 1, 6, 53);
		tableLoaded=true;
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
				return (Character.toString(letters[1][num / 10]) + Character.toString(letters[0][num % 10])).replaceAll("\\s+","");
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
		if (!tableLoaded) {
			throw new NoSuchFieldException("Tora Tables were not loaded, they should be loaded through ToraApp.starter() function");
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


	public static void main(String[] args) throws IOException {
		// findFirstLetters();
		// findWords();
		starter();
		Console.menu();
		Output.printText("Program Exited...");
		
		//printText("One Two Three Four Five Six Seven Eight Nine Ten Eleven Twelve Thirteen Fourteen Fifteen Sixteen Seventeen Eighteen Nineteen Twenty TwentyOne TwentyTwo");
	}
}
