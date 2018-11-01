package StringFormatting;

public class HtmlGenerator {
	String[] htmlCode;
	public HtmlGenerator(int size, int r, int g, int b) {
		htmlCode = setAll_Html_B_Size_RGB_Align_Right(size, r, g, b);
	}
	public String getHtml(int mode) {
		//mode 0 - Html Header
		//mode 1 - Html Tail
		return htmlCode[mode];
	}
	
	final static String[] rAlign = new String[] {"<p align=\"right\">","</p>"};
	public static String[] setRGBHtmlString(int r, int g, int b) {
		return setRGBandSizeHTMLString(0,r,g,b);
	}
	public static String[] setRGBandSizeHTMLString(int size, int r, int g, int b) {
		//size 0 = default size
		return new String[] {"<font "+((size>0)?("size=\""+size+"\""):"")+ "color=\"rgb("+r+", "+g+", "+b+")\">","</font>"};
	}
	final static String[] bold = new String[] {"<b>","</b>"};
	
	public static String[] setAll_Html_B_Size_RGB_Align_Right(int size, int r, int g, int b) {
		String[] strRGBSize = setRGBandSizeHTMLString(size,r,g,b);
		return new String[] {rAlign[0]+strRGBSize[0]+bold[0],bold[1]+strRGBSize[1]+rAlign[1]};
	}
	
}
