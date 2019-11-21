package extras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import frame.Frame;
import ioManagement.ExcelFunctions;
import ioManagement.ManageIO;
import ioManagement.PropStore;
import ioManagement.ManageIO.fileMode;

public class CreateTorahTables {
	public enum TorahModel {
		ORAL, WRITTEN, BOTH, NoTevot
		// BOTH - Oral in line and Written in Brackets
		// NoTevot - Written without Oral, no spaces or line breaks
	}

	public static ArrayList<ArrayList<Integer>> getNumbersFromLineText(String fileName) {
		// Has been adapted to work only with the files Bible Hebrew OT WLC (Westminster
		// Leningrad Codex) from getbible.net
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		int count = -1;
		Boolean isFine = true;
		try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = buffer.readLine()) != null) {
				count++;
				Pattern p = Pattern.compile("\\d+");
				// ^[\\u05C3|\\u05BE]
				Matcher m = p.matcher(line);
				list.add(new ArrayList<Integer>());
				int counter;
				while (m.find()) {
					list.get(count).add(Math.abs((Integer.valueOf(m.group()))));
					// if (count == 4) {
					// System.out.print(m.group() + " ");
					// }
				}
				counter = 0;
				try {
					Pattern p2 = Pattern.compile("([\\u05D0-\\u05EA])");
					Matcher m2;
					try {
						m2 = p2.matcher(line.substring(line.lastIndexOf('|'), line.indexOf('\u05C3')));
					} catch (Exception e) {
						m2 = p2.matcher(line.substring(line.lastIndexOf('|')));
					}
					while (m2.find()) {
						// list.get(count).add(Math.abs((Integer.valueOf(m2.group()))));
						counter++;
						// if (count == 4) {
						// System.out.print(m2.group() + " ");
						// }
					}
					list.get(count).add(counter);
				} catch (Exception e) {
					System.out.println("Error at line " + (count + 1));
				}
				if (list.get(count).size() != 4) {
					System.out.println("Line #" + count + " does not have all properties");
					isFine = false;
				}
			}
		} catch (

		Exception e) {
			System.out.println("Error at " + count);
			e.printStackTrace();
		}
		if (isFine) {
			System.err.println("Function has retrieved all Data with no errors");
		}
		return list;
	}

	public static void adaptLineTextToTorahModel(String fileNameIN, String fileNameOUT, Boolean withVowels,
			TorahModel model) {
		// Has been adapted to work only with the files Bible Hebrew OT WLC (Westminster
		// Leningrad Codex) from getbible.net
		int count = 0;
		// int countAnomolies = 0;
		Boolean isFine = true;
		BufferedReader fileIN = null;
		BufferedWriter fileOUT = null;
		try {
			fileIN = new BufferedReader(new FileReader(fileNameIN));
			fileOUT = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileNameOUT), "UTF-8"));
			String line;
			while ((line = fileIN.readLine()) != null) {
				String line2;
				try {
					line2 = line.substring(line.lastIndexOf('|'), line.indexOf('\u05C3'));
				} catch (Exception e) {
					line2 = line.substring(line.lastIndexOf('|'));
				}
				String line3;
				String regex;
				// \\u05D0-\\u05EA - Hebrew Letters
				// \\s - whitespace
				// \\u05BE - makaf (־)
				// \\u05B0 -\\u05BB - Vowels
				// \\u05C7 - Vowel Qamatz Katan
				// \\u05C0 - Hebrew Paseq ( A vertical line > ׀ < Not the same as the regular |
				// )
				// \\u05C2 - Sin Dot
				// \\u05C4 - Upper Dot (Maybe for Cholam Above Lamed)
				// ^ - negates
				// The regex will look for all characters not included and replace them with ""
				// (nothing)
				count++;
				if (withVowels) {
					regex = "([^\\u05D0-\\u05EA\\s\\u05BE\\u05B0-\\u05BB\\u05C7\\u05C2\\u05C4\\(\\)\\[\\]])";
				} else {
					regex = "([^\\u05D0-\\u05EA\\s\\u05BE\\(\\)\\[\\]])";
				}
				// This regex includes all in the file of Westminster Leningrad Codex with
				// Vowels
				// regex =
				// "([\\u05D0-\\u05EA\\s\\u05BE\\u05B0-\\u05BB\\u05C7\\u05C0\\u05C2\u05C4|
				// ּּּׁׁ\\]\\[\\(\\)])";

				line3 = line2.replaceAll(regex, "");
				// String regexNoVowels = "([^\\u05D0-\\u05EA\\s\\u05BE])";

				/*
				 * //This is useful to compare words with vowels against those without //but
				 * does not work with string.contains Collator collator = Collator.getInstance(
				 * new Locale( "he" ) ); collator.setStrength( Collator.PRIMARY );
				 * System.out.println( collator.equals( withVowelsTwo, withoutVowelsTwo ) );
				 */

				// Remove anything inside ()
				// s.replaceAll("\\([^()]*\\)", "")
				// Remove anything inside []
				// s.replaceAll("\\[[^()]*\\]", "")
				String regexNoOral = "\\([^()]*\\)";
				String regexNoWritten = "\\[[^()]*\\]";
				String regexKeepWritten = "[\\[|כ\\]]";
				String regexKeepOral = "[\\(|ק\\)]";
				// removes trailing spaces, and replaces extra spaces with one
				// string.trim().replaceAll(" +", " ");
				/*
				 * // Finds a word with Vowels when the search term is without if
				 * (line3.replaceAll(regexNoVowels,"").contains("בראשית")) {
				 * System.err.print("line #" + count + " - "); System.err.println(line3); }
				 */
				String writeSTR = "";
				if (line3.contains("(")) {
					// System.err.println(count);
					// Needed to fix an error with original file
					// when the pronuncitaion is different than the written
					// only if it is the last work of the pasuk
					// the ק and parenthesis are omitted, they need to be placed.
					if (!line3.contains("ק)")) {
						line3 += " ק)";
					}
					// countAnomolies++;
					// System.err.print("line #" + count + " - ");
					// This line is supposed to count Unicode Characters, but it does not appear to
					// do that
					// System.err.print(line3.codePointCount(0, line3.length()) + " ");
					switch (model) {
					case WRITTEN:
						writeSTR = line3.replaceAll(regexNoOral, "").replaceAll(regexKeepWritten, "");
						break;
					case ORAL:
						writeSTR = line3.replaceAll(regexNoWritten, "").replaceAll(regexKeepOral, "");
						break;
					case BOTH:
						// BOTH
						writeSTR = line3.replaceAll(regexKeepWritten, "");
						break;
					case NoTevot:
						writeSTR = line3.replaceAll(regexNoOral, "").replaceAll(regexKeepWritten, "");
					}
					/*
					 * if ((countAnomolies>1) && (model == TorahModel.BOTH)) {
					 * System.err.println(writeSTR.trim().replaceAll(" +", " ")); return; }
					 */
				} else {
					writeSTR = line3;
				}

				if (model != TorahModel.NoTevot) {
					writeSTR = writeSTR.trim().replaceAll(" +", " ");
					fileOUT.write(writeSTR);
					fileOUT.newLine();
				} else {
					fileOUT.write(writeSTR.replaceAll("\\s+", "").replaceAll("\\u05BE", ""));
					// removes all whitespaces and then all makafs (־)
				}
			}
			// System.err.println("Anomolies - " + countAnomolies);
		} catch (Exception e) {
			System.out.println("Error at " + count);
			e.printStackTrace();
		} finally {
			try {
				fileIN.close();
				fileOUT.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (isFine) {
			System.err.println("Function has retrieved all Data with no errors");
		}
	}

	public static ArrayList<ArrayList<Integer>> findVerseSumOfLines(ArrayList<ArrayList<Integer>> list) {
		ArrayList<ArrayList<Integer>> list2 = new ArrayList<ArrayList<Integer>>();
		int lastBook = 0;
		int countBook = -1;
		int countPerek = 0;
		int lastPerek = 0;
		int countVerses = 0;
		for (ArrayList<Integer> line : list) {
			if (line.get(0) != lastBook) {
				// list2 will separate by Books
				// line.get(0) has book info (code)
				lastPerek = 0;
				countPerek = 0;
				lastBook = line.get(0);
				countBook++;
				list2.add(new ArrayList<Integer>());
				if (countBook == 0) {
					list2.get(countBook).add(0);
				} else {
					list2.get(countBook).add(list2.get(countBook - 1).get(list2.get(countBook - 1).size() - 1));
					list2.get(countBook - 1).remove(list2.get(countBook - 1).size() - 1);
				}
			}
			if (line.get(1) != lastPerek) {
				// list2 second ArrayList separates by Perek
				// and lists number of verses in each Perek
				lastPerek = line.get(1);
				countPerek++;
				list2.get(countBook).add(0);
			}
			countVerses++;
			try {
				list2.get(countBook).set(countPerek, countVerses);
			} catch (Exception e) {
				System.err.println("Error populating list2 - perek #" + countPerek + " - book #" + countBook);
			}
		}
		return list2;
	}

	public static ArrayList<ArrayList<Integer>> countLetters(String fileName) {
		ArrayList<ArrayList<Integer>> letterCount = new ArrayList<ArrayList<Integer>>();
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(fileName));
			int c;
			int counter = 0;
			int lineCounter = 0;
			letterCount.add(new ArrayList<Integer>());
			letterCount.get(lineCounter).add(lineCounter + 1);
			letterCount.get(lineCounter).add(counter);
			while ((c = inputStream.read()) != -1) {
				if ((c == 32) || (c == 10) || (c == '\u05BE')) {
					if (c == 10) {
						lineCounter++;
						letterCount.add(new ArrayList<Integer>());
						letterCount.get(lineCounter).add(lineCounter + 1);
						letterCount.get(lineCounter).add(counter);
					}
					continue;
				}
				counter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return letterCount;
	}

	// This function should not be used.
	// It has been replaced with countLetters.
	public static ArrayList<ArrayList<Integer>> getCharacterCountLines(ArrayList<ArrayList<Integer>> list) {
		// uses list from getNumbersFromLineText
		ArrayList<ArrayList<Integer>> characterList = new ArrayList<ArrayList<Integer>>();
		int count = 0;
		characterList.add(new ArrayList<Integer>());
		characterList.get(0).add(1);
		characterList.get(0).add(0);
		int lastSum = 0;
		// Boolean notTested=true;
		// System.err.println(lastSum);
		for (ArrayList<Integer> subList : list) {
			count++;
			characterList.add(new ArrayList<Integer>());
			lastSum += subList.get(3);
			characterList.get(count).add(count + 1);
			characterList.get(count).add(lastSum);
			/*
			 * if ((lastSum>4600) &&
			 * 
			 * (lastSum<4800)) { notTested=false; System.err.println(lastSum); }
			 */
		}
		return characterList;
	}

	public static ArrayList<ArrayList<Integer>> findParashot(String fileName) {
		String[][] tableParashot = ExcelFunctions.readBookTableXLS(
				new String[] { torahApp.TorahApp.TorahTables, PropStore.map.get(PropStore.subTorahTablesFile) }, 1, 0,
				1, 2, 56);
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		fileMode fMode = Frame.getComboBox_DifferentSearch(ManageIO.fileMode.Line);
		BufferedReader bReader = ManageIO.getBufferedReader(fMode, true);
		BufferedReader fileToCheck=null;
		int parashaIndex=0;
		String line;
		ArrayList<String> parashaLines= new ArrayList<String>();
		int count = 0;
		try {
			while ((line = bReader.readLine()) != null) {
				if (count==Integer.valueOf(tableParashot[1][parashaIndex])) {
					parashaLines.add(line);
					parashaIndex++;
					if (parashaIndex>=tableParashot[1].length) {
						break;
					}
				}
				count++;
			}
			//Regex removes the Makaf (־), the letter י, and ו, and spaces.
			//It does not remove vowels, vowels are assumed to be removed.
			//Check method adaptLineTextToTorahModel for instructions
			//on how to remove vowels, if needed.
			String regex = "[\\u05BEיו\\s]";
			count=0;
			parashaIndex=0;
			fileToCheck = new BufferedReader(new FileReader(fileName));
			while ((line = fileToCheck.readLine()) != null) {
				if (line.replaceAll(regex, "")
						.equals(parashaLines.get(parashaIndex)
								.replaceAll(regex, ""))) {
					list.add(new ArrayList<Integer>());
					list.get(parashaIndex).add(count);
					parashaIndex++;
					if (parashaIndex>=parashaLines.size()) {
						break;
					}
				}
				count++;
			}
			if (parashaIndex<parashaLines.size()) {
				System.err.print("Could not find Parasha of: ");
				System.err.println(tableParashot[0][parashaIndex]);
				System.err.println(parashaLines.get(parashaIndex)
				.replaceAll(regex, ""));
			} else {
				System.err.println("Found all Parashot");
				ExtraFunctions.printMultiDimensionalArrayList(list);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		// String fileNameIN =
		// "/home/shementov777/Downloads/Tanach_Consonants_Vowels.txt";
		// String fileNameOUT_Cons_Written =
		// "/home/shementov777/Downloads/Tanach_Cons_Written.txt";
		// String fileNameOUT_Cons_Oral =
		// "/home/shementov777/Downloads/Tanach_Cons_Oral.txt";
		// String fileNameOUT_Cons_Both =
		// "/home/shementov777/Downloads/Tanach_Cons_Both.txt";
		// String fileNameOUT_WithVowels_Written =
		// "/home/shementov777/Downloads/Tanach_WithVowels_Written.txt";
		// String fileNameOUT_WithVowels_Oral =
		// "/home/shementov777/Downloads/Tanach_WithVowels_Oral.txt";
    	// String fileNameOUT_WithVowels_Both =
		// "/home/shementov777/Downloads/Tanach_WithVowels_Both.txt";
		// String fileNameOUT_NoTevot =
		// "/home/shementov777/Downloads/Tanach_NoTevot.txt";
		// String fileNameOUT_temp = "/home/shementov777/Downloads/Tanach_temp.txt";

		// These files were created:
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_WithVowels_Both, true,
		// TorahModel.BOTH);
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_WithVowels_Oral, true,
		// TorahModel.ORAL);
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_WithVowels_Written, true,
		// TorahModel.WRITTEN);
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_Cons_Both, false,
		// TorahModel.BOTH);
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_Cons_Oral, false,
		// TorahModel.ORAL);
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_Cons_Written, false,
		// TorahModel.WRITTEN);
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_NoTevot, false,
		// TorahModel.NoTevot);
		// adaptLineTextToTorahModel(fileNameIN, fileNameOUT_temp, false,
		// TorahModel.WRITTEN);

		// ArrayList<ArrayList<Integer>> list =
		// getNumbersFromLineText(fileNameOUT_Cons_Written);

		// Tanach_Consonants.txt is the Westminster Leningrad Codex
		// it differs from the Masoratic Text.
		// ArrayList<ArrayList<Integer>> bookPerekNumberOfVerses =
		// findVerseSumOfLines(list);
		// printMultiDimensionalArrayList(bookPerekNumberOfVerses);
		// CountTorahXLS.writeXLS("TableBooks"
		// ,ExtraFunctions.transpose(bookPerekNumberOfVerses), -1);

		// CountTorahXLS.writeXLS("TableCountLetter",
		// countLetters(fileNameOUT_Cons_Written), null);
		// CountTorahXLS.writeXLS("ParashaCount",findParashot(fileNameOUT_Cons_Written),null);
	}
}
