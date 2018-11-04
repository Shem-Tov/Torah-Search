package ToraApp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class extraFunctions {
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
			inputStream = new BufferedReader(new FileReader("./dbFiles/EveryWord.txt"));
			outputStream = new StringWriter();
			outputStream2 = new FileWriter("./dbFiles/myText.txt", false);
			inputStream.mark(640000);
			int c;
			char chInt = 0;
			char lastChar = 0;
			for (char let : ToraApp.hLetters) {
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
			inputStream = new BufferedReader(new FileReader("./dbFiles/EveryWord.txt"));
			outputStream = new StringWriter();
			inputStream.mark(640000);
			int c;
			char chInt = 0;
			for (char let : ToraApp.hLetters) {
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

}
