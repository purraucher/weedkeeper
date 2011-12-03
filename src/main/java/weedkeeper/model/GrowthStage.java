package weedkeeper.model;

import com.avaje.ebean.annotation.EnumValue;

public enum GrowthStage {
	@EnumValue("veg")
	Vegetative,
	@EnumValue("bloom")
	Flowering
}