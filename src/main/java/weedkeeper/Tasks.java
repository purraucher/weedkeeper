package weedkeeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import weedkeeper.model.GrowPeriod;
import weedkeeper.model.GrowthStage;
import weedkeeper.model.Plant;
import weedkeeper.model.Strain;

public abstract class Tasks {

	// -------------------- take cuttings -------------------- //

	static public void takeCuttings(Db db, Plant mother, int num) {
		GrowPeriod period = new GrowPeriod();
		period.setStage(GrowthStage.Rooting);
		period.setStart(new DateTime());
		period.setDuration(Config.EtaRooting.asInt());

		Plant clone = new Plant();
		clone.setMother(mother);
		clone.setStrain(mother.getStrain());
		clone.setPeriod(period);

		period.setPlants(Arrays.asList(clone));

		try {
			db.beginTransaction();
			db.save(period);
			db.save(clone);
			db.commitTransaction();
		} finally {
			db.endTransaction();
		}
	}

	// -------------------- sowing -------------------- //

	static public void sow(Db db, Strain strain, int numSeeds) {
		GrowPeriod period = new GrowPeriod();
		period.setDuration(Config.EtaGermination.asInt());
		period.setStage(GrowthStage.Germinating);
		period.setStart(new DateTime());
		List<Plant> plants = createPlants(period, strain, numSeeds);
		period.setPlants(plants);
		try {
			db.beginTransaction();
			db.save(period);
			db.save(plants);
			db.commitTransaction();
		} finally {
			db.endTransaction();
		}
	}

	static private List<Plant> createPlants(GrowPeriod period, Strain strain, int numSeeds) {
		List<Plant> plants = new ArrayList<Plant>(numSeeds);
		for (int i = 0; i < numSeeds; ++i) {
			Plant p = new Plant();
			p.setPeriod(period);
			p.setStrain(strain);
			plants.add(p);
		}
		return plants;
	}

	// -------------------- start next period -------------------- //

	static public void startVegetativePeriod(Db db, List<Plant> plants, int duration) {
		changeToNewPeriod(db, plants, GrowthStage.Vegetating, duration);
	}

	static public void startFloweringPeriod(Db db, List<Plant> plants, int duration) {
		changeToNewPeriod(db, plants, GrowthStage.Flowering, duration);
	}

	static private void changeToNewPeriod(Db db, List<Plant> plants, GrowthStage stage, int duration) {
		GrowPeriod period = new GrowPeriod();
		period.setStart(new DateTime());
		period.setStage(stage);
		period.setDuration(duration);
		period.setPlants(plants);
		for (Plant p : plants) {
			p.setPeriod(period);
		}
		try {
			db.beginTransaction();
			db.save(period);
			db.save(plants);
			db.commitTransaction();
		} finally {
			db.endTransaction();
		}
	}
}
