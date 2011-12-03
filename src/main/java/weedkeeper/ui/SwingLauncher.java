package weedkeeper.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.UIManager;

import lombok.extern.java.Log;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import weedkeeper.Const;
import weedkeeper.Db;
import weedkeeper.Messages;
import weedkeeper.Msg;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;


@Log
public class SwingLauncher {
	static final String OPT_DEV = "dev";
	static final String OPT_LANG = "lang";

	private static class ExceptionHandler implements Thread.UncaughtExceptionHandler {
		private static ExceptionHandler instance = new ExceptionHandler();
		public static ExceptionHandler get() {
			return instance;
		}
		private ExceptionHandler() {}
		@Override public void uncaughtException(Thread t, Throwable ex) {
			handleFatalError(ex);
			shutdown(9);
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.get());
			launch(args);
		}
		catch (ParseException e) {
			handleFatalError(e, "Bad arguments", e.getMessage());
			shutdown(1);
		}
		catch (Exception e) {
			handleFatalError(e);
			shutdown(9);
		}
	}

	static void shutdown(int status) {
		System.exit(status);
	}

	static void handleFatalError(Throwable e) {
		handleFatalError(e, Msg.ErrorDialogTitle.get(), Msg.ErrorDialogMsg.get());
	}

	static void handleFatalError(Throwable e, String title) {
		handleFatalError(e, title, null);
	}

	static void handleFatalError(Throwable e, String title, String desc) {
		handleFatalError(e, new ErrorInfo(title, desc, null, null,
			e, Level.SEVERE, null));
	}

	static void handleFatalError(Throwable e, ErrorInfo info) {
		log.log(Level.SEVERE, "fatal error", e);
		JXErrorPane pane = new JXErrorPane();
		pane.setErrorInfo(info);
		JDialog dia = JXErrorPane.createDialog(null, pane);
		dia.setIconImage(Icons.AppIcon.get());
		dia.setVisible(true);
	}

	static void launch(String[] args) throws ParseException, IOException {
		log.info(String.format("%s %s", Const.Name.get(), Const.Version.get()));

		final CommandLine cl = parseArgs(args);

		initMessages(cl.hasOption(OPT_LANG) ?
			cl.getOptionValue(OPT_LANG) : null);

		final boolean dev = cl.hasOption(OPT_DEV);

		final File dbFile = new File(String.format("%s.h2.db", Const.Name.get()));
		log.info(String.format("initializing database: %s", dbFile.getAbsolutePath()));

		final Db db = initDb(!dbFile.exists() || dev);

		MainFrame mf = new MainFrame(db);
		mf.setSize(800, 500);
		mf.setLocationRelativeTo(null);
		mf.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
		mf.setVisible(true);


	}

	static CommandLine parseArgs(String[] args) throws ParseException {
		PosixParser parser = new PosixParser();
		return parser.parse(makeOpts(), args);
	}

	@SuppressWarnings("static-access")
	static Options makeOpts() {
		Options opts = new Options();
		opts.addOption(OptionBuilder
			.withLongOpt(OPT_DEV).create());
		opts.addOption(OptionBuilder
			.withLongOpt(OPT_LANG)
			.hasArg()
			.create());
		return opts;
	}

	static Db initDb(boolean setup) throws IOException {
		ServerConfig sc = new ServerConfig();
		sc.setName(Const.Name.get());
		sc.setLoggingToJavaLogger(true);
		sc.setLoggingLevel(LogLevel.SQL);

		DataSourceConfig ds = new DataSourceConfig();
		ds.setDriver("org.h2.Driver");
		ds.setUsername("sa");
		ds.setPassword("");
		ds.setUrl(String.format("jdbc:h2:./%s", Const.Name.get()));
		ds.setMinConnections(1);
		ds.setMaxConnections(2);

		sc.setDataSourceConfig(ds);

		EbeanServer db = EbeanServerFactory.create(sc);

		if (setup) {
			log.info("setting up database; loading schema");
			BufferedReader in = new BufferedReader(new InputStreamReader(SwingLauncher.class.getResourceAsStream("/schema.sql")));
			StringBuilder sql = new StringBuilder();
			String line = null;
			while (null != (line = in.readLine())) {
				sql.append(line);
				if (line.endsWith(";")) {
					String query = sql.substring(0, sql.length() - 1);
					log.info("executing schema query: " + query);
					db.createSqlUpdate(query).execute();
					sql.setLength(0);
				}
			}
		}

		return new Db(db);
	}

	static void initMessages(String langOverride) {
		String lang = langOverride != null ? langOverride :
			Locale.getDefault().getLanguage();
		try {
			log.info("loading language file for " + lang);
			Messages.create(lang);
		} catch (Exception e) {
			log.warning(String.format(
				"language file for %s not found, loading default (%s)",
				lang, Const.DefaultLanguage.get()));
			Messages.createDefault();
		}
	}
}
