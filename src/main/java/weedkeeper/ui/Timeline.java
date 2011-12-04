package weedkeeper.ui;

import javax.swing.JComponent;
import javax.swing.UIManager;

import weedkeeper.ui.plaf.BasicTimelineUI;
import weedkeeper.ui.plaf.TimelineUI;

public class Timeline extends JComponent {
	private static final String uiClassID = "TimelineUI";

	private int numDays;

	public Timeline() {
		this(32);
	}

	public Timeline(int numDays) {
		this.numDays = numDays;
		updateUI();
	}

	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	public void setUI(TimelineUI ui) {
		super.setUI(ui);
	}

	@Override
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((TimelineUI) UIManager.getUI(this));
		} else {
			setUI(new BasicTimelineUI());
		}
	}
}
