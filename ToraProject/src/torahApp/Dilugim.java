package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import frame.Frame;
import hebrewLetters.HebrewLetters;
import ioManagement.ExcelFunctions;
import ioManagement.ManageIO;
import ioManagement.Output;
import stringFormatting.StringAlignUtils;

public class Dilugim {
	private static Dilugim instance;

	public static Dilugim getInstance() {
		if (instance == null) {
			instance = new Dilugim();
		}
		return instance;
	}

	public class paddingResults {
		private StringBuilder myString;
		private int[] paddingCount;

		paddingResults(StringBuilder strB, int headPad, int tailPad) {
			myString = strB;
			paddingCount = new int[] { headPad, tailPad };
		}

		StringBuilder getMyString() {
			return myString;
		}

		int getPaddingHead() {
			return paddingCount[0];
		}

		int getPaddingTail() {
			return paddingCount[1];
		}
	}

	public paddingResults readDilugExpandedResult(String searchSTR, int countChar, int dilug, int padding) {
		BufferedReader inputStream = null;
		StringBuilder str = null;
		int countJumps = 0, newpadding = 0;
		BufferedReader bReader = ManageIO.getBufferedReader(ToraApp.ToraLetterFile, ToraApp.subTorahLetterFile);
		if (bReader == null) {
			return null;
		}
		try {
			inputStream = bReader;
			@SuppressWarnings("unused")
			int markInt = 640000;
			// inputStream.mark(markInt);
			int c;
			int startChar = countChar - dilug * padding;
			newpadding = padding;
			while (startChar <= 0) {
				// if the padding is too large find the minimum position to read from.
				startChar += dilug;
				newpadding -= 1;
			}
			int maxJumps = padding + newpadding + searchSTR.length();
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
			Output.printText("Error with loading Lines.txt", 1);
			/*
			 * } catch (NoSuchFieldException e) { e.printStackTrace();
			 */
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new paddingResults(str, newpadding, newpadding + searchSTR.length());
	}

	public void searchWordsDilugim(Object[] args) throws IOException {
		// String[][] results=null;
		BufferedReader inputStream = null;
		String searchSTR;
		String searchOriginal;
		int[] searchRange;
		Boolean bool_sofiot;
		Boolean bool_filePaddingFound = true;
		int minDilug;
		int maxDilug;
		int padding;
		int countAll = 0;
		// FileWriter outputStream2 = null;
		BufferedReader bReader = ManageIO.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
		if (bReader == null) {
			return;
		}
		BufferedReader tempReader = ManageIO.getBufferedReader(ToraApp.ToraLetterFile, ToraApp.subTorahLetterFile);
		if (tempReader == null) {
			bool_filePaddingFound = false;
		} else {
			tempReader.close();
		}

		try {
			if (args.length < 6) {
				throw new IllegalArgumentException("Missing Arguments in Dilugim.searchWordsDilugim");
			}
			searchOriginal = ((String) args[0]);
			searchSTR = searchOriginal.replace(" ", "");
			bool_sofiot = (args[1] != null) ? (Boolean) args[1] : true;
			minDilug = ((args[2] != null) && (StringUtils.isNumeric((String)args[2])) && (((String) args[2]).length() > 0)) ? Integer.parseInt((String) args[2])
					: 2;
			maxDilug = ((args[3] != null) && (StringUtils.isNumeric((String)args[3])) && (((String) args[3]).length() > 0)) ? Integer.parseInt((String) args[3])
					: 2;
			padding = ((args[4] != null) && (StringUtils.isNumeric((String)args[4])) && (((String) args[4]).length() > 0)) ? Integer.parseInt((String) args[4]) : 0;
			searchRange = (args[5] != null) ? (int[]) (args[5]) : (new int[] { 0, 0 });
			if (!bool_sofiot) {
				searchSTR = HebrewLetters.switchSofiotStr(searchSTR);
			}
		} catch (ClassCastException e) {
			Output.printText("casting exception...");
			return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		}

		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			final int markInt = 640000;
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "מחפש" + " \"" + searchSTR + "\"...";
			Output.printText(Output.markText(str, frame.Frame.headerStyleHTML));
			str = "\u202B" + "בין דילוג" + ToraApp.cSpace() + minDilug + " ו" + ToraApp.cSpace() + maxDilug + ".";
			Output.printText(Output.markText(str, frame.Frame.headerStyleHTML));
			// Output.printText("");
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
				Output.printText(StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
			} else {
				Output.printText(Frame.HtmlHRLine);
			}
			// System.out.println(formatter.locale());
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
				frame.Frame.setLabel_countMatch("נמצא " + "0" + " פעמים");
				frame.SwingActivity.setFinalProgress(searchRange);
			}
			for (int thisDilug = minDilug; thisDilug <= maxDilug; thisDilug++) {
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.setLabel_dProgress("דילוג " + thisDilug);
				}
				ArrayList<String[][]> results = new ArrayList<String[][]>();
				inputStream = ManageIO.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
				inputStream.mark(markInt);
				int countPOS = 0; // counts char position in line
				int[][] lineForChar = new int[searchSTR.length()][3]; // Holds line and | position of Char found on
																		// Line
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
					if ((searchRange[1] != 0) && ((countLines <= searchRange[0]) || (countLines > searchRange[1]))) {
						if (searchIndex != 0) {
							searchIndex = 0;
							inputStream.reset();
							lastCharIndex = 0;
							countLines = backup_countLines;
							countPOS = backup_countPOS;
							countChar = backup_countChar;
						}
						continue;
					}
					lastCharIndex = (searchIndex == 0) ? 0 : lastCharIndex + 1;
					countPOS++;
					countChar++;
					if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (countLines % 25 == 0)) {
						frame.SwingActivity.getInstance().callProcess(countLines, thisDilug, minDilug, maxDilug);
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
								countAll++;
								if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
									frame.Frame.setLabel_countMatch("נמצא " + countAll + " פעמים");
								}
								if (count == 1) {
									Output.printText("");
									Output.printText(str = Output.markText(("דילוג" + ToraApp.cSpace() + thisDilug),
											frame.Frame.headerStyleHTML));
									// Output.printText("");
									if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
										Output.printText(
												StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
									} else {
										Output.printText(Output.markText("<hr size=5>", frame.Frame.headerStyleHTML));
									}
								}
								Output.printText(
										"\u202B" + "\"" + Output.markText(searchOriginal, frame.Frame.markupStyleHTML)
												+ "\" " + "נמצא ב");
								Boolean boolRepeat = false; // verify if comma and spaces are needed
								// Must change size of resArray when adding more information
								String[][] resArray = new String[searchSTR.length() + 1][7];
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
									try (BufferedReader bReader2 = ManageIO.getBufferedReader(ToraApp.ToraLineFile,
											ToraApp.subTorahLineFile); Stream<String> lines = bReader2.lines()) {
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
									resArray[i + 1][ExcelFunctions.id_lineNum] = String.valueOf(lineForChar[i][0]);
									if (((i + 1) < searchSTR.length())
											&& (lineForChar[i][0] == lineForChar[i + 1][0])) {
										boolRepeat = true;
										continue;
									}
									boolRepeat = false;
									String tempStr1 = Output.markText(reportLine, frame.Frame.markupStyleHTML) + ":  "
											+ StringAlignUtils.padRight(pBookInstance.getBookName(), 6) + " "
											+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters();
									String lineHtml = Output.markMatchPOS(lineText, i, lineForChar,
											frame.Frame.markupStyleHTML);
									Output.printText(StringAlignUtils.padRight(tempStr1, 32) + " =    " + lineHtml);
								}
								if (bool_filePaddingFound) {
									paddingResults strResults = readDilugExpandedResult(searchSTR, lineForChar[0][2],
											thisDilug, padding);

									resArray[0][2] = strResults.getMyString().toString();
									resArray[0][3] = String.valueOf(strResults.getPaddingHead());
									resArray[0][4] = String.valueOf(strResults.getPaddingTail());
									Output.printText("שורת הדילוג:" + ToraApp.cSpace(2)
											+ Output.markTextBounds(strResults.getMyString().toString(),
													strResults.getPaddingHead(), (strResults.getPaddingTail()),
													frame.Frame.markupStyleHTML));
								}
								Output.printText("");
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
				Output.printText("");
				Output.printText(
						Output.markText(
								"\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(count)
										+ " פעמים" + " לדילוג" + ToraApp.cSpace() + thisDilug + ".",
								frame.Frame.footerStyleHTML));
				Output.printText("");
				String Title = "חיפוש מילים בדילוגים בתורה" + ((bool_sofiot) ? " (מתעלם מסופיות)." : ".");
				String fileName = searchOriginal;
				String sheet = "דילוגים" + String.valueOf(thisDilug) + ((bool_sofiot) ? "סופיות" : "ללא_סופיות");
				if (count > 0) {
					ExcelFunctions.writeXLS(fileName, sheet, 2, Title, results, bool_filePaddingFound,
							((ToraApp.getGuiMode()==ToraApp.id_guiMode_Frame)? Frame.get_searchRangeText():"")
							);
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					maxDilug = thisDilug;
					Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
					// break is redundant, because for loop will end anyway because maxDilug has
					// changed to current loop index
					break;
				}
			}
			Output.printText("");
			Output.printText(Output.markText(
					"\u202B" + "נמצא " + "\"" + searchSTR + "\"" + "\u00A0" + String.valueOf(countAll) + " פעמים"
							+ " לדילוגים" + ToraApp.cSpace() + minDilug + " עד" + ToraApp.cSpace() + maxDilug + ".",
					frame.Frame.footerStyleHTML));
			Output.printText(Output.markText("\u202B" + "סיים חיפוש", frame.Frame.footerStyleHTML));
		} catch (Exception e) {
			Output.printText("Error with Dilug", 1);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
