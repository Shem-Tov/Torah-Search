package ToraApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import StringAlignUtils.StringAlignUtils;

public class Dilugim {
	private static Dilugim instance;

	public static Dilugim getInstance() {
		if (instance == null) {
			instance = new Dilugim();
		}
		return instance;
	}

	private static char switchSofiot(char ch) {
		switch (ch) {
		case 'ך':
			ch = 'כ';
			break;
		case 'ם':
			ch = 'מ';
			break;
		case 'ן':
			ch = 'נ';
			break;
		case 'ף':
			ch = 'פ';
			break;
		case 'ץ':
			ch = 'צ';
		}
		return ch;
	}

	private static String switchSofiotStr(String str) {
		String str2 = "";
		for (char ch : str.toCharArray()) {
			str2 += switchSofiot(ch);
		}
		System.out.println(str + " - Original string length: " + str.length());
		System.out.println(str2 + " - New string length: " + str2.length());
		return str2;
	}

	private static Boolean compareChar(char chSearch, char ch2, boolean sofiot) {
		// chSearch must first be without sofiot;
		// use switchSofiotStr()
		if (!sofiot) {
			return (chSearch == switchSofiot(ch2));
		} else {
			return (chSearch == ch2);
		}
	}

	public void searchWordsDilugim(Object[] args) throws IOException {
		// String[][] results=null;
		BufferedReader inputStream = null;
		StringWriter outputStream = null;
		String searchSTR;
		String searchOriginal;
		Boolean bool_sofiot;
		int searchIndex;
		int minDilug;
		int maxDilug;
		int offset;
		// FileWriter outputStream2 = null;
		try {
			searchOriginal = ((String) args[0]);
			searchSTR = searchOriginal.replace(" ", "");
			bool_sofiot = (Boolean) args[1];
			minDilug = Integer.parseInt((String) args[2]);
			maxDilug = Integer.parseInt((String) args[3]);
			offset = Integer.parseInt((String) args[4]);
			if (!bool_sofiot) {
				searchSTR = switchSofiotStr(searchSTR);
			}
		} catch (ClassCastException e) {
			Output.printText("casting exception...");
			return;
		}

		int countChar;
		int count = 0;
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			outputStream = new StringWriter();
			// outputStream2 = new FileWriter("./src/myText.txt", false);

			final int markInt = 640000;
			// outputStream.getBuffer().setLength(0);
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "מחפש" + " \"" + searchSTR + "\"...";
			Output.printText(str);
			str = "\u202B" + "בין דילוג" + ToraApp.cSpace() + minDilug + " ו" + ToraApp.cSpace() + maxDilug + ".";
			Output.printText(str);
			Output.printText(StringAlignUtils.padRight("", str.length()).replace(' ', '-'));
			// System.out.println(formatter.locale());
			for (int thisDilug = minDilug; thisDilug <= maxDilug; thisDilug++) {
				ArrayList<String[][]> results = new ArrayList<String[][]>();
				Output.printText("");
				Output.printText(str = ("דילוג" + ToraApp.cSpace() + thisDilug));
				Output.printText(StringAlignUtils.padRight("", str.length()).replace(' ', '-'));
				inputStream = new BufferedReader(new FileReader("./src/Lines_2.txt"));
				inputStream.mark(markInt);
				int countPOS = 0; // counts char position in line
				int[][] lineForChar = new int[searchSTR.length()][2]; // Holds line and position of char found
				int countLines = 1; // counts line in Tora File
				int backup_countLines = 0; // backup for when reset buffer
				int backup_countPOS = 0; // backup for when reset buffer
				int backup_countChar = 0; // backup for when reset buffer
				searchIndex = 0; // index of letter in searchSTR which is being examined
				countChar = 0; // used to update progressbar
				count = 0; // counts matches
				int lastCharIndex = 0; // counts letters from last match
				int c;
				while ((c = inputStream.read()) != -1) {
					if ((c == 10) || (c == 32)) {
						if (c == 10) {
							countPOS = 0;
							countLines++;
						} else {
							countPOS++;
						}
						continue;
					}
					lastCharIndex = (searchIndex == 0) ? 0 : lastCharIndex + 1;
					countPOS++;
					countChar++;
					if (countChar % 250 == 0) {
						frame.SwingActivity.getInstance().callProcess(countLines);
					}
					if ((searchIndex == 0) || (lastCharIndex % thisDilug == 0)) {
						if (compareChar(searchSTR.charAt(searchIndex), (char) c, bool_sofiot)) {
							lastCharIndex = 0;
							if (searchIndex == 0) {
								inputStream.mark(markInt);
								backup_countLines = countLines;
								backup_countPOS = countPOS;
								backup_countChar = countChar;
							}
							lineForChar[searchIndex][0] = countLines;
							lineForChar[searchIndex][1] = countPOS;
							searchIndex++;
							if (searchIndex == searchSTR.length()) {
								count++;
								Output.printText("\u202B" + "\"" + searchOriginal + "\" " + "נמצא ב");
								Boolean boolRepeat = false; // verify if comma and spaces are needed
								String[][] resArray = new String[searchSTR.length() + 1][6];
								resArray[0][0] = String.valueOf(thisDilug);
								resArray[0][1] = searchOriginal;
								String reportLine = "";
								for (int i = 0; i < searchSTR.length(); i++) {
									reportLine += ((boolRepeat) ? ", " : "") + String.valueOf(searchOriginal.charAt(i));
									ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(lineForChar[i][0]);
									String lineText;
									try (Stream<String> lines = Files.lines(Paths.get("./src/Lines_2.txt"))) {
										lineText = lines.skip(lineForChar[i][0] - 1).findFirst().get();
									}
									resArray[i + 1][0] = String.valueOf(searchSTR.charAt(i));
									resArray[i + 1][1] = pBookInstance.getBookName();
									resArray[i + 1][2] = pBookInstance.getPerekLetters();
									resArray[i + 1][3] = pBookInstance.getPasukLetters();
									resArray[i + 1][4] = lineText;
									resArray[i + 1][5] = String.valueOf(lineForChar[i][1]);
									if (((i + 1) < searchSTR.length())
											&& (lineForChar[i][0] == lineForChar[i + 1][0])) {
										boolRepeat = true;
										continue;
									}
									boolRepeat = false;
									String tempStr1 = reportLine + ":  "
											+ StringAlignUtils.padRight(pBookInstance.getBookName(), 6) + " "
											+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters();
									Output.printText(StringAlignUtils.padRight(tempStr1, 32) + " =    " + lineText);
								}
								results.add(resArray);
								inputStream.reset();
								searchIndex = 0;
								countLines = backup_countLines;
								countPOS = backup_countPOS;
								countChar = backup_countChar;
								lastCharIndex = 0;
							}
						} else if (searchIndex != 0) {
							searchIndex = 0;
							inputStream.reset();
							lastCharIndex = 0;
							countLines = backup_countLines;
							countPOS = backup_countPOS;
							countChar = backup_countChar;
						}
					}
				}
				String Title = "חיפוש מילים בדילוגים בתורה" + ((bool_sofiot) ? " (מתעלם מסופיות)." : ".");
				String fileName = searchOriginal;
				String sheet = "דילוגים" + String.valueOf(thisDilug) + ((bool_sofiot) ? "סופיות" : "ללא_סופיות");
				if (count > 0) {
					ExcelFunctions.writeXLS(fileName,sheet, 1, Title, searchOriginal, results);
				}
			}
			Output.printText("");
			Output.printText(
					"\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(count) + " פעמים" + ".");
		} catch (Exception e) {
			// Output.printText("Found Error at Line: " + countLines);
		} finally {
			Output.printText("");
			Output.printText("\u202B" + "סיים חיפוש");
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}

			/*
			 * if (outputStream2 != null) { outputStream2.close(); }
			 */
		}
	}
}
