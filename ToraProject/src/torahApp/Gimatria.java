package torahApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import frame.Frame;
import frame.Tree;
import ioManagement.ExcelFunctions;
import ioManagement.LineReport;
import ioManagement.ManageIO;
import ioManagement.Output;
import stringFormatting.StringAlignUtils;

public class Gimatria {
	private static Gimatria instance;

	public static Gimatria getInstance() {
		if (instance == null) {
			instance = new Gimatria();
		}
		return instance;
	}

	private static char[] hLetters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע',
			'פ', 'צ', 'ק', 'ר', 'ש', 'ת' };
	private static char[] endLetters = { 'ך', 'ם', 'ן', 'ף', 'ץ' };

	public static int calculateGimatriaLetter(char c, Boolean boolSofiot) {
		int index = new String(hLetters).indexOf(c);
		if (index > -1) {
			// =10^(ROUNDDOWN(A6 / 9))*(MOD(A6,9)+1)
			return (int) (Math.pow(10, index / 9) * ((index % 9) + 1));
		} else {
			index = new String(endLetters).indexOf(c);
			if (index > -1) {
				if (boolSofiot) {
					return (int) ((index) * 100 + 500);
				} else {
					int sum = 0;
					switch (index) {
					case 0:
						sum = 20;
						break;
					case 1:
						sum = 40;
						break;
					case 2:
						sum = 50;
						break;
					case 3:
						sum = 80;
						break;
					case 4:
						sum = 90;
					}
					return sum;
				}
			}
		}
		return 0;
	}

	public static int calculateGimatria(String str) {
		return calculateGimatria(str, false);
	}

	public static int calculateGimatria(String str, Boolean boolSofiot) {
		// boolSofiot=false לא מתחשב בסופיות
		// boolSofiot=true גימטריה מיוחדת לסופיות
		int sumGimatria = 0;
		for (char c : str.toCharArray()) {
			sumGimatria += calculateGimatriaLetter(c, boolSofiot);
		}
		return sumGimatria;
	}

	public static void callCalculateGimatria(Object[] args) {
		Output.printText(String.valueOf(Gimatria.calculateGimatria((String) args[0], (Boolean) args[1])));
	}

	public void searchGimatria(Object[] args) throws IOException {
		ArrayList<LineReport> results = new ArrayList<LineReport>();
		WordCounter wCounter = new WordCounter();
		BufferedReader inputStream = null;
		String searchSTR;
		int[] searchRange;
		boolean bool_wholeWords;
		boolean bool_sofiot;
		BufferedReader bReader = ManageIO.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
		if (bReader == null) {
			Output.printText("לא הצליח לפתוח קובץ תורה", 1);
			return;
		}
		// FileWriter outputStream2 = null;
		try {
			if (args.length < 3) {
				throw new IllegalArgumentException("Missing Arguments in Gimatria.searchGimatria");
			}
			searchSTR = (String) args[0];
			bool_wholeWords = (Boolean) args[1];
			bool_sofiot = (Boolean) args[2];
			searchRange = (args[3] != null) ?(int[])(args[3]) : (new int[] {0,0});
		} catch (ClassCastException e) {
			Output.printText("casting exception...");
			e.printStackTrace();
			return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		}
		int searchGmt;
		int sumGimatria = 0;
		if (StringUtils.isNumeric(searchSTR)) {
			searchGmt = Integer.parseInt(searchSTR);
		} else {
			searchGmt = calculateGimatria(searchSTR, bool_sofiot);
		}
		if (searchGmt == 0) {
			Output.printText("Can not search for a Gimatria of 0");
			return;
		}
		int countLines = 0;
		int count = 0;
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = bReader;
			inputStream.mark(640000);
			count = 0;
//				outputStream.getBuffer().setLength(0);
			String line;
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "מחפש גימטריה " + " \"" + searchGmt + "\"...";
			Output.printText(Output.markText(str, frame.Frame.headerStyleHTML));
			if (bool_wholeWords) {
				Output.printText("\u202B" + Output.markText("חיפוש מילים שלמות", frame.Frame.headerStyleHTML));
			} else {
				Output.printText("\u202B" + Output.markText("חיפוש צירופי אותיות", frame.Frame.headerStyleHTML));
			}
			// Output.printText("");
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
				frame.Frame.setLabel_countMatch("נמצא " + "0" + " פעמים");
				frame.SwingActivity.setFinalProgress(searchRange);
				Output.printLine(Frame.lineHeaderSize);
				Tree.getInstance().changeRootText(Output.markText(String.valueOf(searchGmt), Frame.headerStyleHTML));
			} else {
				Output.printText(StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
			}
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if ((searchRange[1]!=0) && 
						((countLines<=searchRange[0]) ||
								(countLines>searchRange[1]))) {
					continue;
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (countLines % 25 == 0)) {
					frame.SwingActivity.getInstance().callProcess(countLines);
				}
				if (bool_wholeWords) {
					String[] splitStr = line.trim().split("\\s+");
					for (String s : splitStr) {
						// Do your stuff here
						if (searchGmt == calculateGimatria(s, bool_sofiot)) {
							count++;
							if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
								frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
							}
							wCounter.addWord(s);
							results.add(Output.printPasukInfo(countLines, s, line, frame.Frame.markupStyleHTML,
									bool_sofiot, bool_wholeWords));
						}
					}
				} else {
					sumGimatria = 0;
					int lineCountEnd = 0;
					int lineCountStart = 0;
					for (char ch : line.toCharArray()) {
						lineCountEnd += 1;
						sumGimatria += calculateGimatriaLetter(ch, bool_sofiot);
						if (sumGimatria > searchGmt) {
							while ((sumGimatria > searchGmt)
									|| ((line.length() > lineCountStart) && (line.charAt(lineCountStart) == ' '))) {
								sumGimatria -= calculateGimatriaLetter(line.charAt(lineCountStart),
										bool_sofiot);
								lineCountStart += 1;
							}
						}
						if ((sumGimatria == searchGmt)
								&& ((line.length() > lineCountEnd) && (line.charAt(lineCountEnd) != ' '))) {
							count ++;
							if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
								frame.Frame.setLabel_countMatch("נמצא " + count + " פעמים");
							}
							String s = line.substring(lineCountStart, lineCountEnd);
							wCounter.addWord(s);
							results.add(Output.printPasukInfo(countLines, s, line, frame.Frame.markupStyleHTML,
									bool_sofiot, bool_wholeWords));
						}
					}
				}
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
					// break is redundant, because for loop will end anyway because maxDilug has
					// changed to current loop index
					break;
				}
			}
			Output.printText("");
			wCounter.printWords();
			Output.printText("");
			Output.printText("\u202B" + "נמצא " + "\"" + searchGmt + "\"" + "\u00A0" + count + " פעמים.");
			Output.printText("");
			if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame)) {
				Tree.getInstance().flushBuffer((count<50));
			}
			if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
				Output.printText("\u202B" + "המשתמש הפסיק חיפוש באמצע", 1);
				// break is redundant, because for loop will end anyway because maxDilug has
				// changed to current loop index
			}
			String Title = ((bool_wholeWords) ? "גימטריה: מילים שלמות" : "גימטריה: צירוף אותיות");
			String Title2="";
			String fileName = String.valueOf(searchGmt);
			String sheet = ((bool_wholeWords) ? "מילים" : "אותיות");
			if (bool_sofiot) {
				sheet += "_"+"ף";
				Title2 += "התחשבות בסופיות";
			}
			if (count > 0) {
				ExcelFunctions.writeXLS(fileName, sheet, 3, Title, results, true,String.valueOf(searchGmt)
						,Title2,((ToraApp.getGuiMode()==ToraApp.id_guiMode_Frame)? Frame.get_searchRangeText():"")
						);
			}
			Output.printText("\u202B" + "סיים חיפוש");
		} catch (Exception e) {
			Output.printText("");
			Output.printText("Found Error at Line: " + countLines, 1);
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
