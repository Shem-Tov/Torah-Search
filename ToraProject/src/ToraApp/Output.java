package ToraApp;

import StringFormatting.StringAlignUtils;
import StringFormatting.StringAlignUtils.Alignment;
import frame.frame;

public class Output {
	public static String[][] printPasukInfo(int countLines, String searchSTR, String line) throws NoSuchFieldException{
		ToraApp.perekBookInfo pBookInstance = ToraApp.findPerekBook(countLines);
		String[] htmlText1 = StringFormatting.HtmlGenerator.setRGBHtmlString(128, 150, 255);
		String tempStr1 = "\u202B" + "\""+ htmlText1[0] + searchSTR + htmlText1[1] + "\" " + "נמצא ב"
				+ StringAlignUtils.padRight(pBookInstance.getBookName(), 6) + " "
				+ pBookInstance.getPerekLetters() + ":" + pBookInstance.getPasukLetters();
		Output.printText(StringAlignUtils.padRight(tempStr1, 32) + " =    " + line);
		return (new String[][] {{ searchSTR, pBookInstance.getBookName(),
				pBookInstance.getPerekLetters(), pBookInstance.getPasukLetters(), line }});
	}
	
	public static void printText(String text) {
		printText(text, (byte)0);
	}

	public static void printText(String text, int mode)
	{
		printText(text,(byte) mode);
	}
	
	public static void printText(String text, byte mode) {
		// mode 0 = regular
		// mode 1 = attention
		// mode 2 = silence on GUI
		StringAlignUtils util = new StringAlignUtils(frame.panelWidth, Alignment.RIGHT);
		switch (ToraApp.getGuiMode()) {
		case 1: // GUI Mode
				switch (mode) {
				case 0:
				case 1:
					frame.appendText(util.format(text), mode);
					break;
				case 2:
					break;
				}
			break;
		default: // Console Mode - Reserved guiMode=0
			switch (mode) {
			case 0: // user text
				System.out.println(util.format(text));
				break;
			case 1: // debug mode
			case 2:
				System.err.println(util.format(text));
				break;
			}
		}
	}

}
