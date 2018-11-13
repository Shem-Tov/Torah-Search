package frame;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
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

import org.apache.commons.lang3.StringUtils;

import ioManagement.ExcelFunctions;
import ioManagement.PropStore;
import torahApp.ToraApp;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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

	public static final String[] comboBox_sub_Strings_Letters = new String[] { "מילים", "פסוקים" };
	public static final String[] comboBox_sub_Strings_Dilugim = new String[] { "אותיות", "מילים", "פסוקים" };
	static final String combo_strSearch = "חיפוש רגיל";
	static final String combo_strGimatriaSearch = "חיפוש גימטריה";
	static final String combo_strGimatriaCalculate = "חישוב גימטריה";
	static final String combo_strDilugim = "חיפוש בדילוגים";
	static final String combo_strLetterSearch = "חיפוש אותיות";
	static final String combo_strCountSearch = "חיפוש אינדקס";
	static final String combo_strTorahPrint = "הדפסת התורה";
	static final String strLabel_padding_Dilug = "מספר אותיות";
	static final String strLabel_padding_CountSearch = "מספר אינדקס";

	private static final int fontSize_hardCoded = 16;
	private static final int fontSizeBig_hardCoded = fontSize_hardCoded + 2;
	private static final int fontSizeSmall_hardCoded = fontSize_hardCoded - 2;
	private static final int fontSizeSmaller_hardCoded = fontSize_hardCoded - 3;

	private static int fontSize = fontSize_hardCoded;
	private static int fontSizeBig = getFontSize() + 2;
	private static int fontSizeSmall = getFontSize() - 2;
	private static int fontSizeSmaller = getFontSize() - 3;

	private static int textHtmlSize = 5;
	public static final int lineHeaderSize = 5;
	private static final int[] color_mainStyleHTML_hardCoded = new int[] { 128, 88, 255 };
	private static final int[] color_markupStyleHTML_hardCoded = new int[] { 245, 195, 92 };
	private static final int[] color_attentionHTML = new int[] { 250, 40, 40 };
	private static final int[] color_headerStyleHTML = new int[] { 58, 124, 240 };
	private static final int[] color_footerStyleHTML = new int[] { 255, 144, 180 };

	private static int[] color_mainStyleHTML = color_mainStyleHTML_hardCoded.clone();
	private static int[] color_markupStyleHTML = color_markupStyleHTML_hardCoded.clone();

	// searchRange, has two line numbers to search through in the Torah
	// if {0,0} then search through ALL
	private static int[] searchRange = new int[] { 0, 0 };
	private static final String searchRangeAll = "הכול";
	private static String searchRangeString = searchRangeAll;
	private static String searchRangeStringHTML = searchRangeAll;

	private static String paddingSearchMulti = "";
	private static int paddingSearchIndex = 1;
	private static int paddingDilug = 1;
	// JTextPane Formatting
	private static stringFormatting.HtmlGenerator attentionHTML = new stringFormatting.HtmlGenerator(textHtmlSize,
			color_attentionHTML[0], color_attentionHTML[1], color_attentionHTML[2], 0b111);
	public static stringFormatting.HtmlGenerator mainStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize,
			color_mainStyleHTML_hardCoded[0], color_mainStyleHTML_hardCoded[1], color_mainStyleHTML_hardCoded[2],
			0b111);
	// public static StringFormatting.HtmlGenerator markupStyleHTML = new
	// StringFormatting.HtmlGenerator(textHtmlSize+1, 93, 192, 179,0b100);
	public static stringFormatting.HtmlGenerator markupStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1,
			color_markupStyleHTML_hardCoded[0], color_markupStyleHTML_hardCoded[1], color_markupStyleHTML_hardCoded[2],
			0b100);

	public static stringFormatting.HtmlGenerator headerStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1,
			color_headerStyleHTML[0], color_headerStyleHTML[1], color_headerStyleHTML[2], 0b100);
	public static stringFormatting.HtmlGenerator footerStyleHTML = new stringFormatting.HtmlGenerator(0,
			color_footerStyleHTML[0], color_footerStyleHTML[1], color_footerStyleHTML[2], 0b100);

	private static Color ColorBG_comboBox_main = new Color(255, 240, 240);
	private static Color ColorBG_textPane = new Color(251, 255, 253);
	private static Color ColorBG_Panel = new Color(240, 240, 255);
	private static Color customBGColor = ColorBG_Panel;

	private static final String buttonRunText = "חפש";
	private static final String buttonCancelText = "בטל";
	private static final String buttonCancelRequestText = "מבטל..";
	private static final String checkBox_gimatriaSofiot_text = "<html>" + "<p align=\"right\">" + "חישוב מיוחד"
			+ "<br/>" + "לסופיות" + "</p> </html>";
	private static final String checkBox_countPsukim_true = "<html><p align='right'>" + "ספירת פסוקים" + "<br/>"
			+ "שנמצאו" + "</p></html>";
	private static final String checkBox_countPsukim_false = "ספירת מציאות";

	private static Boolean methodCancelRequest = false;
	private static Boolean methodRunning = false;

	private JFrame frame;
	private static JPanel panel;
	private static GridBagLayout gbl_panel;
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
	static JTextPane textPane;
	private static Tree tree;
	private static JScrollPane scrollPane;
	private static JTabbedPane tabbedPane;
	private static HTMLDocument doc = new HTMLDocument();
	private static HTMLEditorKit kit = new HTMLEditorKit();
	private SwingActivity activity;
	static public int panelWidth;
	private JPanel panel_1;
	private JPopupMenu popupMenu;
	private static JProgressBar progressBar;
	private static JComboBox<?> comboBox_sub;
	private static JMenuItem menuItem_bgColor;
	private static JMenu menuItem_textColor;
	private static JMenu menuFiles;
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

	class PopClickListener extends MouseAdapter {
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
		if (checkBox_searchRange.isSelected()) {
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
			button_search.setText(buttonRunText);
			methodCancelRequest = false;
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
			color_mainStyleHTML[0] = temp.getRed();
			color_mainStyleHTML[1] = temp.getGreen();
			color_mainStyleHTML[2] = temp.getBlue();
			mainStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize, color_mainStyleHTML[0],
					color_mainStyleHTML[1], color_mainStyleHTML[2], 0b111);
			// public static StringFormatting.HtmlGenerator markupStyleHTML = new
			// StringFormatting.HtmlGenerator(textHtmlSize+1, 93, 192, 179,0b100);
		} catch (Exception e) {
			color_mainStyleHTML = color_mainStyleHTML_hardCoded.clone();
		}

		try {
			Color temp = new Color(Integer.parseInt(PropStore.map.get(PropStore.markupHtmlColor)));
			color_markupStyleHTML[0] = temp.getRed();
			color_markupStyleHTML[1] = temp.getGreen();
			color_markupStyleHTML[2] = temp.getBlue();
			markupStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1, color_markupStyleHTML[0],
					color_markupStyleHTML[1], color_markupStyleHTML[2], 0b100);
		} catch (Exception e) {
			color_markupStyleHTML = color_markupStyleHTML_hardCoded.clone();
		}

		checkBox_gimatriaSofiot.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_gimatriaSofiot)));
		checkBox_wholeWord.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_wholeWord)));
		checkBox_countPsukim.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_countPsukim)));
		checkBox_countPsukim.setText(
				((checkBox_countPsukim.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
		checkbox_createDocument.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_createDocument)));
		checkbox_createExcel.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_createExcel)));
		checkbox_createTree.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_createTree)));
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
		PropStore.addNotNull(PropStore.mainHtmlColor, String
				.valueOf(new Color(color_mainStyleHTML[0], color_mainStyleHTML[1], color_mainStyleHTML[2]).getRGB()));
		PropStore.addNotNull(PropStore.markupHtmlColor, String.valueOf(
				new Color(color_markupStyleHTML[0], color_markupStyleHTML[1], color_markupStyleHTML[2]).getRGB()));
		PropStore.addNotNull(PropStore.fontSize, String.valueOf(getFontSize()));
		PropStore.addNotNull(PropStore.bool_gimatriaSofiot, String.valueOf(checkBox_gimatriaSofiot.isSelected()));
		PropStore.addNotNull(PropStore.bool_wholeWord, String.valueOf(checkBox_wholeWord.isSelected()));
		PropStore.addNotNull(PropStore.bool_countPsukim, String.valueOf(checkBox_countPsukim.isSelected()));
		PropStore.addNotNull(PropStore.bool_createDocument, String.valueOf(checkbox_createDocument.isSelected()));
		PropStore.addNotNull(PropStore.bool_createExcel, String.valueOf(checkbox_createExcel.isSelected()));
		PropStore.addNotNull(PropStore.bool_createTree, String.valueOf(checkbox_createTree.isSelected()));

		PropStore.store();
	}

	public static void appendText(String str) throws BadLocationException {
		appendText(str, (byte) 0);
	}

	public static void appendText(String str, byte mode) {
		try {
			// doc.insertString(0, "\n"+str, null );
			// mode 0 = regular style with Carriage Return
			// mode 1 = error style with Carriage Return
			// mode 2 = regular style without Carriage Return
			if (!checkbox_createDocument.isSelected()) {
				return;
			}
			switch (mode) {
			case 0:
				// kit.insertHTML(doc, doc.getLength(), "<b>hello", 0, 0, HTML.Tag.B);
				kit.insertHTML(doc, doc.getLength(), (mainStyleHTML.getHtml(0) + str + mainStyleHTML.getHtml(1)), 0, 0,
						null);
				break;
			case 1:
				kit.insertHTML(doc, doc.getLength(), (attentionHTML.getHtml(0) + str + attentionHTML.getHtml(1)), 0, 0,
						null);
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
		menuLinesFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuTorahTable.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuFiles.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuResetExcelFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuExcelFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
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
		gbl_panel.columnWidths = new int[] { (int) (120 + 24 * ((float) getFontSize() / 16)), 42, 0 };
		// textHtmlSize = 5;
		textHtmlSize = (int) (5 * ((float) getFontSize() / 16));
		attentionHTML = new stringFormatting.HtmlGenerator(textHtmlSize, color_attentionHTML[0], color_attentionHTML[1],
				color_attentionHTML[2], 0b111);
		mainStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize, color_mainStyleHTML[0], color_mainStyleHTML[1],
				color_mainStyleHTML[2], 0b111);
		markupStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1, color_markupStyleHTML[0],
				color_markupStyleHTML[1], color_markupStyleHTML[2], 0b100);

		headerStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1, color_headerStyleHTML[0],
				color_headerStyleHTML[1], color_headerStyleHTML[2], 0b100);
		footerStyleHTML = new stringFormatting.HtmlGenerator(0, color_footerStyleHTML[0], color_footerStyleHTML[1],
				color_footerStyleHTML[2], 0b100);

