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
import javax.swing.JCheckBox;

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
	private JCheckBox checkBox_label1;
	private JCheckBox checkBox_label2;
	private static final String label_Parasha = "לפי פרשה";
	private static final String label_Perek = "לפי פרק";

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
		setBounds(100, 100, 435, 139);
		getContentPane().setLayout(new BorderLayout());
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0,
				0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);

		cBox_Perek1 = new JComboBox<String>();
		cBox_Perek1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_cBox_Perek1 = new GridBagConstraints();
		gbc_cBox_Perek1.gridwidth = 4;
		gbc_cBox_Perek1.insets = new Insets(0, 0, 5, 5);
		gbc_cBox_Perek1.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox_Perek1.gridx = 5;
		gbc_cBox_Perek1.gridy = 0;
		contentPanel.add(cBox_Perek1, gbc_cBox_Perek1);

		cBox_Book1 = new JComboBox<String>();
		cBox_Book1.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
		cBox_Book1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_cBox_Book1 = new GridBagConstraints();
		gbc_cBox_Book1.insets = new Insets(0, 0, 5, 5);
		gbc_cBox_Book1.gridwidth = 4;
		gbc_cBox_Book1.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox_Book1.gridx = 10;
		gbc_cBox_Book1.gridy = 0;
		contentPanel.add(cBox_Book1, gbc_cBox_Book1);

		checkBox_label1 = new JCheckBox("לפי פרק");
		GridBagConstraints gbc_checkBox_label1 = new GridBagConstraints();
		gbc_checkBox_label1.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_label1.gridwidth = 4;
		gbc_checkBox_label1.gridx = 15;
		gbc_checkBox_label1.gridy = 0;
		contentPanel.add(checkBox_label1, gbc_checkBox_label1);

		label = new JLabel("התחל");
		label.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridwidth = 4;
		gbc_label.gridx = 20;
		gbc_label.gridy = 0;
		contentPanel.add(label, gbc_label);

		cBox_Perek2 = new JComboBox<String>();
		cBox_Perek2.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_cBox_Perek2 = new GridBagConstraints();
		gbc_cBox_Perek2.gridwidth = 4;
		gbc_cBox_Perek2.insets = new Insets(0, 0, 0, 5);
		gbc_cBox_Perek2.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox_Perek2.gridx = 5;
		gbc_cBox_Perek2.gridy = 1;
		contentPanel.add(cBox_Perek2, gbc_cBox_Perek2);

		cBox_Book2 = new JComboBox<String>();
		cBox_Book2.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
		cBox_Book2.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_cBox_Book2 = new GridBagConstraints();
		gbc_cBox_Book2.gridwidth = 4;
		gbc_cBox_Book2.insets = new Insets(0, 0, 0, 5);
		gbc_cBox_Book2.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox_Book2.gridx = 10;
		gbc_cBox_Book2.gridy = 1;
		contentPanel.add(cBox_Book2, gbc_cBox_Book2);

		checkBox_label2 = new JCheckBox("לפי פרק");
		GridBagConstraints gbc_checkBox_label2 = new GridBagConstraints();
		gbc_checkBox_label2.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_label2.gridwidth = 4;
		gbc_checkBox_label2.gridx = 15;
		gbc_checkBox_label2.gridy = 1;
		contentPanel.add(checkBox_label2, gbc_checkBox_label2);

		label_1 = new JLabel("סיים");
		label_1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridwidth = 4;
		gbc_label_1.gridx = 20;
		gbc_label_1.gridy = 1;
		contentPanel.add(label_1, gbc_label_1);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton buttonCancel = new JButton("בטל");
		buttonCancel.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		buttonCancel.setActionCommand("Cancel");
		buttonPane.add(buttonCancel);

		buttonSearch = new JButton("אשר");
		buttonSearch.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		buttonSearch.setActionCommand("Search");
		buttonPane.add(buttonSearch);

		relistCombo(cBox_Perek1, cBox_Book1.getSelectedIndex());
		relistCombo(cBox_Perek2, cBox_Book2.getSelectedIndex());

		cBox_Book1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkBox_label1.isSelected()) {
					JComboBox<String> cb = (JComboBox<String>) e.getSource();
					relistCombo(cBox_Perek1, cb.getSelectedIndex());
				}
			}
		});
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cBox_Book2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkBox_label2.isSelected()) {
					JComboBox<String> cb = (JComboBox<String>) e.getSource();
					relistCombo(cBox_Perek2, cb.getSelectedIndex());
				}
			}
		});
		checkBox_label1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();
				if (cb.isSelected()) {
					cb.setText(label_Parasha);
					cBox_Perek1.setVisible(false);
					cBox_Book1.setModel(new DefaultComboBoxModel(ToraApp.getParashaNames()));
				} else {
					cb.setText(label_Perek);
					cBox_Perek1.setVisible(true);
					cBox_Book1.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
				}
			}
		});
		checkBox_label2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();
				if (cb.isSelected()) {
					cb.setText(label_Parasha);
					cBox_Perek2.setVisible(false);
					cBox_Book2.setModel(new DefaultComboBoxModel(ToraApp.getParashaNames()));
				} else {
					cb.setText(label_Perek);
					cBox_Perek2.setVisible(true);
					cBox_Book2.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
				}
			}
		});
		buttonSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int start = 0, end = 0;
				String strRange1 = "", strRange2 = "";
				if (checkBox_label1.isSelected()) {
					start = ToraApp.lookupLineNumberFromParasha(cBox_Book1.getSelectedIndex());
					strRange1 = "פר' " + cBox_Book1.getSelectedItem().toString();
				} else {
					start = ToraApp.lookupLineNumberFromPerek(cBox_Book1.getSelectedIndex(),
							cBox_Perek1.getSelectedIndex());
					strRange1 = cBox_Book1.getSelectedItem().toString() + " "
							+ cBox_Perek1.getSelectedItem().toString();
				}
				if (checkBox_label2.isSelected()) {
					end = ToraApp.lookupLineNumberFromParasha(cBox_Book2.getSelectedIndex());
					// Missing the Book, will be added conditionally below
					strRange2 = "פר' " + cBox_Book2.getSelectedItem().toString();
				} else {
					end = ToraApp.lookupLineNumberFromPerek(cBox_Book2.getSelectedIndex(),
							cBox_Perek2.getSelectedIndex());
					strRange2 = cBox_Perek2.getSelectedItem().toString();
				}
				if (end - start <= 0) {
					JOptionPane.showMessageDialog(getInstance(), "טווח לא תקין", "שגיאה", JOptionPane.ERROR_MESSAGE);
				} else {
					String strHTML = "<html>" + strRange1 + " : ";
					if ((!checkBox_label2.isSelected()
							&& (cBox_Book1.getSelectedIndex() != cBox_Book2.getSelectedIndex()))
							|| (!checkBox_label2.isSelected() && checkBox_label1.isSelected())) {
						// Book added conditionally
						strHTML += "<br>" + cBox_Book2.getSelectedItem().toString() + " ";
					} else if (checkBox_label2.isSelected()) {
						strHTML += "<br>";
					}
					String str = "טווח חיפוש: \n" + strRange1 + " - ";
					if (checkBox_label2.isSelected()) {
						str += strRange2;
					} else {
						str += cBox_Book2.getSelectedItem().toString() + " " + cBox_Perek2.getSelectedItem().toString();
					}
					strHTML += strRange2 + "</html>";
					if (ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) {
						Frame.setSearchRange(start, end, str, strHTML);
					}
					dispose();
				}
			}
		});
	}
}
