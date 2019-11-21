package console;

import java.io.IOException;
import java.util.Arrays;

import hebrewLetters.HebrewLetters;
import stringFormat.CheckStrings;
import torahApp.TorahApp;

public class Console {
	public static String formatLine = "-1";

	public static int getFormatLine() {
		if (CheckStrings.isInteger(formatLine)) {
			return Integer.parseInt(formatLine);
		} else {
			System.out.println("--format \"" + formatLine + "\" needs to be a number.");
			System.exit(0);
			return -1;
		}
	}

	public static Boolean checkCommands(String[] args) {
		Boolean isGui = true;
		Object[] args2 = new Object[0];
		int[] range = new int[] { -1, -1 };
		int[] argsInsances = new int[0];
		// -1 - don't check
		// 0 - boolean
		// 1 - int
		// 2 - string
		int shouldSkip = 0;
		int argsCount = -1;
		int selection = -1;
		try {
			if (args.length > 0) {
				String str = ((String) args[0]).toLowerCase();
				switch (str) {
				case "--text-menu":
				case "-text-menu":
				case "--text":
				case "-text":
				case "-t":
					isGui = false;
					Menu.menu();
					break;
				case "-bnames":
				case "--bnames":
				case "-book-names":
				case "--book-names":
					isGui = false;
					String[] bNames = TorahApp.getBookNames();
					System.out.println();
					System.out.println("The book names");
					System.out.println("--------------");
					for (String n : bNames) {
						System.out.println("\"" + n + "\"");
					}
					System.out.println("--------------");
					break;
				case "-pnames":
				case "--pnames":
				case "-parasha-names":
				case "--parasha-names":
					isGui = false;
					String[] pNames = TorahApp.getParashaNames();
					System.out.println();
					System.out.println("The parasha names");
					System.out.println("-----------------");
					for (String n : pNames) {
						System.out.println("\"" + n + "\"");
					}
					System.out.println("-----------------");
					break;
				case "--help":
				case "-help":
				case "-h":
					isGui = false;
					System.out.println();
					System.out.println("These are the console commands:");
					System.out.println("-------------------------------");
					System.out.println("Words in Braces [] should be replaced with user info.");
					System.out.println("Add quotes to words with spaces for example: \"בראשית ברא\"");
					System.out.println("Braces [] should be omitted.");
					System.out.println("Pipe | gives an option between two commands.");
					System.out.println("All Examples assume filename is TorahApp.jar, but it does not have to be.");
					System.out.println();
					System.out.println("These are common options for most modes:");
					System.out.println(
							"(OPTIONAL) Ranges must be used in pairs, start and end, they do not need to be of the same type.");
					System.out.println(
							"  End ranges are not included in search, search will stop just before end range.");
					System.out.println(
							"-r1-book | --start-range-book [BOOK] [PEREK] [PASUK] : (Optional) Start of range to check, using the book name, perek, and pasuk (Pasuk can be omitted).");
					System.out.println(
							"-r2-book | --end-range-book [BOOK] [PEREK] [PASUK]   : (Optional) End of range to check, using the book name, perek, and pasuk (Pasuk can be omitted).");
					System.out.println(
							"-r1-parasha | --start-range-parasha [PARASHA]        : (Optional) Start of range to check, using the parasha name.");
					System.out.println(
							"-r2-parasha | --end-range-parasha [PARASHA]          : (Optional) End of range to check, using the parasha name.");
					System.out.println(
							"-r-start | --range-start                             : (Optional) Uses start of Torah as start range.");
					System.out.println(
							"-r-end | --range-end                                 : (Optional) Uses end of Torah as end range.");
					System.out.println();
					System.out.println("Search through Torah:");
					System.out.println(" First choose a MODE and use it as first parameter.");
					System.out.println();
					System.out.println(
							" -s | --search                            : (MODE) REGULAR SEARCH mode (MUST BE FIRST PARAMETER).");
					System.out.println("   (EXAMPLE) java -jar TorahApp.jar -search -sw אדם -ff -fw");
					System.out.println(
							"                                          :    Searches words or partial words in the Torah Text.");
					System.out.println(
							" -sw | --search-word [SEARCH WORD]        : The Seach Word to use, should be in hebrew to search through Torah text.");
					System.out.println(" -sw2 | --search-word-second [SEARCH WORD]: (Optional) Second search word.");
					System.out.println(
							" -fw | --flag-whole                       : (Optional) Searches through whole words.");
					System.out.println(
							"                                          :    If not used, search will check partial words as well.");
					System.out.println(
							" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
					System.out.println(
							"                                          :    If not used, no distinction will be made.");
					System.out.println(
							" -ff | --flag-force-multiple              : (Optional)(Default) This parameter is only checked when using a second search word.");
					System.out.println(
							"                                          :    When used, search results are restricted to find both words in same line.");
					System.out.println(
							" -fa | --flag-allow-one                   : (Optional) Search results will be shown for any line with at least one of the words.");
					System.out.println(
							"                                          :    If not used, --flag-force-multiple will be assumed.");
					System.out.println(
							"                                          :    --flag-force-multiple and --flag-allow-one cannot be used together.");
					System.out.println(
							"-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
					System.out.println();
					System.out.println(
							" -gsearch | --gimatria-search             : (MODE) GIMATRIA SEARCH mode (MUST BE FIRST PARAMETER)");
					System.out.println("   (EXAMPLE) java -jar TorahApp.jar -gsearch -sw אדם");
					System.out.println("   (EXAMPLE) java -jar TorahApp.jar -gsearch -sw 45");
					System.out.println(
							"                                          :    Finds numbers represented by letters in the Torah using Gimatria as a number converter.");
					System.out.println(
							" -sw | --search-word [WORD | NUMBER]      : The Seach Word to use, should be in hebrew.");
					System.out.println(
							"                                          :    If a hebrew word is used as the parameter, it will be converted to a number using Gimatria.");
					System.out.println(
							"                                               If a number is used, that number will be searched for through the Torah");
					System.out.println(
							" -fw | --flag-whole                       : (Optional) Searches through whole words, for a single word.");
					System.out.println(
							"                                          :    If not used, search will check partial words as well.");
					System.out.println(
							" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
					System.out.println(
							"                                          :    If not used, no distinction will be made.");
					System.out.println(
							" -fm | --flag-multi-whole                 : (Optional) Searches throughwhole words allowing multiple consecutive whole words.");
					System.out.println(
							"                                          :    This flag automatically sets --flag-whole as well.");
					System.out.println(
							"-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
					System.out.println();
					System.out.println("-gimcalc | --gimatria-calculate           : (MODE) GIMATRIA CALCULATOR.");
					System.out.println("   (EXAMPLE) java -jar TorahApp.jar -gimcalc -sw אדם");
					System.out.println(
							"                                          :    Converts a hebrew word into a number using Gimatria as a conversion method.");
					System.out
							.println(" -sw | --search-word [WORD]               : The Word to use, must be in hebrew.");
					System.out.println(
							" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
					System.out.println(
							"                                          :    If not used, no distinction will be made.");
					System.out.println();
					System.out.println(
							" -dsearch | --dilugim-search              : (MODE) DILUGIM SEARCH mode (MUST BE FIRST PARAMETER).");
					System.out.println(
							"   (EXAMPLE) java -jar TorahApp.jar -dsearch -sw אדם -fs -dmin 2 -dmax 3 -p 20 -dmode tp");
					System.out.println(
							"                                          :    Searches letters in the Torah text by skipping letters or words or lines");
					System.out.println(
							" -sw | --search-word [SEARCH LETTERS]     : The sequence of letters, should be in hebrew when searching through Torah text.");
					System.out.println(
							" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
					System.out.println(
							"                                          :    If not used, no distinction will be made.");
					System.out.println(
							" -dmin | --dilug-minimum [NUMBER]         : A number representing a start of range for letter skipping (minimum value 1).");
					System.out.println(
							" -dmax | --dilug-maximum [NUMBER]         : A number representing a end of range for letter skipping.");
					System.out.println(
							"                                          :   -dmin and -dmax together represent a range.");
					System.out.println(
							" -p | --padding [NUMBER]                  : When a match is found, a sequence of letters of only the non-skipped letters will be shown.");
					System.out.println(
							"                                          :   This number will determine the amount of letters to show before and after the requested letters.");
					System.out.println(
							" -ff | --flag-reverse                     : (Optional) Will also find the sequence of letters in reverse order.");
					System.out.println(
							"                                          :   If not used, only the sequence in the original order will be searched for.");
					System.out.println(
							" -dmode | --dilug-mode [DMODE]            : (Optional) The DMODE is a mode for the Dilugim Search, the parameters will be explained next.");
					System.out.println(
							"                                          :   If not used, default mode is to skip letters");
					System.out.println(
							"     (PARAMETER) [DMODE]= reg | regular   : (default) This mode skips letters, and checks letters not skipped.");
					System.out.println(
							"     (PARAMETER) [DMODE]= hp | headpasuk  : This mode skips lines, and checks only the first letter in the lines not skipped.");
					System.out.println(
							"     (PARAMETER) [DMODE]= tp | tailpasuk  : This mode skips lines, and checks only the last letter in the lines not skipped.");
					System.out.println(
							"     (PARAMETER) [DMODE]= hw | headword   : This mode skips words, and checks only the first letter in the words not skipped.");
					System.out.println(
							"     (PARAMETER) [DMODE]= tw | tailword   : This mode skips words, and checks only the last letter in the words not skipped.");
					System.out.println("     (EXAMPLE) --dilug-mode tailpasuk");
					System.out.println(
							"-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
					System.out.println();
					System.out.println(
							" -lsearch | --letter-search               : (MODE) LETTER SEARCH mode (MUST BE FIRST PARAMETER).");
					System.out.println(
							"   (EXAMPLE) java -jar TorahApp.jar -lsearch -sw1 אדם -fh1 -ft1 -sw2 משה -fh2 -ft2 -lmode worddouble");
					System.out.println(
							"                                          : Letter search is a complex search which searches letters in lines and words.");
					System.out.println(
							"                                          :   The letters can appear in order or any order depending on your option.");
					System.out.println(
							"                                          :   Letters do not need to be consecutive.");
					System.out.println(
							"                                          : The letters can appear in one word or one line, depending on your option.");
					System.out.println(
							" -sw1 | --search-word1 [SEARCH LETTERS]   : The sequence of letters, should be in hebrew when searching through Torah text.");
					System.out.println(
							" -sw2 | --search-word-second [S. LETTERS] : (Optional) (Multi-Search) The sequence of letters, should be in hebrew when searching through Torah text.");
					System.out.println("                                          :   Works only in --mode-word");
					System.out.println("                                          :   Sets --mode-word automatically");
					System.out.println(
							" -fs1 | --flag-sofiot1                    : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
					System.out.println(
							" -fs2 | --flag-sofiot2                    : (Optional) (Multi-Search) Distinguishes between regular hebrew letters and end hebrew letters.");
					System.out.println(
							" -lmode | --letter-mode [LMODE]    		  : (Optional) The LMODE is a mode for the Letter Search, the parameters will be explained next.");
					System.out.println(
							"                                          :   If not used, default mode is to search single word");
					System.out.println(
							"     (PARAMETER) [LMODE]= ws | wordsingle : (default) This mode checks letters in each word of line for a match.");
					System.out.println(
							"     (PARAMETER) [LMODE]= wd | worddouble :  This mode checks letters in each word of line for a match for both letter sequences.");
					System.out.println(
							"     (PARAMETER) [LMODE]= p  | pasuk      :  This mode checks letters of line for a match.");
					System.out.println(
							"     (PARAMETER) [LMODE]= pw | pasukword  :  This mode checks letters of line for to match first letter sequence.");
					System.out.println(
							"                                          :    And checks each word of line for a match with second letter sequence.");
					System.out.println("     (EXAMPLE) --letter-mode pasukword");
					System.out.println(
							" -fo1 | --flag-letter-order1              : (Optional) Forces Letters do be find in the order requested.");
					System.out.println(
							" -fo2 | --flag-letter-order2              : (Optional) (Multi-Search) Forces Letters do be find in the order requested.");
					System.out.println(
							"                                              If not used, the letters will be searched for in every possible combination");
					System.out.println(
							" -fh1 | --flag-head1                      : (Optional) Forces first letter to be the first letter of word [-mw] or line [-mp].");
					System.out.println(
							" -ft1 | --flag-tail1                      : (Optional) Forces last letter to be the last letter of word [-mw] or line [-mp].");
					System.out.println(
							" -fh2 | --flag-head2                      : (Optional) (Multi-Search) Forces first letter to be the first letter of word [-mw] or line [-mp].");
					System.out.println(
							" -ft2 | --flag-tail2                      : (Optional) (Multi-Search) Forces last letter to be the last letter of word [-mw] or line [-mp].");
					System.out.println(
							"                                              If not used, first and last letters can appear in other places.");
					System.out.println(
							" -fes | --flag-exact-spaces               : (Optional) (Pasuk Search) Forces exact number of spaces for Pasuk search.");
					System.out.println(
							"                                              If not used, number of spaces can be higher.");
					System.out.println(
							"-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
					System.out.println();
					System.out.println(
							" -csearch | --count-search                : (MODE) COUNT SEARCH mode (MUST BE FIRST PARAMETER).");
					System.out.println("   (EXAMPLE) java -jar TorahApp.jar -csearch -sw אדם -index 12 -fw");
					System.out.println(
							"                                          :    Finds the nth match of words or partial words in the Torah Text, depending on index.");
					System.out.println(
							" -sw | --search-word [SEARCH WORD]        : The Seach Word to use, should be in hebrew to search through Torah text.");
					System.out.println(
							" -fw | --flag-whole                       : (Optional) Searches through whole words.");
					System.out.println(
							"                                          :    If not used, search will check partial words as well.");
					System.out.println(
							" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
					System.out.println(
							"                                          :    If not used, no distinction will be made.");
					System.out.println(
							" -i | --index  [NUMBER]                   : Used for counting the number of matches.");
					System.out
							.println("                                          :   Search will only show this count.");
					System.out.println(
							"-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
					System.out.println();
					System.out.println("-print | --print-torah         : Prints the Torah to the screen.");
					System.out.println("  (EXAMPLE) java -jar TorahApp.jar --print-torah --range-start --range-end");
					System.out.println("  -fm | --flag-mark            :  (Default) Marks beginning of Books, Pereks, Parashot and Psukim.");
					System.out.println("  -fn | --flag-no-mark         :  Only types the words of the Torah, without any markings.");
					System.out.println("                               :  Can be used with ranges for only a partial print (SEE ABOVE for range commands).");
					System.out.println();
					System.out.println("-report | --range-report            : Creates a report of amount of letters words in the Torah in a partial range.");
					System.out.println("  (EXAMPLE) java -jar TorahApp.jar --range-report --range-start --range-end");
					System.out.println("                               :    Can be used with ranges for only a partial print (SEE ABOVE for range commands).");
					System.out.println();
					System.out.println("-bnames | --book-names         : To see the Book Names to setup ranges.");
					System.out.println();
					System.out.println("-pnames | --parasha-names      : To see the Parasha Names to setup ranges.");
					System.out.println();
					System.out.println("-t | --text | --text-menu      : For TEXT CONSOLE MODE.");
					System.out.println("No Parameters for Graphics Mode");
					System.exit(0);
					break;
				case "--search":
				case "-search":
				case "-s":
					isGui = false;
					selection = Methods.id_searchWords;
					args2 = Arrays.copyOf(args2, 7);
					argsInsances = new int[] { 2, 0, 0, -1, 0, 2, 0 };
					args2[1] = false;
					args2[2] = false;
					args2[4] = false;
					args2[6] = true;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							case "--search-word":
							case "-search-word":
							case "-sw":
								shouldSkip++;
								args2[0] = args[1 + argsCount];
								break;
							case "--flag-whole":
							case "-flag-whole":
							case "-fw":
								args2[1] = true;
								break;
							case "--flag-sofiot":
							case "-flag-sofiot":
							case "-fs":
								args2[2] = true;
								break;
							// args2[3] = Frame.get_searchRange();
							case "--search-word-second":
							case "-search-word-second":
							case "-sw2":
								shouldSkip++;
								args2[4] = true;
								args2[5] = args[1 + argsCount];
								break;
							case "--flag-force-multiple":
							case "-flag-force-multiple":
							case "-ff":
								args2[6] = true;
								break;
							case "--flag-allow-one":
							case "-flag-allow-one":
							case "-fa":
								args2[6] = false;
								break;
							case "-r1-book":
							case "-start-range-book":
							case "--start-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r2-book":
							case "-end-range-book":
							case "--end-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r1-parasha":
							case "-start-range-parasha":
							case "--start-range-parasha":
								shouldSkip += 1;
								range[0] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r2-parasha":
							case "-end-range-parasha":
							case "--end-range-parasha":
								shouldSkip += 1;
								range[1] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r-start":
							case "-range-start":
							case "--range-start":
								range[0] = TorahApp.lookupLineStart();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r-end":
							case "-range-end":
							case "--range-end":
								range[1] = TorahApp.lookupLineEnd();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-f":
							case "--format-text":
								shouldSkip++;
								formatLine = args[1 + argsCount];
								break;
							default:
								System.out.println("Unrecognized command: " + args[argsCount]);
								return false;
							}
						}
					}
					break;
				case "--gimatria-search":
				case "-gimatria-search":
				case "-gsearch":
					isGui = false;
					selection = Methods.id_searchGimatria;
					args2 = Arrays.copyOf(args2, 5);
					argsInsances = new int[] { 2, 0, 0, -1, 0 };
					args2[1] = false;
					args2[2] = false;
					args2[4] = false;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							case "--search-word":
							case "-search-word":
							case "-sw":
								shouldSkip++;
								args2[0] = args[1 + argsCount];
								break;
							case "--flag-whole":
							case "-flag-whole":
							case "-fw":
								args2[1] = true;
								break;
							case "--flag-sofiot":
							case "-flag-sofiot":
							case "-fs":
								args2[2] = true;
								break;
							// args2[3] = Frame.get_searchRange();
							case "--flag-multi-whole":
							case "-flag-multi-whole":
							case "-fm":
								args2[1] = true;
								args2[4] = true;
								break;
							case "-r1-book":
							case "-start-range-book":
							case "--start-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r2-book":
							case "-end-range-book":
							case "--end-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r1-parasha":
							case "-start-range-parasha":
							case "--start-range-parasha":
								shouldSkip += 1;
								range[0] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r2-parasha":
							case "-end-range-parasha":
							case "--end-range-parasha":
								shouldSkip += 1;
								range[1] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r-start":
							case "-range-start":
							case "--range-start":
								range[0] = TorahApp.lookupLineStart();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r-end":
							case "-range-end":
							case "--range-end":
								range[1] = TorahApp.lookupLineEnd();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-f":
							case "--format-text":
								shouldSkip++;
								formatLine = args[1 + argsCount];
								break;
							default:
								System.out.println("Unrecognized command: " + args[argsCount]);
								return false;
							}
						}
					}
					break;
				case "--gimatria-calculate":
				case "-gimatria-calculate":
				case "-gimcalc":
					isGui = false;
					selection = Methods.id_calculateGimatria;
					args2 = Arrays.copyOf(args2, 2);
					argsInsances = new int[] { 2, 0 };
					args2[1] = false;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							case "--search-word":
							case "-search-word":
							case "-sw":
								shouldSkip++;
								args2[0] = args[1 + argsCount];
								break;
							case "--flag-sofiot":
							case "-flag-sofiot":
							case "-fs":
								args2[1] = true;
								break;
							default:
								System.out.println("Unrecognized command: " + args[argsCount]);
								return false;
							}
						}
					}
					break;
				case "--dilugim-search":
				case "-dilug-search":
				case "-dsearch":
					isGui = false;
					args2 = Arrays.copyOf(args2, 8);
					argsInsances = new int[] { 2, 0, 1, 1, 1, -1, 0, -1 };
					args2[1] = false;
					selection = Methods.id_searchDilugim;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							case "--search-word":
							case "-search-word":
							case "-sw":
								shouldSkip++;
								args2[0] = args[1 + argsCount];
								break;
							case "--flag-sofiot":
							case "-flag-sofiot":
							case "-fs":
								args2[1] = true;
								break;
							case "--dilug-minimum":
							case "-dilug-minimum":
							case "-dmin":
								shouldSkip++;
								args2[2] = args[1 + argsCount];
								break;
							case "--dilug-maximum":
							case "-dilug-maximum":
							case "-dmax":
								shouldSkip++;
								args2[3] = args[1 + argsCount];
								break;
							case "--padding":
							case "-padding":
							case "-p":
								shouldSkip++;
								args2[4] = args[1 + argsCount];
								break;
							// args2[5] = Frame.get_searchRange();
							case "--flag-reverse":
							case "-flag-reverse":
							case "-fr":
								// reverse dilug
								args2[6] = true;
								break;
							case "--dilug-mode":
							case "-dilug-mode":
							case "-dmode":
								shouldSkip++;
								switch (args[1 + argsCount].toLowerCase()) {
								case "regular":
								case "reg":
									selection = Methods.id_searchDilugim;
									break;
								case "headpasuk":
								case "hp":
									selection = Methods.id_searchDilugWordPasuk;
									args2[7] = 1;
									break;
								case "tailpasuk":
								case "tp":
									selection = Methods.id_searchDilugWordPasuk;
									args2[7] = 2;
									break;
								case "headword":
								case "hw":
									selection = Methods.id_searchDilugWordPasuk;
									args2[7] = 3;
									break;
								case "tailword":
								case "tw":
									selection = Methods.id_searchDilugWordPasuk;
									args2[7] = 4;
									break;
								default:
									System.out.println("Unrecognized -dmode: " + args[1 + argsCount]);
									return false;
								}
								break;
							case "-r1-book":
							case "-start-range-book":
							case "--start-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[5] = range;
								}
								break;
							case "-r2-book":
							case "-end-range-book":
							case "--end-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[5] = range;
								}
								break;
							case "-r1-parasha":
							case "-start-range-parasha":
							case "--start-range-parasha":
								shouldSkip += 1;
								range[0] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[5] = range;
								}
								break;
							case "-r2-parasha":
							case "-end-range-parasha":
							case "--end-range-parasha":
								shouldSkip += 1;
								range[1] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[5] = range;
								}
								break;
							case "-r-start":
							case "-range-start":
							case "--range-start":
								range[0] = TorahApp.lookupLineStart();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[5] = range;
								}
								break;
							case "-r-end":
							case "-range-end":
							case "--range-end":
								range[1] = TorahApp.lookupLineEnd();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[5] = range;
								}
								break;
							case "-f":
							case "--format-text":
								shouldSkip++;
								formatLine = args[1 + argsCount];
								break;
							default:
								System.out.println("Unrecognized command: " + args[argsCount]);
								return false;
							}
						}
					}
					break;
				case "--letter-search":
				case "-letter-search":
				case "-lsearch":
					isGui = false;
					selection = Methods.id_searchLetters;
					args2 = Arrays.copyOf(args2, 13);
					argsInsances = new int[] { 2, 0, -1, -1, 0, 0, 0, 0, 2, 0, 0, 0, 0 };
					args2[1] = false;
					args2[12] = false;
					args2[3] = 0;
					args2[4] = false;
					args2[5] = false;
					args2[6] = false;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							case "--search-word1":
							case "-search-word1":
							case "-sw1":
								shouldSkip++;
								args2[0] = args[1 + argsCount];
								break;
							case "--search-word-second":
							case "-search-word-second":
							case "-sw2":
								shouldSkip++;
								args2[8] = args[1 + argsCount];
								break;
							case "--flag-sofiot1":
							case "-flag-sofiot1":
							case "-fs1":
								args2[1] = true;
								break;
							case "--flag-sofiot2":
							case "-flag-sofiot2":
							case "-fs2":
								args2[12] = true;
								break;
							// args2[2] = Frame.get_searchRange();
							case "--letter-mode":
							case "-letter-mode":
							case "-lmode":
								shouldSkip++;
								switch (args[1 + argsCount].toLowerCase()) {
								case "wordsingle":
								case "ws":
									args2[3] = 0;
									break;
								case "worddouble":
								case "wd":
									args2[3] = 1;
									break;
								case "pasuk":
								case "p":
									args2[3] = 2;
									break;
								case "pasukword":
								case "pw":
									args2[3] = 3;
									break;
								default:
									System.out.println("Unrecognized -lmode: " + args[1 + argsCount]);
									return false;
								}
								break;
							case "--flag-letter-order1":
							case "-flag-letter-order1":
							case "-fo1":
								args2[4] = true;
								break;
							case "--flag-letter-order2":
							case "-flag-letter-order2":
							case "-fo2":
								args2[9] = true;
								break;
							case "--flag-head1":
							case "-flag-head1":
							case "-fh1":
								args2[5] = true;
								break;
							case "--flag-tail1":
							case "-flag-tail1":
							case "-ft1":
								args2[6] = true;
								break;
							case "--flag-head2":
							case "-flag-head2":
							case "-fh2":
								args2[10] = true;
								break;
							case "--flag-tail2":
							case "-flag-tail2":
							case "-ft2":
								args2[11] = true;
								break;
							case "--flag-exact-spaces":
							case "-flag-exact_spaces":
							case "-fes":
								args2[7] = true;
								break;
							case "-r1-book":
							case "-start-range-book":
							case "--start-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[2] = range;
								}
								break;
							case "-r2-book":
							case "-end-range-book":
							case "--end-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[2] = range;
								}
								break;
							case "-r1-parasha":
							case "-start-range-parasha":
							case "--start-range-parasha":
								shouldSkip += 1;
								range[0] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[2] = range;
								}
								break;
							case "-r2-parasha":
							case "-end-range-parasha":
							case "--end-range-parasha":
								shouldSkip += 1;
								range[1] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[2] = range;
								}
								break;
							case "-r-start":
							case "-range-start":
							case "--range-start":
								range[0] = TorahApp.lookupLineStart();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[2] = range;
								}
								break;
							case "-r-end":
							case "-range-end":
							case "--range-end":
								range[1] = TorahApp.lookupLineEnd();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[2] = range;
								}
								break;
							case "-f":
							case "--format-text":
								shouldSkip++;
								formatLine = args[1 + argsCount];
								break;
							default:
								System.out.println("Unrecognized command: " + args[argsCount]);
								return false;
							}
						}
					}
					break;
				case "--count-search":
				case "-count-search":
				case "-csearch":
					isGui = false;
					args2 = Arrays.copyOf(args2, 5);
					argsInsances = new int[] { 2, 0, 0, -1, 1 };
					args2[1] = false;
					args2[2] = false;
					selection = Methods.id_searchCount;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							case "--search-word":
							case "-search-word":
							case "-sw":
								shouldSkip++;
								args2[0] = args[1 + argsCount];
								break;
							case "--flag-whole":
							case "-flag-whole":
							case "-fw":
								args2[1] = true;
								break;
							case "--flag-sofiot":
							case "-flag-sofiot":
							case "-fs":
								args2[2] = true;
								break;
							// args2[3] = Frame.get_searchRange();
							case "--index":
							case "-index":
							case "-i":
								shouldSkip++;
								args2[4] = args[1 + argsCount];
								break;
							case "-r1-book":
							case "-start-range-book":
							case "--start-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r2-book":
							case "-end-range-book":
							case "--end-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r1-parasha":
							case "-start-range-parasha":
							case "--start-range-parasha":
								shouldSkip += 1;
								range[0] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r2-parasha":
							case "-end-range-parasha":
							case "--end-range-parasha":
								shouldSkip += 1;
								range[1] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r-start":
							case "-range-start":
							case "--range-start":
								range[0] = TorahApp.lookupLineStart();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-r-end":
							case "-range-end":
							case "--range-end":
								range[1] = TorahApp.lookupLineEnd();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[3] = range;
								}
								break;
							case "-f":
							case "--format-text":
								shouldSkip++;
								formatLine = args[1 + argsCount];
								break;
							default:
								System.out.println("Unrecognized command: " + args[argsCount]);
								return false;
							}
						}
					}
					break;
				case "--print-torah":
				case "-print-torah":
				case "--print":
				case "-print":
				case "-p":
					isGui = false;
					selection = Methods.id_printTorah;
					args2 = Arrays.copyOf(args2, 2);
					argsInsances = new int[] { -1, 0 };
					args2[1] = true;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							// args2[0] = Frame.get_searchRange();
							case "--flag-mark":
							case "-flag-mark":
							case "-fm":
								args2[1] = true;
							case "--flag-no-mark":
							case "-flag-no-mark":
							case "-fn":
								args2[1] = false;
							case "-r1-book":
							case "-start-range-book":
							case "--start-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
								}
								break;
							case "-r2-book":
							case "-end-range-book":
							case "--end-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									shouldSkip += 3;
								} else {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
								}
								break;
							case "-r1-parasha":
							case "-start-range-parasha":
							case "--start-range-parasha":
								shouldSkip += 1;
								range[0] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
								}
								break;
							case "-r2-parasha":
							case "-end-range-parasha":
							case "--end-range-parasha":
								shouldSkip += 1;
								range[1] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
								}
								break;
							case "-r-start":
							case "-range-start":
							case "--range-start":
								range[0] = TorahApp.lookupLineStart();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
								}
								break;
							case "-r-end":
							case "-range-end":
							case "--range-end":
								range[1] = TorahApp.lookupLineEnd();
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
								}
								break;
							default:
								System.out.println("Unrecognized command: " + args[argsCount]);
								return false;
							}
						}
					}
					break;
				case "--range-report":
				case "-range-report":
				case "--report":
				case "-report":
				case "-r":
					// need to find a way to create a report without dialog
					// args[0] = new int[] {start,end};
					// args[1] = new String[] {startStr,endStr,end2Str};

					// DialogSearchRangeFrame dFrame = DialogSearchRangeFrame.getInstance(false);
					// dFrame.setVisible(true);
					String[] rangeStr = new String[3];  //{"הכול","",""};
					isGui = false;
					argsInsances = new int[] { -1, 0 };
					args2 = Arrays.copyOf(args2, 2);
					selection = Methods.id_ReportTorahCount;
					if (args.length > 1) {
						for (String a : args) {
							argsCount++;
							// skip first parameter
							if (argsCount == 0) {
								continue;
							}
							if (shouldSkip > 0) {
								shouldSkip--;
								continue;
							}
							String str2 = a.toLowerCase();
							switch (str2) {
							case "-r1-book":
							case "-start-range-book":
							case "--start-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									rangeStr[0] = args[1 + argsCount] + " " + args[2 + argsCount] + ":" + args[3 + argsCount];
									shouldSkip += 3;
								} else {
									range[0] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									rangeStr[0] = args[1 + argsCount] + " " + args[2 + argsCount] + ":" + "א";
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
									args2[1] = rangeStr;
								}
								break;
							case "-r2-book":
							case "-end-range-book":
							case "--end-range-book":
								if (HebrewLetters.checkHebrew(args[3 + argsCount].charAt(0) + "")) {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], args[3 + argsCount]);
									rangeStr[1] = args[1 + argsCount] + " " + args[2 + argsCount] + ":" + args[3 + argsCount];
									rangeStr[2] = "(לא כולל)";
									shouldSkip += 3;
								} else {
									range[1] = TorahApp.getLineNumberFromBookStrings(args[1 + argsCount],
											args[2 + argsCount], null);
									rangeStr[1] = args[1 + argsCount] + " " + args[2 + argsCount] + ":" + "א";
									rangeStr[2] = "(לא כולל)";
									shouldSkip += 2;
								}
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
									args2[1] = rangeStr;
								}
								break;
							case "-r1-parasha":
							case "-start-range-parasha":
							case "--start-range-parasha":
								shouldSkip += 1;
								range[0] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								rangeStr[0] = "פרשת " + args[1 + argsCount];
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
									args2[1] = rangeStr;
								}
								break;
							case "-r2-parasha":
							case "-end-range-parasha":
							case "--end-range-parasha":
								shouldSkip += 1;
								range[1] = TorahApp.getLineNumberFromParashaStrings(args[1 + argsCount]);
								rangeStr[1] = "פרשת " + args[1 + argsCount];
								rangeStr[2] = "(לא כולל)";
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
									args2[1] = rangeStr;
								}
								break;
							case "-r-start":
							case "-range-start":
							case "--range-start":
								range[0] = TorahApp.lookupLineStart();
								rangeStr[0] = "בראשית א:א";											
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
									args2[1] = rangeStr;
								}
								break;
							case "-r-end":
							case "-range-end":
							case "--range-end":
								range[1] = TorahApp.lookupLineEnd();
								rangeStr[1] = "הסוף";
								rangeStr[2] = "";
								if ((range[0] != -1) && (range[1] != -1)) {
									args2[0] = range;
									args2[1] = rangeStr;
								}
								break;
							}
						}
					}
					break;
				default:
					System.out.println("Unrecognized command: " + args[0]);
					return false;
				}
			}
			if (selection >= 0) {
				try {
					int count = -1;
					for (Object a : args2) {
						count++;
						switch (argsInsances[count]) {
						// -1 do not check
						case 0: // boolean
							break;
						case 1: // int
							if (!CheckStrings.isInteger((String) a)) {
								System.out.println("Parameter \"" + (String) a + "\" needs to be a number.");
								return false;
							}
							break;
						case 2: // string
							if (!HebrewLetters.checkHebrew((String) a)) {
								System.out.println("Parameter \"" + (String) a + "\" needs to be in hebrew.");
								return false;
							}
							break;
						}
					}
					if ((selection == Methods.id_searchDilugim) || (selection == Methods.id_searchDilugWordPasuk)) {
						if (Integer.parseInt((String) args2[2]) > Integer.parseInt((String) args2[3])) {
							System.out.println("dilug-min \"" + args2[2] + "\" cannot be larger than dilug-max \""
									+ args2[3] + "\".");
							return false;
						}
					}
					if ((range[0] != -1) && (range[1] != -1)) {
						if (range[0] > range[1]) {
							System.out.println("Range Error: Start range is higher than end range.");
							return false;
						}
					} else if (range[0] != -1) {
						System.out.println("Range Error: No end range.");
						return false;
					} else if (range[1] != -1) {
						System.out.println("Range Error: No start range.");
						return false;
					}
					Methods.arrayMethods.get(selection).run(args2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NullPointerException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return isGui;
	}
}
