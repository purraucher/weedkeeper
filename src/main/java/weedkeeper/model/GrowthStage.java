package weedkeeper.model;

import com.avaje.ebean.annotation.EnumValue;

public enum GrowthStage {
	@EnumValue("vegetating")
	Vegetating,
	@EnumValue("flowering")
	Flowering,
	@EnumValue("germinating")
	Germinating,
	@EnumValue("rooting")
	Rooting
}