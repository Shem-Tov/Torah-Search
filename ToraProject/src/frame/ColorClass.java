package frame;

import stringFormatting.HtmlGenerator;

public class ColorClass {

	public static final int[] color_mainStyleHTML_hardCoded = new int[] { 128, 88, 255 };
	public static final int[] color_markupStyleHTML_hardCoded = new int[] { 245, 195, 92 };
	public static final int[][] color_highlightStyleHTML_hardCoded = new int[][] {{135,206,250},{166,220,225},{155,215,240}};
	static final int[] color_attentionHTML = new int[] { 250, 40, 40 };
	static final int[] color_headerStyleHTML = new int[] { 58, 124, 240 };
	static final int[] color_footerStyleHTML = new int[] { 255, 144, 180 };
	static int[] color_mainStyleHTML = color_mainStyleHTML_hardCoded.clone();
	static int[] color_markupStyleHTML = color_markupStyleHTML_hardCoded.clone();
	static int[][] color_highlightStyleHTML = color_highlightStyleHTML_hardCoded.clone();
	
	public static HtmlGenerator mainStyleHTML = new HtmlGenerator(Frame.getTextHtmlSize(),
	color_mainStyleHTML_hardCoded[0], color_mainStyleHTML_hardCoded[1], color_mainStyleHTML_hardCoded[2],
	0b111);
	public static HtmlGenerator markupStyleHTML = new HtmlGenerator(Frame.getTextHtmlSize() + 1,
			color_markupStyleHTML_hardCoded[0], color_markupStyleHTML_hardCoded[1], color_markupStyleHTML_hardCoded[2],
			0b100);
	public static HtmlGenerator headerStyleHTML = new HtmlGenerator(Frame.getTextHtmlSize() + 1,
	color_headerStyleHTML[0], color_headerStyleHTML[1], color_headerStyleHTML[2], 0b100);
	public static HtmlGenerator footerStyleHTML = new HtmlGenerator(0,
	color_footerStyleHTML[0], color_footerStyleHTML[1], color_footerStyleHTML[2], 0b100);
	public static HtmlGenerator[] highlightStyleHTML = new HtmlGenerator[] {
			new HtmlGenerator(Frame.getTextHtmlSize() + 1,color_highlightStyleHTML[0][0], color_highlightStyleHTML[0][1], color_highlightStyleHTML[0][2], 0b100)
			,new HtmlGenerator(Frame.getTextHtmlSize() + 1,color_highlightStyleHTML[1][0], color_highlightStyleHTML[1][1], color_highlightStyleHTML[1][2], 0b100)
			,new HtmlGenerator(Frame.getTextHtmlSize() + 1,color_highlightStyleHTML[2][0], color_highlightStyleHTML[2][1], color_highlightStyleHTML[2][2], 0b100)
	};
	public static String getRGBmainStyleHTML() {
		String str="";
		str += String.format("%02X", color_mainStyleHTML[0]);
		str += String.format("%02X", color_mainStyleHTML[1]);
		str += String.format("%02X", color_mainStyleHTML[2]);
		return str;
	}

	public static String getRGBmarkupStyleHTML() {
		String str="";
		str += String.format("%02X", color_markupStyleHTML[0]);
		str += String.format("%02X", color_markupStyleHTML[1]);
		str += String.format("%02X", color_markupStyleHTML[2]);
		return str;
	}

	public static String getRGBheaderStyleHTML() {
		String str="";
		str += String.format("%02X", color_headerStyleHTML[0]);
		str += String.format("%02X", color_headerStyleHTML[1]);
		str += String.format("%02X", color_headerStyleHTML[2]);
		return str;
	}

	public static String getRGBhighlightStyleHTML() {
		String str="";
		str += String.format("%02X", color_highlightStyleHTML[0]);
		str += String.format("%02X", color_highlightStyleHTML[1]);
		str += String.format("%02X", color_highlightStyleHTML[2]);
		return str;
	}

}
