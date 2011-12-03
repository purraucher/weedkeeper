package weedkeeper.ui.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import weedkeeper.Db;
import weedkeeper.model.Breeder;
import weedkeeper.model.Strain;

public class StrainTree implements View {
	private DefaultMutableTreeNode root;
	private JTree tree;
	private DefaultTreeModel model;
	private Map<Long,BreederNode> breederNodes;
	private final Db db;
	public StrainTree(Db db) {
		this.db = db;
		breederNodes = new HashMap<Long,BreederNode>();
	}

	private void init() {
		List<Strain> strains = db.fetchAllStrains();
		root = new DefaultMutableTreeNode("root");
		model = new DefaultTreeModel(root);
		tree = new JTree(model) {
			@Override public String convertValueToText(Object value, boolean selected,
					boolean expanded, boolean leaf, int row, boolean hasFocus) {
				if (value instanceof Node<?>) {
					return ((Node<?>)value).getLabel();
				}
				return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
			}
		};
		tree.setRootVisible(false);
		for (Strain s : strains) {
			add(s, false);
		}
		tree.expandPath(new TreePath(root.getPath()));
	}

	private BreederNode add(Strain s, boolean updateModel) {
		Breeder b = s.getBreeder();
		BreederNode bNode = breederNodes.get(b.getId());
		if (bNode == null) {
			bNode = new BreederNode(b);
			root.add(bNode);
			if (updateModel) {
				model.nodesWereInserted(root, new int[] { root.getIndex(bNode) });
			}
			breederNodes.put(b.getId(), bNode);
		}
		StrainNode sNode = new StrainNode(s);
		bNode.add(sNode);
		if (updateModel) {
			model.nodesWereInserted(bNode, new int[] { bNode.getIndex(sNode) });
		}
		return bNode;
	}

	public void add(Strain s) {
		BreederNode node = add(s, true);
		tree.expandPath(new TreePath(node.getPath()));
	}

	@Override
	public JComponent getComponent() {
		if (tree == null) {
			init();
		}
		return tree;
	}

	private abstract static class Node<T> extends DefaultMutableTreeNode {
		protected T item;
		public Node(T item) {
			super(item);
			this.item = item;
		}
		public T getItem() {
			return item;
		}
		public String getLabel() {
			return item.toString();
		}
	}
	private static class BreederNode extends Node<Breeder> {
		public BreederNode(Breeder breeder) {
			super(breeder);
		}
	}
	private static class StrainNode extends Node<Strain> {
		public StrainNode(Strain strain) {
			super(strain);
		}
	}
}
