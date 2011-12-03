package weedkeeper.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import weedkeeper.Const;
import weedkeeper.Db;
import weedkeeper.ui.actions.CreateStrainAction;

public class MainFrame extends JFrame {
	private final Db db;
	public MainFrame(Db db) {
		super(String.format("%s %s", Const.Name.get(), Const.Version.get()));
		this.db = db;
		setIconImage(Icons.AppIcon.get());
		setupMenubar();
	}

	private void setupMenubar() {
		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);
		JMenu newMenu = new JMenu("New");
		JMenuItem newStrain = new JMenuItem("Strain");
		newMenu.add(newStrain);
		mbar.add(newMenu);
		newStrain.addActionListener(new CreateStrainAction(db, this));
	}
}
