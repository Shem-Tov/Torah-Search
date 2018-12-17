package console;

import java.io.IOException;
import java.util.Arrays;

public class Console {
	public static int formatLine=-1;
	
	public static int getFormatLine() {
		return formatLine;
	}
	
	public static Boolean checkCommands(String[] args) {
		Boolean isGui = true;
		Object[] args2 = new Object[0];
		int shouldSkip=0;
		int argsCount = -1;
		int selection = -1;
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
			case "--help":
			case "-help":
			case "-h":
				isGui = false;
				System.out.println();
				System.out.println("These are the console commands:");
				System.out.println("-------------------------------");
				System.out.println("Words in Braces [] should be replaced with user info.");
				System.out.println("Braces [] should be omitted.");
				System.out.println("Pipe | gives an option between two commands.");
				System.out.println("All Examples assume filename is TorahApp.jar, but it does not have to be.");
				System.out.println();
				System.out.println("Search through Torah:");
				System.out.println(" First choose a MODE and use it as first parameter.");
				System.out.println();
				System.out.println(" -s | --search                            : (MODE) REGULAR SEARCH mode (MUST BE FIRST PARAMETER).");
				System.out.println("   (EXAMPLE) java -jar TorahApp.jar -search -sw אדם -ff -fw");
				System.out.println("                                          :    Searches words or partial words in the Torah Text.");
				System.out.println(" -sw | --search-word [SEARCH WORD]        : The Seach Word to use, should be in hebrew to search through Torah text.");
				System.out.println(" -sw2 | --search-word-second [SEARCH WORD]: (Optional) Second search word.");
				System.out.println(" -fw | --flag-whole                       : (Optional) Searches through whole words.");
				System.out.println("                                          :    If not used, search will check partial words as well.");
				System.out.println(" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
				System.out.println("                                          :    If not used, no distinction will be made.");
				System.out.println(" -ff | --flag-force-multiple              : (Optional)(Default) This parameter is only checked when using a second search word.");
				System.out.println("                                          :    When used, search results are restricted to find both words in same line.");
				System.out.println(" -fa | --flag-allow-one                   : (Optional) Search results will be shown for any line with at least one of the words.");    
				System.out.println("                                          :    If not used, --flag-force-multiple will be assumed.");
				System.out.println("                                          :    --flag-force-multiple and --flag-allow-one cannot be used together.");
				System.out.println("-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
				System.out.println();
				System.out.println(" -gsearch | --gimatria-search             : (MODE) GIMATRIA SEARCH mode (MUST BE FIRST PARAMETER)");
				System.out.println("   (EXAMPLE) java -jar TorahApp.jar -gsearch -sw אדם");
				System.out.println("   (EXAMPLE) java -jar TorahApp.jar -gsearch -sw 45");
				System.out.println("                                          :    Finds numbers represented by letters in the Torah using Gimatria as a number converter.");
				System.out.println(" -sw | --search-word [WORD | NUMBER]      : The Seach Word to use, should be in hebrew.");
				System.out.println("                                          :    If a hebrew word is used as the parameter, it will be converted to a number using Gimatria.");
				System.out.println("                                               If a number is used, that number will be searched for through the Torah");
				System.out.println(" -fw | --flag-whole                       : (Optional) Searches through whole words.");
				System.out.println("                                          :    If not used, search will check partial words as well.");
				System.out.println(" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
				System.out.println("                                          :    If not used, no distinction will be made.");
				System.out.println("-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
				System.out.println();
				System.out.println("-gimcalc | --gimatria-calculate           : (MODE) GIMATRIA CALCULATOR.");
				System.out.println("   (EXAMPLE) java -jar TorahApp.jar -gimcalc -sw אדם");
				System.out.println("                                          :    Converts a hebrew word into a number using Gimatria as a conversion method.");
				System.out.println(" -sw | --search-word [WORD]               : The Word to use, must be in hebrew.");
				System.out.println(" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
				System.out.println("                                          :    If not used, no distinction will be made.");
				System.out.println();
				System.out.println(" -dsearch | --dilugim-search              : (MODE) DILUGIM SEARCH mode (MUST BE FIRST PARAMETER).");
				System.out.println("   (EXAMPLE) java -jar TorahApp.jar -dsearch -sw אדם -fs -dmin 2 -dmax 3 -p 20 -dmode tp");
				System.out.println("                                          :    Searches letters in the Torah text by skipping letters or words or lines");
				System.out.println(" -sw | --search-word [SEARCH LETTERS]     : The sequence of letters, should be in hebrew when searching through Torah text.");
				System.out.println(" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
				System.out.println("                                          :    If not used, no distinction will be made.");
				System.out.println(" -dmin | --dilug-minimum [NUMBER]         : A number representing a start of range for letter skipping (minimum value 1).");
				System.out.println(" -dmax | --dilug-maximum [NUMBER]         : A number representing a end of range for letter skipping.");
				System.out.println("                                          :   -dmin and -dmax together represent a range.");
				System.out.println(" -p | --padding [NUMBER]                  : When a match is found, a sequence of letters of only the non-skipped letters will be shown.");
				System.out.println("                                          :   This number will determine the amount of letters to show before and after the requested letters.");  
				System.out.println(" -ff | --flag-reverse                     : (Optional) Will also find the sequence of letters in reverse order.");
				System.out.println("                                          :   If not used, only the sequence in the original order will be searched for.");
				System.out.println(" -dmode | --dilug-mode [DMODE]            : (Optional) The DMODE is a mode for the Dilugim Search, the parameters will be explained next.");
				System.out.println("                                          :   If not used, default mode is to skip letters");
				System.out.println("     (PARAMETER) [DMODE]= reg | regular   : (default) This mode skips letters, and checks letters not skipped.");
				System.out.println("     (PARAMETER) [DMODE]= hp | headpasuk  : This mode skips lines, and checks only the first letter in the lines not skipped.");
				System.out.println("     (PARAMETER) [DMODE]= tp | tailpasuk  : This mode skips lines, and checks only the last letter in the lines not skipped.");
				System.out.println("     (PARAMETER) [DMODE]= hw | headword   : This mode skips words, and checks only the first letter in the words not skipped.");
				System.out.println("     (PARAMETER) [DMODE]= tw | tailword   : This mode skips words, and checks only the last letter in the words not skipped.");
				System.out.println("     (EXAMPLE) --dilug-mode tailpasuk");
				System.out.println("-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
				System.out.println();
				System.out.println(" -lsearch | --letter-search               : (MODE) LETTER SEARCH mode (MUST BE FIRST PARAMETER).");
				System.out.println("   (EXAMPLE) java -jar TorahApp.jar -lsearch -sw אדם -mp -fht");
				System.out.println("                                          : Letter search is a complex search which searches letters in lines and words.");
				System.out.println("                                          :   The letters can appear in order or any order depending on your option.");
				System.out.println("                                          :   Letters do not need to be consecutive.");
				System.out.println("                                          : The letters can appear in one word or one line, depending on your option.");
				System.out.println(" -sw | --search-word [SEARCH LETTERS]     : The sequence of letters, should be in hebrew when searching through Torah text.");
				System.out.println(" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
				System.out.println(" -mw | --mode-word                        : (Optional) (Default) Searches for all letters in one word.");
				System.out.println(" -mp | --mode-pasuk                       : (Optional) Searches for all letters in one line.");
				System.out.println(" -fo | --flag-letter-order                : (Optional) Forces Letters do be find in the order requested.");    
				System.out.println("                                              If not used, the letters will be searched for in every possible combination");
				System.out.println(" -fht | --flag-head-tail                  : (Optional) Forces first letter to be the first letter of word [-mw] or line [-mp].");
				System.out.println("                                              Forces last letter to be the last letter of a word [-mw] or line [-mp].");
				System.out.println("                                              If not used, first and last letters can appear in other places.");
				System.out.println("-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
				System.out.println();
				System.out.println(" -csearch | --count-search                : (MODE) COUNT SEARCH mode (MUST BE FIRST PARAMETER).");
				System.out.println("   (EXAMPLE) java -jar TorahApp.jar -csearch -sw אדם -index 12 -fw");
				System.out.println("                                          :    Finds the nth match of words or partial words in the Torah Text, depending on index.");
				System.out.println(" -sw | --search-word [SEARCH WORD]        : The Seach Word to use, should be in hebrew to search through Torah text.");
				System.out.println(" -fw | --flag-whole                       : (Optional) Searches through whole words.");
				System.out.println("                                          :    If not used, search will check partial words as well.");
				System.out.println(" -fs | --flag-sofiot                      : (Optional) Distinguishes between regular hebrew letters and end hebrew letters.");
				System.out.println("                                          :    If not used, no distinction will be made.");
				System.out.println(" -i | --index  [NUMBER]                   : Used for counting the number of matches.");
				System.out.println("                                          :   Search will only show this count.");
				System.out.println("-f | --format-text [NUMBER]               : (Optional) Format line text to certain amount of characters per line.");
				System.out.println();
				System.out.println("-t | --text | --text-menu                 : For TEXT CONSOLE MODE.");
				System.out.println();
				System.out.println("No Parameters for Graphics Mode");
				System.exit(0);
				break;
			case "--search":
			case "-search":
			case "-s":
				isGui = false;
				selection = Methods.id_searchWords;
				args2 = Arrays.copyOf(args2, 7);
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
						if (shouldSkip>0) {
							shouldSkip--;
							continue;
						}
						String str2 = a.toLowerCase();
						switch (str2) {
						case "--search-word":
						case "-search-word":
						case "-sw":
							shouldSkip++;
							args2[0] = args[1+argsCount];
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
							args2[5] = args[1+argsCount];
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
						case "-f":
						case "--format-text":
							shouldSkip++;
							formatLine = Integer.parseInt(args[1+argsCount]);
							break;
						default:
							System.out.println("Unrecognized command: "+args[argsCount]);
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
				args2 = Arrays.copyOf(args2, 4);
				args2[1] = false;
				args2[2] = false;
				if (args.length > 1) {
					for (String a : args) {
						argsCount++;
						// skip first parameter
						if (argsCount == 0) {
							continue;
						}
						if (shouldSkip>0) {
							shouldSkip--;
							continue;
						}
						String str2 = a.toLowerCase();
						switch (str2) {
						case "--search-word":
						case "-search-word":
						case "-sw":
							shouldSkip++;
							args2[0] = args[1+argsCount];
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
						case "-f":
						case "--format-text":
							shouldSkip++;
							formatLine = Integer.parseInt(args[1+argsCount]);
							break;
						default:
							System.out.println("Unrecognized command: "+args[argsCount]);
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
				args2[1] = false;
				if (args.length > 1) {
					for (String a : args) {
						argsCount++;
						// skip first parameter
						if (argsCount == 0) {
							continue;
						}
						if (shouldSkip>0) {
							shouldSkip--;
							continue;
						}
						String str2 = a.toLowerCase();
						switch (str2) {
						case "--search-word":
						case "-search-word":
						case "-sw":
							shouldSkip++;
							args2[0] = args[1+argsCount];
							break;
						case "--flag-sofiot":
						case "-flag-sofiot":
						case "-fs":
							args2[1] = true;
							break;
						default:
							System.out.println("Unrecognized command: "+args[argsCount]);
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
				args2[1] = false;
				selection = Methods.id_searchDilugim;
				if (args.length > 1) {
					for (String a : args) {
						argsCount++;
						// skip first parameter
						if (argsCount == 0) {
							continue;
						}
						if (shouldSkip>0) {
							shouldSkip--;
							continue;
						}
						String str2 = a.toLowerCase();
						switch (str2) {
						case "--search-word":
						case "-search-word":
						case "-sw":
							shouldSkip++;
							args2[0] = args[1+argsCount];
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
							args2[2] = args[1+argsCount];
							break;
						case "--dilug-maximum":
						case "-dilug-maximum":
						case "-dmax":
							shouldSkip++;
							args2[3] = args[1+argsCount];
							break;
						case "--padding":
						case "-padding":
						case "-p":
							shouldSkip++;
							args2[4] = args[1+argsCount];
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
							switch (args[1+argsCount].toLowerCase()) {
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
								System.out.println("Unrecognized command: "+args[argsCount]);
								return false;
							}
							break;
						case "-f":
						case "--format-text":
							shouldSkip++;
							formatLine = Integer.parseInt(args[1+argsCount]);
							break;
						default:
							System.out.println("Unrecognized command: "+args[argsCount]);
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
				args2 = Arrays.copyOf(args2, 6);
				args2[1] = false;
				args2[3] = false;
				args2[4] = false;
				args2[5] = false;
				if (args.length > 1) {
					for (String a : args) {
						argsCount++;
						// skip first parameter
						if (argsCount == 0) {
							continue;
						}
						if (shouldSkip>0) {
							shouldSkip--;
							continue;
						}
						String str2 = a.toLowerCase();
						switch (str2) {
						case "--search-word":
						case "-search-word":
						case "-sw":
							shouldSkip++;
							args2[0] = args[1+argsCount];
							break;
						case "--flag-sofiot":
						case "-flag-sofiot":
						case "-fs":
							args2[1] = true;
							break;
						// args2[2] = Frame.get_searchRange();
						case "--mode-word":
						case "-mode-word":
						case "-mw":
							args2[3] = false;
							break;
						case "--mode-pasuk":
						case "-mode-pasuk":
						case "-mp":
							args2[3] = true;
							break;
						case "--flag-letter-order":
						case "-flag-letter-order":
						case "-fo":
							args2[4] = true;
							break;
						case "--flag-head-tail":
						case "-flag-head-tail":
						case "-fht":
							args2[5] = true;
							break;
						case "-f":
						case "--format-text":
							shouldSkip++;
							formatLine = Integer.parseInt(args[1+argsCount]);
							break;
						default:
							System.out.println("Unrecognized command: "+args[argsCount]);
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
						if (shouldSkip>0) {
							shouldSkip--;
							continue;
						}
						String str2 = a.toLowerCase();
						switch (str2) {
						case "--search-word":
						case "-search-word":
						case "-sw":
							shouldSkip++;
							args2[0] = args[1+argsCount];
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
							args2[4] = args[1+argsCount];
							break;
						case "-f":
						case "--format-text":
							shouldSkip++;
							formatLine = Integer.parseInt(args[1+argsCount]);
							break;
						default:
							System.out.println("Unrecognized command: "+args[argsCount]);
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
				args2[1] = true;
				if (args.length > 1) {
					for (String a : args) {
						argsCount++;
						// skip first parameter
						if (argsCount == 0) {
							continue;
						}
						if (shouldSkip>0) {
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
						default:
							System.out.println("Unrecognized command: "+args[argsCount]);
							return false;
						}
					}
				}
				break;
			case "--range-report":
			case "-range-report":
			case "-r":
				// need to find a way to create a report without dialog
				isGui = false;
				selection = Methods.id_ReportTorahCount;
				//args[0] = new int[] {start,end};
				//args[1] = new String[] {startStr,endStr,end2Str};

				// DialogSearchRangeFrame dFrame = DialogSearchRangeFrame.getInstance(false);
				// dFrame.setVisible(true);
				break;
			default:
				System.out.println("Unrecognized command: "+args[0]);
				return false;
			}
		}
		if (selection >= 0) {
			try {
				Methods.arrayMethods.get(selection).run(args2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return isGui;
	}
}
