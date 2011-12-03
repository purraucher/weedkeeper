package weedkeeper.ui;

import java.awt.Window;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import weedkeeper.Db;
import weedkeeper.Msg;
import weedkeeper.model.Strain;

public class SowDialog extends AbstractDialog<AbstractDialog.DefaultChoice> {
	private JComboBox strain;
	private JTextField numSeeds;
	public SowDialog(Db db, Window parent) {
		super(Msg.SowDialogTitle.get(), parent);

		List<Strain> strains = db.fetchAllStrains();
		strain = new JComboBox(strains.toArray(new Strain[strains.size()]));
		numSeeds = new JTextField();

		content.add(new JLabel(Msg.FormLabelStrain.get()));
		content.add(strain, "wrap, wmin 200, w 100%");

		content.add(new JLabel(Msg.FormLabelNumSeeds.get()));
		content.add(numSeeds, "w 30!");
	}
	public Strain getStrain() {
		return (Strain)strain.getSelectedItem();
	}
	public int getNumSeeds() {
		return Integer.parseInt(numSeeds.getText());
	}
}