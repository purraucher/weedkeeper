package weedkeeper.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;

import com.avaje.ebean.annotation.EnumValue;

@Getter
@Setter
@Entity
public class GrowCycle {
	public static enum Type {
		@EnumValue("veg")
		Vegetative,
		@EnumValue("bloom")
		Flowering
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private DateTime start;
	private Integer duration;
	@OneToMany
	private List<Plant> plants;
}
