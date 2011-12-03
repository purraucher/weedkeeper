package weedkeeper.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import weedkeeper.Db;
import weedkeeper.Msg;
import weedkeeper.Tasks;
import weedkeeper.ui.AbstractDialog.DefaultChoice;
import weedkeeper.ui.SowDialog;

public class SowAction extends AbstractAction {
	final private Db db;
	final private Window parent;
	public SowAction(Db db, Window parent) {
		super(Msg.SowDialogTitle.get());
		this.db = db;
		this.parent = parent;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		SowDialog dia = new SowDialog(db, parent);
		DefaultChoice choice = dia.showDialog();
		if (choice == DefaultChoice.Ok) {
			Tasks.sow(db, dia.getStrain(), dia.getNumSeeds());
		}
	}

}
