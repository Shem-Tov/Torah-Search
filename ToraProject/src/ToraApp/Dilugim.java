package ToraApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import HebrewLetters.HebrewLetters;
import StringFormatting.StringAlignUtils;

public class Dilugim {
	private static Dilugim instance;

	public static Dilugim getInstance() {
		if (instance == null) {
			instance = new Dilugim();
		}
		return instance;
	}

	public StringBuilder readDilugExpandedResult(String searchSTR, int countChar, int dilug, int padding) {
		BufferedReader inputStream = null;
		StringBuilder str = null;
		try {
			inputStream = new BufferedReader(new FileReader(ToraApp.ToraLetterFile));
			@SuppressWarnings("unused")
			int markInt = 640000;
			// inputStream.mark(markInt);
			int c;
			int startChar = countChar - dilug * padding;
			int newpadding = padding;
			while (startChar < 0) {
				// if the padding is too large find the minimum position to read from.
				startChar += dilug;
				newpadding -= 1;
			}
			int maxJumps = padding + newpadding + searchSTR.length();
			int countJumps = 0;
			str = new StringBuilder(maxJumps);
			if (startChar > 1) {
				inputStream.skip(startChar - 1);
			}
			while ((c = inputStream.read()) != -1) {
				countJumps++;
				str.append((char) c);
				if (countJumps < maxJumps) {
					try {
						if (dilug > 0) {
							inputStream.skip((dilug - 1));
						}
					} catch (IOException e) {
						e.printStackTrace();
						break;
					}
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}

	public void searchWordsDilugim(Object[] args) throws IOException {
		// String[][] results=null;
		BufferedReader inputStream = null;
		StringWriter outputStream = null;
		String searchSTR;
		String searchOriginal;
		Boolean bool_sofiot;
		int minDilug;
		int maxDilug;
		int padding;
		@SuppressWarnings("unused")
		int offset;
		// FileWriter outputStream2 = null;
		try {
			searchOriginal = ((String) args[0]);
			searchSTR = searchOriginal.replace(" ", "");
			bool_sofiot = (args[1] != null) ? (Boolean) args[1] : true;
			minDilug = ((args[2] != null) && (((String) args[2]).length() > 0)) ? Integer.parseInt((String) args[2])
					: 2;
			maxDilug = ((args[3] != null) && (((String) args[3]).length() > 0)) ? Integer.parseInt((String) args[3])
					: 2;
			padding = ((args[4] != null) && (((String) args[4]).length() > 0)) ? Integer.parseInt((String) args[4]) : 0;
			offset = ((args[5] != null) && (((String) args[5]).length() > 0)) ? Integer.parseInt((String) args[5]) : 0;

			if (!bool_sofiot) {
				searchSTR = HebrewLetters.switchSofiotStr(searchSTR);
			}
		} catch (ClassCastException e) {
			Output.printText("casting exception...");
			return;
		}

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
			Output.printText(Output.markText(str, frame.frame.headerStyleHTML));
			str = "\u202B" + "בין דילוג" + ToraApp.cSpace() + minDilug + " ו" + ToraApp.cSpace() + maxDilug + ".";
			Output.printText(Output.markText(str, frame.frame.headerStyleHTML));
			Output.printText(Output.markText(StringAlignUtils.padRight("", str.length()).replace(' ', '-'),
					frame.frame.headerStyleHTML));
			// System.out.println(formatter.locale());
			for (int thisDilug = minDilug; thisDilug <= maxDilug; thisDilug++) {
				ArrayList<String[][]> results = new ArrayList<String[][]>();
				inputStream = new BufferedReader(new FileReader(ToraApp.ToraLineFile));
				inputStream.mark(markInt);
				int countPOS = 0; // counts char position in line
				int[][] lineForChar = new int[searchSTR.length()][3]; // Holds line and | position of Char found on Line
																		// | position of Char in File
				int countLines = 1; // counts line in Tora File
				int backup_countLines = 0; // backup for when reset buffer
				int backup_countPOS = 0; // backup for when reset buffer
				int backup_countChar = 0; // backup for when reset buffer
				int searchIndex = 0; // index of letter in searchSTR which is being examined
				int countChar = 0; // used to update progressbar and connect with Letter version of Torah
				int count = 0; // counts matches
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
						if (HebrewLetters.compareChar(searchSTR.charAt(searchIndex), (char) c, bool_sofiot)) {
							lastCharIndex = 0;
							if (searchIndex == 0) {
								inputStream.mark(markInt);
								backup_countLines = countLines;
								backup_countPOS = countPOS;
								backup_countChar = countChar;
							}
							lineForChar[searchIndex][0] = countLines;
							lineForChar[searchIndex][1] = countPOS;
							lineForChar[searchIndex][2] = countChar;
							searchIndex++;
							if (searchIndex == searchSTR.length()) {
								count++;
								if (count == 1) {
									Output.printText("");
									Output.printText(str = Output.markText(("דילוג" + ToraApp.cSpace() + thisDilug),
											frame.frame.headerStyleHTML));
									Output.printText(Output.markText(
											StringAlignUtils.padRight("", str.length()).replace(' ', '-'),
											frame.frame.headerStyleHTML));
								}
								Output.printText(
										"\u202B" + "\"" + Output.markText(searchOriginal, frame.frame.markupStyleHTML)
												+ "\" " + "נמצא ב");
								Boolean boolRepeat = false; // verify if comma and spaces are needed
								String[][] resArray = new String[searchSTR.length() + 1][6];
								resArray[0][0] = String.valueOf(thisDilug);
								resArray[0][1] = searchOriginal;
								String reportLine = "";
								for (int i = 0; i < searchSTR.length(); i++) {
									if (!boolRepeat) {
										reportLine = "";
									}
									reportLine += ((boolRepeat) ? ", " : "") + String.valueOf(searchOriginal.charAt(i));
									ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(lineForChar[i][0]);
									String lineText;
									try (Stream<String> lines = Files.lines(Paths.get(ToraApp.ToraLineFile))) {
										// Recieves words of Pasuk
										lineText = lines.skip(lineForChar[i][0] - 1).findFirst().get();
									}
									// must change dimension of resArray if you add to the results
									resArray[i + 1][0] = String.valueOf(searchSTR.charAt(i));
									resArray[i + 1][1] = pBookInstance.getBookName();
									resArray[i + 1][2] = pBookInstance.getPerekLetters();
									resArray[i + 1][3] = pBookInstance.getPasukLetters();
									resArray[i + 1][ExcelFunctions.id_toraLine] = lineText;
									resArray[i + 1][ExcelFunctions.id_charPOS] = String.valueOf(lineForChar[i][1]);
									if (((i + 1) < searchSTR.length())
											&& (lineForChar[i][0] == lineForChar[i + 1][0])) {
										boolRepeat = true;
										continue;
									}
									boolRepeat = false;
									String tempStr1 = Output.markText(reportLine, frame.frame.markupStyleHTML) + ":  "
											+ StringAlignUtils.padRight(pBookInstance.getBookName(), 6) + " "
											+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters();
									String lineHtml = Output.markMatchPOS(lineText, i, lineForChar,
											frame.frame.markupStyleHTML);
									Output.printText(StringAlignUtils.padRight(tempStr1, 32) + " =    " + lineHtml);
								}
								results.add(resArray);
								StringBuilder strBuilder = readDilugExpandedResult(searchSTR, lineForChar[0][2],
										thisDilug, padding);
								Output.printText(strBuilder.toString());
								Output.printText("");
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
				Output.printText("");
				Output.printText(Output.markText("\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0"
						+ String.valueOf(count) + " פעמים" + ".", frame.frame.footerStyleHTML));
				Output.printText("");
				String Title = "חיפוש מילים בדילוגים בתורה" + ((bool_sofiot) ? " (מתעלם מסופיות)." : ".");
				String fileName = searchOriginal;
				String sheet = "דילוגים" + String.valueOf(thisDilug) + ((bool_sofiot) ? "סופיות" : "ללא_סופיות");
				if (count > 0) {
					ExcelFunctions.writeXLS(fileName, sheet, 2, Title, results);
				}
			}
		} catch (Exception e) {
			// Output.printText("Found Error at Line: " + countLines);
		} finally {
			Output.printText("");
			Output.printText(Output.markText("\u202B" + "סיים חיפוש",frame.frame.footerStyleHTML));
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
