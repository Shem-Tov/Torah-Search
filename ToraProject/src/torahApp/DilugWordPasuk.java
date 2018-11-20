package torahApp;
//Did not create Excel Sheet for this
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import frame.Frame;
import frame.Tree;
import hebrewLetters.HebrewLetters;
import ioManagement.ExcelFunctions;
import ioManagement.LineReport;
import ioManagement.ManageIO;
import ioManagement.Output;
import ioManagement.PaddingReport;
import stringFormatting.StringAlignUtils;

public class DilugWordPasuk {
	private static DilugWordPasuk instance;

	public static DilugWordPasuk getInstance() {
		if (instance == null) {
			instance = new DilugWordPasuk();
		}
		return instance;
	}

	public PaddingReport paddingDilugWordPasuk(String searchSTR, int padding, int mode,int dilug,int firstLineNum,int firstCharIndex) {
		// mode 1 ראש פסוק
		// mode 2 סוף פסוק
		// mode 3 ראש מילה
		// mode 4 סוף מילה
		int frontLinePadding=0;
		int indexWord=0;
		int endPadding=0,frontPadding;
		int reportFrontPadding=0;
		int searchSTRcount = searchSTR.length();
		ArrayList<String> arrayLines = new ArrayList<String>();
		try (BufferedReader bReader = ManageIO.getBufferedReader(ToraApp.ToraLineFile,
				ToraApp.subTorahLineFile);) {
			// Recieves words of Pasuk
			switch (mode) {
			case 1:
			case 2:
				frontLinePadding = Math.min(padding*dilug, firstLineNum-1);
			break;
			default:
				frontLinePadding = (int) Math.min(Math.ceil((float)padding*dilug/7), firstLineNum-1);
			}
			//used for modes 1,2 will be replaced otherwise
			indexWord=frontLinePadding;
			for (int i=0; i<(firstLineNum - 1-frontLinePadding);i++) {
				bReader.readLine();
			}
			//Stream<String> line = lines.skip(firstLineNum - 1-frontLinePadding);
			outer:
			for (int i=(firstLineNum-1-frontLinePadding); i<(firstLineNum+searchSTRcount*dilug+padding*dilug); i++) {
				switch (mode) {
				case 1:
				case 2:
					arrayLines.add(bReader.readLine());
					if (arrayLines.get(arrayLines.size()-1)==null) {
						arrayLines.remove(arrayLines.size()-1);
						break outer;
					}
					break;
				default:
					String tempStr=bReader.readLine();
					if (tempStr==null) {
						break outer;
					}
					List<String> list = Arrays.asList(tempStr.split("\\s+"));
					if (i==(firstLineNum-1)) {
						indexWord=arrayLines.size();
						int countChar=-1;
						//the first char is in position 0, that is why -1 is the start of count
						for (String s:list) {
							countChar+=s.length()+1;
							if (firstCharIndex>countChar) {
								indexWord++;
							} else {
								break;
							}
						}
					}
					arrayLines.addAll(list);
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
			//return new PaddingReport("Error creating padding",0,0);
		}
		switch (mode) {
		case 1:
		case 2:
			reportFrontPadding=Math.min(padding,(int)((firstLineNum-1)/dilug) );
			frontPadding = frontLinePadding;
			endPadding= Math.min(arrayLines.size()-frontPadding-searchSTRcount*dilug, padding*dilug);
			break;
		default:
			reportFrontPadding=Math.min(padding, (int)(indexWord/dilug));
			frontPadding = Math.min(padding*dilug, indexWord);
			endPadding= Math.min(arrayLines.size()-indexWord-searchSTRcount*dilug, padding*dilug);
		}
		if (frontPadding%dilug!=0) {
			frontPadding -= (frontPadding%dilug);
		}
		String paddingLine="";
		for (int i=0;i<(frontPadding+searchSTRcount*dilug+endPadding);i++) {
			if (i%dilug!=0) {
				continue;
			}
			String tempStr;
			tempStr = arrayLines.get(i+indexWord-frontPadding);
			int lookupIndex=0;
			switch (mode) {
			case 2:
			case 4:
				lookupIndex = tempStr.length()-1;
				break;
			}
			paddingLine += tempStr.substring(lookupIndex, lookupIndex+1);
		}
		return new PaddingReport(paddingLine,reportFrontPadding,reportFrontPadding+searchSTRcount);
	}
	
	public void searchDilugWordPasuk(Object[] args) throws IOException {
		// String[][] results=null;
		BufferedReader inputStream = null;
		String searchConvert;
		String searchSTR;
		int[] searchRange;
		Boolean bool_sofiot;
		int mode;
		// mode 1 ראש פסוק
		// mode 2 סוף פסוק
		// mode 3 ראש מילה
		// mode 4 סוף מילה
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
			if (args.length < 7) {
				throw new IllegalArgumentException("Missing Arguments in Dilugim.searchWordsDilugim");
			}
			searchSTR = ((String) args[0]);
			searchConvert = searchSTR.replace(" ", "");
			bool_sofiot = (args[1] != null) ? (Boolean) args[1] : true;
			minDilug = ((args[2] != null) && (StringUtils.isNumeric((String) args[2]))
					&& (((String) args[2]).length() > 0)) ? Integer.parseInt((String) args[2]) : 2;
			maxDilug = ((args[3] != null) && (StringUtils.isNumeric((String) args[3]))
					&& (((String) args[3]).length() > 0)) ? Integer.parseInt((String) args[3]) : 2;
			padding = ((args[4] != null) && (StringUtils.isNumeric((String) args[4]))
					&& (((String) args[4]).length() > 0)) ? Integer.parseInt((String) args[4]) : 0;
			searchRange = (args[5] != null) ? (int[]) (args[5]) : (new int[] { 0, 0 });
			mode = (int) ((args[6] != null) ? args[6] : 1);
			if (!bool_sofiot) {
				searchConvert = HebrewLetters.switchSofiotStr(searchConvert);
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
			String str = "\u202B" + "מחפש" + " \"" + searchConvert + "\"...";
			Output.printText(Output.markText(str, frame.Frame.headerStyleHTML));
			str = "\u202B" + "בין דילוג" + ToraApp.cSpace() + minDilug + " ו" + ToraApp.cSpace() + maxDilug + ".";
			Output.printText(Output.markText(str, frame.Frame.headerStyleHTML));
			// Output.printText("");
			if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Console) {
				Output.printText(StringAlignUtils.padRight("", str.length() + 4).replace(' ', '-'));
			} else {
				Tree.getInstance().changeRootText(Output.markText(searchConvert, Frame.headerStyleHTML));
				Output.printLine(Frame.lineHeaderSize);
				frame.Frame.setLabel_countMatch("נמצא " + "0" + " פעמים");
				frame.SwingActivity.setFinalProgress(searchRange);
			}
			// System.out.println(formatter.locale());
			for (int thisDilug = minDilug; thisDilug <= maxDilug; thisDilug++) {
				if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
					frame.Frame.setLabel_dProgress("דילוג " + thisDilug);
				}
				ArrayList<LineReport> results = new ArrayList<LineReport>();
				inputStream = ManageIO.getBufferedReader(ToraApp.ToraLineFile, ToraApp.subTorahLineFile);
				inputStream.mark(markInt);
				int countLines = 0; // counts line in Tora File
				int wordIndex = 0;
				int backup_wordIndex = 0;
				int backup_countLines = 0;
				int backup_wordIndex_0 = -1;
				int searchIndex = 0; // index of letter in searchSTR which is being examined
				int savedLineIndex = 0;
				int backup_savedLineIndex = 0;
				int backup_savedLineIndexFromLast = 0;
				// int backup_savedLineIndex=0;
				int count = 0; // counts matches
				boolean skipLine = false;
				String line;
				// first line of array list is line Count, next lines are char position.
				ArrayList<ArrayList<Integer>> resArray = new ArrayList<ArrayList<Integer>>();
				int indexResArray = -1;
				while ((line = inputStream.readLine()) != null) {
					if (skipLine) {
						countLines++;
						skipLine = false;
						continue;
					}
					if (savedLineIndex == 0) {
						// countLines only if this line is a new line
						countLines++;
					}
					
					if ((searchRange[1] != 0) && ((countLines <= searchRange[0]) || (countLines > searchRange[1]))) {
						continue;
					}
					if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (countLines % 25 == 0)) {
						frame.SwingActivity.getInstance().callProcess(countLines, thisDilug, minDilug, maxDilug);
					}
					String[] splitStr;
					String lineConvert;
					if (!bool_sofiot) {
						lineConvert = HebrewLetters.switchSofiotStr(line).trim();
					} else {
						lineConvert = line.trim();
					}
					switch (mode) {
					case 1:
					case 2:
						splitStr = new String[] { lineConvert };
						break;
					default:
						splitStr = lineConvert.split("\\s+");
					}
					for (int i = savedLineIndex; i < (splitStr.length); i++) {
						// word Index used for dilug
						wordIndex++;
						if (searchIndex == 0) {
							if (i == (splitStr.length - 1)) {
								inputStream.mark(markInt);
								backup_savedLineIndex = 0;
								backup_savedLineIndexFromLast = splitStr.length;
							} else {
								backup_savedLineIndex = i + 1;
								backup_savedLineIndexFromLast = (splitStr.length - 1 - i);
							}
							backup_wordIndex = wordIndex;
							backup_countLines = countLines;
						}
						if (i == splitStr.length - 1) {
							savedLineIndex = 0;
						}
						int lookupIndex = 0;
						switch (mode) {
						case 2:
						case 4:
							lookupIndex = splitStr[i].length() - 1;
						}
						// if not int the middle of a search (searchIndex==0)
						// or thiswordIndex is the correct dilug gap from the first letter found
						// (backup_wordIndex_0)

						if ((searchIndex == 0) || ((wordIndex - backup_wordIndex_0) % thisDilug == 0)) {
							if (splitStr[i].charAt(lookupIndex) == searchConvert.charAt(searchIndex)) {
								if (searchIndex == 0) {
									backup_wordIndex_0 = wordIndex;
								}
								// This collects information of char
								// positions of matches and organizes
								// them where char positions in the
								// same line will be in the same
								// arraylist.
								if ((resArray.size() == 0) || (resArray.get(indexResArray).get(0) != countLines)) {
									// create new index
									resArray.add(new ArrayList<Integer>());
									indexResArray++;
									resArray.get(indexResArray).add(countLines);
								}
								// add to last index created
								// add char Position
								int charPOS = 0;
								for (int l = 0; l < i; l++) {
									charPOS += splitStr[l].length() + 1;
								}
								charPOS += lookupIndex;
								resArray.get(indexResArray).add(charPOS);
								// increment the searchIndex
								searchIndex++;
								if (searchIndex == searchConvert.length()) {
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
											Tree.getInstance().addNodeDilug(str);
											Output.printLine(2, "orange");
										}
									}
									Output.printText("\u202B" + "\""
											+ Output.markText(searchConvert, frame.Frame.markupStyleHTML) + "\" "
											+ "נמצא ב");

									int reportLineIndex = 0;
									String treeString = "";
									for (ArrayList<Integer> j : resArray) {
										String reportLine = "";
										for (int k = reportLineIndex; k < (reportLineIndex + j.size() - 1); k++) {
											if (k != reportLineIndex) {
												reportLine += ", ";
											}
											reportLine += searchConvert.substring(k, k + 1);
										}
										reportLineIndex += j.size() - 1;
										String lineHtml = Output.markMatchesFromArrayList(j, Frame.markupStyleHTML);
										ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(j.get(0));
										String tempStr1 = Output.markText(reportLine, frame.Frame.markupStyleHTML)
												+ ":  " + StringAlignUtils.padRight(pBookInstance.getBookName(), 6)
												+ " " + pBookInstance.getPerekLetters() + ":"
												+ pBookInstance.getPasukLetters();
										String tempStr2 = StringAlignUtils.padRight(tempStr1, 32) + " =    " + lineHtml;
										treeString += tempStr2 + "<br>";
										Output.printText(tempStr2);
									}
									PaddingReport pReport = paddingDilugWordPasuk(searchSTR, padding, mode, thisDilug, resArray.get(0).get(0), resArray.get(0).get(1));
									String treeString2 = "שורת הדילוג:" + ToraApp.cSpace(2)
									+ Output.markTextBounds(pReport.getPaddingLine(),
											pReport.getStartMark(), pReport.getEndMark(),
											frame.Frame.markupStyleHTML);
									Output.printText(treeString2);
									treeString += treeString2;
									Output.printTree(resArray.get(0).get(0), treeString, true);
									Output.printLine(1);
									Output.printText("");
									resArray = new ArrayList<ArrayList<Integer>>();
									indexResArray = -1;
									searchIndex = 0;
									inputStream.reset();
									countLines = backup_countLines;
									wordIndex = backup_wordIndex;
									backup_wordIndex_0 = -1;
									if (backup_savedLineIndexFromLast == 0) {
										backup_savedLineIndex = 0;
										skipLine = true;
									}
									savedLineIndex = backup_savedLineIndex;
									break;
								}
							} else {
								if (searchIndex != 0) {
									resArray = new ArrayList<ArrayList<Integer>>();
									indexResArray = -1;
									searchIndex = 0;
									inputStream.reset();
									countLines = backup_countLines;
									wordIndex = backup_wordIndex;
									backup_wordIndex_0 = -1;
									if (backup_savedLineIndexFromLast == 0) {
										backup_savedLineIndex = 0;
										skipLine = true;
									}
									savedLineIndex = backup_savedLineIndex;
									break;
								}
							}
						}
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
				Output.printText(
						Output.markText(
								"\u202B" + "נמצא " + "\"" + searchConvert + "\"" + "\u00A0" + String.valueOf(count)
										+ " פעמים" + " לדילוג" + ToraApp.cSpace() + thisDilug + ".",
								frame.Frame.footerStyleHTML));
				Output.printLine(2, "orange");
				Output.printText("");
				/*
				String Title = "חיפוש מילים בדילוגים בתורה" + ".";
				String Title2 = "";
				String fileName = searchSTR.replace(' ', '_');
				String sheet = "דילוגים" + String.valueOf(thisDilug);
				if (bool_sofiot) {
					sheet += "_" + "ף";
					Title2 += "התחשבות בסופיות";
				}
				if (count > 0) {
					ExcelFunctions.writeXLS(fileName, sheet, 2, Title, results, bool_filePaddingFound, searchSTR,
							Title2,
							((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) ? Frame.get_searchRangeText() : ""));
				}
				*/
			}
			if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame)) {
				Tree.getInstance().flushBuffer((countAll < 50), true);
			}
			Output.printText("");
			Output.printText(Output.markText(
					"\u202B" + "נמצא " + "\"" + searchConvert + "\"" + "\u00A0" + String.valueOf(countAll) + " פעמים"
							+ " לדילוגים" + ToraApp.cSpace() + minDilug + " עד" + ToraApp.cSpace() + maxDilug + ".",
					frame.Frame.footerStyleHTML));
			Output.printText(Output.markText("\u202B" + "סיים חיפוש", frame.Frame.footerStyleHTML));
		} catch (

		Exception e) {
			Output.printText("Error with Dilug", 1);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
