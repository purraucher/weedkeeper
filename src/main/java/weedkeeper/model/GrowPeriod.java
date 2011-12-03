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


@Getter
@Setter
@Entity
public class GrowPeriod {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private DateTime start;
	private Integer duration;
	@OneToMany
	private List<Plant> plants;
	private GrowthStage stage;
}
