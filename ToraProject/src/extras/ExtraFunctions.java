package extras;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

import ioManagement.Output;
import torahApp.TorahApp;

public class ExtraFunctions {
	public static int createRandomNumber(int max) {
		return createRandomNumber(0,max);
	}
	
	public static int createRandomNumber(int min, int max) {
		Random randomNum = new Random();
		return (min+randomNum.nextInt(max+1-min));
	}
	public static boolean logicalXOR(boolean x, boolean y) {
	    return ( ( x || y ) && ! ( x && y ) );
	}
	
	public static void findWords() throws IOException {

		BufferedReader inputStream = null;
		StringWriter outputStream = null;
		FileWriter outputStream2 = null;
		Boolean boolFoundLetter = false;
		int count = 0;
		int lastCount = 0;
		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = new BufferedReader(new FileReader("/EveryWord.txt"));
			outputStream = new StringWriter();
			outputStream2 = new FileWriter("/myText.txt", false);
			inputStream.mark(640000);
			int c;
			char chInt = 0;
			char lastChar = 0;
			for (char let : TorahApp.getHLetters()) {
				count = 0;
				lastCount = 0;
				outputStream.getBuffer().setLength(0);
				boolFoundLetter = false;
				while (((c = inputStream.read()) != -1) && ((lastCount < 1) || (count < 10))) {
					chInt = (char) c;
					switch (chInt) {
					case ' ':
					case '\r':
						continue;
					case 'ך':
						chInt = 'כ';
						break;
					case 'ם':
						chInt = 'מ';
						break;
					case 'ן':
						chInt = 'נ';
						break;
					case 'ף':
						chInt = 'פ';
						break;
					case 'ץ':
						chInt = 'צ';
						break;
					default:
						break;
					}
					outputStream.write(c);
					if (chInt == let) {
						count++;
						boolFoundLetter = true;
					}
					// find char in array
					// if ((IntStream.of(hLetters).anyMatch(x -> x != chInt)) &&
					// (IntStream.of(endLetters).anyMatch(x -> x != chInt)) &&
					// (IntStream.of(oLetters).anyMatch(x -> x != chInt)) && boolFoundLetter)
					if ((!String.valueOf(chInt).matches("."))) {
						if (boolFoundLetter) {
							String tempSTR = "\"" + let + "\" " + outputStream.toString();
							Output.printText(tempSTR);
							outputStream2.write(tempSTR);
							outputStream.getBuffer().setLength(0);
							boolFoundLetter = false;
							if (lastChar == let) {
								lastCount++;
							}
						} else {
							outputStream.getBuffer().setLength(0);
						}
					}
					lastChar = chInt;
				}

				inputStream.reset();
			}

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}
			if (outputStream2 != null) {
				outputStream2.close();
			}
		}
	}

	public static void findFirstLetters() throws IOException {
		BufferedReader inputStream = null;
		StringWriter outputStream = null;
		Boolean boolLetter = false;

		try {
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			inputStream = new BufferedReader(new FileReader("/EveryWord.txt"));
			outputStream = new StringWriter();
			inputStream.mark(640000);
			int c;
			char chInt = 0;
			for (char let : TorahApp.getHLetters()) {
				while ((c = inputStream.read()) != -1) {
					chInt = (char) c;
					outputStream.write(c);
					if (chInt == let) {
						boolLetter = true;
					}
					if (!String.valueOf(chInt).matches(".")) {
						if (boolLetter) {
							Output.printText("\"" + let + "\" " + outputStream.toString());
							boolLetter = false;
							outputStream.getBuffer().setLength(0);
							break;
						}
						outputStream.getBuffer().setLength(0);
					}
				}

				inputStream.reset();
			}

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public static ArrayList<ArrayList<Integer>> transpose(ArrayList<ArrayList<Integer>> matrixIn) {
		ArrayList<ArrayList<Integer>> matrixOut = new ArrayList<>();
		int rowCount = matrixIn.size();
		int colCount = 0;
	
		// find max width
		for (int i = 0; i < rowCount; i++) {
			ArrayList<Integer> row = matrixIn.get(i);
			int rowSize = row.size();
			if (rowSize > colCount) {
				colCount = rowSize;
			}
		}
		// for each row in matrix
		for (int r = 0; r < rowCount; r++) {
			ArrayList<Integer> innerIn = matrixIn.get(r);
	
			// for each item in that row
			for (int c = 0; c < colCount; c++) {
	
				// add it to the outgoing matrix
				// get matrixOut[c], or create it
				ArrayList<Integer> matrixOutRow = new ArrayList<>();
				if (r != 0) {
					try {
						matrixOutRow = matrixOut.get(c);
					} catch (java.lang.IndexOutOfBoundsException e) {
						System.out.println("Transposition error!\n" + "could not get matrixOut at index " + c
								+ " - out of bounds" + e);
						matrixOutRow.add(-1);
					}
				}
				// add innerIn[c]
				try {
					matrixOutRow.add(innerIn.get(c));
				} catch (java.lang.IndexOutOfBoundsException e) {
					matrixOutRow.add(-1);
				}
	
				// reset to matrixOut[c]
				try {
					matrixOut.set(c, matrixOutRow);
				} catch (java.lang.IndexOutOfBoundsException e) {
					matrixOut.add(matrixOutRow);
				}
			}
		}
		return matrixOut;
	}

	public static void printMultiDimensionalArrayList(ArrayList<ArrayList<Integer>> list) {
		for (ArrayList<Integer> subList : list) {
			for (Integer i : subList) {
				System.out.print(String.format("%-4s", i + " "));
			}
			System.out.println();
		}
	}

	public static void printMultiDimensionalStringArray(String[][] strMultiArray) {
		int count = 1;
		for (String[] strArray : strMultiArray) {
			System.out.print(String.format("%-3s", "#" + count) + ":");
			for (String str : strArray) {
				System.out.print(String.format("%-8s", str + " | "));
			}
			System.out.println();
			count++;
		}
	}

}
