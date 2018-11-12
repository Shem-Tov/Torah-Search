package frame;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
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

	public final DefaultMutableTreeNode findNode(String grandparent, String parent) {
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
			if (searchNodes.get(index).toString().toLowerCase().equals(parent.toLowerCase())) {
				if ((grandparent == null) || (searchNodes.get(index).getParent().toString().toLowerCase()
						.equals(grandparent.toLowerCase()))) {
					foundNode = searchNodes.get(index);
					break;
				}
			}
		}
		if (foundNode == null) {
			for (int index = 0; index <= bookmark; index++) {
				if (searchNodes.get(index).toString().toLowerCase().equals(parent.toLowerCase())) {
					if ((grandparent == null) || (searchNodes.get(index).getParent().toString().toLowerCase()
							.equals(grandparent.toLowerCase()))) {
						foundNode = searchNodes.get(index);
						break;
					}
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

	public void addNode(String grandparent, String parent, ArrayList<String> children) {
		DefaultMutableTreeNode parentNode = findNode(grandparent, parent);
		for (String child : children) {
			parentNode.add(new DefaultMutableTreeNode(child));
		}

		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		// model.reload(parentNode);
		model.reload(root);
	}

	private static String rootString = "תורה";
	private int nextParashaIndex = 0;
	private String thisParasha = "";
	private ArrayList<String> dataBuffer = new ArrayList<String>();
	private String thisDilugNode = "";

	public void addNodeParasha(int lineChild, String child) {
		addNodeParasha(lineChild, child, false);
	}

	public void addNodeParasha(int lineChild, String child, Boolean isDilug) {
		if (lineChild > nextParashaIndex) {
			if (dataBuffer != null) {
				addNode((isDilug) ? thisDilugNode:null ,thisParasha, dataBuffer);
				dataBuffer = new ArrayList<String>();
			}
			int childIndex = ToraApp.lookupParashaIndexFromLine(lineChild);
			String parent = ToraApp.tablePerekParashot[0][childIndex];
			nextParashaIndex = Integer.parseInt(ToraApp.tablePerekParashot[1][childIndex + 1]);
			thisParasha = parent;
			String str = "";
			if (isDilug) {
				str = thisDilugNode;
				// System.out.println(thisDilugNode);
			} else {
				str = rootString;
			}
			addNode(null,str, new ArrayList<String>(Arrays.asList(parent)));
		}
		dataBuffer.add(child);
	}

	public String packHTML(String str) {
		return "<html>" + str + "</html>";
	}

	public void addNodeDilug(String dilug) {
		if (dataBuffer != null) {
			addNode(null,thisParasha, dataBuffer);
			dataBuffer = new ArrayList<String>();
		}
		nextParashaIndex = 0;
		thisParasha = "";
		thisDilugNode = packHTML(dilug);
		addNode(null,rootString, new ArrayList<String>(Arrays.asList(thisDilugNode)));
	}
	public void flushBuffer() {
		flushBuffer(true,false);
	}
	
	public void flushBuffer(Boolean expandtree) {
		flushBuffer(expandtree,false);
	}

	public void flushBuffer(Boolean expandtree,Boolean isDilug) {
		if (dataBuffer != null) {
			addNode((isDilug) ? thisDilugNode:null,thisParasha, dataBuffer);
			dataBuffer = new ArrayList<String>();
		}
		// expands the tree
		if (expandtree) {
			for (int i = 0; i < this.getRowCount(); i++) {
				this.expandRow(i);
			}
		}
	}

	public void clearTree() {
		if (this.toString() == null) {
			return;
		}
		nextParashaIndex = 0;
		thisParasha = "";
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.removeAllChildren();
		model.reload();
	}

	public void changeRootText(String str) {
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		rootString = packHTML(str);
		root.setUserObject(rootString);
		model.nodeChanged(root);
	}

	public Tree() {
		// create the root node
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootString);
		// create the tree by passing in the root node
		this.setModel(new DefaultTreeModel(root));
		setFontSize(Frame.getFontSize());
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setVisible(true);

		/*
		 * tree_wrap should be set as the cls for your TreePanel, under
		 * Ext.AbstractComponent However, this will place tree-wrap several div's up the
		 * heirarchy from the actual element you are targeting. So, x-grid-cell-inner is
		 * used with a descendent selector to be more precise. Hopefully, the
		 * combination will avoid applying the styles to anything unintended.
		 * 
		 * Only the height and white-space are actually needed. The other lines make it
		 * pretty.
		 */
	}
	/*
	 * public static void main(String[] args) { SwingUtilities.invokeLater(new
	 * Runnable() {
	 * 
	 * @Override public void run() { new Tree(); } }); }
	 */
}