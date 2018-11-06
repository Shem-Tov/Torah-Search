package extras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import ioManagement.Output;
import torahApp.ToraApp;

public class FileManipulation {
	static final Integer ONE = 1;
	static Hashtable<Integer, Integer> charCodeMap = new Hashtable<Integer, Integer>();

	public static void printCharCodes() throws IOException {
		Enumeration<Integer> e = charCodeMap.keys();
		while (e.hasMoreElements()) {
			Integer key = (Integer) e.nextElement();
			Output.printText(key + " : " + charCodeMap.get(key));
		}
	}

	public static void collectCharCode(Integer charCode) {
		Object obj = charCodeMap.get(charCode);
		if (obj == null) {
			charCodeMap.put(charCode, ONE);
		} else {
			int i = ((Integer) obj).intValue() + 1;
			charCodeMap.put(charCode, i);
		}
	}

	static char[] letters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע', 'פ', 'צ',
			'ק', 'ר', 'ש', 'ת', 'ך', 'ם', 'ן', 'ף', 'ץ' };
	static char[] endLetters = {};

	public static String readTextFile(String fileName) {
		String returnValue = "";
		FileReader file;
		try {
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			try {
				int linecount = 1;
				int counter = 0;
				int c;
				boolean foundWhiteSpace = false;
				while ((c = reader.read()) != -1) {
					counter++;
					if (c == 10) {
						linecount++;
					}
					if (new String(letters).indexOf((char) c) == -1) {
						if (foundWhiteSpace) {
							System.out.println("Found double whitespace at: " + linecount);
						}
						foundWhiteSpace = true;
						collectCharCode(c);
						// do something
					} else {
						foundWhiteSpace = false;
					}
				}
				System.out.println("Number of Letters: " + counter);
				printCharCodes();
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occured");
		}
		return returnValue;

	}

	public static void compareTextFileLines(String fileName, String fileName2, int diff, int numErrors) {
		// numErrors will show up to that amount, 0 is all
		BufferedReader br = null;
		BufferedReader br2 = null;
		int lineCount = 0;
		int countErrors=0;
		try {
			br = new BufferedReader(new FileReader(fileName));
			br2 = new BufferedReader(new FileReader(fileName2));
			String line;
			String line2;
			System.out.println("Checking line length differences greater than and equal to "+diff);
			while ((line = br.readLine()) != null) {
				lineCount++;
				line2 = br2.readLine();
				if (Math.abs(line2.length() - line.length()) > diff) {
					countErrors++;
					ToraApp.perekBookInfo pBookInstance;
					try {
						pBookInstance = ToraApp.findPerekBook(lineCount);
						String tempStr1 = pBookInstance.getBookName() + " " + pBookInstance.getPerekLetters() + ":"
								+ pBookInstance.getPasukLetters();
						System.out.println("Line Number: " + lineCount + " - Line Lengths: " + line.length() + " and "
								+ line2.length() + " are different.");
						System.out.println(tempStr1);
						System.out.println();
						if ((numErrors>0) && (countErrors>=numErrors)) {
							return;
						}
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// Thread.sleep(1000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void compareTextFileBytes(String fileName, String fileName2) {
		FileReader file;
		FileReader file2;
		try {
			file = new FileReader(fileName);
			file2 = new FileReader(fileName2);
			BufferedReader reader = new BufferedReader(file);
			BufferedReader reader2 = new BufferedReader(file2);
			try {
				int linecount = 1;
				int counter = 0;
				int countPOS = 0;
				int c;
				int d;
				int countErrors = 0;
				boolean lastError = false;
				while ((c = reader.read()) != -1) {
					if ((c == 10) || (c == 32)) {
						if (c == 10) {
							countPOS = 0;
							linecount++;
						}
						continue;
					}
					countPOS++;
					counter++;
					d = reader2.read();
					if (d == -1) {
						System.out.println("Counted Errors: " + countErrors);
						return;
					}
					if (c != d) {
						if (lastError) {
							System.out.println("Counted Errors: " + countErrors);
							return;
						}
						lastError = true;
						ToraApp.perekBookInfo pBookInstance;
						try {
							countErrors++;
							pBookInstance = ToraApp.findPerekBook(linecount);
							String tempStr1 = pBookInstance.getBookName() + " " + pBookInstance.getPerekLetters() + ":"
									+ pBookInstance.getPasukLetters();
							System.out.println("Letters " + (char) c + " and " + (char) d + " are different. Line is: "
									+ linecount + " and Letter Position is : " + countPOS + ". ");
							System.out.println(tempStr1);
							System.out.println();
							// return;
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						lastError = false;
					}
				}
				System.out.println("Number of Letters: " + counter);
			} finally {
				reader.close();
				reader2.close();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occured");
		}
	}

	public static void writeTextFile(String fileName, String s) {
		FileWriter output;
		try {
			output = new FileWriter(fileName);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// readTextFile("/Lines_3.txt");
		try {
			ToraApp.starter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//compareTextFileLines("/Lines.txt", "/Lines_2.txt",2,10);
		
		compareTextFileBytes("/Lines.txt", "/NoTevot.txt");
	}
}
