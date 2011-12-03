package weedkeeper;

public enum Msg {
	ErrorDialogTitle("dialog.error.title"),
	ErrorDialogMsg("dialog.error.message"),
	CreateStrainDialogTitle("dialog.createStrain.title"),
	LabelOk("label.ok"),
	LabelCancel("label.cancel");
	final private String key;
	private Msg(String key) {
		this.key = key;
	}
	public String get() {
		return Messages.getOrCreateInstance().get(this);
	}
	public String get(Object ... args) {
		return Messages.getOrCreateInstance().get(this, args);
	}
	public String toString() {
		return getKey();
	}
	public String getKey() {
		return key;
	}
}