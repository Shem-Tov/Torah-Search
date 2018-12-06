package frame;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.TreePath;

import org.apache.commons.lang3.StringUtils;

import ioManagement.ClipboardClass;
import ioManagement.ExcelFunctions;
import ioManagement.Output;
import ioManagement.PropStore;
import stringFormatting.HtmlGenerator;
import stringFormatting.OtherHtml;
import torahApp.ToraApp;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;

public class Frame {
	private static Frame frame_instance;

	public static Frame getInstance() {
		if (frame_instance == null) {
			try {
				frame_instance = new Frame();
			} catch (IOException | BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return frame_instance;
	}

	private static final int rowHeight_hardCoded = 30;
	private static int rowHeight = rowHeight_hardCoded;
	private static final int id_panel_search = 0;
	private static final int id_panel_dilug = 1;
	private static final int id_panel_padding = 2;
	private static final int id_panel_combosub = 3;
	private static final int id_panel_letterOrder = 4;
	private static final int id_panel_wholeWord = 5;
	private static final int id_panel_gimatriaSofiot = 6;
	private static final int id_panel_countPsukim = 7;
	private static final int id_panel_searchMulti = 8;
	private static final int id_panel_searchRange = 9;
	@SuppressWarnings("unused")
	private static final int id_panel_progress = 10;
	@SuppressWarnings("unused")
	private static final int id_panel_BUTTON = 11;
	public static final String[] comboBox_sub_Strings_Letters = new String[] { "מילים", "פסוקים" };
	public static final String[] comboBox_sub_Strings_Dilugim = new String[] { "רגיל", "ראש פסוק", "סוף פסוק",
			"ראש מילה", "סוף מילה" };
	public static final String[] comboBox_sub_Strings_Search_Multi = new String[] { "חובה שתי המילים",
			"מספיק מילה אחת" };
	static final String combo_strSearch = "חיפוש רגיל";
	static final String combo_strGimatriaSearch = "חיפוש גימטריה";
	static final String combo_strGimatriaCalculate = "חישוב גימטריה";
	static final String combo_strDilugim = "חיפוש בדילוגים";
	static final String combo_strLetterSearch = "חיפוש אותיות";
	static final String combo_strCountSearch = "חיפוש אינדקס";
	static final String combo_strTorahPrint = "הדפסת התורה";
	static final String combo_strTorahRangeReport = "דוח ספירה";
	private static final String checkBox_countPsukim_true = "<html><p align='right'>" + "ספירת פסוקים" + "</p></html>";
	private static final String checkBox_countPsukim_false = "ספירת מציאות";
	private static final String strLabel_padding_Dilug = "מספר אותיות";
	private static final String strLabel_padding_CountSearch = "מספר אינדקס";
	private static final String strLabel_padding_Search = "מילה שניה";
	private static final String checkBox_searchMultiple_String = "<html><p align='right'>" + "חיפוש יותר ממילה אחת"
			+ "</p></html>";
	private static final String checkBox_searchMultiple_ReverseDilug = "חיפוש דילוג הפוך";
	private static final String checkBox_searchMultiple_placeInfo = "להוסיף סימונים";
	private static Boolean bool_placeInfo = true;
	private static Boolean bool_dilugReversed = false;
	private static Boolean bool_searchMultiple = false;
	static final String checkBox_letterOrder_String = "שמור סדר האותיות";
	static final String checkBox_letterOrder_Tooltip = "שמור על סדר האותיות";
	static final String checkBox_wholeWord_Letters = "שמור על ראשי וסופי תיבות";
	static final String checkBox_wholeWord_Regular = "מילים שלמות";
	private static final int fontSize_hardCoded = 16;
	private static final int fontSizeBig_hardCoded = fontSize_hardCoded + 2;
	private static final int fontSizeSmall_hardCoded = fontSize_hardCoded - 2;
	private static final int fontSizeSmaller_hardCoded = fontSize_hardCoded - 3;

	private static int fontSize = fontSize_hardCoded;
	private static int fontSizeBig = getFontSize() + 2;
	private static int fontSizeSmall = getFontSize() - 2;
	private static int fontSizeSmaller = getFontSize() - 3;

	static int textHtmlSize = 5;
	public static final int lineHeaderSize = 5;
	// searchRange, has two line numbers to search through in the Torah
	// if {0,0} then search through ALL
	private static int[] searchRange = new int[] { 0, 0 };
	private static final String searchRangeAll = "הכול";
	private static String searchRangeString = searchRangeAll;
	private static String searchRangeStringHTML = searchRangeAll;
	private static String paddingSearchMulti = "";
	private static int paddingSearchIndex = 1;
	private static int paddingDilug = 1;
	private static Color ColorBG_comboBox_main = new Color(255, 240, 240);
	private static Color ColorBG_textPane = new Color(251, 255, 253);
	private static Color ColorBG_Panel = new Color(240, 240, 255);
	private static Color customBGColor = ColorBG_Panel;
	private static Color ColorBG_menu2 = new Color(238, 242, 250);
	private static Color ColorBG_menu = new Color(240, 248, 255);
	private static Color ColorBG_menu3 = new Color(251, 255, 255);

	private static final String buttonRunTorahRangeReport = "צור דוח";
	private static final String buttonRunText = "חפש";
	private static final String buttonCancelText = "בטל";
	private static final String buttonCancelRequestText = "מבטל..";
	private static final String checkBox_gimatriaSofiot_text = "<html>" + "<p align=\"right\">" + "חישוב מיוחד"
			+ " לסופיות" + "</p> </html>";

	private static Boolean methodCancelRequest = false;
	private static Boolean methodRunning = false;

	private JFrame frame;
	private static ArrayList<JPanel> subPanels = new ArrayList<JPanel>();
	private static JPanel panel, subPanelProgressLabels, panelGroup;
	private static JButton button_search;
	private static JButton button_defaultSettings;
	private static JButton button_searchRange;
	private static JLabel label_padding;
	private static JLabel label_dProgress;
	private static JLabel label_countMatch;
	private static JLabel label_dilugMin;
	private static JLabel label_dilugMax;
	private static JLabel label_textfield_Search;
	private static JTextField textField_Search;
	private static JTextField textField_dilugMin;
	private static JTextField textField_dilugMax;
	private static JTextField textField_padding;
	private static JComboBox<?> comboBox_main;
	private static JCheckBox checkBox_gimatriaSofiot;
	private static JCheckBox checkBox_wholeWord;
	private static JCheckBox checkBox_countPsukim;
	private static JCheckBox checkBox_searchRange;
	private static JCheckBox checkBox_searchMultiple;
	private static JCheckBox checkBox_letterOrder;
	static JTextPane textPane;
	// static JTable table;
	private static Tree tree;
	private static JScrollPane scrollPane;
	private static JTabbedPane tabbedPane;
	private static HTMLDocument doc = new HTMLDocument();
	private static HTMLEditorKit kit = new HTMLEditorKit();
	private SwingActivity activity;
	static public int panelWidth;
	private JPopupMenu popupMenu;
	private static JProgressBar progressBar;
	private static JComboBox<?> comboBox_sub;
	private static JCheckBoxMenuItem checkBox_DifferentSearch;
	private static JCheckBoxMenuItem checkBox_TooltipOption;
	private static JMenuItem menuItem_bgColor;
	private static JMenu menuItem_textColor;
	private static JMenu menuFiles;
	private static JMenuItem menuDifferentFile;
	private static JMenuItem menuNoTevotFile;
	private static JMenuItem menuLinesFile;
	private static JMenuItem menuTorahTable;
	private static JMenuItem menuExcelFolder;
	private static JMenuItem menuResetExcelFolder;
	private static JMenuItem menuItem_textColorMain;
	private static JMenuItem menuItem_textColorMarkup;
	private static JMenuItem menuItem_textSize;
	private static JCheckBoxMenuItem checkbox_createExcel;
	private static JCheckBoxMenuItem checkbox_createTree;
	private static JCheckBoxMenuItem checkbox_createDocument;
	private static JMenuBar menuBar;
	private static JMenu menuSettings;

	class PopUpTextPane extends JPopupMenu {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JMenuItem anItem;

		public PopUpTextPane() {
			anItem = new JMenuItem("נקה מסך");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					clearText();
				}
			});
			add(anItem);
			anItem = new JMenuItem("העתק");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					ClipboardClass.setSysClipboardText(textPane.getSelectedText());
				}
			});
			add(anItem);
			anItem = new JMenuItem("חפש...");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					DialogFindWordFrame dFrame = DialogFindWordFrame.getInstance();
					Point p = frame.getLocation();
					dFrame.setLocation((int) (p.getX() + frame.getWidth() - dFrame.getWidth()),
							(int) (p.getY() + frame.getHeight() - dFrame.getHeight()));
					dFrame.setVisible(true);
				}
			});
			add(anItem);
			anItem = new JMenuItem("הסר הארות");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					DialogFindWordFrame.clearLastSearch();
					HighLighter.removeHighlights(textPane);
				}
			});
			add(anItem);
			anItem = new JMenuItem("לעבור על המציאות");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					HighLighter.scrollWords(textPane);
					frame.repaint();
				}
			});
			add(anItem);
		}
	}

	class PopUpTree extends JPopupMenu {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JMenuItem anItem;

		public PopUpTree() {
			anItem = new JMenuItem("נקה מסך");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					Tree.getInstance().clearTree();
				}
			});
			add(anItem);
			anItem = new JMenuItem("העתק");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					//ClipboardClass.setSysClipboardText(OtherHtml.html2text(tree.getLastSelectedPathComponent().toString()));
					TreePath[] treePaths = tree.getSelectionPaths();
					String str = "";
					for (TreePath t:treePaths) {
						str += OtherHtml.html2text(t.toString());
					}
					ClipboardClass.setSysClipboardText(str);
				}
			});
			add(anItem);
		}
	}

	class PopClickTreeListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		private void doPop(MouseEvent e) {
			PopUpTree menu = new PopUpTree();
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	class PopClickTextPaneListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		private void doPop(MouseEvent e) {
			PopUpTextPane menu = new PopUpTextPane();
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public static void clearTextPane() {
		textPane.setText("");
	}

	public static String getTextField_padding() {
		return textField_padding.getText();
	}

	public static String getTextField_Search() {
		return textField_Search.getText();
	}

	public static String getTextField_dilugMin() {
		return textField_dilugMin.getText();
	}

	public static String getTextField_dilugMax() {
		return textField_dilugMax.getText();
	}

	public static int[] get_searchRange() {
		// Functions only with Torah file, not with custom files
		if ((checkBox_searchRange.isSelected()) && (!getCheckBox_DifferentSearch())) {
			return searchRange;
		} else {
			return new int[] { 0, 0 };
		}
	}

	public static String get_searchRangeText() {
		// checks if frame exists
		if ((frame_instance != null) && (checkBox_searchRange.isSelected())) {
			return searchRangeString;
		} else {
			return "";
		}
	}

	public static Boolean getCheckBox_gimatriaSofiot() {
		return checkBox_gimatriaSofiot.isSelected();
	}

	public static Boolean getCheckBox_wholeWord() {
		return checkBox_wholeWord.isSelected();
	}

	public static Boolean getCheckBox_countPsukim() {
		return checkBox_countPsukim.isSelected();
	}

	public static String getComboBox_main() {
		return comboBox_main.getSelectedItem().toString();
	}

	public static String getComboBox_sub() {
		return comboBox_sub.getSelectedItem().toString();
	}

	public static int getComboBox_sub_Index() {
		return comboBox_sub.getSelectedIndex();
	}

	public static Boolean getMethodCancelRequest() {
		return methodCancelRequest;
	}

	public static int getTabbedPaneWidth() {
		return tabbedPane.getWidth();
	}

	public static void setSearchRange(int start, int end, String str, String strHTML) {
		searchRange = new int[] { start, end };
		searchRangeString = str;
		searchRangeStringHTML = strHTML;
		checkBox_searchRange.setText(strHTML);
		checkBox_searchRange.setSelected(true);
	}

	public static void setButtonEnabled(Boolean bool) {
		button_search.setEnabled(bool);
	}

	public static void setMethodRunning(Boolean bool) {
		methodRunning = bool;
		if (bool) {
			button_search.setText(buttonCancelText);
		} else {
			setButtonText();
			methodCancelRequest = false;
		}
	}

	public static void setButtonText() {
		if (getComboBox_main() == combo_strTorahRangeReport) {
			button_search.setText(buttonRunTorahRangeReport);
		} else {
			button_search.setText(buttonRunText);
		}
	}

	public static void showProgressBar(Boolean bool, int flag) {
		// 0b01 - progressBar and countLabel
		// 0b10 - label
		if ((flag & 0b01) == 0b01) {
			progressBar.setVisible(bool);
			label_countMatch.setVisible(bool);
		}
		if ((flag & 0b10) == 0b10) {
			label_dProgress.setVisible(bool);
		}
	}

	public static void setProgressBar(int num) {
		progressBar.setValue(num);
	}

	public static void setLabel_countMatch(String str) {
		label_countMatch.setText(str);
	}

	public static void setLabel_dProgress(String str) {
		label_dProgress.setText(str);
	}

	public static void initValues() {
		textField_Search.setText(PropStore.map.get(PropStore.searchWord));
		textField_dilugMin.setText(PropStore.map.get(PropStore.minDilug));
		textField_dilugMax.setText(PropStore.map.get(PropStore.maxDilug));
		paddingSearchMulti = PropStore.map.get(PropStore.paddingSearchMulti);
		try {
			paddingDilug = Integer.parseInt(PropStore.map.get(PropStore.paddingDilug));
		} catch (Exception e) {

		}
		try {
			paddingSearchIndex = Integer.parseInt(PropStore.map.get(PropStore.paddingSearchIndex));
		} catch (Exception e) {

		}
		ToraApp.subTorahTableFile = PropStore.map.get(PropStore.subTorahTablesFile);
		ToraApp.subTorahLineFile = PropStore.map.get(PropStore.subTorahLineFile);
		ToraApp.subTorahLetterFile = PropStore.map.get(PropStore.subTorahLettersFile);
		ToraApp.differentSearchFile = PropStore.map.get(PropStore.differentSearchFile);
		if ((ToraApp.differentSearchFile != null) && (ToraApp.differentSearchFile.length() > 0)) {
			checkBox_DifferentSearch
					.setToolTipText(Output.markText(ToraApp.differentSearchFile, ColorClass.headerStyleHTML, true));
		} else {
			checkBox_DifferentSearch.setToolTipText(Output.markText(
					"להגדרת קובץ לחיפוש -> הגדרות<br> -> קבצים -> קובץ אחר לחיפוש", ColorClass.headerStyleHTML, true));
		}
		String excelFolder = PropStore.map.get(PropStore.excelFolder);
		if ((excelFolder != null) && (excelFolder.length() > 0)) {
			ExcelFunctions.setExcel_File_Location(excelFolder);
		}
		try {
			customBGColor = new Color(Integer.parseInt(PropStore.map.get(PropStore.bgColor)));
		} catch (Exception e) {
			customBGColor = ColorBG_Panel;
		}
		try {
			setFontSize(Integer.parseInt(PropStore.map.get(PropStore.fontSize)));
		} catch (Exception e) {
			setFontSize(fontSize_hardCoded);
		}

		try {
			Color temp = new Color(Integer.parseInt(PropStore.map.get(PropStore.mainHtmlColor)));
			ColorClass.color_mainStyleHTML[0] = temp.getRed();
			ColorClass.color_mainStyleHTML[1] = temp.getGreen();
			ColorClass.color_mainStyleHTML[2] = temp.getBlue();
			ColorClass.mainStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize,
					ColorClass.color_mainStyleHTML[0], ColorClass.color_mainStyleHTML[1],
					ColorClass.color_mainStyleHTML[2], 0b1101);
			// public static StringFormatting.HtmlGenerator markupStyleHTML = new
			// StringFormatting.HtmlGenerator(textHtmlSize+1, 93, 192, 179,0b100);
		} catch (Exception e) {
			ColorClass.color_mainStyleHTML = ColorClass.color_mainStyleHTML_hardCoded.clone();
		}

		try {
			Color temp = new Color(Integer.parseInt(PropStore.map.get(PropStore.markupHtmlColor)));
			ColorClass.color_markupStyleHTML[0] = temp.getRed();
			ColorClass.color_markupStyleHTML[1] = temp.getGreen();
			ColorClass.color_markupStyleHTML[2] = temp.getBlue();
			ColorClass.markupStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1,
					ColorClass.color_markupStyleHTML[0], ColorClass.color_markupStyleHTML[1],
					ColorClass.color_markupStyleHTML[2], 0b100);
		} catch (Exception e) {
			ColorClass.color_markupStyleHTML = ColorClass.color_markupStyleHTML_hardCoded.clone();
		}
		checkBox_letterOrder.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_letterOrder)));
		checkBox_gimatriaSofiot.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_gimatriaSofiot)));
		checkBox_wholeWord.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_wholeWord)));
		checkBox_countPsukim.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_countPsukim)));
		checkBox_countPsukim.setText(
				((checkBox_countPsukim.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
		if (PropStore.map.get(PropStore.bool_createDocument) != null) {
			checkbox_createDocument.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_createDocument)));
		} else {
			checkbox_createDocument.setSelected(true);
		}
		if (PropStore.map.get(PropStore.bool_createTree) != null) {
			checkbox_createTree.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_createTree)));
		} else {
			checkbox_createTree.setSelected(true);
		}
		checkbox_createExcel.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_createExcel)));
		checkBox_TooltipOption.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_TorahTooltip)));
		textPane.setVisible(checkbox_createDocument.isSelected());
		tree.setVisible(checkbox_createTree.isSelected());
	}

	public static void saveValues() {
		PropStore.addNotNull(PropStore.searchWord, textField_Search.getText());
		PropStore.addNotNull(PropStore.minDilug, textField_dilugMin.getText());
		PropStore.addNotNull(PropStore.maxDilug, textField_dilugMax.getText());

		switch (comboBox_main.getSelectedItem().toString()) {
		case combo_strDilugim:
			PropStore.addNotNull(PropStore.paddingDilug, textField_padding.getText());
			break;
		case combo_strCountSearch:
			PropStore.addNotNull(PropStore.paddingSearchIndex, textField_padding.getText());
			break;
		case combo_strSearch:
			PropStore.addNotNull(PropStore.paddingSearchMulti, textField_padding.getText());
			break;
		}
		PropStore.addNotNull(PropStore.subTorahTablesFile, ToraApp.subTorahTableFile);
		PropStore.addNotNull(PropStore.subTorahLineFile, ToraApp.subTorahLineFile);
		PropStore.addNotNull(PropStore.subTorahLettersFile, ToraApp.subTorahLetterFile);
		PropStore.addNotNull(PropStore.bgColor, String.valueOf(customBGColor.getRGB()));
		PropStore.addNotNull(PropStore.mainHtmlColor, String.valueOf(new Color(ColorClass.color_mainStyleHTML[0],
				ColorClass.color_mainStyleHTML[1], ColorClass.color_mainStyleHTML[2]).getRGB()));
		PropStore.addNotNull(PropStore.markupHtmlColor, String.valueOf(new Color(ColorClass.color_markupStyleHTML[0],
				ColorClass.color_markupStyleHTML[1], ColorClass.color_markupStyleHTML[2]).getRGB()));
		PropStore.addNotNull(PropStore.fontSize, String.valueOf(getFontSize()));
		PropStore.addNotNull(PropStore.bool_gimatriaSofiot, String.valueOf(checkBox_gimatriaSofiot.isSelected()));
		PropStore.addNotNull(PropStore.bool_wholeWord, String.valueOf(checkBox_wholeWord.isSelected()));
		PropStore.addNotNull(PropStore.bool_countPsukim, String.valueOf(checkBox_countPsukim.isSelected()));
		PropStore.addNotNull(PropStore.bool_createDocument, String.valueOf(checkbox_createDocument.isSelected()));
		PropStore.addNotNull(PropStore.bool_createExcel, String.valueOf(checkbox_createExcel.isSelected()));
		PropStore.addNotNull(PropStore.bool_createTree, String.valueOf(checkbox_createTree.isSelected()));
		PropStore.addNotNull(PropStore.bool_letterOrder, String.valueOf(checkBox_letterOrder.isSelected()));
		PropStore.addNotNull(PropStore.bool_TorahTooltip, String.valueOf(checkBox_TooltipOption.isSelected()));
		PropStore.store();
	}

	private static String packHtml(String str) {
		return "<html>" + str + "</html>";
	}

	private static String packHtmlAlign(String str) {
		return "<html><p align=\"right\"><b>" + str + "</b></p></html>";
	}

	public static void Text(String str) throws BadLocationException {
		appendText(str, (byte) 0);
	}

	public static void appendText(String str, byte mode, String... tooltipText) {
		try {
			// doc.insertString(0, "\n"+str, null );
			// mode 0 = regular style with Carriage Return
			// mode 1 = error style with Carriage Return
			// mode 2 = silent mode
			// mode 3 = label
			if (!checkbox_createDocument.isSelected()) {
				return;
			}
			switch (mode) {
			case 0:
				// kit.insertHTML(doc, doc.getLength(), "<b>hello", 0, 0, HTML.Tag.B);
				kit.insertHTML(doc, doc.getLength(),
						(ColorClass.mainStyleHTML.getHtml(0) + str + ColorClass.mainStyleHTML.getHtml(1)), 0, 0, null);
				break;
			case 1:
				kit.insertHTML(doc, doc.getLength(),
						(ColorClass.attentionHTML.getHtml(0) + str + ColorClass.attentionHTML.getHtml(1)), 0, 0, null);
				break;
			case 3:
				// Tooltips need to be disabled here
				// and also in Output.printSomePsukimHtml
				if (!checkBox_TooltipOption.isSelected())
					break;
				JLabel l = new JLabel(packHtml(str));
				l.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				textPane.setSelectionStart(textPane.getText().length());
				textPane.setSelectionEnd(textPane.getText().length());
				if (tooltipText.length > 0) {
					l.setToolTipText(packHtmlAlign(ColorClass.tooltipStyleHTML.getHtml(0) + tooltipText[0]
							+ ColorClass.tooltipStyleHTML.getHtml(1)));
				}
				textPane.insertComponent(l);
				break;
			}
			// scrollPane.getHorizontalScrollBar().setValue(0);
			// scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e + " in Code of frame.appendText()");
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println(e + " in Code of frame.appendText()");
		}

	}

	public static void clearText() {
		textPane.setText("");
	}

	private static void setFonts() {
		// frame.getContentPane().setFont(new Font("Miriam Mono CLM", Font.PLAIN,
		// fontSize));
		// frame.setFont(new Font("Miriam Mono CLM", Font.PLAIN, fontSize));
		setFontSizeBig(getFontSize() + 2);
		fontSizeSmall = getFontSize() - 2;
		fontSizeSmaller = getFontSize() - 4;
		comboBox_main.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		label_textfield_Search.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		textField_Search.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		label_dilugMin.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		textField_dilugMin.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		label_dilugMax.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		textField_dilugMax.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		textPane.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		button_search.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		comboBox_sub.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_wholeWord.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_gimatriaSofiot.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_countPsukim.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_searchMultiple.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_letterOrder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		label_padding.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		textField_padding.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		progressBar.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		label_dProgress.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		label_countMatch.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmaller));
		menuSettings.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_bgColor.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_textColor.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_textSize.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuNoTevotFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuDifferentFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuLinesFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuTorahTable.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuFiles.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuResetExcelFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuExcelFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkBox_TooltipOption.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkBox_DifferentSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createDocument.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createExcel.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createTree.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		button_defaultSettings.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmall));
		button_searchRange.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmall));
		checkBox_searchRange.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmaller));
		int temp = (int) (95 * ((float) fontSizeSmall / fontSizeSmall_hardCoded));
		button_defaultSettings.setPreferredSize(new Dimension(temp, 25));
		temp = (int) (200 * ((float) getFontSizeBig() / fontSizeBig_hardCoded));
		comboBox_main.setMaximumSize(new Dimension(temp, 32767));
		temp = (int) (46 * ((float) fontSizeSmaller / fontSizeSmaller_hardCoded));
		checkBox_searchRange.setPreferredSize(new Dimension(140, temp));
		panel.setPreferredSize(new Dimension((int) (120 + 200 * ((float) getFontSize() / 16)), 10));
		// textHtmlSize = 5;
		textHtmlSize = (int) (5 * ((float) getFontSize() / 16));
		ColorClass.attentionHTML = new stringFormatting.HtmlGenerator(textHtmlSize, ColorClass.color_attentionHTML[0],
				ColorClass.color_attentionHTML[1], ColorClass.color_attentionHTML[2], 0b1101);
		ColorClass.mainStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize, ColorClass.color_mainStyleHTML[0],
				ColorClass.color_mainStyleHTML[1], ColorClass.color_mainStyleHTML[2], 0b1101);
		ColorClass.tooltipStyleHTML = new HtmlGenerator(textHtmlSize, ColorClass.color_tooltipStyleHTML_hardCoded[0],
				ColorClass.color_tooltipStyleHTML_hardCoded[1], ColorClass.color_tooltipStyleHTML_hardCoded[2], 0b100);
		ColorClass.markupStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1,
				ColorClass.color_markupStyleHTML[0], ColorClass.color_markupStyleHTML[1],
				ColorClass.color_markupStyleHTML[2], 0b100);
		for (int i = 0; i < ColorClass.highlightStyleHTML.length; i++) {
			ColorClass.highlightStyleHTML[i] = new stringFormatting.HtmlGenerator(textHtmlSize,
					ColorClass.color_highlightStyleHTML[i][0], ColorClass.color_highlightStyleHTML[i][1],
					ColorClass.color_highlightStyleHTML[i][2], 0b100);
		}
		ColorClass.headerStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1,
				ColorClass.color_headerStyleHTML[0], ColorClass.color_headerStyleHTML[1],
				ColorClass.color_headerStyleHTML[2], 0b100);
		ColorClass.footerStyleHTML = new stringFormatting.HtmlGenerator(0, ColorClass.color_footerStyleHTML[0],
				ColorClass.color_footerStyleHTML[1], ColorClass.color_footerStyleHTML[2], 0b100);

