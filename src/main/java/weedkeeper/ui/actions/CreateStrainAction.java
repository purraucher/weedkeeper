package weedkeeper.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import weedkeeper.Db;
import weedkeeper.model.Breeder;
import weedkeeper.model.Strain;
import weedkeeper.ui.AbstractDialog.DefaultChoice;
import weedkeeper.ui.CreateStrainDialog;
import weedkeeper.ui.views.StrainTree;

public class CreateStrainAction extends AbstractAction {
	private final Window parent;
	private final Db db;
	private final StrainTree strainTree;
	public CreateStrainAction(StrainTree strainTree, Db db, Window parent) {
		this.db = db;
		this.parent = parent;
		this.strainTree = strainTree;
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
			strainTree.add(s);
		}
	}
}
