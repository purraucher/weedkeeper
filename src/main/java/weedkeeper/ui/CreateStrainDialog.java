package weedkeeper.ui;

import java.awt.Window;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import weedkeeper.Db;
import weedkeeper.Msg;
import weedkeeper.model.Breeder;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

public class CreateStrainDialog extends AbstractDialog<AbstractDialog.DefaultChoice> {
	private JTextField name;
	private JComboBox breeder;
	public CreateStrainDialog(Db db, Window parent) {
		super(Msg.CreateStrainDialogTitle.get(), parent);

		List<Breeder> breeders = db.fetchAllBreeders();
		breeder = new JComboBox(breeders.toArray(new Breeder[breeders.size()]));
		breeder.setEditable(true);
		AutoCompleteSupport.install(breeder, GlazedLists.eventList(breeders));
		name = new JTextField();

		content.add(new JLabel(Msg.FormLabelName.get()));
		content.add(name, "w 100%, wmin 200, wrap");

		content.add(new JLabel(Msg.FormLabelBreeder.get()));
		content.add(breeder, "w 100%, wmin 200");
	}

	public String getName() {
		return StringUtils.trim(name.getText());
	}

	public Breeder getBreeder() {
		Object item = breeder.getSelectedItem();
		if (item instanceof Breeder) {
			return (Breeder)item;
		}
		if (item == null || StringUtils.isBlank((String)item)) {
			return null;
		}
		Breeder b = new Breeder();
		b.setName((String)item);
		return b;
	}
}
