package ioManagement;

import java.util.ArrayList;

import frame.Frame;
import hebrewLetters.HebrewLetters;
import stringFormatting.HtmlGenerator;
import stringFormatting.StringAlignUtils;
import stringFormatting.StringAlignUtils.Alignment;
import torahApp.ToraApp;

public class Output {
	public static String markMatchPOS(String line, int indexOfArray, int[][] charPOSArray,
			stringFormatting.HtmlGenerator htmlFormat) {
		// Does not mark, if in console mode
		if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
			return line;
		}
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		String lineHtml = "";
		for (int[] i : charPOSArray) {
			if (charPOSArray[indexOfArray][0] == i[0]) {
				indexes.add(i[1] - 1);
			}
		}
		int lastIndex = 0;
		for (Integer thisIndex : indexes) {
			// Boolean wasSpace = false;
			String tempStr = "";
			if (thisIndex > 0) {
				tempStr = line.substring(lastIndex, thisIndex);
				/*
				 * if (tempStr.charAt(tempStr.length() - 1) == ' ') { wasSpace = true; //
				 * removes whitespace from the end tempStr = tempStr.replaceFirst("\\s++$", "");
				 * }
				 */
			}
			// lineHtml += tempStr + ((wasSpace) ? ToraApp.cSpace() : "") +
			// htmlFormat.getHtml(0)
			// + line.substring(thisIndex, 1 + thisIndex) + htmlFormat.getHtml(1);
			lineHtml += tempStr + htmlFormat.getHtml(0) + line.substring(thisIndex, 1 + thisIndex)
					+ htmlFormat.getHtml(1);

			lastIndex = thisIndex + 1;
		}
		lineHtml += line.substring(lastIndex);
		return lineHtml;
	}

	public static String markMatchesInLine(String line, String searchSTR, stringFormatting.HtmlGenerator htmlFormat,
			Boolean bool_sofiot, Boolean bool_wholeWords) {
		return markMatchesInLine(line, searchSTR, htmlFormat,
				bool_sofiot, bool_wholeWords,-1);
	}
	
	public static String markMatchesInLine(String line, String searchSTR, stringFormatting.HtmlGenerator htmlFormat,
			Boolean bool_sofiot, Boolean bool_wholeWords, int index) {

		// Does not mark, if in console mode
		if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
			return line;
		}
		String lineHtml = "";
		String lineConvert;
		String searchConvert;
		if (!bool_sofiot) {
			searchConvert = HebrewLetters.switchSofiotStr(searchSTR);
			lineConvert = HebrewLetters.switchSofiotStr(line);
		} else {
			searchConvert = searchSTR;
			lineConvert = line;
		}
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		int myIndex;
		indexes.add(lineConvert.indexOf(searchConvert, 0));
		try {
			if (indexes.get(0) == -1) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		int STRLength = searchSTR.length();
		int newIndex = 0;
		int startPOS = indexes.get(0);
		if (bool_wholeWords) {
			// checks for spaces before and after word
			boolean cancel = false;
			if ((indexes.get(0) > 0) && (lineConvert.charAt(indexes.get(0) - 1) != ' ')) {
				cancel = true;
			}
			if (((indexes.get(0) + searchConvert.length() < lineConvert.length())
					&& (lineConvert.charAt(indexes.get(0) + searchConvert.length()) != ' '))) {
				cancel = true;
			}
			if (cancel) {
				startPOS += 1;
				indexes.remove(0);
			}
		}
		// find all occurences of searchSTR in Line and Color them
		while ((newIndex = lineConvert.indexOf(searchConvert, startPOS + 1)) != -1) {
			if (bool_wholeWords) {
				// checks for spaces before and after word
				Boolean cancel = false;
				if ((newIndex > 0) && (lineConvert.charAt(newIndex - 1) != ' ')) {
					cancel = true;
				}
				if (((newIndex + searchConvert.length() < lineConvert.length())
						&& (lineConvert.charAt(newIndex + searchConvert.length()) != ' '))) {
					cancel = true;
				}
				if (!cancel) {
					startPOS = newIndex;
					indexes.add(newIndex);
				} else {
					startPOS += 1;
				}
			} else {
				startPOS = newIndex;
				indexes.add(newIndex);
			}
		}
		if (index!=-1) {
			myIndex = indexes.get(index);
			indexes = new ArrayList<Integer>();
			indexes.add(myIndex);
		}
		int lastIndex = 0;
		for (Integer thisIndex : indexes) {

			// Boolean wasSpace = false;
			String tempStr = "";
			if (thisIndex > 0) {
				tempStr = line.substring(lastIndex, thisIndex);
				/*
				 * if ((tempStr.length() >= 1) && (tempStr.charAt(tempStr.length() - 1) == ' '))
				 * { wasSpace = true; // removes whitespace from the end tempStr =
				 * tempStr.replaceFirst("\\s++$", ""); }
				 */
			}

			// lineHtml += tempStr + ((wasSpace) ? ToraApp.cSpace() : "") +
			// htmlFormat.getHtml(0)
			// + line.substring(thisIndex, STRLength + thisIndex) + htmlFormat.getHtml(1);

			lineHtml += tempStr + htmlFormat.getHtml(0) + line.substring(thisIndex, STRLength + thisIndex)
					+ htmlFormat.getHtml(1);

			lastIndex = thisIndex + STRLength;
		}
		lineHtml += line.substring(lastIndex);
		return lineHtml;
	}

	public static String markText(String str, HtmlGenerator markupStyle) {
		// Does not mark, if in console mode
		if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
			return str;
		}
		return (markupStyle.getHtml(0) + str + markupStyle.getHtml(1));
	}

	public static String markTextBounds(String str, int startMark, int finishMark, HtmlGenerator markupStyle) {
		// Does not mark, if in console mode
		if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
			return str;
		}
		String[] strArray = new String[] { "", "", "" };
		strArray[0] = str.substring(0, startMark);
		strArray[1] = markupStyle.getHtml(0) + str.substring(startMark, finishMark) + markupStyle.getHtml(1);
		strArray[2] = str.substring(finishMark);
		return strArray[0] + strArray[1] + strArray[2];
	}

	public static String[][] printPasukInfo(int countLines, String searchSTR, String line, HtmlGenerator markupStyle,
			Boolean bool_sofiot, Boolean bool_wholeWords) throws NoSuchFieldException {
		return printPasukInfo(countLines, searchSTR, line, markupStyle,
				bool_sofiot, bool_wholeWords, -1);
	}

	public static String[][] printPasukInfo(int countLines, String searchSTR, String line, HtmlGenerator markupStyle,
			Boolean bool_sofiot, Boolean bool_wholeWords, int index) throws NoSuchFieldException {
		ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(countLines);
		try {
			String tempStr1 = "\u202B" + "\"" + markText(searchSTR, markupStyle) + "\" " + "נמצא ב"
					+ StringAlignUtils.padRight(pBookInstance.getBookName(), 6) + " " + pBookInstance.getPerekLetters()
					+ ":" + pBookInstance.getPasukLetters();
			// Output.printText(StringAlignUtils.padRight(tempStr1, 32) + " = " + line);
			String lineHtml = markMatchesInLine(line, searchSTR, markupStyle, bool_sofiot, bool_wholeWords, index);
			printText(StringAlignUtils.padRight(tempStr1, 32) + " =    " + lineHtml);
			printLine(1);
		} catch (Exception e) {
			System.out.println("Error at line: " + countLines);
			e.printStackTrace();
		}
		return (new String[][] { { searchSTR, pBookInstance.getBookName(), pBookInstance.getPerekLetters(),
				pBookInstance.getPasukLetters(), line } });
	}

	public static void printLine(int size) {
		//blue is default color
		printLine(size,"blue");
	}
	
	public static void printLine(int size, String color) {
		String line = "<div style=\"height:" + size + 
				"px; font-size:0; background-color:" +
				color+";\"></div>";
		if (ToraApp.getGuiMode()==ToraApp.id_guiMode_Frame) {
			Frame.appendText(line,(byte)0);
		}
	}

	public static void printText(String text) {
		printText(text, (byte) 0);
	}

	public static void printText(String text, int mode) {
		printText(text, (byte) mode);
	}

	public static void printText(String text, byte mode) {
		// mode 0 = regular
		// mode 1 = attention
		// mode 2 = silence on GUI
		switch (ToraApp.getGuiMode()) {
		case ToraApp.id_guiMode_Frame: // GUI Mode
			switch (mode) {
			case 0:
			case 1:
				Frame.appendText(text, mode);
				// util.format is not needed because document is html and auto wraps text
				// Frame.appendText(util.format(text), mode);
				break;
			case 2:
				break;
			}
			break;
		default: // Console Mode - Reserved guiMode=0
			StringAlignUtils util = new StringAlignUtils(Frame.panelWidth, Alignment.RIGHT);
			switch (mode) {
			case 0: // user text
				System.out.println(util.format(text));
				break;
			case 1: // debug mode
			case 2:
				System.err.println(util.format(text));
				break;
			}
		}
	}
}
