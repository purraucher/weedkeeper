package weedkeeper.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import weedkeeper.Db;
import weedkeeper.model.Breeder;
import weedkeeper.model.Strain;
import weedkeeper.ui.AbstractDialog.DefaultChoice;
import weedkeeper.ui.CreateStrainDialog;

public class CreateStrainAction extends AbstractAction {
	private final Window parent;
	private final Db db;
	public CreateStrainAction(Db db, Window parent) {
		this.db = db;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CreateStrainDialog dia = new CreateStrainDialog(db, parent);
		DefaultChoice choice = dia.showDialog();
		if (choice == DefaultChoice.Ok) {
			Strain s = new Strain();
			s.setName(dia.getName());
			Breeder b = dia.getBreeder();
			if (b.getId() == null) {
				db.save(b);
			}
			s.setBreeder(b);
			db.save(s);
		}
	}

}
