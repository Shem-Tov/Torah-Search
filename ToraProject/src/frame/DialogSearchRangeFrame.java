package frame;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ioManagement.ExcelFunctions;
import torahApp.ToraApp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DialogSearchRangeFrame extends JDialog {

	/**
	 * 
	 */
	private static DialogSearchRangeFrame instance;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JComboBox<String> cBox_Book1, cBox_Perek1;
	private static JComboBox<String> cBox_Book2, cBox_Perek2;
	private JLabel label;
	private JLabel label_1;
	JButton buttonSearch;

	public static DialogSearchRangeFrame getInstance() {
		if (instance == null) {
			instance = new DialogSearchRangeFrame();
		}
		return instance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void relistCombo(JComboBox cb, int bookNum) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel((ToraApp.getPerekArray(bookNum).toArray()));
		// cBox_Perek2.removeAllItems();
		cb.setModel(model);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			if (!ExcelFunctions.tableLoaded) {
				ToraApp.starter();
			}
			DialogSearchRangeFrame dialog = getInstance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DialogSearchRangeFrame() {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setFont(new Font("Miriam Mono CLM", Font.PLAIN, 18));
		setTitle("טווח חיפוש");
		setBounds(100, 100, 289, 148);
		getContentPane().setLayout(new BorderLayout());
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			cBox_Perek1 = new JComboBox<String>();
			cBox_Perek1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
			GridBagConstraints gbc_cBox_Perek1 = new GridBagConstraints();
			gbc_cBox_Perek1.gridwidth = 4;
			gbc_cBox_Perek1.insets = new Insets(0, 0, 5, 5);
			gbc_cBox_Perek1.fill = GridBagConstraints.HORIZONTAL;
			gbc_cBox_Perek1.gridx = 12;
			gbc_cBox_Perek1.gridy = 0;
			contentPanel.add(cBox_Perek1, gbc_cBox_Perek1);
		}
		{
			cBox_Book1 = new JComboBox<String>();
			cBox_Book1.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
			cBox_Book1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
			GridBagConstraints gbc_cBox_Book1 = new GridBagConstraints();
			gbc_cBox_Book1.insets = new Insets(0, 0, 5, 5);
			gbc_cBox_Book1.gridwidth = 5;
			gbc_cBox_Book1.fill = GridBagConstraints.HORIZONTAL;
			gbc_cBox_Book1.gridx = 17;
			gbc_cBox_Book1.gridy = 0;
			contentPanel.add(cBox_Book1, gbc_cBox_Book1);
		}
		{
			label = new JLabel("התחל");
			label.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 24;
			gbc_label.gridy = 0;
			contentPanel.add(label, gbc_label);
		}
		{
			cBox_Perek2 = new JComboBox<String>();
			cBox_Perek2.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
			GridBagConstraints gbc_cBox_Perek2 = new GridBagConstraints();
			gbc_cBox_Perek2.gridwidth = 4;
			gbc_cBox_Perek2.insets = new Insets(0, 0, 0, 5);
			gbc_cBox_Perek2.fill = GridBagConstraints.HORIZONTAL;
			gbc_cBox_Perek2.gridx = 12;
			gbc_cBox_Perek2.gridy = 1;
			contentPanel.add(cBox_Perek2, gbc_cBox_Perek2);
		}
		{
			cBox_Book2 = new JComboBox<String>();
			cBox_Book2.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
			cBox_Book2.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
			GridBagConstraints gbc_cBox_Book2 = new GridBagConstraints();
			gbc_cBox_Book2.gridwidth = 5;
			gbc_cBox_Book2.insets = new Insets(0, 0, 0, 5);
			gbc_cBox_Book2.fill = GridBagConstraints.HORIZONTAL;
			gbc_cBox_Book2.gridx = 17;
			gbc_cBox_Book2.gridy = 1;
			contentPanel.add(cBox_Book2, gbc_cBox_Book2);
		}
		{
			label_1 = new JLabel("סיים");
			label_1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
			GridBagConstraints gbc_label_1 = new GridBagConstraints();
			gbc_label_1.insets = new Insets(0, 0, 0, 5);
			gbc_label_1.gridx = 24;
			gbc_label_1.gridy = 1;
			contentPanel.add(label_1, gbc_label_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton buttonCancel = new JButton("בטל");
				buttonCancel.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
				buttonCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonCancel.setActionCommand("Cancel");
				buttonPane.add(buttonCancel);
			}
			{
				buttonSearch = new JButton("אשר");
				buttonSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
				buttonSearch.setActionCommand("Search");
				buttonPane.add(buttonSearch);
				relistCombo(cBox_Perek1, cBox_Book1.getSelectedIndex());
				relistCombo(cBox_Perek2, cBox_Book2.getSelectedIndex());
			}
		}
		cBox_Book1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				relistCombo(cBox_Perek1, cb.getSelectedIndex());
			}
		});
		cBox_Book2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				relistCombo(cBox_Perek2, cb.getSelectedIndex());
			}
		});
		buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int start, end;
				start = ToraApp.lookupLineNumber(cBox_Book1.getSelectedIndex(), cBox_Perek1.getSelectedIndex());
				end = ToraApp.lookupLineNumber(cBox_Book2.getSelectedIndex(), cBox_Perek2.getSelectedIndex());
				if (end - start <= 0) {
					JOptionPane.showMessageDialog(getInstance(), "טווח לא תקין", "שגיאה", JOptionPane.ERROR_MESSAGE);
				} else {
					
					String str = "<html>"+cBox_Book1.getSelectedItem().toString() + " "
					+cBox_Perek1.getSelectedItem().toString() + " : ";
				    if (cBox_Book1.getSelectedIndex()!=cBox_Book2.getSelectedIndex()) {
				    	str += "<br>"+cBox_Book2.getSelectedItem().toString() + " ";
				    }
					str += cBox_Perek2.getSelectedItem().toString()+"</html>";
						Frame.setSearchRange(start, end, str);
					dispose();
				}
			}
		});
	}
}
