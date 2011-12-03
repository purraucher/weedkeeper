package weedkeeper;

import java.util.List;

import weedkeeper.model.Breeder;
import weedkeeper.model.Strain;

import com.avaje.ebean.EbeanServer;

public class Db {
	private final EbeanServer ebean;
	public Db(EbeanServer ebean) {
		this.ebean = ebean;
	}

	public List<Breeder> fetchAllBreeders() {
		return ebean.find(Breeder.class).findList();
	}

	public List<Strain> fetchAllStrains() {
		return ebean.find(Strain.class).findList();
	}

	public void save(Object obj) {
		ebean.save(obj);
	}
}
