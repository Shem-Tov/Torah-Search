package console;

import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import hebrewLetters.HebrewLetters;
import ioManagement.Output;
import ioManagement.PropStore;
import stringFormat.CheckStrings;
import stringFormat.StringAlignUtils;
import torahApp.Gimatria;

public class Menu {
	static Scanner input = new Scanner(System.in);

	public static void menu() {
		int selection = -1;
		Object[] args = null;
		while (selection != 0) {
			System.out.println("\nתפריט אפשרויות");
			System.out.println("-------------------------");
			for (Integer key : Methods.getArrayMethodTitles().keySet()) {
				System.out.println(StringAlignUtils.padLeft(String.valueOf(key), 2) + " - " + Methods.getArrayMethodTitles().get(key));
			}
			System.out.println(StringAlignUtils.padLeft("0", 2) + " - יציאה");
			System.out.println("---------------------------");
			try {
				selection = input.nextInt();
				input.nextLine();
				if (selection > 0) {
					System.out.println(Methods.getArrayMethodTitleString(selection));
					switch (selection) {
					case Methods.id_searchWords:
						args = menu_regular_search();
						break;
					case Methods.id_searchGimatria:
						args = menu_gimatria_search();
						break;
					case Methods.id_calculateGimatria:
						args = menu_gimatria_calculator();
						break;
					case Methods.id_searchDilugim:
						args = menu_dilugim_search();
						if ((int)args[7]!=0) {
							selection = Methods.id_searchDilugWordPasuk;
						}
						break;
					case Methods.id_searchLetters:
						args = menu_letter_search();
						break;
					case Methods.id_searchCount:
						args = menu_count_search();
						break;
					case Methods.id_printTorah:
						break;
					case Methods.id_ReportTorahCount:
						break;
					default:
						selection = -1;
						break;
					}
					try {
						Methods.arrayMethods.get(selection).run(args);
						System.out.println(Output.r2l()+"לחץ" +" ENTER "+"כדי להמשיך");
						input.nextLine();
						input.nextLine();
					} catch (NullPointerException e) {
						System.out.println("אין בחירה כזאת");
					}
					selection =-1;
				}
			} catch (IOException e) {
				selection = -1;
				input.nextLine();
			}
		}
		input.close();
		System.out.println("סיים");
	}

	public static Object[] menu_regular_search() {
		Object[] args = new Object[7];
		// printSettings(PropStore.searchWord, PropStore.bool_wholeWord);
		args[0] = menu_getText("הקלד מילה בעברית לחיפוש", true);
		args[1] = menu_getBoolean("חיפוש מילים שלמות?");
		args[2] = menu_getBoolean("חישוב מיוחד לסופיות?");
		if (menu_getBoolean("מעונין לעשות חיפוש מרובה מילים?")) {
			args[4] = true;
			args[5] = menu_getText("הקלד מילה שניה בעברית לחיפוש", true);
			args[6] = menu_getBoolean("האם לחייב את שני המילים להופיע באותו פסוק?");
		} else {
			args[4] = false;
		}
		return args;
	}

	public static Object[] menu_gimatria_search() {
		Object[] args = new Object[5];
		String str;
		do {
			str = menu_getText("הקלד מילה בעברית או מספר לחיפוש", false);
		} while (!HebrewLetters.checkHebrew(str) && !CheckStrings.isInteger(str));
		args[0] = str;
		args[2] = menu_getBoolean("חישוב מיוחד לסופיות?");
		System.out.println("המספר לחיפוש הוא:" + Gimatria.calculateGimatria(str, (boolean)args[2]));
		args[1] = menu_getBoolean("חיפוש מילים שלמות?");
		if ((Boolean) args[1]) {
			args[4] = menu_getBoolean("לאפשר חיפוש עבור סכום כולל במספר מילים?");
		}
		return args;
	}

	public static Object[] menu_gimatria_calculator() {
		Object[] args = new Object[2];
		args[0] = menu_getText("הקלד מילה בעברית", true);
		args[1] = menu_getBoolean("חישוב מיוחד לסופיות");
		return args;
	}

	public static Object[] menu_dilugim_search() {
		Object[] args = new Object[8];
		args[0] = menu_getText("הקלד מילה בעברית לחיפוש", true);
		args[1] = menu_getBoolean("חישוב מיוחד לסופיות?");
		args[2] = String.valueOf(menu_getInteger("טווח דילוג - הכנס טווח תחתון"));
		args[3] = String.valueOf(menu_getInteger("טווח דילוג - הכנס טווח עליון", Integer.parseInt((String)args[2])));
		args[4] = String.valueOf(menu_getInteger("מספר אותיות לפני ואחרי"));
		args[6] = menu_getBoolean("האם לעשות בנוסף דילוג למילה בסדר הפוך?");
		MenuOptionClass mOptions = new MenuOptionClass(
				"דילוג רגיל","דילוג ראש פסוק","דילוג סוף פסוק",
				"דילוג ראש מילה","דילוג סוף מילה");
		args[7] = menu_getOption("בחירת סוג דילוג", mOptions);
		return args;
	}

