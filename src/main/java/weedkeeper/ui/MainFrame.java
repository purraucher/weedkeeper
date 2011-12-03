package weedkeeper.ui;

import javax.swing.JFrame;

import weedkeeper.Const;

public class MainFrame extends JFrame {
	public MainFrame() {
		super(String.format("%s %s", Const.Name.get(), Const.Version.get()));
		setIconImage(Icons.AppIcon.get());
	}
}
