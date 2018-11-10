package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ioManagement.ExcelFunctions;
import torahApp.ToraApp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;

public class DialogSearchRangeFrame extends JDialog {

	/**
	 * 
	 */
	private static DialogSearchRangeFrame instance;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel mainGridPanel = new JPanel();
	private JPanel grid1Panel = new JPanel();
	private JPanel grid2Panel = new JPanel();
	private JPanel grid3Panel = new JPanel();
	private JPanel grid4Panel = new JPanel();

	private static JComboBox<String> cBox_Book1, cBox_Perek1;
	private static JComboBox<String> cBox_Book2, cBox_Perek2;
	private JLabel label;
	private JLabel label_1;
	JButton buttonSearch;
	private JCheckBox checkBox_label1;
	private JCheckBox checkBox_label2;
	private static final String label_Parasha = "לפי פרשה";
	private static final String label_Perek = "לפי פרק";
	private static final String CB_string_end = "סוף";

	public static DialogSearchRangeFrame getInstance() {
		if (instance == null) {
			instance = new DialogSearchRangeFrame();
		}
		return instance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void relistCombo(JComboBox cb, int bookNum) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel((ToraApp.getPerekArray(bookNum).toArray()));
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
		setBounds(100, 100, 461, 134);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout());
		GridLayout sub1Grid = new GridLayout(2, 1, 1, 1);
		GridLayout sub2Grid = new GridLayout(2, 1, 1, 1);
		mainGridPanel = new JPanel();
		mainGridPanel.setLayout(new BoxLayout(mainGridPanel, BoxLayout.X_AXIS));
		grid1Panel = new JPanel(sub1Grid);
		grid2Panel = new JPanel(sub1Grid);
		grid3Panel = new JPanel(sub1Grid);
		grid4Panel = new JPanel(sub2Grid);
		mainGridPanel.add(grid1Panel);
		mainGridPanel.add(grid2Panel);
		mainGridPanel.add(grid3Panel);
		mainGridPanel.add(grid4Panel);
		contentPanel.add(mainGridPanel, BorderLayout.CENTER);

		cBox_Perek1 = new JComboBox<String>();
		cBox_Perek1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		((JLabel) cBox_Perek1.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		grid1Panel.add(cBox_Perek1);

		cBox_Book1 = new JComboBox<String>();
		cBox_Book1.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
		cBox_Book1.addItem(CB_string_end);
		cBox_Book1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		((JLabel) cBox_Book1.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		grid2Panel.add(cBox_Book1);

		checkBox_label1 = new JCheckBox("לפי פרק");
		checkBox_label1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_label1.setHorizontalAlignment(SwingConstants.RIGHT);
		grid3Panel.add(checkBox_label1);

		label = new JLabel("התחל ב: (כולל)");
		label.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		grid4Panel.add(label);

		cBox_Perek2 = new JComboBox<String>();
		cBox_Perek2.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		((JLabel) cBox_Perek2.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		grid1Panel.add(cBox_Perek2);

		cBox_Book2 = new JComboBox<String>();
		cBox_Book2.setModel(new DefaultComboBoxModel(ToraApp.getBookNames()));
		cBox_Book2.addItem(CB_string_end);
		cBox_Book2.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		((JLabel) cBox_Book2.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		grid2Panel.add(cBox_Book2);

		checkBox_label2 = new JCheckBox("לפי פרק");
		checkBox_label2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBox_label2.setHorizontalAlignment(SwingConstants.RIGHT);
		grid3Panel.add(checkBox_label2);

		label_1 = new JLabel("סיים ב: (לא כולל)");
		label_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("Miriam Mono CLM", Font.BOLD, 16));
		label_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		grid4Panel.add(label_1);

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
					if (cb.getSelectedIndex() == 5) {
						cBox_Perek1.setVisible(false);
					} else {
						cBox_Perek1.setVisible(true);
						relistCombo(cBox_Perek1, cb.getSelectedIndex());
					}
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
					cBox_Book1.addItem(CB_string_end);
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
					cBox_Book2.addItem(CB_string_end);
				}
			}
		});

		cBox_Book2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkBox_label2.isSelected()) {
					JComboBox<String> cb = (JComboBox<String>) e.getSource();
					if (cb.getSelectedIndex() == 5) {
						cBox_Perek2.setVisible(false);
					} else {
						cBox_Perek2.setVisible(true);
						relistCombo(cBox_Perek2, cb.getSelectedIndex());
					}
				}
			}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
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
					if (cBox_Book2.getSelectedIndex() < 5) {
						strRange2 = cBox_Perek2.getSelectedItem().toString();
					}
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
						str += cBox_Book2.getSelectedItem().toString();
						if (cBox_Book2.getSelectedIndex() < 5) {
							str += " " + cBox_Perek2.getSelectedItem().toString();
						}
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
