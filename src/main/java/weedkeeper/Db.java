package weedkeeper;

import java.util.Collection;
import java.util.List;

import javax.persistence.OptimisticLockException;

import weedkeeper.model.Breeder;
import weedkeeper.model.Strain;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;

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
	public int save(Collection<?> xs) throws OptimisticLockException {
		return ebean.save(xs);
	}

	public void refresh(Object obj) {
		ebean.refresh(obj);
	}

	public Transaction beginTransaction() {
		return ebean.beginTransaction();
	}

	public void commitTransaction() {
		ebean.commitTransaction();
	}

	public void endTransaction() {
		ebean.endTransaction();
	}
}
