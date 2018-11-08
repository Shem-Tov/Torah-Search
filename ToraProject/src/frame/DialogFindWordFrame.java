package frame;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


public class DialogFindWordFrame extends JDialog {

	/**
	 * 
	 */
	private static DialogFindWordFrame instance;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private static String lastSearch = null;
	private static Boolean isOpen = false;

	public static DialogFindWordFrame getInstance() {
		if (instance == null) {
			instance = new DialogFindWordFrame();
		}
		return instance;
	}

	static Boolean getIsOpen() {
		return isOpen;
	}

	public static void clearLastSearch() {
		lastSearch = null;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogFindWordFrame dialog = new DialogFindWordFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogFindWordFrame() {
		isOpen = true;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setFont(new Font("Miriam Mono CLM", Font.PLAIN, 18));
		setTitle("חיפוש בדוח");
		setBounds(100, 100, 223, 134);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JLabel lblNewLabel = new JLabel("הקלד מילה לחפש");
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewLabel);
		}
		{
			textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.RIGHT);
			textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			if ((lastSearch != null) && (lastSearch.length() > 0)) {
				textField.setText(lastSearch);
			}
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton buttonCancel = new JButton("בטל");
				buttonCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						isOpen = false;
						dispose();
					}
				});
				buttonCancel.setActionCommand("Cancel");
				buttonPane.add(buttonCancel);
			}
			{
				JButton buttonNext = new JButton("הבא");
				buttonNext.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						HighLighter.scrollWords(Frame.textPane);
					}
				});
				buttonNext.setActionCommand("Next");
				buttonPane.add(buttonNext);
				getRootPane().setDefaultButton(buttonNext);
			}
			{
				JButton buttonSearch = new JButton("חפש");
				buttonSearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String searchTerm = textField.getText();
						if ((searchTerm != null) && (searchTerm.length() > 0)) {
							lastSearch = searchTerm;
							HighLighter.highlight(Frame.textPane, searchTerm);
						}
					}
				});
				buttonSearch.setActionCommand("Search");
				buttonPane.add(buttonSearch);
			}
		}
	}
}
