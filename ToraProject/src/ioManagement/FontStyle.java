package ioManagement;

import org.apache.poi.ss.usermodel.IndexedColors;

public class FontStyle {
	IndexedColors color;
	int fontSize;

	public FontStyle(IndexedColors c, int s) {
		color = c;
		fontSize = s;
	}

	IndexedColors getColor() {
		return color;
	}

	int getFontSize() {
		return fontSize;
	}
}
