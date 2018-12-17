package frame;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class DialogFindWordFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7646912867926463775L;
	private static DialogFindWordFrame instanceTextPane;
	private static DialogFindWordFrame instanceTorahPane;
	private JPanel contentPanel = new JPanel();
	private JTextField textField;
	private String lastSearch = null;
	private Boolean isOpen = false;
	private Boolean isText;
	//int posX=0,posY=0;
	
	public static DialogFindWordFrame getInstance(JTextPane tPane, Boolean isText) {
		if (isText) {
			if (instanceTextPane == null) {
				instanceTextPane = new DialogFindWordFrame(tPane);
				instanceTextPane.isText = isText;
			}
			return instanceTextPane;
		} else {
			if (instanceTorahPane == null) {
				instanceTorahPane = new DialogFindWordFrame(tPane);
				instanceTorahPane.isText = isText;
			}
			return instanceTorahPane;
		}
	}

	Boolean getIsOpen() {
		return isOpen;
	}

	public void clearLastSearch() {
		lastSearch = null;
	}

	
	/*
	 * public static void main(String[] args) { try { DialogFindWordFrame dialog =
	 * new DialogFindWordFrame();
	 * dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 * dialog.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 */
	/**
	 * Create the dialog.
	 */
	public DialogFindWordFrame(JTextPane tPane) {
		isOpen = true;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		//setUndecorated(true);
		setFont(new Font("Miriam Mono CLM", Font.PLAIN, 18));
		setTitle("חיפוש מילים");
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
						//isOpen = false;
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
						HighLighter.getInstance(tPane, isText).scrollWords();
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
							HighLighter.getInstance(tPane, isText).highlight(searchTerm);
						}
					}
				});
				buttonSearch.setActionCommand("Search");
				buttonPane.add(buttonSearch);
			}
		}
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		        isOpen = false;
		    }
		});
		/*
		this.addMouseListener(new MouseAdapter()
		{
		   public void mousePressed(MouseEvent e)
		   {
		      posX=e.getX();
		      posY=e.getY();
		   }
		});
		this.addMouseMotionListener(new MouseAdapter()
		{
		     public void mouseDragged(MouseEvent evt)
		     {
				//sets frame position when mouse dragged			
				setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
							
		     }
		});
		*/
	}
}
