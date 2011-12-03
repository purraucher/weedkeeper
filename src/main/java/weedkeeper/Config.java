package weedkeeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public enum Config {
	EtaGermination("eta.germination"),
	EtaRooting("eta.rooting");
	private final String key;
	private static Properties defaults;
	private static Properties config;
	private Config(String key) {
		this.key = key;
	}
	private static String lookup(Config key) {
		if (defaults == null) {
			defaults = new Properties();
			File conf = new File(Const.ConfigFilename.get());
			if (conf.exists()) {
				config = new Properties();
				try {
					FileReader in = new FileReader(conf);
					config.load(in);
					in.close();
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					log.log(Level.WARNING, "I/O error while trying to load app config", e);
				}
			}
			try {
				defaults.load(Config.class.getResourceAsStream("/config-defaults.properties"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return config != null && config.containsKey(key.key) ?
			config.getProperty(key.key) : defaults.getProperty(key.key);
	}
	public String get() {
		return lookup(this);
	}
	public int asInt() {
		return Integer.parseInt(get());
	}
}
