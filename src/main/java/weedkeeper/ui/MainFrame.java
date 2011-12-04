package weedkeeper.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.plaf.basic.BasicHyperlinkUI;

import net.miginfocom.swing.MigLayout;

import weedkeeper.Const;
import weedkeeper.Db;
import weedkeeper.ui.actions.CreateStrainAction;
import weedkeeper.ui.actions.SowAction;
import weedkeeper.ui.views.StrainTree;
import weedkeeper.ui.views.TimelineView;

public class MainFrame extends JFrame {
	private final Db db;
	private StrainTree strainTree;
	private TimelineView timelineView;
	public MainFrame(Db db) {
		super(String.format("%s %s", Const.Name.get(), Const.Version.get()));
		this.db = db;
		setIconImage(Icons.AppIcon.get());

		strainTree = new StrainTree(db);
		timelineView = new TimelineView();

		setupMenubar();
		setupContent();
	}

	private void setupContent() {
		getContentPane().setLayout(new MigLayout("fill"));
		add(new JScrollPane(strainTree.getComponent()), "dock west, h 100%, wmin 160");
		add(timelineView.getComponent(), "w 100%, h 100%");
	}

	private void setupMenubar() {
		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);
		JMenu newMenu = new JMenu("New");
		JMenuItem newStrain = new JMenuItem("Strain");
		newMenu.add(newStrain);
		mbar.add(newMenu);
		newStrain.addActionListener(new CreateStrainAction(strainTree, db, this));
		JMenu actions = new JMenu("Actions");
		mbar.add(actions);
		JMenuItem sow = new JMenuItem(new SowAction(db, this));
		actions.add(sow);
	}
}