//		thisFrame.frame.setMinimumSize(new Dimension(550, 520));
		if (frame_instance != null) {
			frame_instance.frame.setMinimumSize(new Dimension((int) (300 + 250 * ((float) getFontSize() / 16)),
					(int) (300 + 220 * ((float) getFontSize() / 16))));
			// frame_instance.frame.getSize();

			// frame_instance.frame.repaint();
			// frame_instance.frame.revalidate();
		}
	}

	private static void setBGColorPanel(Color c) {
		customBGColor = c;
		panel.setBackground(c);
		panelGroup.setBackground(c);
		for (JPanel thisPanel : subPanels) {
			thisPanel.setBackground(c);
		}
		menuBar.setBackground(c);
		subPanelProgressLabels.setBackground(c);
		comboBox_sub.setBackground(c);
		checkBox_wholeWord.setBackground(c);
		checkBox_gimatriaSofiot.setBackground(c);
		checkBox_countPsukim.setBackground(c);
		checkBox_searchRange.setBackground(c);
		checkBox_searchMultiple.setBackground(c);
		checkBox_letterOrder.setBackground(c);
		// frame_instance.frame.repaint();
		// frame_instance.frame.revalidate();
	}

	private static void setBGColorMenu(Color c, Color c2, Color c3) {
		// menu
		UIManager.put("ToolTip.background", c);
		checkBox_TooltipOption.setBackground(c3);
		checkBox_DifferentSearch.setBackground(c);
		menuItem_bgColor.setBackground(c3);
		menuItem_textColor.setBackground(c);
		menuItem_textSize.setBackground(c3);
		checkbox_createDocument.setBackground(c);
		checkbox_createExcel.setBackground(c3);
		checkbox_createTree.setBackground(c);
		menuFiles.setBackground(c3);
		// submenu
		menuDifferentFile.setBackground(c2);
		menuExcelFolder.setBackground(c);
		menuResetExcelFolder.setBackground(c2);
		menuTorahTable.setBackground(c);
		menuLinesFile.setBackground(c2);
		menuNoTevotFile.setBackground(c);
		menuItem_textColorMain.setBackground(c2);
		menuItem_textColorMarkup.setBackground(c);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void changeLayout(String str) {
		DefaultComboBoxModel model;
		if (str != combo_strTorahRangeReport) {
			if (!checkbox_createDocument.isSelected()) {
				noDocumentMessage();
			}
		}
		setButtonText();
		switch (str) {
		case combo_strSearch:
		case combo_strCountSearch:
		case combo_strGimatriaSearch:
		case combo_strGimatriaCalculate:
		case combo_strLetterSearch:
			subPanels.get(id_panel_search).setVisible(true);
			subPanels.get(id_panel_dilug).setVisible(false);
			subPanels.get(id_panel_gimatriaSofiot).setVisible(true);
			subPanels.get(id_panel_searchRange).setVisible(true);
			switch (str) {
			case combo_strSearch:
				subPanels.get(id_panel_padding).setVisible(checkBox_searchMultiple.isSelected());
				subPanels.get(id_panel_combosub).setVisible(checkBox_searchMultiple.isSelected());
				subPanels.get(id_panel_countPsukim).setVisible(true);
				subPanels.get(id_panel_wholeWord).setVisible(true);
				subPanels.get(id_panel_searchMulti).setVisible(true);
				subPanels.get(id_panel_letterOrder).setVisible(false);
				checkBox_wholeWord.setText(checkBox_wholeWord_Regular);
				checkBox_searchMultiple.setText(checkBox_searchMultiple_String);
				if (checkBox_searchMultiple.isSelected()) {
					model = new DefaultComboBoxModel(comboBox_sub_Strings_Search_Multi);
					comboBox_sub.setModel(model);
				}
				if (checkBox_searchMultiple.isSelected()) {
					label_padding.setText(strLabel_padding_Search);
					textField_padding.setText(paddingSearchMulti);
				}
				checkBox_searchMultiple.setSelected(bool_searchMultiple);
				break;
			case combo_strCountSearch:
				subPanels.get(id_panel_padding).setVisible(true);
				subPanels.get(id_panel_combosub).setVisible(false);
				subPanels.get(id_panel_countPsukim).setVisible(true);
				subPanels.get(id_panel_wholeWord).setVisible(true);
				subPanels.get(id_panel_searchMulti).setVisible(false);
				subPanels.get(id_panel_letterOrder).setVisible(false);
				checkBox_wholeWord.setText(checkBox_wholeWord_Regular);
				label_padding.setText(strLabel_padding_CountSearch);
				textField_padding.setText(String.valueOf(paddingSearchIndex));
				break;
			case combo_strGimatriaSearch:
				subPanels.get(id_panel_padding).setVisible(false);
				subPanels.get(id_panel_combosub).setVisible(false);
				subPanels.get(id_panel_countPsukim).setVisible(false);
				subPanels.get(id_panel_wholeWord).setVisible(true);
				subPanels.get(id_panel_letterOrder).setVisible(false);
				subPanels.get(id_panel_searchMulti).setVisible(false);
				checkBox_wholeWord.setText(checkBox_wholeWord_Regular);
				break;
			case combo_strLetterSearch:
				subPanels.get(id_panel_padding).setVisible(false);
				subPanels.get(id_panel_combosub).setVisible(true);
				subPanels.get(id_panel_countPsukim).setVisible(false);
				subPanels.get(id_panel_wholeWord).setVisible(true);
				subPanels.get(id_panel_letterOrder).setVisible(true);
				subPanels.get(id_panel_searchMulti).setVisible(false);
				checkBox_wholeWord.setText(checkBox_wholeWord_Letters);
				model = new DefaultComboBoxModel(comboBox_sub_Strings_Letters);
				comboBox_sub.setModel(model);
				break;
			case combo_strGimatriaCalculate:
				subPanels.get(id_panel_padding).setVisible(false);
				subPanels.get(id_panel_letterOrder).setVisible(false);
				subPanels.get(id_panel_combosub).setVisible(false);
				subPanels.get(id_panel_countPsukim).setVisible(false);
				subPanels.get(id_panel_wholeWord).setVisible(false);
				subPanels.get(id_panel_searchMulti).setVisible(false);
				break;
			}
			break;
		case combo_strDilugim:
			subPanels.get(id_panel_search).setVisible(true);
			subPanels.get(id_panel_dilug).setVisible(true);
			subPanels.get(id_panel_gimatriaSofiot).setVisible(true);
			subPanels.get(id_panel_padding).setVisible(true);
			subPanels.get(id_panel_combosub).setVisible(true);
			subPanels.get(id_panel_letterOrder).setVisible(false);
			subPanels.get(id_panel_countPsukim).setVisible(false);
			subPanels.get(id_panel_wholeWord).setVisible(false);
			subPanels.get(id_panel_searchMulti).setVisible(true);
			subPanels.get(id_panel_searchRange).setVisible(true);
			label_padding.setText(strLabel_padding_Dilug);
			checkBox_searchMultiple.setText(checkBox_searchMultiple_ReverseDilug);
			textField_padding.setText(String.valueOf(paddingDilug));
			model = new DefaultComboBoxModel(comboBox_sub_Strings_Dilugim);
			comboBox_sub.setModel(model);
			checkBox_searchMultiple.setSelected(bool_dilugReversed);
			break;
		case combo_strTorahRangeReport:
			subPanels.get(id_panel_search).setVisible(false);
			subPanels.get(id_panel_dilug).setVisible(false);
			subPanels.get(id_panel_gimatriaSofiot).setVisible(false);
			subPanels.get(id_panel_padding).setVisible(false);
			subPanels.get(id_panel_combosub).setVisible(false);
			subPanels.get(id_panel_letterOrder).setVisible(false);
			subPanels.get(id_panel_countPsukim).setVisible(false);
			subPanels.get(id_panel_wholeWord).setVisible(false);
			subPanels.get(id_panel_searchMulti).setVisible(false);
			subPanels.get(id_panel_searchRange).setVisible(false);
			break;
		case combo_strTorahPrint:
			subPanels.get(id_panel_search).setVisible(false);
			subPanels.get(id_panel_dilug).setVisible(false);
			subPanels.get(id_panel_gimatriaSofiot).setVisible(false);
			subPanels.get(id_panel_padding).setVisible(false);
			subPanels.get(id_panel_combosub).setVisible(false);
			subPanels.get(id_panel_letterOrder).setVisible(false);
			subPanels.get(id_panel_countPsukim).setVisible(false);
			subPanels.get(id_panel_wholeWord).setVisible(false);
			subPanels.get(id_panel_searchMulti).setVisible(true);
			subPanels.get(id_panel_searchRange).setVisible(true);
			checkBox_searchMultiple.setText(checkBox_searchMultiple_placeInfo);
			checkBox_searchMultiple.setSelected(bool_placeInfo);
			break;
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = Frame.getInstance();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 * @throws BadLocationException
	 */
	public Frame() throws IOException, BadLocationException {
		initialize();
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				panelWidth = (int) (scrollPane.getWidth() / 10);
				// do stuff
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 * @throws BadLocationException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private void initialize() throws IOException, BadLocationException {
		ToraApp.setGuiMode(ToraApp.id_guiMode_Frame);
		frame = new JFrame();
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		frame.setTitle("חיפוש בתורה");
		frame.getContentPane().setFont(new Font("Miriam Mono CLM", Font.PLAIN, getFontSize()));
		frame.setFont(new Font("Miriam Mono CLM", Font.PLAIN, getFontSize()));
		frame.setBounds(100, 100, 1600, 887);
		frame.setMinimumSize(new Dimension(550, 520));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		comboBox_main = new JComboBox();
		// menuPanel.add(comboBox_main);
		comboBox_main.setMaximumSize(new Dimension(200, 32767));
		comboBox_main.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		comboBox_main.setModel(new DefaultComboBoxModel(
				new String[] { combo_strSearch, combo_strGimatriaSearch, combo_strGimatriaCalculate, combo_strDilugim,
						combo_strLetterSearch, combo_strCountSearch, combo_strTorahPrint, combo_strTorahRangeReport }));
		comboBox_main.setBackground(ColorBG_comboBox_main);
		((JLabel) comboBox_main.getRenderer()).setHorizontalTextPosition(SwingConstants.RIGHT);
		((JLabel) comboBox_main.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);

		panel = new JPanel();
		GridLayout subPanelGrid1_1 = new GridLayout(1, 1, 1, 1);
		GridLayout subPanelGrid1_2 = new GridLayout(1, 2, 1, 1);
		GridLayout subPanelGrid2_1 = new GridLayout(2, 1, 1, 1);
		GridLayout subPanelGrid2_2 = new GridLayout(2, 2, 1, 1);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(300, 10));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		frame.getContentPane().add(panel, BorderLayout.EAST);
		int countPanels = 0;

		subPanels.add(new JPanel(subPanelGrid1_2) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		label_textfield_Search = new JLabel("חיפוש: ");
		label_textfield_Search.setHorizontalAlignment(SwingConstants.RIGHT);
		subPanels.get(countPanels).add(label_textfield_Search);

		// subPanels.get(countPanels).add(Box.createHorizontalGlue());
		textField_Search = new JTextField();
		textField_Search.setMinimumSize(new Dimension(150, 25));
		textField_Search.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_Search.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(textField_Search);
		textField_Search.setColumns(10);

		subPanels.add(new JPanel(subPanelGrid2_2) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight * 2);
			};
		});
		label_dilugMin = new JLabel("דילוג מינימום");
		label_dilugMin.setHorizontalAlignment(SwingConstants.RIGHT);
		subPanels.get(countPanels).add(label_dilugMin);
		// subPanels.get(countPanels).add(Box.createHorizontalGlue());

		textField_dilugMin = new JTextField();
		textField_dilugMin.setMinimumSize(new Dimension(150, 25));
		textField_dilugMin.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_dilugMin.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels).add(textField_dilugMin);
		textField_dilugMin.setColumns(10);

		label_dilugMax = new JLabel("דילוג מקסימום");
		label_dilugMax.setHorizontalAlignment(SwingConstants.RIGHT);
		subPanels.get(countPanels).add(label_dilugMax);
		// subPanels.get(countPanels).add(Box.createHorizontalGlue());

		textField_dilugMax = new JTextField();
		textField_dilugMax.setMinimumSize(new Dimension(150, 25));
		textField_dilugMax.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_dilugMax.setColumns(10);
		textField_dilugMax.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(textField_dilugMax);

		label_padding = new JLabel(strLabel_padding_Dilug);
		label_padding.setHorizontalAlignment(SwingConstants.RIGHT);
		subPanels.add(new JPanel(subPanelGrid1_2) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels).add(label_padding);
		// subPanels.get(countPanels).add(Box.createHorizontalGlue());
		textField_padding = new JTextField();
		textField_padding.setText((String) null);
		textField_padding.setMinimumSize(new Dimension(150, 25));
		textField_padding.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_padding.setColumns(10);
		textField_padding.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(textField_padding);

		popupMenu = new JPopupMenu();
		popupMenu.setLabel("");
		// Then on your component(s)

		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane = new JScrollPane(textPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(7);
		panelWidth = scrollPane.getWidth();
		textPane.setBackground(ColorBG_textPane);
		textPane.addMouseListener(new PopClickTextPaneListener());
		// Better support for right to left languages
		textPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		textPane.setEditorKit(kit);
		textPane.setDocument(doc);
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
		tabbedPane.addTab("דוח", scrollPane);
		// add JTree here
		tree = Tree.getInstance();
		tree.setBackground(ColorBG_textPane);
		JScrollPane scrollTree = new JScrollPane(tree);
		scrollTree.getVerticalScrollBar().setUnitIncrement(7);
		tree.addMouseListener(new PopClickTreeListener());
		tabbedPane.addTab("עץ", scrollTree);

		// table = Table.getInstance(false);
		// table.setBackground(ColorBG_textPane);
		// JScrollPane scrollTable = new JScrollPane(table);
		// tabbedPane.addTab("טבלה", scrollTable);

		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		button_search = new JButton("חפש");
		comboBox_sub = new JComboBox();
		comboBox_sub.setAlignmentX(Component.RIGHT_ALIGNMENT);
		comboBox_sub.setModel(new DefaultComboBoxModel(comboBox_sub_Strings_Letters));
		comboBox_sub.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		((JLabel) comboBox_sub.getRenderer()).setHorizontalTextPosition(SwingConstants.RIGHT);
		((JLabel) comboBox_sub.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);

		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(comboBox_sub);
		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		checkBox_letterOrder = new JCheckBox(checkBox_letterOrder_String);
		checkBox_letterOrder.setToolTipText(checkBox_letterOrder_Tooltip);
		checkBox_letterOrder.setSelected(false);
		checkBox_letterOrder.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_letterOrder.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(checkBox_letterOrder);

		checkBox_wholeWord = new JCheckBox(checkBox_wholeWord_Regular);
		checkBox_wholeWord.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_wholeWord.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(checkBox_wholeWord);
		checkBox_gimatriaSofiot = new JCheckBox(checkBox_gimatriaSofiot_text);
		checkBox_gimatriaSofiot.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_gimatriaSofiot.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(checkBox_gimatriaSofiot);

		checkBox_countPsukim = new JCheckBox(checkBox_countPsukim_true);
		checkBox_countPsukim.setSelected(true);
		checkBox_countPsukim.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_countPsukim.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(checkBox_countPsukim);
		checkBox_searchMultiple = new JCheckBox(checkBox_searchMultiple_String);
		checkBox_searchMultiple.setSelected(false);
		checkBox_searchMultiple.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_searchMultiple.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(checkBox_searchMultiple);
		// Change countPsukim checkbox text when changing selected state
		progressBar = new JProgressBar();
		progressBar.setMinimumSize(new Dimension(150, 30));
		progressBar.setVisible(false);

		button_searchRange = new JButton("טווח חיפוש");
		subPanels.add(new JPanel(subPanelGrid1_2) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), (int) (rowHeight * 1.8));
			};
		});
		subPanels.get(countPanels).add(button_searchRange);

		checkBox_searchRange = new JCheckBox("הכול");
		checkBox_searchRange.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBox_searchRange.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(checkBox_searchRange);

		subPanels.add(new JPanel(subPanelGrid1_2) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), (int) (rowHeight * 1.1));
			};
		});
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		subPanels.get(countPanels).add(progressBar);

		subPanelProgressLabels = new JPanel(subPanelGrid2_1);
		label_countMatch = new JLabel("dilug progress");
		label_countMatch.setVisible(false);
		label_dProgress = new JLabel("dilug progress");
		label_dProgress.setVisible(false);
		subPanelProgressLabels.add(label_dProgress);
		subPanelProgressLabels.add(label_countMatch);
		label_dProgress.setHorizontalAlignment(SwingConstants.CENTER);
		label_countMatch.setHorizontalAlignment(SwingConstants.CENTER);
		subPanels.get(countPanels++).add(subPanelProgressLabels);

		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(button_search);
		panelGroup = new JPanel();
		for (JPanel thisPanel : subPanels) {
			thisPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			panelGroup.add(thisPanel);
		}
		panel.add(panelGroup);
		// panelGroup.setBorder(new EmptyBorder(20, 20, 20, 20));
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuSettings = new JMenu("הגדרות");
		menuSettings.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuSettings.setHorizontalAlignment(SwingConstants.RIGHT);
		menuSettings.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuSettings.setBackground(ColorBG_textPane);
		menuSettings.setOpaque(true);
		menuBar.add(menuSettings);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(comboBox_main);
		checkBox_TooltipOption = new JCheckBoxMenuItem(
				"<html> <p align=\"right\">" + "הוספת טקסט נוסף" + "</p></html>");
		checkBox_TooltipOption.setSelected(true);
		checkBox_TooltipOption.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkBox_TooltipOption.setHorizontalTextPosition(SwingConstants.RIGHT);
		checkBox_TooltipOption.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBox_TooltipOption.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkBox_DifferentSearch = new JCheckBoxMenuItem(
				"<html> <p align=\"right\">" + "חיפוש בקובץ<br> של המשתמש" + "</p></html>");
		checkBox_DifferentSearch.setSelected(false);
		checkBox_DifferentSearch.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkBox_DifferentSearch.setHorizontalTextPosition(SwingConstants.RIGHT);
		checkBox_DifferentSearch.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBox_DifferentSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_bgColor = new JMenuItem("צבע רקע");
		menuItem_bgColor.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuItem_bgColor.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuItem_bgColor.setHorizontalAlignment(SwingConstants.RIGHT);
		menuItem_bgColor.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_textColor = new JMenu("צבע טקסט");
		menuItem_textColor.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuItem_textColor.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuItem_textColor.setHorizontalAlignment(SwingConstants.RIGHT);
		menuItem_textColor.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_textColorMain = new JMenuItem("טקסט דוח רגיל");
		menuItem_textColorMain.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuItem_textColorMain.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuItem_textColorMain.setHorizontalAlignment(SwingConstants.RIGHT);
		menuItem_textColorMain.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_textColorMarkup = new JMenuItem("צבע הארה");
		menuItem_textColorMarkup.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuItem_textColorMarkup.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuItem_textColorMarkup.setHorizontalAlignment(SwingConstants.RIGHT);
		menuItem_textColorMarkup.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_textSize = new JMenuItem("גודל טקסט");
		menuItem_textSize.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuItem_textSize.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuItem_textSize.setHorizontalAlignment(SwingConstants.RIGHT);
		menuItem_textSize.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuItem_textColor.add(menuItem_textColorMain);
		menuItem_textColor.add(menuItem_textColorMarkup);
		checkbox_createDocument = new JCheckBoxMenuItem("להכין דוח");
		checkbox_createDocument.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkbox_createDocument.setHorizontalTextPosition(SwingConstants.RIGHT);
		checkbox_createDocument.setHorizontalAlignment(SwingConstants.RIGHT);
		checkbox_createDocument.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createDocument.setSelected(true);
		checkbox_createExcel = new JCheckBoxMenuItem("להכין קובץ אקסל");
		checkbox_createExcel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkbox_createExcel.setHorizontalTextPosition(SwingConstants.RIGHT);
		checkbox_createExcel.setHorizontalAlignment(SwingConstants.RIGHT);
		checkbox_createExcel.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createExcel.setSelected(true);
		checkbox_createTree = new JCheckBoxMenuItem("להכין עץ");
		checkbox_createTree.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkbox_createTree.setHorizontalTextPosition(SwingConstants.RIGHT);
		checkbox_createTree.setHorizontalAlignment(SwingConstants.RIGHT);
		checkbox_createTree.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createTree.setSelected(true);
		menuSettings.add(checkBox_TooltipOption);
		menuSettings.add(checkBox_DifferentSearch);
		menuSettings.add(menuItem_bgColor);
		menuSettings.add(menuItem_textColor);
		menuSettings.add(menuItem_textSize);
		menuSettings.add(checkbox_createDocument);
		menuSettings.add(checkbox_createExcel);
		menuSettings.add(checkbox_createTree);

		menuFiles = new JMenu("קבצים");
		menuFiles.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuFiles.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuFiles.setHorizontalAlignment(SwingConstants.RIGHT);
		menuFiles.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuDifferentFile = new JMenuItem("קובץ אחר לחיפוש");
		menuDifferentFile.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuDifferentFile.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuDifferentFile.setHorizontalAlignment(SwingConstants.RIGHT);
		menuDifferentFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuExcelFolder = new JMenuItem("שינוי תיקיה לדוחות אקסל");
		menuExcelFolder.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuExcelFolder.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuExcelFolder.setHorizontalAlignment(SwingConstants.RIGHT);
		menuExcelFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuResetExcelFolder = new JMenuItem("החזרת תיקיה מקורית לדוחות אקסל");
		menuResetExcelFolder.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuResetExcelFolder.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuResetExcelFolder.setHorizontalAlignment(SwingConstants.RIGHT);
		menuResetExcelFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuTorahTable = new JMenuItem("טבלת אינדקס - TorahTables.xls");
		menuTorahTable.setToolTipText(Output.markText(
				"קובץ גיבוי במקרה והתוכנה לא מוצאת את הקובץ בעצמה"
						+ " <br> https://github.com/Shem-Tov/Torah-Search/blob/master/TorahTables.xls",
				ColorClass.headerStyleHTML, true));
		menuTorahTable.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuTorahTable.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuTorahTable.setHorizontalAlignment(SwingConstants.RIGHT);
		menuTorahTable.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuLinesFile = new JMenuItem("קובץ תורה מחולק בשורות - Lines.txt");
		menuLinesFile.setToolTipText(Output.markText("קובץ גיבוי במקרה והתוכנה <br> לא מוצאת את הקובץ בעצמה",
				ColorClass.headerStyleHTML, true));
		menuLinesFile.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuLinesFile.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuLinesFile.setHorizontalAlignment(SwingConstants.RIGHT);
		menuLinesFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuNoTevotFile = new JMenuItem("קובץ תורה אותיות - NoTevot.txt");
		menuNoTevotFile.setToolTipText(Output.markText("קובץ גיבוי במקרה והתוכנה <br> לא מוצאת את הקובץ בעצמה",
				ColorClass.headerStyleHTML, true));
		menuNoTevotFile.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuNoTevotFile.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuNoTevotFile.setHorizontalAlignment(SwingConstants.RIGHT);
		menuNoTevotFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuFiles.add(menuDifferentFile);
		menuFiles.add(menuExcelFolder);
		menuFiles.add(menuResetExcelFolder);
		menuFiles.add(menuTorahTable);
		menuFiles.add(menuLinesFile);
		menuFiles.add(menuNoTevotFile);
		button_defaultSettings = new JButton("קבע ברירת מחדל");
		button_defaultSettings.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// bad - choice =
		// button_defaultSettings.setAlignmentX(Component.RIGHT_ALIGNMENT);
		button_defaultSettings.setMargin(new Insets(2, 2, 2, 2));
		button_defaultSettings.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmall));
		button_defaultSettings.setHorizontalAlignment(SwingConstants.RIGHT);
		menuSettings.add(menuFiles);
		menuSettings.add(button_defaultSettings);
		menuItem_textColor.setOpaque(true);
		menuFiles.setOpaque(true);
		button_defaultSettings
				.setToolTipText(Output.markText("שמירת ערכי חיפוש לברירת מחדל", ColorClass.headerStyleHTML, true));
		// Change countPsukim checkbox text when changing selected state
		checkBox_countPsukim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBox cb = (JCheckBox) event.getSource();
				cb.setText(((cb.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
			}
		});
		// Add second textbox for multiple Search
		checkBox_searchMultiple.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (getComboBox_main() == combo_strSearch) {
					JCheckBox cb = (JCheckBox) event.getSource();
					subPanels.get(id_panel_padding).setVisible(cb.isSelected());
					subPanels.get(id_panel_combosub).setVisible(cb.isSelected());
					if (checkBox_searchMultiple.isSelected()) {
						label_padding.setText(strLabel_padding_Search);
						DefaultComboBoxModel model;
						model = new DefaultComboBoxModel(comboBox_sub_Strings_Search_Multi);
						comboBox_sub.setModel(model);
					}
					// frame_instance.frame.repaint();
					// frame_instance.frame.revalidate();
				}
			}
		});

		// Button to save settings
		button_defaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveValues();
			}
		});
		// Button to start search
		button_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// button.setEnabled(false);
				if (!methodRunning) {
					switch (comboBox_main.getSelectedItem().toString()) {
					case combo_strDilugim:
						if (StringUtils.isNumeric(textField_padding.getText())) {
							paddingDilug = Integer.parseInt(textField_padding.getText());
						}
						break;
					case combo_strCountSearch:
						if (StringUtils.isNumeric(textField_padding.getText())) {
							paddingSearchIndex = Integer.parseInt(textField_padding.getText());
						}
						break;
					}
					methodRunning = true;
					button_search.setText(buttonCancelText);
					textPane.setText("");
					activity = SwingActivity.getInstance();
					activity.execute();
				} else {
					methodCancelRequest = true;
					button_search.setText(buttonCancelRequestText);
				}
			}
		});
		// Change frame layout depending on comboBox choice
		comboBox_main.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();
				Object selected = comboBox.getSelectedItem();
				changeLayout(selected.toString());
			}
		});
		// Change frame layout depending on comboBox choice
		menuItem_bgColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * JColorChooser chooser = new JColorChooser(); AbstractColorChooserPanel[]
				 * panels = chooser.getChooserPanels(); chooser.setChooserPanels(new
				 * AbstractColorChooserPanel[] { panels[1] }); Color c = chooser.getColor();
				 */
				Color c = JColorChooser.showDialog(null, "בחר צבע", ColorBG_Panel);
				setBGColorPanel(c);
			}
		});
		menuResetExcelFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CustomDialog cDialog = new CustomDialog();
				cDialog.setTitle("שחזור הגדרות תיקיה");
				JLabel label = new JLabel(
						"האם להחזיר חזרה את התיקיה ל " + ExcelFunctions.getExcel_File_Location_Hardcoded());
				cDialog.addComponent(label);
				Object selection = cDialog.show();
				// System.out.println(selection);
				// null is canceled
				if (selection != null) {
					ExcelFunctions.resetExcel_File_Location();
					PropStore.map.put(PropStore.excelFolder, ExcelFunctions.getExcel_File_Location());
					PropStore.store();
					// Saving code here
				}
			}
		});

		menuExcelFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				int returnVal = chooser.showOpenDialog((Component) event.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// System.out.println("You chose this folder: " +
					// chooser.getSelectedFile().getAbsolutePath());
					System.out.println("You chose this folder: " + chooser.getSelectedFile().getAbsolutePath());
					ExcelFunctions.setExcel_File_Location(chooser.getSelectedFile().getAbsolutePath() + "/");
					// chooser.getSelectedFile().getAbsolutePath();
					PropStore.map.put(PropStore.excelFolder, ExcelFunctions.getExcel_File_Location());
					PropStore.store();
				}
			}
		});
		menuTorahTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				chooser.setDialogTitle("בחר קובץ גיבוי טבלת פסוקים (TorahTables.xls)");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS files", "xls");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog((Component) event.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					ToraApp.subTorahTableFile = chooser.getSelectedFile().getAbsolutePath();
					// There are identical calls like this, one here the another in
					// ToraApp.starter()
					ToraApp.tablePerekBooks = ExcelFunctions
							.readBookTableXLS(new String[] { ToraApp.subTorahTableFile }, 0, 0, 1, 6, 53);
					PropStore.map.put(PropStore.subTorahTablesFile, ToraApp.subTorahTableFile);
					PropStore.store();
				}
			}
		});
		menuLinesFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				chooser.setDialogTitle("בחר קובץ גיבוי תורה (שורות ורווחים בין מילים)");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog((Component) event.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					ToraApp.subTorahLineFile = chooser.getSelectedFile().getAbsolutePath();
					// There are identical calls like this, one here the another in
					// ToraApp.starter()
					PropStore.map.put(PropStore.subTorahLineFile, ToraApp.subTorahLineFile);
					PropStore.store();
				}
			}
		});
		menuNoTevotFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				chooser.setDialogTitle("בחר קובץ גיבוי תורה (ללא רווחים או שורות)");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog((Component) event.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					ToraApp.subTorahLetterFile = chooser.getSelectedFile().getAbsolutePath();
					// There are identical calls like this, one here the another in
					// ToraApp.starter()
					PropStore.map.put(PropStore.subTorahLettersFile, ToraApp.subTorahLetterFile);
					PropStore.store();
				}
			}
		});
		menuDifferentFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setDialogTitle("בחר קובץ לחיפוש");
				chooser.setCurrentDirectory(workingDirectory);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog((Component) event.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					ToraApp.differentSearchFile = chooser.getSelectedFile().getAbsolutePath();
					checkBox_DifferentSearch.setToolTipText(
							Output.markText(ToraApp.differentSearchFile, ColorClass.headerStyleHTML, true));
					// There are identical calls like this, one here the another in
					// ToraApp.starter()
					PropStore.map.put(PropStore.differentSearchFile, ToraApp.differentSearchFile);
					PropStore.store();
				}
			}
		});
		menuItem_textColorMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, "בחר צבע",
						new Color(ColorClass.color_mainStyleHTML_hardCoded[0],
								ColorClass.color_mainStyleHTML_hardCoded[1],
								ColorClass.color_mainStyleHTML_hardCoded[2]));
				if (c != null) {
					ColorClass.color_mainStyleHTML[0] = c.getRed();
					ColorClass.color_mainStyleHTML[1] = c.getGreen();
					ColorClass.color_mainStyleHTML[2] = c.getBlue();
					ColorClass.mainStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1,
							ColorClass.color_mainStyleHTML[0], ColorClass.color_mainStyleHTML[1],
							ColorClass.color_mainStyleHTML[2], 0b100);
				}
			}
		});
		menuItem_textColorMarkup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, "בחר צבע",
						new Color(ColorClass.color_markupStyleHTML_hardCoded[0],
								ColorClass.color_markupStyleHTML_hardCoded[1],
								ColorClass.color_markupStyleHTML_hardCoded[2]));
				if (c != null) {
					ColorClass.color_markupStyleHTML[0] = c.getRed();
					ColorClass.color_markupStyleHTML[1] = c.getGreen();
					ColorClass.color_markupStyleHTML[2] = c.getBlue();
					ColorClass.markupStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1,
							ColorClass.color_markupStyleHTML[0], ColorClass.color_markupStyleHTML[1],
							ColorClass.color_markupStyleHTML[2], 0b100);
				}
			}
		});

		menuItem_textSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CustomDialog cDialog = new CustomDialog();
				cDialog.setTitle("שינוי גודל פונט");
				JComboBox comboBox_fontSize = new JComboBox();
				String[] textSizes = new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
						"19", "20", "21", "22", "23", "24", "25", "26", "27", "28" };
				comboBox_fontSize.setModel(new DefaultComboBoxModel(textSizes));
				// find the index of fontSize in textSizes
				int index = 0;
				for (int i = 0; i < textSizes.length; i++) {
					if (textSizes[i].equals(String.valueOf(getFontSize()))) {
						index = i;
						break;
					}
				}
				cDialog.addComponent(comboBox_fontSize, true, index);
				Object obj = cDialog.show();
				if (obj != null) {
					setFontSize(Integer.valueOf((String) obj));
					setFonts();
				}
				/*
				 * Point p = frame.getLocation(); dFrame.setLocation((int) (p.getX() +
				 * frame.getWidth() - dFrame.getWidth()), (int) (p.getY() + frame.getHeight() -
				 * dFrame.getHeight())); dFrame.setVisible(true);
				 */
			}
		});
		checkbox_createTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tree.setVisible(checkbox_createTree.isSelected());
			}
		});
		// Open Dialog for Search Range
		button_searchRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogSearchRangeFrame dFrame = DialogSearchRangeFrame.getInstance(true);
				dFrame.setVisible(true);
			}
		});
		checkBox_searchRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (checkBox_searchRange.isSelected()) {
					checkBox_searchRange.setText(searchRangeStringHTML);
				} else {
					checkBox_searchRange.setText(searchRangeAll);
				}
			}
		});
		// Setup Method Array
		// and Create Table for Torah Lookup
		ToraApp.starter();
		// Retrieve saved values for frame components
		initValues();
		setFonts();
		setBGColorPanel(customBGColor);
		setBGColorMenu(ColorBG_menu, ColorBG_menu2, ColorBG_menu3);
		changeLayout(comboBox_main.getSelectedItem().toString());
	}

	public static int getFontSize() {
		return fontSize;
	}

	public static void setFontSize(int fontSize) {
		Frame.fontSize = fontSize;
	}

	public static int getFontSizeBig() {
		return fontSizeBig;
	}

	public static void setFontSizeBig(int fontSizeBig) {
		Frame.fontSizeBig = fontSizeBig;
	}

	public static Boolean getCheckbox_createExcel() {
		return checkbox_createExcel.isSelected();
	}

	public static Boolean getCheckbox_createTree() {
		return checkbox_createTree.isSelected();
	}

	public static Boolean getCheckbox_createDocument() {
		return checkbox_createDocument.isSelected();
	}

	public static Boolean getCheckbox_searchMultiple() {
		return checkBox_searchMultiple.isSelected();
	}

	public static Boolean getCheckbox_letterOrder() {
		return checkBox_letterOrder.isSelected();
	}

	public static void setTextPaneVisible(Boolean bool) {
		textPane.setVisible(bool);
	}

	public static Boolean getCheckBox_DifferentSearch() {
		try {
			return checkBox_DifferentSearch.isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	static void noDocumentMessage() {
		clearTextPane();
		Output.printText("התוכנה כרגע אינה מכינה דוחות", 1);
		Output.printText("בכדי להפעיל אפשרות זו צריך לשנות את ההגדרה", 1);
		Output.printText("בהגדרות -> להכין דוח", 1);
	}

	public static Boolean getCheckBox_Tooltip() {
		return checkBox_TooltipOption.isSelected();
	}

	static int getTextHtmlSize() {
		return textHtmlSize;
	}
	
	public static void setBool_placeInfo(Boolean bool) {
		bool_placeInfo = bool;
	}
	public static void setBool_searchMultiple(Boolean bool) {
		bool_searchMultiple = bool;
	}
	public static void setBool_reverseDilug(Boolean bool) {
		bool_dilugReversed = bool;
	}
}
