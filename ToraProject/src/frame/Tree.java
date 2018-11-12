package frame;
import java.awt.ComponentOrientation;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
public class Tree extends JTree
{
	public static Tree getInstance() {
		if (instance == null) {
			instance = new Tree();
		}
		return instance;
	}
	private static Tree instance;
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Tree()
    {
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("תורה");
        //create the child nodes
        DefaultMutableTreeNode bereshitNode = new DefaultMutableTreeNode("בראשית");
        DefaultMutableTreeNode shmotNode = new DefaultMutableTreeNode("שמות");
        DefaultMutableTreeNode vayikraNode = new DefaultMutableTreeNode("ויקרא");
        DefaultMutableTreeNode baMidbarNode = new DefaultMutableTreeNode("במדבר");
        DefaultMutableTreeNode dvarimNode = new DefaultMutableTreeNode("דברים");
        //add the child nodes to the root node
        root.add(bereshitNode);
        root.add(shmotNode);
        root.add(vayikraNode);
        root.add(baMidbarNode);
        root.add(dvarimNode);
        //create the tree by passing in the root node
        this.setModel(new DefaultTreeModel(root));
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setVisible(true);
    }
     
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tree();
            }
        });
    }       
}