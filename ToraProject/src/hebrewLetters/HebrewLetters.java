package hebrewLetters;

public class HebrewLetters {
	private static char[] letters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע',
			'פ', 'צ', 'ק', 'ר', 'ש', 'ת','ך', 'ם', 'ן', 'ף', 'ץ' };
	
	private static char[][] hebrewLetters = { { ' ', 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט' },
			{ ' ', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע', 'פ', 'צ' }, { ' ', 'ק', 'ר', 'ש', 'ת' } };

	public static String removeVowels(String hebString){
	    String newString = "";
	    for(int j=0; j<hebString.length() ; j++) {
	        //char c = hebString.charAt(j);
	        if(hebString.charAt(j)<1425 || hebString.charAt(j)>1479)
	            newString = newString + hebString.charAt(j);
	    }
	    return newString;
	}

	public static String findHebrewLetters(int num) {
		String str = "";
		if (num >= 1000) {
			str += (Character.toString(hebrewLetters[0][num / 1000])).replaceAll("\\s+", "") + "'";
			num = (num % 1000);
		}
		while (num >= 100) {
			str += (Character.toString(hebrewLetters[2][Math.min(num, 400) / 100])).replaceAll("\\s+", "");
			num -= Math.min((num - (num % 100)), 400);
		}
		switch (num) {
		case 15:
			str += "טו";
			break;
		case 16:
			str += "טז";
			break;
		default:
			str += (Character.toString(hebrewLetters[1][num / 10]).replaceAll("\\s+", "")
					+ Character.toString(hebrewLetters[0][num % 10])).replaceAll("\\s+", "");
		}
		return str;
	}

	public static Boolean checkHebrew(String str) {
		Boolean finalBool = true;
		// OK to be empty
		if ((str==null) || (str.length()==0)) {
			return true;
		}
		for (char ch:str.toCharArray()) {
			Boolean bool = false;
			if (ch==' ') continue;
			for (char letter:letters) {
				if (ch==letter) {
					bool = true;
					break;
				}
			}
			if (!bool) {
				finalBool = false;
				break;
			}
		}
		return finalBool;
	}

	
	private static char switchSofiot(char ch) {
		switch (ch) {
		case 'ך':
			ch = 'כ';
			break;
		case 'ם':
			ch = 'מ';
			break;
		case 'ן':
			ch = 'נ';
			break;
		case 'ף':
			ch = 'פ';
			break;
		case 'ץ':
			ch = 'צ';
		}
		return ch;
	}

	public static String switchSofiotStr(String str) {
		String str2 = "";
		for (char ch : str.toCharArray()) {
			str2 += switchSofiot(ch);
		}
		//System.out.println(str + " - Original string length: " + str.length());
		//System.out.println(str2 + " - New string length: " + str2.length());
		return str2;
	}

	public static Boolean compareChar(char chSearch, char ch2, boolean sofiot) {
		// chSearch must first be without sofiot;
		// use switchSofiotStr()
		if (!sofiot) {
			return (chSearch == switchSofiot(ch2));
		} else {
			return (chSearch == ch2);
		}
	}
}
