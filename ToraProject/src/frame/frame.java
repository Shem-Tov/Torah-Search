package frame;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
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
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import ToraApp.ToraApp;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Dimension;
import ToraApp.PropStore;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;

public class frame {

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
					dialogFrame dFrame = dialogFrame.getInstance();
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
					dialogFrame.clearLastSearch();
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

	private JFrame frame;
	private static JButton button;
	private static JTextField textField_Search;
	private static JTextField textField_dilugMin;
	private static JTextField textField_dilugMax;
	private static JComboBox<?> comboBox_main;
	
	public static String getTextField_Search() {
		return textField_Search.getText();
	}
	public static String getTextField_dilugMin() {
		return textField_dilugMin.getText();
	}
	public static String getTextField_dilugMax() {
		return textField_dilugMax.getText();
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
	public static int getComboBox_main() {
		return comboBox_main.getSelectedIndex();
	}
	public static void setButtonEnabled() 
	{
		button.setEnabled(true);
	}
	static JTextPane textPane;
	private SwingActivity activity;
	private static JScrollPane scrollPane;
	private static StyledDocument doc;
	private static SimpleAttributeSet mainStyle;
	private static SimpleAttributeSet attentionStyle;
	private static JCheckBox checkBox_gimatriaSofiot;
	private static JCheckBox checkBox_wholeWord;
	private static JCheckBox checkBox_countPsukim;
	static public int panelWidth;
	private JButton btnNewButton;
	private static final String checkBox_gimatriaSofiot_text = "<html>" + "חישוב מיוחד" + "<br/>" + "לסופיות"
			+ "</html>";
	private static final String checkBox_countPsukim_true = "<html>" + "ספירת פסוקים" + "<br/>" + "שנמצאו" + "</html>";
	private static final String checkBox_countPsukim_false = "ספירת מציאות";
	private JPanel panel_1;
	private JPopupMenu popupMenu;
	private static JProgressBar progressBar;
	
	public static void showProgressBar(Boolean bool)
	{
		progressBar.setVisible(bool);
	}
	public static void setProgressBar(int num) 
	{
		progressBar.setValue(num);
	}

	public static void initValues() {
		textField_Search.setText(PropStore.map.get(PropStore.searchWord));
		textField_dilugMin.setText(PropStore.map.get(PropStore.minDilug));
		textField_dilugMax.setText(PropStore.map.get(PropStore.maxDilug));
		checkBox_gimatriaSofiot.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_gimatriaSofiot)));
		checkBox_wholeWord.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_wholeWord)));
		checkBox_countPsukim.setSelected(Boolean.parseBoolean(PropStore.map.get(PropStore.bool_countPsukim)));
		checkBox_countPsukim.setText(
				((checkBox_countPsukim.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
	}

	public static void saveValues() {
		PropStore.map.put(PropStore.searchWord, textField_Search.getText());
		PropStore.map.put(PropStore.minDilug, textField_dilugMin.getText());
		PropStore.map.put(PropStore.maxDilug, textField_dilugMax.getText());
		PropStore.map.put(PropStore.bool_gimatriaSofiot, String.valueOf(checkBox_gimatriaSofiot.isSelected()));
		PropStore.map.put(PropStore.bool_wholeWord, String.valueOf(checkBox_wholeWord.isSelected()));
		PropStore.map.put(PropStore.bool_countPsukim, String.valueOf(checkBox_countPsukim.isSelected()));
		PropStore.store();
	}

	public static void appendText(String str) throws BadLocationException {
		appendText(str, true);
	}

	public static void appendText(String str, boolean mode) throws BadLocationException {
		try {
			// doc.insertString(0, "\n"+str, null );
			doc.insertString(doc.getLength(), "\n" + str, ((mode) ? mainStyle : attentionStyle));
			scrollPane.getHorizontalScrollBar().setValue(0);
			// scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void clearText() {
		textPane.setText("");
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame window = new frame();
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
	public frame() throws IOException, BadLocationException {
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
		ToraApp.setGuiMode((byte) 1);
		frame = new JFrame();
		frame.setTitle("חיפוש בתורה");
		frame.getContentPane().setFont(new Font("Miriam Mono CLM", Font.PLAIN, 16));
		frame.setFont(new Font("Miriam Mono CLM", Font.PLAIN, 16));
		frame.setBounds(100, 100, 1600, 887);
		frame.setMinimumSize(new Dimension(550, 420));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JInternalFrame internalFrame = new JInternalFrame("חיפוש בתורה");
		frame.getContentPane().add(internalFrame, BorderLayout.NORTH);

		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		btnNewButton = new JButton("קבע ברירת מחדל");
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveValues();
			}
		});
		btnNewButton.setToolTipText("שמירת ערכי חיפוש לברירת מחדל");

		comboBox_main = new JComboBox();
		panel_1.add(comboBox_main);
		comboBox_main.setMaximumSize(new Dimension(200, 32767));
		comboBox_main.setFont(new Font("Miriam Mono CLM", Font.BOLD, 18));
		comboBox_main.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		comboBox_main.setModel(
				new DefaultComboBoxModel(new String[] { "חיפוש רגיל", "חיפוש גימטריה", "חישוב גימטריה", "דילוגים" }));

		comboBox_main.setBackground(new java.awt.Color(255, 240, 240));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 10));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		frame.getContentPane().add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 124, 42, 0 };
		gbl_panel.rowHeights = new int[] { 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel("חיפוש: ");
		label.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);

		textField_Search = new JTextField();
		textField_Search.setMinimumSize(new Dimension(150, 25));
		textField_Search.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		textField_Search.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_textField_Search = new GridBagConstraints();
		gbc_textField_Search.anchor = GridBagConstraints.NORTHEAST;
		gbc_textField_Search.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Search.gridx = 1;
		gbc_textField_Search.gridy = 0;
		panel.add(textField_Search, gbc_textField_Search);
		textField_Search.setColumns(10);

		JLabel label_dilugMin = new JLabel("דילוג מינימום");
		label_dilugMin.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_label_dilugMin = new GridBagConstraints();
		gbc_label_dilugMin.anchor = GridBagConstraints.EAST;
		gbc_label_dilugMin.insets = new Insets(0, 0, 5, 5);
		gbc_label_dilugMin.gridx = 0;
		gbc_label_dilugMin.gridy = 1;
		panel.add(label_dilugMin, gbc_label_dilugMin);

		textField_dilugMin = new JTextField();
		textField_dilugMin.setMinimumSize(new Dimension(150, 25));
		textField_dilugMin.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		textField_dilugMin.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_textField_dilugMin = new GridBagConstraints();
		gbc_textField_dilugMin.anchor = GridBagConstraints.EAST;
		gbc_textField_dilugMin.insets = new Insets(0, 0, 5, 0);
		gbc_textField_dilugMin.gridx = 1;
		gbc_textField_dilugMin.gridy = 1;
		panel.add(textField_dilugMin, gbc_textField_dilugMin);
		textField_dilugMin.setColumns(10);

		JLabel label_dilugMax = new JLabel("דילוג מקסימום");
		label_dilugMax.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_label_dilugMax = new GridBagConstraints();
		gbc_label_dilugMax.anchor = GridBagConstraints.EAST;
		gbc_label_dilugMax.insets = new Insets(0, 0, 5, 5);
		gbc_label_dilugMax.gridx = 0;
		gbc_label_dilugMax.gridy = 2;
		panel.add(label_dilugMax, gbc_label_dilugMax);

		textField_dilugMax = new JTextField();
		textField_dilugMax.setMinimumSize(new Dimension(150, 25));
		textField_dilugMax.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		textField_dilugMax.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_dilugMax.setColumns(10);
		GridBagConstraints gbc_textField_dilugMax = new GridBagConstraints();
		gbc_textField_dilugMax.anchor = GridBagConstraints.EAST;
		gbc_textField_dilugMax.insets = new Insets(0, 0, 5, 0);
		gbc_textField_dilugMax.gridx = 1;
		gbc_textField_dilugMax.gridy = 2;
		panel.add(textField_dilugMax, gbc_textField_dilugMax);

		JComboBox comboBox_sub = new JComboBox();
		comboBox_sub.setAlignmentX(Component.RIGHT_ALIGNMENT);
		comboBox_sub.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		comboBox_sub.setModel(new DefaultComboBoxModel(new String[] { "אותיות", "מילים", "פסוקים" }));
		GridBagConstraints gbc_comboBox_sub = new GridBagConstraints();
		gbc_comboBox_sub.anchor = GridBagConstraints.EAST;
		gbc_comboBox_sub.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_sub.gridx = 0;
		gbc_comboBox_sub.gridy = 3;
		panel.add(comboBox_sub, gbc_comboBox_sub);

		checkBox_wholeWord = new JCheckBox("מילים שלמות");
		checkBox_wholeWord.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_wholeWord.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_checkBox_wholeWord = new GridBagConstraints();
		gbc_checkBox_wholeWord.anchor = GridBagConstraints.EAST;
		gbc_checkBox_wholeWord.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_wholeWord.gridx = 0;
		gbc_checkBox_wholeWord.gridy = 4;
		panel.add(checkBox_wholeWord, gbc_checkBox_wholeWord);

		checkBox_gimatriaSofiot = new JCheckBox(checkBox_gimatriaSofiot_text);
		checkBox_gimatriaSofiot.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_gimatriaSofiot.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_checkBox_gimatriaSofiot = new GridBagConstraints();
		gbc_checkBox_gimatriaSofiot.anchor = GridBagConstraints.EAST;
		gbc_checkBox_gimatriaSofiot.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_gimatriaSofiot.gridx = 0;
		gbc_checkBox_gimatriaSofiot.gridy = 5;
		panel.add(checkBox_gimatriaSofiot, gbc_checkBox_gimatriaSofiot);

		checkBox_countPsukim = new JCheckBox(checkBox_countPsukim_true);
		checkBox_countPsukim.setSelected(true);
		checkBox_countPsukim.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_countPsukim.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_checkBox_countPsukim = new GridBagConstraints();
		gbc_checkBox_countPsukim.anchor = GridBagConstraints.EAST;
		gbc_checkBox_countPsukim.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_countPsukim.gridx = 0;
		gbc_checkBox_countPsukim.gridy = 6;
		checkBox_countPsukim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBox cb = (JCheckBox) event.getSource();
				cb.setText(((cb.isSelected()) ? checkBox_countPsukim_true : checkBox_countPsukim_false));
			}
		});

		panel.add(checkBox_countPsukim, gbc_checkBox_countPsukim);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 1;
		gbc_button.gridy = 16;

		popupMenu = new JPopupMenu();
		popupMenu.setLabel("");
		// Then on your component(s)

		textPane = new JTextPane();
		textPane.setFont(new Font("Miriam CLM", Font.BOLD, 18));
		textPane.setEditable(false);
		scrollPane = new JScrollPane(textPane);
		panelWidth = scrollPane.getWidth();
		textPane.setBackground(new java.awt.Color(251, 255, 243));
		textPane.addMouseListener(new PopClickListener());

		Color color1 = new java.awt.Color(240, 240, 255);
		panel.setBackground(color1);
		checkBox_gimatriaSofiot.setBackground(color1);
		checkBox_wholeWord.setBackground(color1);
		checkBox_countPsukim.setBackground(color1);
		comboBox_sub.setBackground(color1);
		GroupLayout groupLayout = new GroupLayout(internalFrame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 1598, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 38, Short.MAX_VALUE));
		internalFrame.getContentPane().setLayout(groupLayout);
		;

		doc = textPane.getStyledDocument();
		mainStyle = new SimpleAttributeSet();
		StyleConstants.setForeground(mainStyle, new java.awt.Color(128, 88, 255));
		attentionStyle = new SimpleAttributeSet();
		StyleConstants.setForeground(attentionStyle, new java.awt.Color(250, 40, 40));
		StyleConstants.setBold(mainStyle, true);
		StyleConstants.setBold(attentionStyle, true);
		StyleConstants.setAlignment(mainStyle, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setAlignment(attentionStyle, StyleConstants.ALIGN_RIGHT);

		doc.setParagraphAttributes(0, doc.getLength(), mainStyle, false);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		internalFrame.setVisible(true);

		ToraApp.starter();

		button = new JButton("חפש");
		button.setFont(new Font("Miriam Mono CLM", Font.BOLD, 18));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				button.setEnabled(false);
				progressBar.setVisible(true);
				textPane.setText("");
				activity = SwingActivity.getInstance();
				activity.execute();
			}
		});

		panel.add(button, gbc_button);

		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setVisible(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridheight = 2;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 24;
		panel.add(progressBar, gbc_progressBar);

		initValues();
	}
}
