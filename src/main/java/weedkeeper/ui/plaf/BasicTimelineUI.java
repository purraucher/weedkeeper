package weedkeeper.ui.plaf;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class BasicTimelineUI extends TimelineUI {
	static public ComponentUI createUI(JComponent c) {
		return new BasicTimelineUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		g.setColor(Color.yellow);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		g.setColor(Color.black);
		g.drawString("TODO: " + BasicTimelineUI.class.getName(), 10, 20);
	}
}
