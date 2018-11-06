package frame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class CustomDialog
{
    private List<JComponent> components;
    private int getComponentIndex = 0;
    private String title;
    private int messageType;
    private JRootPane rootPane;
    private String[] options;
    private int optionIndex = 1;
    
    public CustomDialog()
    {
        components = new ArrayList<>();
        setTitle("Custom dialog");
        setMessageType(JOptionPane.PLAIN_MESSAGE);
        setRootPane(null);
        setOptions(new String[] { "בטל", "אשר" });
        setOptionSelection(1);
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setMessageType(int messageType)
    {
        this.messageType = messageType;
    }

    public void addComponent(JComponent component)
    {
        addComponent(component,false,0);
    }
    
    public void addComponent(JComponent component, Boolean getComponent)
    {
    	addComponent(component,getComponent,0);
    }
    
    public void addComponent(JComponent component, Boolean getComponent, int index)
    {
        //getComponent = true  => will return the value of the component;
    	if (component instanceof JComboBox) {
    		((JComboBox<?>)component).setSelectedIndex(index);
    	}
    	components.add(component);
    	if (getComponent) {
    		getComponentIndex = components.size()-1;
    	}
    }
    
    public void addMessageText(String messageText)
    {
        JLabel label = new JLabel("<html>" + messageText + "</html>");

        components.add(label);
    }

    public void setRootPane(JRootPane rootPane)
    {
        this.rootPane = rootPane;
    }

    public void setOptions(String[] options)
    {
        this.options = options;
    }

    public void setOptionSelection(int optionIndex)
    {
        this.optionIndex = optionIndex;
    }

    public void setOptions(String[] options,int optionIndex) {
    	setOptions(options);
    	setOptionSelection(optionIndex);
    }
    
    public Object show()
    {
        int optionType = JOptionPane.OK_CANCEL_OPTION;
        Object optionSelection = null;

        if(options.length != 0)
        {
            optionSelection = options[optionIndex];
        }

        int selection = JOptionPane.showOptionDialog(rootPane,
                components.toArray(), title, optionType, messageType, null,
                options, optionSelection);

        if (selection==optionIndex) {
        	JComponent indexedComponent = components.get(getComponentIndex);
         	if (indexedComponent instanceof JTextField) {
         	      //JTextField returns String
        		  return ((JTextField) indexedComponent).getText();
          	} else if (indexedComponent instanceof JComboBox) {
         	      //JComboBox returns String
          		  return ((JComboBox<?>) indexedComponent).getSelectedItem().toString();
           	} else if  (indexedComponent instanceof JCheckBox) {
      	      //JCheckBox returns Boolean
      		  return ((JCheckBox) indexedComponent).isSelected();
       	} else {
         		// If different JComponent returns int
        		return selection;
        	}
        } else
        {
        	// If not selected returns null
        	return null;
        }
    }

    public static String getLineBreak()
    {
        return "<br>";
    }
}
