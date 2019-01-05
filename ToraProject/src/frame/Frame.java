package frame;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import console.Console;
import ioManagement.ClipboardClass;
import ioManagement.ExcelFunctions;
import ioManagement.LastSearchClass;
import ioManagement.ManageIO.fileMode;
import ioManagement.Output;
import ioManagement.PropStore;
import stringFormat.HtmlGenerator;
import stringFormat.OtherHtml;
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
import java.awt.event.MouseListener;

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
	private static final int id_panel_combosub = 0;
	private static final int id_panel_search = 1;
	private static final int id_panel_dilug = 2;
	private static final int id_panel_letters1_1 = 3;
	private static final int id_panel_letters1_2 = 4;
	private static final int id_panel_letters1_3 = 5;
	private static final int id_panel_wholeWord = 6;
	private static final int id_panel_gimatriaSofiot = 7;
	private static final int id_panel_padding = 8;
	private static final int id_panel_letters2_1 = 9;
	private static final int id_panel_countPsukim = 10;
	private static final int id_panel_letters2_3 = 11;
	private static final int id_panel_searchMulti = 12;
	private static final int id_panel_searchRange = 13;
	@SuppressWarnings("unused")
	private static final int id_panel_progress = 14;
	@SuppressWarnings("unused")
	private static final int id_panel_BUTTON = 15;
	public static final String[] comboBox_sub_Strings_Letters = new String[] { "מילים - יחיד", "מילים - רבים", "פסוקים",
			"פסוקים ומילים" };
	public static final String[] comboBox_sub_Strings_Dilugim = new String[] { "רגיל", "ראש פסוק", "סוף פסוק",
			"ראש מילה", "סוף מילה" };
	public static final String[] comboBox_sub_Strings_Search_Multi = new String[] { "חובה שתי המילים",
			"מספיק מילה אחת" };
	public static final String[] comboBox_sub_Strings_InputLocation = new String[] { "תורה", "חיפוש קודם",
			"קובץ משתמש" };
	static final String combo_strSearch = "חיפוש רגיל";
	static final String combo_strGimatriaSearch = "חיפוש גימטריה";
	static final String combo_strGimatriaCalculate = "חישוב גימטריה";
	static final String combo_strDilugim = "חיפוש בדילוגים";
	static final String combo_strLetterSearch = "חיפוש אותיות";
	static final String combo_strCountSearch = "חיפוש אינדקס";
	static final String combo_strTorahPrint = "הדפסת התורה";
	static final String combo_strTorahRangeReport = "דוח ספירה";
	private static final String strLabel_Search_Standard = "חיפוש ";
	private static final String strLabel_Search_LetterPasuk = "חיפוש בפסוק ";
	private static final String checkBox_countPsukim_true = "<html><p align='right'>" + "ספירת פסוקים" + "</p></html>";
	private static final String checkBox_countPsukim_false = "ספירת מציאות";
	private static final String checkBox_countPsukim_Letter = "שמור על תיבה סופית 2";
	private static final String strLabel_padding_Dilug = "מספר אותיות";
	private static final String strLabel_padding_CountSearch = "מספר אינדקס";
	private static final String strLabel_padding_Search = "מילה שניה";
	private static final String strLabel_padding_LetterSearch = "חיפוש במילה";
	private static final String checkBox_searchMultiple_String = "<html><p align='right'>" + "חיפוש יותר ממילה אחת"
			+ "</p></html>";
	private static final String checkBox_searchMultiple_Gimatria = "חיפוש במספר מילים";
	private static final String checkBox_searchMultiple_ReverseDilug = "חיפוש דילוג הפוך";
	private static final String checkBox_searchMultiple_placeInfo = "להוסיף סימונים";
	private static final String checkBox_searchMultiple_Letter_Sofiot = "<html>" + "<p align=\"right\">" + "חישוב מיוחד"
			+ " לסופיות 2" + "</p> </html>";
	private static Boolean bool_placeInfo = true;
	private static Boolean bool_dilugReversed = false;
	private static Boolean bool_searchMultiple = false;
	private static Boolean bool_gimatriaMultiple = false;
	private static Boolean bool_letters_last2 = false;
	private static Boolean bool_letters_exactSpaces = false;
	private static Boolean bool_stored = false;
	private static Boolean bool_canStore = false;
	private static String savedString_padding_Dilug = "";
	private static String savedString_searchSTR2 = "";
	private static String savedString_countIndex = "";
	private static int savedMode_search = 0;
	private static int savedMode_letter = 0;
	private static int savedMode_dilugim = 0;

	static final String checkBox_letterOrder1_String = "שמור סדר האותיות";
	static final String checkBox_letterOrder1_Tooltip = "שמור על סדר האותיות, צירוף ראשון";
	static final String checkBox_letterOrder2_String = "שמור סדר האותיות 2";
	static final String checkBox_letterOrder2_Tooltip = "שמור על סדר האותיות 2";
	static final String checkBox_first1_Letters = "שמור על תיבה ראשית 1";
	static final String checkBox_last1_Letters = "שמור על תיבה סופית 1";
	static final String checkBox_firstlast1_Letters_Tooltip = "בחיפוש רב צירופי, צירוף הראשון";
	static final String checkBox_first2_Letters = "שמור על תיבה ראשית 2";
	static final String checkBox_firstlast2_Letters_Tooltip = "בחיפוש רב צירופי, צירוף השני";
	static final String checkBox_wholeWord_Regular = "מילים שלמות";
	static final String checkBox_wholeWord_Letter = "חיפוש רווחים מדויק";
	static final String checkBox_wholeWord_Regular_Tooltip = null;
	static final String checkBox_wholeWord_Letter_Tooltip = "עובד רק עם שמירת סדר האותיות";
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
	private static JButton button_storeSearch;
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
	private static JCheckBox checkBox_letterOrder1;
	private static JCheckBox checkBox_letterOrder2;
	private static JCheckBox checkBox_first1;
	private static JCheckBox checkBox_last1;
	private static JCheckBox checkBox_first2;
	// last2 is checkbox_countPsukim
	static JTextPane textPane;
	// static JTable table;
	private static Tree tree;
	private static JScrollPane scrollPane;
	private static JTabbedPane tabbedPane;
	private static HTMLDocument doc = new HTMLDocument();
	private static HTMLEditorKit kit = new HTMLEditorKit();
	// used for Torah Print
	private static HTMLDocument doc2 = new HTMLDocument();
	private static HTMLEditorKit kit2 = new HTMLEditorKit();
	private static JScrollPane scrollPane2;
	static JTextPane textPane2;

	private SwingActivity activity;
	static public int panelWidth;
	private JPopupMenu popupMenu;
	private static JProgressBar progressBar;
	private static JComboBox<?> comboBox_sub;
	private static JComboBox<?> comboBox_DifferentSearch;
	private static JCheckBoxMenuItem checkBox_TooltipOption;
	private static JMenuItem menuItem_bgColor;
	private static JMenu menuItem_textColor;
	private static JMenu menuFiles;
	private static JMenuItem menuDifferentFile;
	private static JMenuItem menuNoTevotFile;
	private static JMenuItem menuLinesFile;
	private static JMenuItem menuTorahTable;
	private static JMenuItem menuDataFolder;
	private static JMenuItem menuResetDataFolder;
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
					clearTextPane();
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
					DialogFindWordFrame dFrame = DialogFindWordFrame.getInstance(textPane, true);
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
					DialogFindWordFrame.getInstance(textPane, true).clearLastSearch();
					HighLighter.getInstance(textPane, true).removeHighlights();
				}
			});
			add(anItem);
			anItem = new JMenuItem("לעבור על המציאות");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					HighLighter.getInstance(textPane, true).scrollWords();
					frame.repaint();
				}
			});
			add(anItem);
		}
	}

	class PopUpTorahPane extends JPopupMenu {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JMenuItem anItem;

		public PopUpTorahPane() {
			anItem = new JMenuItem("נקה מסך");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					clearTorahPane();
				}
			});
			add(anItem);
			anItem = new JMenuItem("העתק");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					ClipboardClass.setSysClipboardText(textPane2.getSelectedText());
				}
			});
			add(anItem);
			anItem = new JMenuItem("חפש...");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					DialogFindWordFrame dFrame = DialogFindWordFrame.getInstance(textPane2, false);
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
					DialogFindWordFrame.getInstance(textPane2, false).clearLastSearch();
					HighLighter.getInstance(textPane2, false).removeHighlights();
				}
			});
			add(anItem);
			anItem = new JMenuItem("לעבור על המציאות");
			anItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					HighLighter.getInstance(textPane2, false).scrollWords();
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
					// ClipboardClass.setSysClipboardText(OtherHtml.html2text(tree.getLastSelectedPathComponent().toString()));
					TreePath[] treePaths = tree.getSelectionPaths();
					String str = "";
					for (TreePath t : treePaths) {
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

	class PopClickTorahPaneListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		private void doPop(MouseEvent e) {
			PopUpTorahPane menu = new PopUpTorahPane();
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public static void clearTextPane() {
		textPane.setText("");
	}

	public static void clearTorahPane() {
		textPane2.setText("");
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
		if ((checkBox_searchRange.isSelected()) && (isTorahSearch())) {
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

	public static void setBool_CanStore(Boolean bool) {
		bool_canStore = bool;
	}

	public static void initValues() {
		try {
			comboBox_main.setSelectedIndex(Integer.parseInt(PropStore.map.get(PropStore.mode_main_number)));
		} catch (Exception e) {

		}
		try {
			setSaveMode_search(Integer.parseInt(PropStore.map.get(PropStore.mode_sub_search)));
			setSaveMode_letter(Integer.parseInt(PropStore.map.get(PropStore.mode_sub_letter)));
			setSaveMode_dilugim(Integer.parseInt(PropStore.map.get(PropStore.mode_sub_dilugim)));
			setBool_searchMultiple(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_search_Multi)));
		} catch (Exception e) {

		}
		try {
			switch (getComboBox_main()) {
			case combo_strSearch:
				checkBox_searchMultiple.setSelected(bool_searchMultiple);
				break;
			}
		} catch (Exception e) {

		}
		textField_Search.setText(PropStore.map.get(PropStore.searchWord));
		setString_searchSTR2(PropStore.map.get(PropStore.searchWord2));
		setString_countIndex(PropStore.map.get(PropStore.countSearchIndex));
		setString_padding_Dilug(PropStore.map.get(PropStore.paddingDilug));
		textField_dilugMin.setText(PropStore.map.get(PropStore.minDilug));
		textField_dilugMax.setText(PropStore.map.get(PropStore.maxDilug));
		ToraApp.subTorahTableFile = PropStore.map.get(PropStore.subTorahTablesFile);
		ToraApp.subTorahLineFile = PropStore.map.get(PropStore.subTorahLineFile);
		ToraApp.subTorahLetterFile = PropStore.map.get(PropStore.subTorahLettersFile);
		ToraApp.differentSearchFile = PropStore.map.get(PropStore.differentSearchFile);
		if ((ToraApp.differentSearchFile != null) && (ToraApp.differentSearchFile.length() > 0)) {
			comboBox_DifferentSearch
					.setToolTipText(Output.markText(ToraApp.differentSearchFile, ColorClass.headerStyleHTML, true));
		} else {
			comboBox_DifferentSearch.setToolTipText(Output.markText(
					"להגדרת קובץ לחיפוש -> הגדרות<br> -> קבצים -> קובץ אחר לחיפוש", ColorClass.headerStyleHTML, true));
		}
		String dataFolder = PropStore.map.get(PropStore.dataFolder);
		if ((dataFolder != null) && (dataFolder.length() > 0)) {
			ExcelFunctions.setData_Folder_Location(dataFolder);
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
			ColorClass.mainStyleHTML = new stringFormat.HtmlGenerator(textHtmlSize, ColorClass.color_mainStyleHTML[0],
					ColorClass.color_mainStyleHTML[1], ColorClass.color_mainStyleHTML[2], 0b1101);
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
			ColorClass.markupStyleHTML = new stringFormat.HtmlGenerator(textHtmlSize + 1,
					ColorClass.color_markupStyleHTML[0], ColorClass.color_markupStyleHTML[1],
					ColorClass.color_markupStyleHTML[2], 0b100);
		} catch (Exception e) {
			ColorClass.color_markupStyleHTML = ColorClass.color_markupStyleHTML_hardCoded.clone();
		}
		checkBox_letterOrder1.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_letterOrder1)));
		checkBox_letterOrder2.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_letterOrder2)));
		checkBox_first1.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_first1)));
		checkBox_first2.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_first2)));
		checkBox_last1.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_last1)));
		bool_letters_last2 = Boolean.parseBoolean(PropStore.map.get(PropStore.bool_last2));
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
		switch (getComboBox_main()) {
		case combo_strSearch:
			if (checkBox_searchMultiple.isSelected()) {
				setSaveMode_search(getComboBox_sub_Index());
			}
			break;
		case combo_strLetterSearch:
			setSaveMode_letter(getComboBox_sub_Index());
			break;
		case combo_strDilugim:
			setSaveMode_dilugim(getComboBox_sub_Index());
			break;
		}
		PropStore.addNotNull(PropStore.mode_main_number, String.valueOf(comboBox_main.getSelectedIndex()));
		PropStore.addNotNull(PropStore.mode_sub_dilugim, String.valueOf(savedMode_dilugim));
		PropStore.addNotNull(PropStore.mode_sub_letter, String.valueOf(savedMode_letter));
		PropStore.addNotNull(PropStore.mode_sub_search, String.valueOf(savedMode_search));
		PropStore.addNotNull(PropStore.searchWord, textField_Search.getText());
		PropStore.addNotNull(PropStore.minDilug, textField_dilugMin.getText());
		PropStore.addNotNull(PropStore.maxDilug, textField_dilugMax.getText());
		PropStore.addNotNull(PropStore.paddingDilug, savedString_padding_Dilug);
		PropStore.addNotNull(PropStore.countSearchIndex, savedString_countIndex);
		PropStore.addNotNull(PropStore.searchWord2, savedString_searchSTR2);
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
		PropStore.addNotNull(PropStore.bool_letterOrder1, String.valueOf(checkBox_letterOrder1.isSelected()));
		PropStore.addNotNull(PropStore.bool_letterOrder2, String.valueOf(checkBox_letterOrder2.isSelected()));
		PropStore.addNotNull(PropStore.bool_first1, String.valueOf(checkBox_first1.isSelected()));
		PropStore.addNotNull(PropStore.bool_first2, String.valueOf(checkBox_first2.isSelected()));
		PropStore.addNotNull(PropStore.bool_last1, String.valueOf(checkBox_last1.isSelected()));
		PropStore.addNotNull(PropStore.bool_last2, String.valueOf(bool_letters_last2));
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
			// mode 4 = TorahPrint
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
			case 4:
				// kit.insertHTML(doc, doc.getLength(), "<b>hello", 0, 0, HTML.Tag.B);
				kit2.insertHTML(doc2, doc2.getLength(),
						(ColorClass.mainStyleHTML.getHtml(0) + str + ColorClass.mainStyleHTML.getHtml(1)), 0, 0, null);
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
		checkBox_letterOrder1.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_letterOrder2.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_first1.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_first2.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
		checkBox_last1.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSize()));
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
		menuResetDataFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuDataFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkBox_TooltipOption.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		comboBox_DifferentSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createDocument.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createExcel.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		checkbox_createTree.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		button_defaultSettings.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmall));
		button_searchRange.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmall));
		button_storeSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmall));
		checkBox_searchRange.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSizeSmaller));
		int temp = (int) (95 * ((float) fontSizeSmall / fontSizeSmall_hardCoded));
		button_defaultSettings.setPreferredSize(new Dimension(temp, 25));
		temp = (int) (200 * ((float) getFontSizeBig() / fontSizeBig_hardCoded));
		comboBox_main.setMaximumSize(new Dimension(temp, 32767));
		comboBox_DifferentSearch.setMaximumSize(new Dimension(temp, 32767));
		temp = (int) (46 * ((float) fontSizeSmaller / fontSizeSmaller_hardCoded));
		checkBox_searchRange.setPreferredSize(new Dimension(140, temp));
		panel.setPreferredSize(new Dimension((int) (120 + 200 * ((float) getFontSize() / 16)), 10));
		// textHtmlSize = 5;
		textHtmlSize = (int) (5 * ((float) getFontSize() / 16));
		ColorClass.attentionHTML = new stringFormat.HtmlGenerator(textHtmlSize, ColorClass.color_attentionHTML[0],
				ColorClass.color_attentionHTML[1], ColorClass.color_attentionHTML[2], 0b1101);
		ColorClass.mainStyleHTML = new stringFormat.HtmlGenerator(textHtmlSize, ColorClass.color_mainStyleHTML[0],
				ColorClass.color_mainStyleHTML[1], ColorClass.color_mainStyleHTML[2], 0b1101);
		ColorClass.tooltipStyleHTML = new HtmlGenerator(textHtmlSize, ColorClass.color_tooltipStyleHTML_hardCoded[0],
				ColorClass.color_tooltipStyleHTML_hardCoded[1], ColorClass.color_tooltipStyleHTML_hardCoded[2], 0b100);
		ColorClass.markupStyleHTML = new stringFormat.HtmlGenerator(textHtmlSize + 1,
				ColorClass.color_markupStyleHTML[0], ColorClass.color_markupStyleHTML[1],
				ColorClass.color_markupStyleHTML[2], 0b100);
		for (int i = 0; i < ColorClass.highlightStyleHTML.length; i++) {
			ColorClass.highlightStyleHTML[i] = new stringFormat.HtmlGenerator(textHtmlSize,
					ColorClass.color_highlightStyleHTML[i][0], ColorClass.color_highlightStyleHTML[i][1],
					ColorClass.color_highlightStyleHTML[i][2], 0b100);
		}
		ColorClass.headerStyleHTML = new stringFormat.HtmlGenerator(textHtmlSize + 1,
				ColorClass.color_headerStyleHTML[0], ColorClass.color_headerStyleHTML[1],
				ColorClass.color_headerStyleHTML[2], 0b100);
		ColorClass.footerStyleHTML = new stringFormat.HtmlGenerator(0, ColorClass.color_footerStyleHTML[0],
				ColorClass.color_footerStyleHTML[1], ColorClass.color_footerStyleHTML[2], 0b100);
		checkBox_letterOrder1
				.setToolTipText(Output.markText(checkBox_letterOrder1_Tooltip, ColorClass.headerStyleHTML, true));
		checkBox_letterOrder2
				.setToolTipText(Output.markText(checkBox_letterOrder2_Tooltip, ColorClass.headerStyleHTML, true));
		checkBox_first1
				.setToolTipText(Output.markText(checkBox_firstlast1_Letters_Tooltip, ColorClass.headerStyleHTML, true));
		checkBox_first2
				.setToolTipText(Output.markText(checkBox_firstlast2_Letters_Tooltip, ColorClass.headerStyleHTML, true));
		checkBox_last1
				.setToolTipText(Output.markText(checkBox_firstlast1_Letters_Tooltip, ColorClass.headerStyleHTML, true));
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
		checkBox_letterOrder1.setBackground(c);
		checkBox_letterOrder2.setBackground(c);
		checkBox_first1.setBackground(c);
		checkBox_first2.setBackground(c);
		checkBox_last1.setBackground(c);
		// frame_instance.frame.repaint();
		// frame_instance.frame.revalidate();
		// menubar
		comboBox_DifferentSearch.setBackground(ColorBG_textPane);
		button_storeSearch.setBackground((bool_stored) ? ColorBG_comboBox_main : ColorBG_textPane);
	}

	private static void setBGColorMenu(Color c, Color c2, Color c3) {
		// menu
		UIManager.put("ToolTip.background", c);
		checkBox_TooltipOption.setBackground(c);
		menuItem_bgColor.setBackground(c3);
		menuItem_textColor.setBackground(c);
		menuItem_textSize.setBackground(c3);
		checkbox_createDocument.setBackground(c);
		checkbox_createExcel.setBackground(c3);
		checkbox_createTree.setBackground(c);
		menuFiles.setBackground(c3);
		// submenu
		menuDifferentFile.setBackground(c2);
		menuDataFolder.setBackground(c);
		menuResetDataFolder.setBackground(c2);
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
				label_textfield_Search.setText(strLabel_Search_Standard);
				checkBox_searchMultiple.setSelected(bool_searchMultiple);
				checkBox_searchMultiple.setText(checkBox_searchMultiple_String);
				if (checkBox_searchMultiple.isSelected()) {
					model = new DefaultComboBoxModel(comboBox_sub_Strings_Search_Multi);
					comboBox_sub.setModel(model);
					subPanels.get(id_panel_padding).setVisible(true);
					subPanels.get(id_panel_combosub).setVisible(true);
					comboBox_sub.setSelectedIndex(savedMode_search);
					textField_padding.setText(savedString_searchSTR2);
					label_padding.setText(strLabel_padding_Search);
				} else {
					subPanels.get(id_panel_padding).setVisible(false);
					subPanels.get(id_panel_combosub).setVisible(false);
				}
				subPanels.get(id_panel_countPsukim).setVisible(true);
				subPanels.get(id_panel_wholeWord).setVisible(true);
				subPanels.get(id_panel_searchMulti).setVisible(true);
				subPanels.get(id_panel_letters1_1).setVisible(false);
				subPanels.get(id_panel_letters1_2).setVisible(false);
				subPanels.get(id_panel_letters1_3).setVisible(false);
				subPanels.get(id_panel_letters2_1).setVisible(false);
				subPanels.get(id_panel_letters2_3).setVisible(false);
				checkBox_wholeWord.setText(checkBox_wholeWord_Regular);
				checkBox_wholeWord.setToolTipText(checkBox_wholeWord_Regular_Tooltip);
				checkBox_countPsukim.setText(
						((checkBox_countPsukim.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
				checkBox_countPsukim.setToolTipText(null);
				break;
			case combo_strCountSearch:
				label_textfield_Search.setText(strLabel_Search_Standard);
				subPanels.get(id_panel_padding).setVisible(true);
				textField_padding.setText(savedString_countIndex);
				subPanels.get(id_panel_combosub).setVisible(false);
				subPanels.get(id_panel_countPsukim).setVisible(true);
				subPanels.get(id_panel_wholeWord).setVisible(true);
				subPanels.get(id_panel_searchMulti).setVisible(false);
				subPanels.get(id_panel_letters1_1).setVisible(false);
				subPanels.get(id_panel_letters1_2).setVisible(false);
				subPanels.get(id_panel_letters1_3).setVisible(false);
				subPanels.get(id_panel_letters2_1).setVisible(false);
				subPanels.get(id_panel_letters2_3).setVisible(false);
				checkBox_wholeWord.setText(checkBox_wholeWord_Regular);
				checkBox_wholeWord.setToolTipText(
						Output.markText(checkBox_wholeWord_Regular_Tooltip, ColorClass.headerStyleHTML, true));
				checkBox_countPsukim.setText(
						((checkBox_countPsukim.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
				checkBox_countPsukim.setToolTipText(null);
				label_padding.setText(strLabel_padding_CountSearch);
				break;
			case combo_strGimatriaSearch:
				label_textfield_Search.setText(strLabel_Search_Standard);
				subPanels.get(id_panel_padding).setVisible(false);
				subPanels.get(id_panel_combosub).setVisible(false);
				subPanels.get(id_panel_countPsukim).setVisible(false);
				subPanels.get(id_panel_wholeWord).setVisible(true);
				subPanels.get(id_panel_letters1_1).setVisible(false);
				subPanels.get(id_panel_letters1_2).setVisible(false);
				subPanels.get(id_panel_letters1_3).setVisible(false);
				subPanels.get(id_panel_letters2_1).setVisible(false);
				subPanels.get(id_panel_letters2_3).setVisible(false);
				subPanels.get(id_panel_searchMulti).setVisible(true);
				checkBox_searchMultiple.setText(checkBox_searchMultiple_Gimatria);
				checkBox_searchMultiple.setSelected(bool_gimatriaMultiple);
				checkBox_wholeWord.setText(checkBox_wholeWord_Regular);
				checkBox_wholeWord.setToolTipText(
						Output.markText(checkBox_wholeWord_Regular_Tooltip, ColorClass.headerStyleHTML, true));
				break;
			case combo_strLetterSearch:
				// subPanels.get(id_panel_padding).setVisible(false);
				changeLetterLayout();
				label_padding.setText(strLabel_padding_LetterSearch);
				subPanels.get(id_panel_combosub).setVisible(true);
				subPanels.get(id_panel_countPsukim).setVisible(true);
				subPanels.get(id_panel_letters1_1).setVisible(true);
				subPanels.get(id_panel_letters1_2).setVisible(true);
				subPanels.get(id_panel_letters1_3).setVisible(true);
				checkBox_countPsukim.setText(checkBox_countPsukim_Letter);
				checkBox_countPsukim.setToolTipText(
						Output.markText(checkBox_firstlast2_Letters_Tooltip, ColorClass.headerStyleHTML, true));
				model = new DefaultComboBoxModel(comboBox_sub_Strings_Letters);
				comboBox_sub.setModel(model);
				comboBox_sub.setSelectedIndex(savedMode_letter);
				break;
			case combo_strGimatriaCalculate:
				label_textfield_Search.setText(strLabel_Search_Standard);
				subPanels.get(id_panel_padding).setVisible(false);
				subPanels.get(id_panel_letters1_1).setVisible(false);
				subPanels.get(id_panel_letters1_2).setVisible(false);
				subPanels.get(id_panel_letters1_3).setVisible(false);
				subPanels.get(id_panel_letters2_1).setVisible(false);
				subPanels.get(id_panel_letters2_3).setVisible(false);
				subPanels.get(id_panel_combosub).setVisible(false);
				subPanels.get(id_panel_countPsukim).setVisible(false);
				subPanels.get(id_panel_wholeWord).setVisible(false);
				subPanels.get(id_panel_searchMulti).setVisible(false);
				break;
			}
			break;
		case combo_strDilugim:
			label_textfield_Search.setText(strLabel_Search_Standard);
			subPanels.get(id_panel_search).setVisible(true);
			subPanels.get(id_panel_dilug).setVisible(true);
			subPanels.get(id_panel_gimatriaSofiot).setVisible(true);
			subPanels.get(id_panel_padding).setVisible(true);
			textField_padding.setText(savedString_padding_Dilug);
			subPanels.get(id_panel_combosub).setVisible(true);
			subPanels.get(id_panel_letters1_1).setVisible(false);
			subPanels.get(id_panel_letters1_2).setVisible(false);
			subPanels.get(id_panel_letters1_3).setVisible(false);
			subPanels.get(id_panel_letters2_1).setVisible(false);
			subPanels.get(id_panel_letters2_3).setVisible(false);
			subPanels.get(id_panel_countPsukim).setVisible(false);
			subPanels.get(id_panel_wholeWord).setVisible(false);
			subPanels.get(id_panel_searchMulti).setVisible(true);
			subPanels.get(id_panel_searchRange).setVisible(true);
			label_padding.setText(strLabel_padding_Dilug);
			checkBox_searchMultiple.setText(checkBox_searchMultiple_ReverseDilug);
			model = new DefaultComboBoxModel(comboBox_sub_Strings_Dilugim);
			comboBox_sub.setModel(model);
			comboBox_sub.setSelectedIndex(savedMode_dilugim);
			checkBox_searchMultiple.setSelected(bool_dilugReversed);
			break;
		case combo_strTorahRangeReport:
			subPanels.get(id_panel_search).setVisible(false);
			subPanels.get(id_panel_dilug).setVisible(false);
			subPanels.get(id_panel_gimatriaSofiot).setVisible(false);
			subPanels.get(id_panel_padding).setVisible(false);
			subPanels.get(id_panel_combosub).setVisible(false);
			subPanels.get(id_panel_letters1_1).setVisible(false);
			subPanels.get(id_panel_letters1_2).setVisible(false);
			subPanels.get(id_panel_letters1_3).setVisible(false);
			subPanels.get(id_panel_letters2_1).setVisible(false);
			subPanels.get(id_panel_letters2_3).setVisible(false);
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
			subPanels.get(id_panel_letters1_1).setVisible(false);
			subPanels.get(id_panel_letters1_2).setVisible(false);
			subPanels.get(id_panel_letters1_3).setVisible(false);
			subPanels.get(id_panel_letters2_1).setVisible(false);
			subPanels.get(id_panel_letters2_3).setVisible(false);
			subPanels.get(id_panel_countPsukim).setVisible(false);
			subPanels.get(id_panel_wholeWord).setVisible(false);
			subPanels.get(id_panel_searchMulti).setVisible(true);
			subPanels.get(id_panel_searchRange).setVisible(true);
			checkBox_searchMultiple.setText(checkBox_searchMultiple_placeInfo);
			checkBox_searchMultiple.setSelected(bool_placeInfo);
			break;
		}
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

	public static Boolean getCheckbox_letterOrder1() {
		return checkBox_letterOrder1.isSelected();
	}

	public static Boolean getCheckbox_letterOrder2() {
		return checkBox_letterOrder2.isSelected();
	}

	public static Boolean getCheckbox_first1() {
		return checkBox_first1.isSelected();
	}

	public static Boolean getCheckbox_first2() {
		return checkBox_first2.isSelected();
	}

	public static Boolean getCheckbox_last1() {
		return checkBox_last1.isSelected();
	}

	public static void setTextPaneVisible(Boolean bool) {
		textPane.setVisible(bool);
	}

	public static fileMode getComboBox_DifferentSearch(fileMode fMode) {
		try {
			switch (comboBox_DifferentSearch.getSelectedIndex()) {
			case 0:
				return fMode;
			case 1:
				return fileMode.LastSearch;
			case 2:
				return fileMode.Different;
			}
		} catch (Exception e) {
			return fMode;
		}
		return fMode;
	}

	public static Boolean isTorahSearch() {
		try {
			switch (comboBox_DifferentSearch.getSelectedIndex()) {
			case 0:
				// Regular Torah Search
			case 1:
				// Torah Search from Last Search
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
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

	public static void setBool_gimatriaMultiple(Boolean bool) {
		bool_gimatriaMultiple = bool;
	}

	public static void setBool_reverseDilug(Boolean bool) {
		bool_dilugReversed = bool;
	}

	public static void setBool_letter_exactSpaces(Boolean bool) {
		bool_letters_exactSpaces = bool;
	}

	public static void setBool_letter_last2(Boolean bool) {
		bool_letters_last2 = bool;
	}

	public static void setString_countIndex(String str) {
		savedString_countIndex = str;
	}

	public static void setString_padding_Dilug(String str) {
		savedString_padding_Dilug = str;
	}

	public static void setString_searchSTR2(String str) {
		savedString_searchSTR2 = str;
	}

	public static void setSaveMode_search(int num) {
		savedMode_search = num;
	}

	public static void setSaveMode_letter(int num) {
		savedMode_letter = num;
	}

	public static void setSaveMode_dilugim(int num) {
		savedMode_dilugim = num;
	}

	public static void resetButton_storeSearch() {
		bool_stored = false;
		if (LastSearchClass.getStoredSize() == -1) {
			button_storeSearch.setBackground(ColorBG_textPane);
		} else {
			button_storeSearch.setBackground(ColorBG_menu);
		}
	}

	private static void changeLetterLayout() {
		switch (comboBox_sub.getSelectedIndex()) {
		case 1:
		case 3:
			subPanels.get(id_panel_padding).setVisible(true);
			textField_padding.setText(savedString_searchSTR2);
			subPanels.get(id_panel_letters2_1).setVisible(true);
			subPanels.get(id_panel_countPsukim).setVisible(true);
			checkBox_countPsukim.setSelected(bool_letters_last2);
			subPanels.get(id_panel_letters2_3).setVisible(true);
			subPanels.get(id_panel_searchMulti).setVisible(true);
			checkBox_searchMultiple.setText(checkBox_searchMultiple_Letter_Sofiot);
			break;
		default:
			subPanels.get(id_panel_padding).setVisible(false);
			subPanels.get(id_panel_letters2_1).setVisible(false);
			subPanels.get(id_panel_countPsukim).setVisible(false);
			subPanels.get(id_panel_letters2_3).setVisible(false);
			subPanels.get(id_panel_searchMulti).setVisible(false);
		}
		switch (comboBox_sub.getSelectedIndex()) {
		case 0:
		case 1:
			label_textfield_Search.setText(strLabel_padding_LetterSearch);
			subPanels.get(id_panel_wholeWord).setVisible(false);
			break;
		default:
			label_textfield_Search.setText(strLabel_Search_LetterPasuk);
			subPanels.get(id_panel_wholeWord).setVisible(true);
			checkBox_wholeWord.setSelected(bool_letters_exactSpaces);
			checkBox_wholeWord.setText(checkBox_wholeWord_Letter);
			checkBox_wholeWord.setToolTipText(
					Output.markText(checkBox_wholeWord_Letter_Tooltip, ColorClass.headerStyleHTML, true));
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Setup Method Array
					// and Create Table for Torah Lookup
					ToraApp.starter();
					// Checks and runs console commands,
					// and returns Boolean to determine
					// if should start GUI.
					if (Console.checkCommands(args)) {
						Frame window = Frame.getInstance();
						window.frame.setVisible(true);
					}
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

		subPanels.add(new JPanel(subPanelGrid1_2) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		label_textfield_Search = new JLabel(strLabel_Search_Standard);
		label_textfield_Search.setHorizontalAlignment(SwingConstants.RIGHT);
		label_textfield_Search.setToolTipText(packHtml("%1 - יהוה"+"<br>"+"%2 - אלהים"));
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

		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		checkBox_first1 = new JCheckBox(checkBox_first1_Letters);
		checkBox_first1.setToolTipText(checkBox_firstlast1_Letters_Tooltip);
		checkBox_first1.setSelected(false);
		checkBox_first1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_first1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(checkBox_first1);

		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		checkBox_last1 = new JCheckBox(checkBox_last1_Letters);
		checkBox_last1.setToolTipText(checkBox_firstlast1_Letters_Tooltip);
		checkBox_last1.setSelected(false);
		checkBox_last1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_last1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(checkBox_last1);

		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		checkBox_letterOrder1 = new JCheckBox(checkBox_letterOrder1_String);
		checkBox_letterOrder1.setSelected(false);
		checkBox_letterOrder1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_letterOrder1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subPanels.get(countPanels++).add(checkBox_letterOrder1);

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

		textPane2 = new JTextPane();
		textPane2.setEditable(false);
		scrollPane2 = new JScrollPane(textPane2);
		scrollPane2.getVerticalScrollBar().setUnitIncrement(7);
		textPane2.setBackground(ColorBG_textPane);
		textPane2.addMouseListener(new PopClickTorahPaneListener());
		// Better support for right to left languages
		textPane2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textPane2.setEditorKit(kit2);
		textPane2.setDocument(doc2);

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
		tabbedPane.addTab("תורה", scrollPane2);

		// table = Table.getInstance(false);
		// table.setBackground(ColorBG_textPane);
		// JScrollPane scrollTable = new JScrollPane(table);
		// tabbedPane.addTab("טבלה", scrollTable);

		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		button_search = new JButton("חפש");

		checkBox_first2 = new JCheckBox(checkBox_first2_Letters);
		checkBox_first2.setToolTipText(checkBox_firstlast2_Letters_Tooltip);
		checkBox_first2.setSelected(false);
		checkBox_first2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_first2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkBox_first2.setBorder(new EmptyBorder(30, 4, 30, 4));
		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(checkBox_first2);

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

		checkBox_letterOrder2 = new JCheckBox(checkBox_letterOrder2_String);
		checkBox_letterOrder2.setSelected(false);
		checkBox_letterOrder2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_letterOrder2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkBox_letterOrder2.setBorder(new EmptyBorder(30, 4, 30, 4));
		subPanels.add(new JPanel(subPanelGrid1_1) {
			public Dimension getPreferredSize() {
				return new Dimension(panel.getWidth(), rowHeight);
			};
		});
		subPanels.get(countPanels++).add(checkBox_letterOrder2);

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
		checkBox_TooltipOption = new JCheckBoxMenuItem(
				"<html> <p align=\"right\">" + "הוספת טקסט נוסף" + "</p></html>");
		checkBox_TooltipOption.setSelected(true);
		checkBox_TooltipOption.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		checkBox_TooltipOption.setHorizontalTextPosition(SwingConstants.RIGHT);
		checkBox_TooltipOption.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBox_TooltipOption.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		comboBox_DifferentSearch = new JComboBox();
		comboBox_DifferentSearch.setModel(new DefaultComboBoxModel(comboBox_sub_Strings_InputLocation));
		comboBox_DifferentSearch.setSelectedIndex(0);
		comboBox_DifferentSearch.setMaximumSize(new Dimension(200, 32767));
		comboBox_DifferentSearch.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		comboBox_DifferentSearch.setAlignmentX(Component.RIGHT_ALIGNMENT);
		((JLabel) comboBox_DifferentSearch.getRenderer()).setHorizontalTextPosition(SwingConstants.RIGHT);
		((JLabel) comboBox_DifferentSearch.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		comboBox_DifferentSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));

		button_storeSearch = new JButton("שמור חיפוש לזכרון");
		button_storeSearch.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		button_storeSearch.setMaximumSize(new Dimension(200, 32767));
		button_storeSearch.setMargin(new Insets(2, 2, 2, 2));
		button_storeSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, fontSize));
		button_storeSearch.setHorizontalAlignment(SwingConstants.RIGHT);

		menuBar.add(menuSettings);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(button_storeSearch);
		menuBar.add(comboBox_DifferentSearch);
		menuBar.add(comboBox_main);
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
		menuDataFolder = new JMenuItem("שינוי תיקיה לדוחות");
		menuDataFolder.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuDataFolder.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuDataFolder.setHorizontalAlignment(SwingConstants.RIGHT);
		menuDataFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
		menuResetDataFolder = new JMenuItem("החזרת תיקיה מקורית לדוחות");
		menuResetDataFolder.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuResetDataFolder.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuResetDataFolder.setHorizontalAlignment(SwingConstants.RIGHT);
		menuResetDataFolder.setFont(new Font("Miriam Mono CLM", Font.BOLD, getFontSizeBig()));
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
		menuFiles.add(menuDataFolder);
		menuFiles.add(menuResetDataFolder);
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
				switch (getComboBox_main()) {
				case combo_strSearch:
				case combo_strCountSearch:
					cb.setText(((cb.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
				}
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
							setString_padding_Dilug(textField_padding.getText());
						}
						break;
					case combo_strCountSearch:
						if (StringUtils.isNumeric(textField_padding.getText())) {
							setString_countIndex(textField_padding.getText());
						}
						break;
					}
					methodRunning = true;
					button_search.setText(buttonCancelText);
					if (comboBox_main.getSelectedItem().toString().equals(combo_strTorahPrint)) {
						clearTorahPane();
						tabbedPane.setSelectedIndex(2);
					} else {
						clearTextPane();
					}
					activity = SwingActivity.getInstance();
					activity.execute();
				} else {
					methodCancelRequest = true;
					button_search.setText(buttonCancelRequestText);
				}
			}
		});
		comboBox_sub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getComboBox_main() == combo_strLetterSearch) {
					changeLetterLayout();
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
		menuResetDataFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CustomDialog cDialog = new CustomDialog();
				cDialog.setTitle("שחזור הגדרות תיקיה");
				JLabel label = new JLabel(
						"האם להחזיר חזרה את התיקיה ל " + ExcelFunctions.getData_Folder_Location_Hardcoded());
				cDialog.addComponent(label);
				Object selection = cDialog.show();
				// System.out.println(selection);
				// null is canceled
				if (selection != null) {
					ExcelFunctions.resetData_Folder_Location();
					PropStore.map.put(PropStore.dataFolder, ExcelFunctions.getData_Folder_Location());
					PropStore.store();
					// Saving code here
				}
			}
		});

		menuDataFolder.addActionListener(new ActionListener() {
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
					ExcelFunctions.setData_Folder_Location(chooser.getSelectedFile().getAbsolutePath() + "/");
					// chooser.getSelectedFile().getAbsolutePath();
					PropStore.map.put(PropStore.dataFolder, ExcelFunctions.getData_Folder_Location());
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
					comboBox_DifferentSearch.setToolTipText(
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
					ColorClass.mainStyleHTML = new stringFormat.HtmlGenerator(textHtmlSize + 1,
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
					ColorClass.markupStyleHTML = new stringFormat.HtmlGenerator(textHtmlSize + 1,
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
		button_storeSearch.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isRightMouseButton(arg0)) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					String extension = LastSearchClass.lastSearchFileExtension_HardCoded.substring(1); // remove dot (.)
																										// from start
					FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(
							extension + " files (*." + extension + ")", extension);
					// add filters
					chooser.addChoosableFileFilter(extensionFilter);
					chooser.setFileFilter(extensionFilter);
					File folder = new File(LastSearchClass.getLastSearchFolder());
					folder.mkdirs();
					chooser.setCurrentDirectory(folder);
					int result = chooser.showOpenDialog(null);

					if (result == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						LastSearchClass.load(file);
						comboBox_DifferentSearch.setSelectedIndex(1);
					}
				} else if (SwingUtilities.isLeftMouseButton(arg0)) {
					if (bool_canStore) {
						if (!bool_stored) {
							try {
								bool_stored = LastSearchClass.getInstance().storeCurrent();
								comboBox_DifferentSearch.setSelectedIndex(1);
							} catch (NullPointerException e) {

							}
							if (bool_stored) {
								button_storeSearch.setBackground(ColorBG_comboBox_main);
							}
						} else {
							JFileChooser chooser = new JFileChooser();
							chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
							String extension = LastSearchClass.lastSearchFileExtension_HardCoded.substring(1); // remove
																												// dot
																												// (.)
																												// from
																												// start
							FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(
									extension + " files (*." + extension + ")", extension);
							// add filters
							chooser.addChoosableFileFilter(extensionFilter);
							chooser.setFileFilter(extensionFilter);
							File folder = new File(LastSearchClass.getLastSearchFolder());
							folder.mkdirs();
							chooser.setCurrentDirectory(folder);
							int result = chooser.showSaveDialog(null);

							if (result == JFileChooser.APPROVE_OPTION) {
								File file = chooser.getSelectedFile();
								if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(extension)) {
									// filename is OK as-is
								} else {
									// file = new File(file.toString() + "."+extension); // append .xml if
									// "foo.jpg.xml" is OK
									file = new File(file.getParentFile(),
											FilenameUtils.getBaseName(file.getName()) + "." + extension); // ALTERNATIVELY:
								}
								LastSearchClass.store(file);
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "ניתן לשמור רק חיפוש רגיל / אותיות / גימטריה", "הודעה",
								JOptionPane.WARNING_MESSAGE);
					}
				}
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
		// Retrieve saved values for frame components
		initValues();
		setFonts();
		setBGColorPanel(customBGColor);
		setBGColorMenu(ColorBG_menu, ColorBG_menu2, ColorBG_menu3);
		changeLayout(comboBox_main.getSelectedItem().toString());
	}

}
