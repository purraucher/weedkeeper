package weedkeeper;

import java.io.IOException;
import java.util.Properties;

public enum Const {
	Name("name"),
	DefaultLanguage("lang.default"),
	ConfigFilename("config.filename"),
	Version("version");
	static final Properties props = new Properties();
	static {
		try {
			props.load(Const.class.getResourceAsStream("/const.properties"));
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	private final String key;
	private Const(String key) {
		this.key = key;
	}
	public String get() {
		return props.getProperty(key);
	}
	public int asInt() {
		return Integer.parseInt(get());
	}
}
