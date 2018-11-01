package StringFormatting;

public class HtmlGenerator {
	String[] htmlCode;
	final int flag_RGB_Size = 0b100;
	final int flag_bold = 0b010;
	final int flag_rAlign = 0b001;
	
	public HtmlGenerator(int flag) {
		this(0, 0, 0, 0, flag);
		if ((flag & flag_RGB_Size)==flag_RGB_Size) {
			throw new IllegalArgumentException("StringFormatting.HtmlGenerator - This flag is reserved for Color and Size parameters, but none were passed");
		} 
	}
	public HtmlGenerator(int r, int g, int b, int flag) {
		this(0, r, g, b, flag);
	}
	public HtmlGenerator(int size, int r, int g, int b, int flag) {
		//flag 0b111 - setAll
		//flag 0b100 - setRGBandSize
		//flag 0b010 - bold
		//flag 0b001 - rAlign
		String[] strRGBSize = setRGBandSizeHTMLString(size,r,g,b);
		htmlCode = 	new String[] 
				{(((flag & flag_rAlign)==flag_rAlign)? rAlign[0]:"")
				  + (((flag & flag_RGB_Size)==flag_RGB_Size)? strRGBSize[0]:"")
				  + (((flag & flag_bold)==flag_bold)? bold[0]:""),
				    (((flag & flag_bold)==flag_bold)? bold[1]:"")
				  + (((flag & flag_RGB_Size)==flag_RGB_Size)? strRGBSize[1]:"")
				  + (((flag & flag_rAlign)==flag_rAlign)?rAlign[1]:"")};

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
