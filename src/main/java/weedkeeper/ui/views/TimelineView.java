package weedkeeper.ui.views;

import javax.swing.JComponent;

import weedkeeper.ui.Timeline;

public class TimelineView implements View {
	private Timeline timeline;
	public TimelineView() {
		timeline = new Timeline();
	}
	@Override
	public JComponent getComponent() {
		return timeline;
	}
}
