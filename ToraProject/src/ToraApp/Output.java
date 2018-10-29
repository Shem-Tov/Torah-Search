package ToraApp;
import javax.swing.text.BadLocationException;

import StringAlignUtils.StringAlignUtils;
import StringAlignUtils.StringAlignUtils.Alignment;
import frame.frame;

public class Output {
		public static void printText(String text) {
		  printText(text, true);	
		}
		

		public static void printText(String text, boolean mode) {
			StringAlignUtils util = new StringAlignUtils(frame.panelWidth, Alignment.RIGHT);
			switch (ToraApp.getGuiMode()){
			case 1: //GUI Mode
					try {
						frame.appendText(util.format(text),mode);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				break;
			default: //Console Mode - Reserved guiMode=0
				if (mode) { //user text
					System.out.println(util.format(text));
				}
				else { //debug mode
					System.err.println(util.format(text));
				}
			}
		}

}
