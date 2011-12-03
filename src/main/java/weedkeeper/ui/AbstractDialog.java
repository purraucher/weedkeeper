package weedkeeper.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.EnumMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import weedkeeper.Msg;

public abstract class AbstractDialog<T extends Enum<T>> extends JDialog {
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Label {
		Msg value();
	}
	public static enum DefaultChoice {
		@Label(Msg.LabelOk)
		Ok,
		@Label(Msg.LabelCancel)
		Cancel
	}
	private final Window parent;
	private EnumMap<T, JButton> buttons;
	protected T choice;
	protected JPanel content;

	public AbstractDialog(Window parent) {
		this(null, parent);
	}

	public AbstractDialog(Window parent, boolean modal) {
		this(null, parent, modal);
	}

	public AbstractDialog(String title, Window parent) {
		this(title, parent, true);
	}

	public AbstractDialog(String title, Window parent, boolean modal) {
		this(title, (Class<T>) AbstractDialog.DefaultChoice.class, parent, modal);
	}

	public AbstractDialog(String title, Class<T> type, Window parent, boolean modal) {
		super(parent, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
		setTitle(title);
		this.parent = parent;
		content = new JPanel(new MigLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override public void windowClosed(WindowEvent e) {
				synchronized(AbstractDialog.this) {
					AbstractDialog.this.notifyAll();
				}
			}
		});
		getContentPane().setLayout(new MigLayout("fill", "[grow]", "[grow][]"));
		add(content, "span, wrap, top");
		setupButtons(type);
	}

	protected JButton getButton(T choice) {
		return buttons.get(choice);
	}

	protected void setupButtons(final Class<T> type) {
		JPanel buttonPanel = new JPanel(new MigLayout());
		buttons = new EnumMap<T, JButton>(type);
		final T[] consts = type.getEnumConstants();
		for (final T choice : consts) {
			String label = null;
			try {
				label = type.getField(choice.name())
					.getAnnotation(Label.class).value().get();
			} catch (Exception e) {
				label = choice.toString();
			}
			final JButton bt = new JButton(label);
			buttonPanel.add(bt);
			buttons.put(choice, bt);
			bt.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent ev) {
					AbstractDialog.this.choice = choice;
					setVisible(false);
					dispose();
				}
			});
		}
		add(buttonPanel);
	}

	public T showDialog() {
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
		while (isVisible()) {
			synchronized(this) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return choice;
	}
}