	public static Object[] menu_letter_search() {
		Object[] args = new Object[13];
		MenuOptionClass mOptions = new MenuOptionClass(
				"חיפוש מילה","חיפוש שני מילים","חיפוש פסוק",
				"חיפוש פסוק ומילה");
		args[3] = menu_getOption("בחירת סוג חיפוש אותיות", mOptions);
		switch ((int)args[3]) {
		case 0:
		case 1:
			args[0] = menu_getText("הקלד אותיות בעברית לחיפוש מילה", true);
			args[1] = menu_getBoolean("חישוב מיוחד לסופיות למילה");
			args[5] = menu_getBoolean("האם לחייב את האות הראשונה להתחיל את המילה?");
			args[6] = menu_getBoolean("האם לחייב את האות האחרונה לסיים את המילה?");
			break;
		case 2:
		case 3:
			args[0] = menu_getText("הקלד אותיות בעברית לחיפוש בפסוק", true);
			args[1] = menu_getBoolean("חישוב מיוחד לסופיות לפסוק");
			args[5] = menu_getBoolean("האם לחייב את האות הראשונה להתחיל את הפסוק?");
			args[6] = menu_getBoolean("האם לחייב את האות האחרונה לסיים את הפסוק?");
			args[7] = menu_getBoolean("האם לחייב מספר רווחים מדויק בחיפוש בפסוק?");
			break;
		}
		switch ((int)args[3]) {
		case 0:
		case 2:
			args[4] = menu_getBoolean("האם סדר האותיות חשוב?");
			break;
		case 1:
		case 3:
			args[4] = menu_getBoolean("האם סדר האותיות חשוב?");
			args[8] = menu_getText("הקלד אותיות בעברית לחיפוש מילה שניה", true);
			args[12] = menu_getBoolean("חישוב מיוחד לסופיות למילה");
			args[9] = menu_getBoolean("האם סדר האותיות חשוב?");
			args[10] = menu_getBoolean("האם לחייב את האות הראשונה להתחיל את המילה?");
			args[11] = menu_getBoolean("האם לחייב את האות האחרונה לסיים את המילה?");
			break;
		}
		return args;
	}

	public static Object[] menu_count_search() {
		Object[] args = new Object[5];
		args[0] = menu_getText("הקלד אותיות בעברית לחיפוש", true);
		args[1] = menu_getBoolean("חיפוש מילים שלמות?");
		args[2] = menu_getBoolean("חישוב מיוחד לסופיות?");
		args[4] = String.valueOf(menu_getInteger("מספר ההופעה של אותו רצף אותיות"));
		return args;
	}

	public static Object[] menu_print_Torah() {
		Object[] args = new Object[1];
		return args;
	}

	public static Object[] menu_report_Torah_count() {
		Object[] args = new Object[1];
		return args;
	}

	public static String menu_getText(String message, Boolean forceHebrew) {
		String str;
		do {
			System.out.println("\n"+ Output.r2l() + message + ":");
			str = input.nextLine();
		} while ((str.length() < 1) || ((forceHebrew) && !(HebrewLetters.checkHebrew(str))));
		return str;
	}

	public static int menu_getInteger(String message) {
		return menu_getInteger(message, -1);
	}

	public static int menu_getInteger(String message, int minimum) {
		String str;
		do {
			System.out.println("\n"+Output.r2l() +"(מספר) " + message + ":");
			str = input.nextLine();
		} while ((str.length() < 1) || !CheckStrings.isInteger(str)
				|| ((minimum != -1) && ((Integer.parseInt(str)) < minimum)));
		return Integer.parseInt(str);
	}

	public static Boolean menu_getBoolean(String message) {
		Boolean bool = null;
		char ch;
		do {
			System.out.println("\n"+Output.r2l() + message + " (כ)ן (ל)א:");
			ch = input.next().charAt(0);
			switch (ch) {
			case 'כ':
			case 'f':
			case 'F':
			case 'Y':
			case 'y':
				bool = true;
				break;
			case 'ל':
			case 'k':
			case 'K':
			case 'N':
			case 'n':
				bool = false;
				break;
			}
		} while (bool == null);
		return bool;
	}

	public static int menu_getOption(String message,MenuOptionClass mOptions) {
		int selection=0;
		do {
			System.out.println("\n"+Output.r2l()+message);
			System.out.println(Output.r2l()+"-------------------------");
			int i = 0;
			for (String str : mOptions.getMessages()) {
				i += 1;
				System.out.println(StringAlignUtils.padLeft(String.valueOf(i), 2) + " - " + str);
			}
			System.out.println(Output.r2l()+"---------------------------");
			try {
				selection = input.nextInt();
				input.nextLine();
			}
			catch (Exception e) {
				selection = 0;
				input.nextLine();
			}
		} while ((selection<1) || (selection>mOptions.size()));
		return (selection-1);
	}

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
			System.out
					.println("(3) - Special Gimatria for Sofiot: " + PropStore.map.get(PropStore.bool_gimatriaSofiot));
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

}
