package ToraApp;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class Console {
	static Scanner input = new Scanner(System.in);

	public static void printSettings(String... arg) {
		for (String str : arg) {
			switch (str) {
			case PropStore.searchWord:
				System.out.println("	Search Word: " + PropStore.map.get(PropStore.searchWord));
				break;
			case PropStore.searchGmt:
				System.out.println("	Gimatria Sum: " + PropStore.map.get(PropStore.searchGmt));
				break;
			case PropStore.bool_gimatriaSofiot:
				System.out
						.println("	Special Gimatria for Sofiot: " + PropStore.map.get(PropStore.bool_gimatriaSofiot));
				break;
			case PropStore.bool_wholeWord:
				System.out.println("	Search only for whole words: " + PropStore.map.get(PropStore.bool_wholeWord));
				break;
			case PropStore.bool_countPsukim:
				System.out.println("	Return count of matches in distinct Psukim: "
						+ PropStore.map.get(PropStore.bool_countPsukim));
			}
		}
	}

	public static void menuSettings() throws IOException {
		Boolean bool_saved = true;
		char selection = 0;
		/***************************************************/
		while (true) {
			System.out.println("\nChoose from these choices");
			System.out.println("-------------------------");
			System.out.println("(1) - Search Word: " + PropStore.map.get(PropStore.searchWord));
			System.out.println("(2) - Gimatria Sum: " + PropStore.map.get(PropStore.searchGmt));
			System.out.println("(3) - Special Gimatria for Sofiot: " + PropStore.map.get(PropStore.bool_gimatriaSofiot));
			System.out.println("(4) - Search only for whole words: " + PropStore.map.get(PropStore.bool_wholeWord));
			System.out.println("(5) - Return count of matches in distinct Psukim: "
					+ PropStore.map.get(PropStore.bool_countPsukim));
			System.out.println("(s)(ש) - Save");
			System.out.println("(q)(י) - Quit");
			System.out.println("----------------------------");
			try {
				selection = input.next().charAt(0);
				input.nextLine();
				String str;
				char ch = '\u0000';
				switch (selection) {
				case '1':
					System.out.println("\nType a word to search: ");
					str = input.nextLine();
					if (!str.equals(PropStore.map.get(PropStore.searchWord))) {
						PropStore.map.put(PropStore.searchWord, str);
						bool_saved = false;
					}
					break;
				case '2':
					System.out.println("\nType a number to search for: ");
					str = input.nextLine();
					if (StringUtils.isNumeric(str))
						if (!str.equals(PropStore.map.get(PropStore.searchGmt))) {
							PropStore.map.put(PropStore.searchGmt, str);
							bool_saved = false;
						} else {
							System.out.println("Error: Not Numeric");
						}
					break;
				case '3':
					System.out.println("\nSpecial Gimatria for Sofiot? (y)es (n)o כ)ן (ל)א)?");
					ch = input.next().charAt(0);
					input.nextLine();
					switch (ch) {
					case 'כ':
					case 'y':
					case 'Y':
						if (!("true").equals(PropStore.map.get(PropStore.bool_gimatriaSofiot))) {
							PropStore.map.put(PropStore.bool_gimatriaSofiot, "true");
							bool_saved = false;
						}
						break;
					case 'ל':
					case 'n':
					case 'N':
						if (!("false").equals(PropStore.map.get(PropStore.bool_gimatriaSofiot))) {
							PropStore.map.put(PropStore.bool_gimatriaSofiot, "false");
							bool_saved = false;
						}
						break;
					default:
						System.out.println("Error: Not a viable option");
					}
					break;
				case '4':
					System.out.println("\nSearch only for whole words? (y)es (n)o כ)ן (ל)א)?");
					ch = input.next().charAt(0);
					input.nextLine();
					switch (ch) {
					case 'כ':
					case 'y':
					case 'Y':
						if (!("true").equals(PropStore.map.get(PropStore.bool_wholeWord))) {
							PropStore.map.put(PropStore.bool_wholeWord, "true");
							bool_saved = false;
						}
						break;
					case 'ל':
					case 'n':
					case 'N':
						if (!("false").equals(PropStore.map.get(PropStore.bool_wholeWord))) {
							PropStore.map.put(PropStore.bool_wholeWord, "false");
							bool_saved = false;
						}
						break;
					default:
						System.out.println("Error: Not a viable option");
					}
					break;
				case '5':
					System.out.println("\nCount (p)(פ)Psukim or (m)(ה)Matches?");
					ch = input.next().charAt(0);
					input.nextLine();
					switch (ch) {
					case 'פ':
					case 'p':
					case 'P':
						if (!("true").equals(PropStore.map.get(PropStore.bool_countPsukim))) {
							PropStore.map.put(PropStore.bool_countPsukim, "true");
							bool_saved = false;
						}
						break;
					case 'ה':
					case 'm':
					case 'M':
						if (!("false").equals(PropStore.map.get(PropStore.bool_countPsukim))) {
							PropStore.map.put(PropStore.bool_countPsukim, "false");
							bool_saved = false;
						}
						break;
					default:
						System.out.println("Error: Not a viable option");
					}
					break;
				case 's':
				case 'S':
				case 'ש':
					PropStore.store();
					bool_saved = true;
					break;
				case 'q':
				case 'Q':
				case 'י':
					if (!bool_saved) {
						System.out.println("\nSettings have not been saved.");
						System.out.println("(ש)(S)ave and quit");
						System.out.println("(י)(Q)uit without saving");
						System.out.println(" Any other key to stay in settings");
						ch = input.next().charAt(0);
						input.nextLine();
						switch (ch) {
						case 's':
						case 'S':
						case 'ש':
							PropStore.store();
							bool_saved = true;
							// should not be a break here
						case 'q':
						case 'Q':
						case 'י':
							return;

						}
					} else {
						return;
					}
				}
			} catch (Exception e) {
				selection = 0;
				input.nextLine();
			}
		}
	}

	public static void menu() throws IOException {
		int selection = 0;
		Object[] args = { null };
		char ch;
		/***************************************************/
		while (selection != (Methods.arrayMethods.size() + 1)) {
			System.out.println("\nChoose from these choices");
			System.out.println("-------------------------");
			int i = 0;
			for (String str : Methods.arrayMethodTitle) {
				i += 1;
				System.out.println(i + " - " + str);
			}
			System.out.println((Methods.arrayMethods.size() + 1) + " - Quit");
			System.out.println("---------------------------");
			try {
				selection = input.nextInt();
				input.nextLine();
				if ((selection > 0) && (selection <= Methods.arrayMethods.size())) {
					switch (Methods.arrayMethodID.get(selection - 1)) {
					case 1:
						args = Arrays.copyOf(args, 2);
						printSettings(PropStore.searchWord, PropStore.bool_wholeWord);
						System.out.println("(ק)(T)ake info from settings");
						ch = input.next().charAt(0);
						input.nextLine();
						switch (ch) {
						case 't':
						case 'T':
						case 'ק':
							args[0] = PropStore.map.get(PropStore.searchWord);
							args[1] = Boolean.parseBoolean(PropStore.map.get(PropStore.bool_wholeWord));
							break;
						default:
							System.out.println("\nType a word to search:");
							do {
								args[0] = input.nextLine();
							} while (((String) args[0]).length() < 1);
							System.out.println("\nSearch for whole words (n)o (y)es?");
							if ((char) input.next().charAt(0) == 'y') {
								args[1] = true;
							} else {
								args[1] = false;
							}
							;
							input.nextLine();
						}
						break;
					case 2:
						args = Arrays.copyOf(args, 4);
						printSettings(PropStore.searchWord, PropStore.searchGmt, PropStore.bool_wholeWord,
								PropStore.bool_gimatriaSofiot, PropStore.bool_countPsukim);
						System.out.println("(ק)(T)ake info from settings");
						ch = input.next().charAt(0);
						input.nextLine();
						switch (ch) {
						case 't':
						case 'T':
						case 'ק':
							if (PropStore.map.get(PropStore.searchWord) == null) {
								args[0] = PropStore.map.get(PropStore.searchGmt);
							} else if (PropStore.map.get(PropStore.searchGmt) == null) {
								args[0] = PropStore.map.get(PropStore.searchWord);
							} else {
								System.out.println("(1) - " + PropStore.map.get(PropStore.searchWord));
								System.out.println(" 2 - " + PropStore.map.get(PropStore.searchGmt));
								int num = input.nextInt();
								switch (num) {
								case 2:
									args[0] = PropStore.map.get(PropStore.searchGmt);
									break;
								default:
									args[0] = PropStore.map.get(PropStore.searchWord);
								}
							}
							args[1] = Boolean.parseBoolean(PropStore.map.get(PropStore.bool_wholeWord));
							args[2] = Boolean.parseBoolean(PropStore.map.get(PropStore.bool_gimatriaSofiot));
							args[3] = Boolean.parseBoolean(PropStore.map.get(PropStore.bool_countPsukim));
							break;
						default:
							System.out.println("\nType a word or number to search:");
							do {
								args[0] = input.nextLine();
							} while (((String) args[0]).length() < 1);
							System.out.println("\nSearch for whole words? (n)o (y)es");
							if ((char) input.next().charAt(0) == 'y') {
								args[1] = true;
							} else {
								args[1] = false;
							}
							;
							input.nextLine();
							System.out.println("\nSpecial Gimatria for Sofiot? (n)o (y)es?");
							if ((char) input.next().charAt(0) == 'y') {
								args[2] = true;
							} else {
								args[2] = false;
							}
							;
							input.nextLine();
							System.out.println("\nCount (p)Psukim or (m)Matches?");
							if ((char) input.next().charAt(0) == 'm') {
								args[3] = false;
							} else {
								args[3] = true;
							}
							;
							input.nextLine();
						}
						break;
					case 3:
						args = Arrays.copyOf(args, 2);
						System.out.println("\nChoose a word to calculate Gimatria:");
						args = Arrays.copyOf(args, 2);
						do {
							args[0] = input.nextLine();
						} while (((String) args[0]).length() < 1);

					}
					Methods.arrayMethods.get(selection - 1).run(args);
				}
			} catch (Exception e) {
				selection = 0;
				input.nextLine();
			}
		}
		input.close();
	}
}
