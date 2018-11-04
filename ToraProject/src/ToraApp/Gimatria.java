package ToraApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.lang3.StringUtils;

import StringFormatting.StringAlignUtils;


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

	public static void callCalculateGimatria(String str) {
		Output.printText(String.valueOf(Gimatria.calculateGimatria(str)));
	}

	public void searchGimatria(Object[] args) throws IOException {
		WordCounter wCounter = new WordCounter();
		BufferedReader inputStream = null;
		StringWriter outputStream = null;
		String searchSTR;
		boolean bool_wholeWords;
		boolean bool_gimatriaSofiot;
		boolean bool_countPsukim;
		// FileWriter outputStream2 = null;
		try {
			searchSTR = (String) args[0];
			bool_wholeWords = (Boolean) args[1];
			bool_gimatriaSofiot = (Boolean) args[2];
			bool_countPsukim = (Boolean) args[3];
		} catch (ClassCastException e) {
			Output.printText("casting exception...");
			e.printStackTrace();
			return;
		}
		int searchGmt;
		int sumGimatria = 0;
		if (StringUtils.isNumeric(searchSTR)) {
			searchGmt = Integer.parseInt(searchSTR);
		} else {
			searchGmt = calculateGimatria(searchSTR, bool_gimatriaSofiot);
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
			inputStream = new BufferedReader(new FileReader(new File(ClassLoader.getSystemResource(ToraApp.ToraLineFile).toURI())));
			outputStream = new StringWriter();
			// outputStream2 = new FileWriter("/myText.txt", false);
			inputStream.mark(640000);
			count = 0;
//				outputStream.getBuffer().setLength(0);
			String line;
			// \u202A - Left to Right Formatting
			// \u202B - Right to Left Formatting
			// \u202C - Pop Directional Formatting
			String str = "\u202B" + "מחפש גימטריה " + " \"" + searchGmt + "\"...";
			Output.printText(Output.markText(str, frame.frame.headerStyleHTML));
			if (bool_wholeWords) {
				Output.printText("\u202B" + Output.markText("חיפוש מילים שלמות",frame.frame.headerStyleHTML));
			} else {
				Output.printText("\u202B" + Output.markText("חיפוש צירופי אותיות",frame.frame.headerStyleHTML));
			}
			Output.printText(Output.markText(String.format("%1$-" + (str.length() + 1) + "s", "").replace(' ', '-'), frame.frame.headerStyleHTML));
			// System.out.println(formatter.locale());
			while ((line = inputStream.readLine()) != null) {
				countLines++;
				if (countLines % 25 == 0) {
					frame.SwingActivity.getInstance().callProcess(countLines);
				}
				if (bool_wholeWords) {
					String[] splitStr = line.trim().split("\\s+");
					for (String s : splitStr) {
						// Do your stuff here
						if (searchGmt == calculateGimatria(s, bool_gimatriaSofiot)) {
							count++;
							ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(countLines);
							String tempStr1 = "\u202B" + "\"" + Output.markText(s, frame.frame.markupStyleHTML) + "\" " + "נמצא ב" + pBookInstance.getBookName() + " "
									+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters();
							wCounter.addWord(s);
							Output.printText(StringAlignUtils.padRight(tempStr1, 32) + "  =  " + Output.markMatchesInLine(line, s, frame.frame.markupStyleHTML, bool_gimatriaSofiot, bool_wholeWords));
						}
					}
				} else {
					sumGimatria = 0;
					int lineCountEnd = 0;
					int lineCountStart = 0;
					for (char ch : line.toCharArray()) {
						lineCountEnd += 1;
						sumGimatria += calculateGimatriaLetter(ch, bool_gimatriaSofiot);
						if (sumGimatria > searchGmt) {
							while ((sumGimatria > searchGmt)
									|| ((line.length() > lineCountStart) && (line.charAt(lineCountStart) == ' '))) {
								sumGimatria -= calculateGimatriaLetter(line.charAt(lineCountStart),
										bool_gimatriaSofiot);
								lineCountStart += 1;
							}
						}
						if ((sumGimatria == searchGmt)
								&& ((line.length() > lineCountEnd) && (line.charAt(lineCountEnd) != ' '))) {
							count += 1;
							ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(countLines);
							String s = line.substring(lineCountStart, lineCountEnd);
							String tempStr1 = "\u202B" + "\"" + Output.markText(s, frame.frame.markupStyleHTML) + "\" " + "נמצא ב" + pBookInstance.getBookName() + " "
									+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters();
							wCounter.addWord(s);
							Output.printText(StringAlignUtils.padRight(tempStr1, 32) + "  =  " + Output.markMatchesInLine(line, s, frame.frame.markupStyleHTML, bool_gimatriaSofiot, bool_wholeWords));
							if (bool_countPsukim) {
								break;
							}
						}
					}
				}
				if (frame.frame.getMethodCancelRequest()) {
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
		} catch (Exception e) {
			Output.printText("");
			Output.printText("Found Error at Line: " + countLines,1);
			e.printStackTrace();
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
