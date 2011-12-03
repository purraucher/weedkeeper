package weedkeeper;

import java.io.IOException;
import java.util.Properties;

public class Messages {
	private static Messages instance;
	public static void createDefault() {
		try {
			create(Const.DefaultLanguage.get());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static void create(String lang) throws IOException {
		if (instance != null) {
			throw new UnsupportedOperationException("already initialized");
		}
		Properties p = new Properties();
		p.load(Messages.class.getResourceAsStream("/lang/" + lang + ".properties"));
		instance = new Messages(p);
	}
	public static Messages getInstance() {
		if (instance == null) {
			throw new UnsupportedOperationException("not yet initialized");
		}
		return instance;
	}
	public static boolean isInitialized() {
		return instance != null;
	}
	public static Messages getOrCreateInstance() {
		if (!isInitialized()) {
			createDefault();
		}
		return getInstance();
	}
	private final Properties lang;
	private Messages(Properties lang) {
		this.lang = lang;
	}
	public String get(Msg key) {
		if (!hasMsg(key)) {
			return key.toString();
		}
		return lang.getProperty(key.getKey());
	}
	public String get(Msg key, Object ... args) {
		if (!hasMsg(key)) {
			return get(key);
		}
		return String.format(get(key), args);
	}
	public boolean hasMsg(Msg key) {
		return lang.containsKey(key.getKey());
	}
}
