package weedkeeper.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import weedkeeper.Const;
import weedkeeper.Db;
import weedkeeper.ui.actions.CreateStrainAction;
import weedkeeper.ui.views.StrainTree;

public class MainFrame extends JFrame {
	private final Db db;
	private StrainTree strainTree;
	public MainFrame(Db db) {
		super(String.format("%s %s", Const.Name.get(), Const.Version.get()));
		this.db = db;
		setIconImage(Icons.AppIcon.get());

		strainTree = new StrainTree(db);

		setupMenubar();
		setupContent();
	}

	private void setupContent() {
		getContentPane().setLayout(new MigLayout());
		add(new JScrollPane(strainTree.getComponent()), "dock west, h 100%, wmin 160");
	}

	private void setupMenubar() {
		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);
		JMenu newMenu = new JMenu("New");
		JMenuItem newStrain = new JMenuItem("Strain");
		newMenu.add(newStrain);
		mbar.add(newMenu);
		newStrain.addActionListener(new CreateStrainAction(strainTree, db, this));
	}
}