//		thisFrame.frame.setMinimumSize(new Dimension(550, 520));
		if (frame_instance != null) {
			frame_instance.frame.setMinimumSize(new Dimension((int) (300 + 250 * ((float) getFontSize() / 16)),
					(int) (300 + 220 * ((float) getFontSize() / 16))));
			// frame_instance.frame.getSize();

			// frame_instance.frame.repaint();
			// frame_instance.frame.revalidate();
		}
	}

	private static void setBGColor(Color c) {
		customBGColor = c;
		panel.setBackground(c);
		comboBox_sub.setBackground(c);
		checkBox_wholeWord.setBackground(c);
		checkBox_gimatriaSofiot.setBackground(c);
		checkBox_countPsukim.setBackground(c);
		checkBox_searchRange.setBackground(c);
		checkBox_searchMultiple.setBackground(c);
		// frame_instance.frame.repaint();
		// frame_instance.frame.revalidate();
	}

	private static void changeLayout(String str) {
		switch (str) {
		case combo_strSearch:
		case combo_strCountSearch:
		case combo_strGimatriaSearch:
		case combo_strGimatriaCalculate:
		case combo_strLetterSearch:
			label_dilugMax.setVisible(false);
			label_dilugMin.setVisible(false);
			textField_dilugMax.setVisible(false);
			textField_dilugMin.setVisible(false);
			switch (str) {
			case combo_strSearch:
				label_padding.setVisible(false);
				comboBox_sub.setVisible(false);
				checkBox_countPsukim.setVisible(true);
				checkBox_wholeWord.setVisible(true);
				checkBox_searchMultiple.setVisible(true);
				textField_padding.setVisible(checkBox_searchMultiple.isSelected());
				textField_padding.setText(paddingSearchMulti);
				break;
			case combo_strCountSearch:
				label_padding.setVisible(true);
				label_padding.setText(strLabel_padding_CountSearch);
				textField_padding.setText(String.valueOf(paddingSearchIndex));
				textField_padding.setVisible(true);
				comboBox_sub.setVisible(false);
				checkBox_countPsukim.setVisible(true);
				checkBox_wholeWord.setVisible(true);
				checkBox_searchMultiple.setVisible(false);
				break;
			case combo_strGimatriaSearch:
				label_padding.setVisible(false);
				textField_padding.setVisible(false);
				comboBox_sub.setVisible(false);
				checkBox_countPsukim.setVisible(false);
				checkBox_wholeWord.setVisible(true);
				checkBox_searchMultiple.setVisible(false);
				break;
			case combo_strLetterSearch:
				label_padding.setVisible(false);
				textField_padding.setVisible(false);
				comboBox_sub.setVisible(true);
				//DefaultComboBoxModel model = new DefaultComboBoxModel(comboBox_sub_Strings_Letters);
				//comboBox_sub.setModel( model );
				checkBox_countPsukim.setVisible(false);
				checkBox_wholeWord.setVisible(false);
				checkBox_searchMultiple.setVisible(false);
				break;
			case combo_strGimatriaCalculate:
				label_padding.setVisible(false);
				textField_padding.setVisible(false);
				comboBox_sub.setVisible(false);
				checkBox_countPsukim.setVisible(false);
				checkBox_wholeWord.setVisible(false);
				checkBox_searchMultiple.setVisible(false);
				break;
			}
			break;
		case combo_strDilugim:
			label_dilugMax.setVisible(true);
			label_dilugMin.setVisible(true);
			textField_dilugMax.setVisible(true);
			textField_dilugMin.setVisible(true);
			label_padding.setVisible(true);
			label_padding.setText(strLabel_padding_Dilug);
			textField_padding.setText(String.valueOf(paddingDilug));
			textField_padding.setVisible(true);
			comboBox_sub.setVisible(false);
			//DefaultComboBoxModel model = new DefaultComboBoxModel(comboBox_sub_Strings_Dilugim);
			//comboBox_sub.setModel( model );
			checkBox_countPsukim.setVisible(false);
			checkBox_wholeWord.setVisible(false);
			checkBox_searchMultiple.setVisible(false);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() throws IOException, BadLocationException {
		ToraApp.setGuiMode(ToraApp.id_guiMode_Frame);
		frame = new JFrame();
		frame.setTitle("חיפוש בתורה");
		frame.getContentPane().setFont(new Font("Miriam Mono CLM", Font.PLAIN, getFontSize()));
		frame.setFont(new Font("Miriam Mono CLM", Font.PLAIN, getFontSize()));
		frame.setBounds(100, 100, 1600, 887);
		frame.setMinimumSize(new Dimension(550, 520));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		JInternalFrame internalFrame = new JInternalFrame("חיפוש בתורה");
		frame.getContentPane().add(internalFrame, BorderLayout.NORTH);
		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		comboBox_main = new JComboBox();
		panel_1.add(comboBox_main);
		comboBox_main.setMaximumSize(new Dimension(200, 32767));
		comboBox_main.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		comboBox_main.setModel(new DefaultComboBoxModel(
				new String[] { combo_strSearch, combo_strGimatriaSearch, combo_strGimatriaCalculate, combo_strDilugim,
						combo_strLetterSearch, combo_strCountSearch, combo_strTorahPrint }));
		comboBox_main.setBackground(ColorBG_comboBox_main);
		((JLabel) comboBox_main.getRenderer()).setHorizontalTextPosition(SwingConstants.RIGHT);
		((JLabel) comboBox_main.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);

		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setPreferredSize(new Dimension(300, 10));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		frame.getContentPane().add(panel, BorderLayout.EAST);
		gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 124, 42, 0 };
		gbl_panel.rowHeights = new int[] { 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		label_textfield_Search = new JLabel("חיפוש: ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label_textfield_Search, gbc_label);

		textField_Search = new JTextField();
		textField_Search.setMinimumSize(new Dimension(150, 25));
		textField_Search.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_Search.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_textField_Search = new GridBagConstraints();
		gbc_textField_Search.anchor = GridBagConstraints.NORTHEAST;
		gbc_textField_Search.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Search.gridx = 1;
		gbc_textField_Search.gridy = 0;
		panel.add(textField_Search, gbc_textField_Search);
		textField_Search.setColumns(10);

		label_dilugMin = new JLabel("דילוג מינימום");
		GridBagConstraints gbc_label_dilugMin = new GridBagConstraints();
		gbc_label_dilugMin.anchor = GridBagConstraints.EAST;
		gbc_label_dilugMin.insets = new Insets(0, 0, 5, 5);
		gbc_label_dilugMin.gridx = 0;
		gbc_label_dilugMin.gridy = 1;
		panel.add(label_dilugMin, gbc_label_dilugMin);

		textField_dilugMin = new JTextField();
		textField_dilugMin.setMinimumSize(new Dimension(150, 25));
		textField_dilugMin.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_dilugMin.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_textField_dilugMin = new GridBagConstraints();
		gbc_textField_dilugMin.anchor = GridBagConstraints.EAST;
		gbc_textField_dilugMin.insets = new Insets(0, 0, 5, 0);
		gbc_textField_dilugMin.gridx = 1;
		gbc_textField_dilugMin.gridy = 1;
		panel.add(textField_dilugMin, gbc_textField_dilugMin);
		textField_dilugMin.setColumns(10);

		label_dilugMax = new JLabel("דילוג מקסימום");
		GridBagConstraints gbc_label_dilugMax = new GridBagConstraints();
		gbc_label_dilugMax.anchor = GridBagConstraints.EAST;
		gbc_label_dilugMax.insets = new Insets(0, 0, 5, 5);
		gbc_label_dilugMax.gridx = 0;
		gbc_label_dilugMax.gridy = 2;
		panel.add(label_dilugMax, gbc_label_dilugMax);

		textField_dilugMax = new JTextField();
		textField_dilugMax.setMinimumSize(new Dimension(150, 25));
		textField_dilugMax.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_dilugMax.setColumns(10);
		textField_dilugMax.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_textField_dilugMax = new GridBagConstraints();
		gbc_textField_dilugMax.anchor = GridBagConstraints.EAST;
		gbc_textField_dilugMax.insets = new Insets(0, 0, 5, 0);
		gbc_textField_dilugMax.gridx = 1;
		gbc_textField_dilugMax.gridy = 2;
		panel.add(textField_dilugMax, gbc_textField_dilugMax);
		GridBagConstraints gbc_button_search = new GridBagConstraints();
		gbc_button_search.insets = new Insets(0, 0, 5, 0);
		gbc_button_search.gridx = 1;
		gbc_button_search.gridy = 16;

		popupMenu = new JPopupMenu();
		popupMenu.setLabel("");
		// Then on your component(s)

		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane = new JScrollPane(textPane);
		panelWidth = scrollPane.getWidth();
		textPane.setBackground(ColorBG_textPane);
		textPane.addMouseListener(new PopClickListener());
		// Better support for right to left languages
		textPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GroupLayout groupLayout = new GroupLayout(internalFrame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 1598, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 38, Short.MAX_VALUE));
		internalFrame.getContentPane().setLayout(groupLayout);

		textPane.setEditorKit(kit);
		textPane.setDocument(doc);
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
		tabbedPane.addTab("דוח", scrollPane);
		// add JTree here
		tree = Tree.getInstance();
		tree.setBackground(ColorBG_textPane);
		tabbedPane.addTab("עץ", new JScrollPane(tree));
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		internalFrame.setVisible(true);

		button_search = new JButton("חפש");
		comboBox_sub = new JComboBox();
		comboBox_sub.setAlignmentX(Component.RIGHT_ALIGNMENT);
		comboBox_sub.setModel(new DefaultComboBoxModel(comboBox_sub_Strings_Letters));
		comboBox_sub.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		((JLabel) comboBox_sub.getRenderer()).setHorizontalTextPosition(SwingConstants.RIGHT);
		((JLabel) comboBox_sub.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		label_padding = new JLabel(strLabel_padding_Dilug);
		GridBagConstraints gbc_label_padding = new GridBagConstraints();
		gbc_label_padding.anchor = GridBagConstraints.EAST;
		gbc_label_padding.insets = new Insets(0, 0, 5, 5);
		gbc_label_padding.gridx = 0;
		gbc_label_padding.gridy = 3;
		panel.add(label_padding, gbc_label_padding);
		textField_padding = new JTextField();
		textField_padding.setText((String) null);
		textField_padding.setMinimumSize(new Dimension(150, 25));
		textField_padding.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_padding.setColumns(10);
		textField_padding.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_textField_padding = new GridBagConstraints();
		gbc_textField_padding.anchor = GridBagConstraints.EAST;
		gbc_textField_padding.insets = new Insets(0, 0, 5, 0);
		gbc_textField_padding.gridx = 1;
		gbc_textField_padding.gridy = 3;
		panel.add(textField_padding, gbc_textField_padding);
		GridBagConstraints gbc_comboBox_sub = new GridBagConstraints();
		gbc_comboBox_sub.anchor = GridBagConstraints.EAST;
		gbc_comboBox_sub.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_sub.gridx = 0;
		gbc_comboBox_sub.gridy = 4;
		panel.add(comboBox_sub, gbc_comboBox_sub);
		checkBox_wholeWord = new JCheckBox("מילים שלמות");
		checkBox_wholeWord.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_wholeWord.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		GridBagConstraints gbc_checkBox_wholeWord = new GridBagConstraints();
		gbc_checkBox_wholeWord.anchor = GridBagConstraints.EAST;
		gbc_checkBox_wholeWord.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_wholeWord.gridx = 0;
		gbc_checkBox_wholeWord.gridy = 5;
		panel.add(checkBox_wholeWord, gbc_checkBox_wholeWord);
		checkBox_gimatriaSofiot = new JCheckBox(checkBox_gimatriaSofiot_text);
		checkBox_gimatriaSofiot.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_gimatriaSofiot.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		GridBagConstraints gbc_checkBox_gimatriaSofiot = new GridBagConstraints();
		gbc_checkBox_gimatriaSofiot.anchor = GridBagConstraints.EAST;
		gbc_checkBox_gimatriaSofiot.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_gimatriaSofiot.gridx = 0;
		gbc_checkBox_gimatriaSofiot.gridy = 6;
		panel.add(checkBox_gimatriaSofiot, gbc_checkBox_gimatriaSofiot);

		checkBox_countPsukim = new JCheckBox(checkBox_countPsukim_true);
		checkBox_countPsukim.setSelected(true);
		checkBox_countPsukim.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_countPsukim.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_checkBox_countPsukim = new GridBagConstraints();
		gbc_checkBox_countPsukim.anchor = GridBagConstraints.EAST;
		gbc_checkBox_countPsukim.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_countPsukim.gridx = 0;
		gbc_checkBox_countPsukim.gridy = 7;
		panel.add(checkBox_countPsukim, gbc_checkBox_countPsukim);
		checkBox_searchMultiple = new JCheckBox("<html><p align='right'>" + "חיפוש יותר<br> ממילה אחת" + "</p></html>");
		checkBox_searchMultiple.setSelected(false);
		checkBox_searchMultiple.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_searchMultiple.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_checkBox_searchMultiple = new GridBagConstraints();
		gbc_checkBox_searchMultiple.anchor = GridBagConstraints.EAST;
		gbc_checkBox_searchMultiple.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_searchMultiple.gridx = 0;
		gbc_checkBox_searchMultiple.gridy = 8;
		panel.add(checkBox_searchMultiple, gbc_checkBox_searchMultiple);
		// Change countPsukim checkbox text when changing selected state
		progressBar = new JProgressBar();
		progressBar.setMinimumSize(new Dimension(150, 30));
		progressBar.setVisible(false);

		button_searchRange = new JButton("טווח חיפוש");
		GridBagConstraints gbc_button_searchRange = new GridBagConstraints();
		gbc_button_searchRange.anchor = GridBagConstraints.EAST;
		gbc_button_searchRange.insets = new Insets(0, 0, 5, 5);
		gbc_button_searchRange.gridx = 0;
		gbc_button_searchRange.gridy = 10;
		panel.add(button_searchRange, gbc_button_searchRange);

		checkBox_searchRange = new JCheckBox("הכול");
		checkBox_searchRange.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBox_searchRange.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_checkBox_searchRange = new GridBagConstraints();
		gbc_checkBox_searchRange.anchor = GridBagConstraints.EAST;
		gbc_checkBox_searchRange.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_searchRange.gridx = 1;
		gbc_checkBox_searchRange.gridy = 10;
		panel.add(checkBox_searchRange, gbc_checkBox_searchRange);
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridheight = 2;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 14;
		panel.add(progressBar, gbc_progressBar);

		label_countMatch = new JLabel("dilug progress");
		label_countMatch.setVisible(false);

		label_dProgress = new JLabel("dilug progress");
		label_dProgress.setVisible(false);
		GridBagConstraints gbc_label_dProgress = new GridBagConstraints();
		gbc_label_dProgress.anchor = GridBagConstraints.EAST;
		gbc_label_dProgress.insets = new Insets(0, 0, 5, 0);
		gbc_label_dProgress.gridx = 1;
		gbc_label_dProgress.gridy = 14;
		panel.add(label_dProgress, gbc_label_dProgress);
		GridBagConstraints gbc_label_dCountMatch = new GridBagConstraints();
		gbc_label_dCountMatch.anchor = GridBagConstraints.EAST;
		gbc_label_dCountMatch.insets = new Insets(0, 0, 5, 0);
		gbc_label_dCountMatch.gridx = 1;
		gbc_label_dCountMatch.gridy = 15;
		panel.add(label_countMatch, gbc_label_dCountMatch);

		panel.add(button_search, gbc_button_search);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		menuSettings = new JMenu("הגדרות");
		menuSettings.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuSettings.setHorizontalAlignment(SwingConstants.RIGHT);
		menuSettings.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuBar.add(menuSettings);
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
		menuSettings.add(menuItem_bgColor);
		menuSettings.add(menuItem_textColor);
		menuSettings.add(menuItem_textSize);
		menuSettings.add(checkbox_createDocument);
		menuSettings.add(checkbox_createExcel);
		menuSettings.add(checkbox_createTree);
		menuFiles = new JMenu("טעינת קובץ חדש");
		menuSettings.add(menuFiles);
		menuFiles.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuFiles.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuFiles.setHorizontalAlignment(SwingConstants.RIGHT);
		menuFiles.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
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
		menuTorahTable.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuTorahTable.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuTorahTable.setHorizontalAlignment(SwingConstants.RIGHT);
		menuTorahTable.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuLinesFile = new JMenuItem("קובץ תורה מחולק בשורות - Lines.txt");
		menuLinesFile.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuLinesFile.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuLinesFile.setHorizontalAlignment(SwingConstants.RIGHT);
		menuLinesFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuNoTevotFile = new JMenuItem("קובץ תורה אותיות - NoTevot.txt");
		menuNoTevotFile.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuNoTevotFile.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuNoTevotFile.setHorizontalAlignment(SwingConstants.RIGHT);
		menuNoTevotFile.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuFiles.add(menuExcelFolder);
		menuFiles.add(menuResetExcelFolder);
		menuFiles.add(menuTorahTable);
		menuFiles.add(menuLinesFile);
		menuFiles.add(menuNoTevotFile);
		button_defaultSettings = new JButton("קבע ברירת מחדל");
		button_defaultSettings.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		button_defaultSettings.setAlignmentX(Component.RIGHT_ALIGNMENT);
		button_defaultSettings.setPreferredSize(new Dimension(63, 25));
		button_defaultSettings.setMargin(new Insets(2, 2, 2, 2));
		button_defaultSettings.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmall));
		button_defaultSettings.setHorizontalAlignment(SwingConstants.RIGHT);
		menuSettings.add(button_defaultSettings);
		button_defaultSettings.setToolTipText("שמירת ערכי חיפוש לברירת מחדל");
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
					textField_padding.setVisible(cb.isSelected());
					frame_instance.frame.repaint();
					frame_instance.frame.revalidate();
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
				setBGColor(c);
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
					System.out.println("You chose this folder: " + chooser.getSelectedFile().getName());
					ExcelFunctions.setExcel_File_Location("./" + chooser.getSelectedFile().getName() + "/");
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
		menuItem_textColorMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, "בחר צבע", new Color(color_mainStyleHTML_hardCoded[0],
						color_mainStyleHTML_hardCoded[1], color_mainStyleHTML_hardCoded[2]));
				if (c != null) {
					color_mainStyleHTML[0] = c.getRed();
					color_mainStyleHTML[1] = c.getGreen();
					color_mainStyleHTML[2] = c.getBlue();
					mainStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1, color_mainStyleHTML[0],
							color_mainStyleHTML[1], color_mainStyleHTML[2], 0b100);
				}
			}
		});
		menuItem_textColorMarkup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, "בחר צבע", new Color(color_markupStyleHTML_hardCoded[0],
						color_markupStyleHTML_hardCoded[1], color_markupStyleHTML_hardCoded[2]));
				if (c != null) {
					color_markupStyleHTML[0] = c.getRed();
					color_markupStyleHTML[1] = c.getGreen();
					color_markupStyleHTML[2] = c.getBlue();
					markupStyleHTML = new stringFormatting.HtmlGenerator(textHtmlSize + 1, color_markupStyleHTML[0],
							color_markupStyleHTML[1], color_markupStyleHTML[2], 0b100);
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
				}
				setFonts();
				/*
				 * Point p = frame.getLocation(); dFrame.setLocation((int) (p.getX() +
				 * frame.getWidth() - dFrame.getWidth()), (int) (p.getY() + frame.getHeight() -
				 * dFrame.getHeight())); dFrame.setVisible(true);
				 */
			}
		});
		checkbox_createDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setVisible(checkbox_createDocument.isSelected());
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
				DialogSearchRangeFrame dFrame = DialogSearchRangeFrame.getInstance();
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
		setBGColor(customBGColor);
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

}
