package frame;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class HighLighter {

	private static String findWord = null;
	private static int pos = 0;

	// Creates highlights around all occurrences of pattern in textComp
	public static void highlight(JTextComponent textComp, String pattern) {
		// First remove all old highlights
		removeHighlights(textComp);

		try {
			pos = 0;
			findWord = pattern;
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			// Search for pattern
			// see I have updated now its not case sensitive
			while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
				// Create highlighter using private painter and apply around pattern
				hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}
			scrollWords(textComp);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	// Removes only our private highlights
	public static void removeHighlights(JTextComponent textComp) {
		findWord = null;
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();
		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof MyHighlightPainter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	// An instance of the private subclass of the default highlight painter
	static HighLighter outer = new HighLighter();
	static MyHighlightPainter myHighlightPainter = outer.new MyHighlightPainter(new java.awt.Color(245, 230, 210));

	// A private subclass of the default highlight painter
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighlightPainter(Color color) {
			super(color);
		}
	}

	public static void scrollWords(JTextComponent textComp) {
		// Focus the text area, otherwise the highlighting won't show up
		textComp.requestFocusInWindow();
		// Make sure we have a valid search term
		if (findWord != null && findWord.length() > 0) {
			String find = findWord.toLowerCase();
			Document document = textComp.getDocument();
			int findLength = find.length();
			try {
				boolean found = false;
				// Rest the search position if we're at the end of the document
				if (pos + findLength > document.getLength()) {
					pos = 0;
				}
				// While we haven't reached the end...
				// "<=" Correction
				while (pos + findLength <= document.getLength()) {
					// Extract the text from teh docuemnt
					String match = document.getText(pos, findLength).toLowerCase();
					// Check to see if it matches or request
					if (match.equals(find)) {
						found = true;
						break;
					}
					pos++;
				}

				// Did we find something...
				if (found) {
					// Get the rectangle of the where the text would be visible...
					@SuppressWarnings("deprecation")
					Rectangle viewRect = textComp.modelToView(pos);
					// Scroll to make the rectangle visible
					textComp.scrollRectToVisible(viewRect);
					// Highlight the text
					textComp.setCaretPosition(pos + findLength);
					textComp.moveCaretPosition(pos);
					// Move the search position beyond the current match
					pos += findLength;
				}

			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}
}
