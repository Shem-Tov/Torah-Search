package frame;
import java.awt.ComponentOrientation;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
public class Tree extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
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
        tree = new JTree(root);
        tree.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(tree);
         
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("עץ החיים");       
        this.pack();
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