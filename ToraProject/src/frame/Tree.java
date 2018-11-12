package frame;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import torahApp.ToraApp;

public class Tree extends JTree {
	public static Tree getInstance() {
		if (instance == null) {
			instance = new Tree();
		}
		return instance;
	}

	private static Tree instance;

	public void setMaxSize(Dimension dimension) {
		this.setMaximumSize(dimension);
	}
	public void setFontSize(int size) {
		final int fontSizeDiff = 10;
		// final Font currentFont = this.getFont();
		// final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(),
		// currentFont.getSize() + 10);
		// this.setFont(bigFont);
		this.setFont(new Font("Miriam Mono CLM", Font.BOLD, size + fontSizeDiff));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * private TreePath find(DefaultMutableTreeNode root, String s) { Enumeration<?>
	 * e = root.depthFirstEnumeration(); while (e.hasMoreElements()) {
	 * DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement(); if
	 * (node.toString().equalsIgnoreCase(s)) { return new TreePath(node.getPath());
	 * } } return null; }
	 */

	public final DefaultMutableTreeNode findNode(String searchString) {
		List<DefaultMutableTreeNode> searchNodes = getSearchNodes((DefaultMutableTreeNode) this.getModel().getRoot());
		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();
		DefaultMutableTreeNode foundNode = null;
		int bookmark = -1;
		if (currentNode != null) {
			for (int index = 0; index < searchNodes.size(); index++) {
				if (searchNodes.get(index) == currentNode) {
					bookmark = index;
					break;
				}
			}
		}
		for (int index = bookmark + 1; index < searchNodes.size(); index++) {
			if (searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
				foundNode = searchNodes.get(index);
				break;
			}
		}
		if (foundNode == null) {
			for (int index = 0; index <= bookmark; index++) {
				if (searchNodes.get(index).toString().toLowerCase().contains(searchString.toLowerCase())) {
					foundNode = searchNodes.get(index);
					break;
				}
			}
		}
		return foundNode;
	}

	private final List<DefaultMutableTreeNode> getSearchNodes(DefaultMutableTreeNode root) {
		List<DefaultMutableTreeNode> searchNodes = new ArrayList<DefaultMutableTreeNode>();
		Enumeration<?> e = root.preorderEnumeration();
		while (e.hasMoreElements()) {
			searchNodes.add((DefaultMutableTreeNode) e.nextElement());
		}
		return searchNodes;
	}

	public void addNode(String parent, String child) {
		DefaultMutableTreeNode parentNode = findNode(parent);
		parentNode.add(new DefaultMutableTreeNode(child));
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		// model.reload(parentNode);
		model.reload(root);
	}

	private static final String rootString = "תורה";
	private int nextParashaIndex = 0;
	private String thisParasha = "";

	public void addNodeParasha(int lineChild, String child) {
		if (lineChild > nextParashaIndex) {
			int childIndex = ToraApp.lookupParashaIndexFromLine(lineChild);
			String parent = ToraApp.tablePerekParashot[0][childIndex];
			nextParashaIndex = Integer.parseInt(ToraApp.tablePerekParashot[1][childIndex + 1]);
			thisParasha = parent;
			addNode(rootString, parent);
		}
		addNode(thisParasha, child);
	}

	public void clearTree() {
		if (this.toString() == null) {
			return;
		}
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.removeAllChildren();
		model.reload();
	}

	public Tree() {
		// create the root node
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootString);
		// create the child nodes
		/*
		 * DefaultMutableTreeNode bereshitNode = new DefaultMutableTreeNode("בראשית");
		 * DefaultMutableTreeNode shmotNode = new DefaultMutableTreeNode("שמות");
		 * DefaultMutableTreeNode vayikraNode = new DefaultMutableTreeNode("ויקרא");
		 * DefaultMutableTreeNode baMidbarNode = new DefaultMutableTreeNode("במדבר");
		 * DefaultMutableTreeNode dvarimNode = new DefaultMutableTreeNode("דברים");
		 * //add the child nodes to the root node root.add(bereshitNode);
		 * root.add(shmotNode); root.add(vayikraNode); root.add(baMidbarNode);
		 * root.add(dvarimNode);
		 */
		// create the tree by passing in the root node
		this.setModel(new DefaultTreeModel(root));
		setFontSize(Frame.getFontSize());
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setVisible(true);
	}
	/*
	 * public static void main(String[] args) { SwingUtilities.invokeLater(new
	 * Runnable() {
	 * 
	 * @Override public void run() { new Tree(); } }); }
	 */
}