package hebrewLetters;

public class HebrewLetters {
	private static char[] letters = { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע',
			'פ', 'צ', 'ק', 'ר', 'ש', 'ת','ך', 'ם', 'ן', 'ף', 'ץ' };
	
	public static Boolean checkHebrew(String str) {
		Boolean finalBool = true;
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
