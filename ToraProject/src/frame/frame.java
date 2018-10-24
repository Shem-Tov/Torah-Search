package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ToraApp.Methods;
import ToraApp.ToraApp;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Dimension;

public class frame {

	private JFrame frame;
	private JTextField textField_Search;
	private JTextField textField_dilugMin;
	private JTextField textField_dilugMax;
	private static JTextPane textPane;
	static JScrollPane scrollPane;
	static StyledDocument doc;
	static SimpleAttributeSet keyWord;
	
	static public int panelWidth;
	
	
	public static void appendText(String str) throws BadLocationException {
		try
		{
		    //doc.insertString(0, "\n"+str, null );
		    doc.insertString(doc.getLength(), "\n"+str, keyWord );
		    scrollPane.getHorizontalScrollBar().setValue(0);
		    //scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());

		}
		catch(Exception e) { System.out.println(e); }
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
	 * @throws IOException 
	 * @throws BadLocationException 
	 */
	public frame() throws IOException, BadLocationException {
		initialize();
		frame.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	panelWidth = (int)(scrollPane.getWidth()/10);
		        // do stuff
		    }
		});
	}


	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws BadLocationException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() throws IOException, BadLocationException {
		ToraApp.setGuiMode((byte) 1) ;
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Miriam Mono CLM", Font.PLAIN, 16));
		frame.setFont(new Font("Miriam Mono CLM", Font.PLAIN, 16));
		frame.setBounds(100, 100, 1600, 887);
		frame.setMinimumSize(new Dimension(550,380));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JInternalFrame internalFrame = new JInternalFrame("חיפוש בתורה");
		frame.getContentPane().add(internalFrame, BorderLayout.NORTH);
		
		JComboBox comboBox_main = new JComboBox();
		comboBox_main.setFont(new Font("Miriam Mono CLM", Font.BOLD, 18));
		comboBox_main.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		comboBox_main.setModel(new DefaultComboBoxModel(new String[] {"חיפוש רגיל", "חיפוש גימטריה", "חישוב גימטריה", "דילוגים"}));
		internalFrame.getContentPane().add(comboBox_main, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 10));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		frame.getContentPane().add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{124, 42, 0};
		gbl_panel.rowHeights = new int[]{19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		comboBox_sub.setModel(new DefaultComboBoxModel(new String[] {"אותיות", "מילים", "פסוקים"}));
		GridBagConstraints gbc_comboBox_sub = new GridBagConstraints();
		gbc_comboBox_sub.anchor = GridBagConstraints.EAST;
		gbc_comboBox_sub.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_sub.gridx = 0;
		gbc_comboBox_sub.gridy = 3;
		panel.add(comboBox_sub, gbc_comboBox_sub);
		
		JCheckBox checkBox_wholeWord = new JCheckBox("מילים שלמות");
		checkBox_wholeWord.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_wholeWord.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_checkBox_wholeWord = new GridBagConstraints();
		gbc_checkBox_wholeWord.anchor = GridBagConstraints.EAST;
		gbc_checkBox_wholeWord.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_wholeWord.gridx = 0;
		gbc_checkBox_wholeWord.gridy = 4;
		panel.add(checkBox_wholeWord, gbc_checkBox_wholeWord);
		
		JCheckBox checkBox_gimatriaSofiot = new JCheckBox("חישוב מיוחד לסופיות");
		checkBox_gimatriaSofiot.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_gimatriaSofiot.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_checkBox_gimatriaSofiot = new GridBagConstraints();
		gbc_checkBox_gimatriaSofiot.anchor = GridBagConstraints.EAST;
		gbc_checkBox_gimatriaSofiot.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_gimatriaSofiot.gridx = 0;
		gbc_checkBox_gimatriaSofiot.gridy = 5;
		panel.add(checkBox_gimatriaSofiot, gbc_checkBox_gimatriaSofiot);
		
		JComboBox comboBox_countPsukim = new JComboBox();
		comboBox_countPsukim.setAlignmentX(Component.RIGHT_ALIGNMENT);
		comboBox_countPsukim.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		comboBox_countPsukim.setModel(new DefaultComboBoxModel(new String[] {"ספירת פסוקים", "ספירת התאמות"}));
		GridBagConstraints gbc_comboBox_countPsukim = new GridBagConstraints();
		gbc_comboBox_countPsukim.anchor = GridBagConstraints.EAST;
		gbc_comboBox_countPsukim.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_countPsukim.gridx = 0;
		gbc_comboBox_countPsukim.gridy = 6;
		panel.add(comboBox_countPsukim, gbc_comboBox_countPsukim);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 1;
		gbc_button.gridy = 16;
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Miriam CLM", Font.BOLD, 18));
		textPane.setEditable(false);
		scrollPane = new JScrollPane(textPane);
		doc = textPane.getStyledDocument();
		keyWord = new SimpleAttributeSet();
		panelWidth = scrollPane.getWidth();
		textPane.setBackground(new java.awt.Color(251, 255, 243));
		StyleConstants.setForeground(keyWord, new java.awt.Color(128, 88, 255));
		Color color1 = new java.awt.Color(240,240,255);
		panel.setBackground(color1);
		checkBox_gimatriaSofiot.setBackground(color1);
		checkBox_wholeWord.setBackground(color1);
		comboBox_countPsukim.setBackground(color1);
		comboBox_sub.setBackground(color1);

		comboBox_main.setBackground(new java.awt.Color(255,240,240));;
		
		StyleConstants.setBold(keyWord, true);
		StyleConstants.setAlignment(keyWord,StyleConstants.ALIGN_RIGHT);
		doc.setParagraphAttributes(0, doc.getLength(), keyWord, false);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		internalFrame.setVisible(true);
		
		ToraApp.starter();
		
		JButton button = new JButton("חפש");
		button.setFont(new Font("Miriam Mono CLM", Font.BOLD, 18));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
				Object[] args = { null };
				int selection = 0;
				switch (comboBox_main.getSelectedIndex()) {
				case 0:
					args = Arrays.copyOf(args, 2);
					args[0] = textField_Search.getText();
					args[1] = checkBox_wholeWord.isSelected();
					selection = 1;
					break;
				case 1:
					args = Arrays.copyOf(args, 4);
					args[0] = textField_Search.getText();
					args[1] = checkBox_wholeWord.isSelected();
					args[2] = checkBox_gimatriaSofiot.isSelected();
					args[3] = (comboBox_countPsukim.getSelectedIndex()==0) ? true:false;
					selection = 2;					
					break;
				case 2:
					args = Arrays.copyOf(args, 2);
					args[0] = textField_Search.getText();
					args[1] = checkBox_gimatriaSofiot.isSelected();
					selection = 3;
					break;
				}	
				try {
					if (selection>0) {
						Methods.arrayMethods.get(selection - 1).run(args);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		panel.add(button, gbc_button);
		

	}
}
