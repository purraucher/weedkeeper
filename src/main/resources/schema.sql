DROP TABLE IF EXISTS breeder, strain, plant, grow_cycle;

CREATE TABLE breeder (
	id INTEGER NOT NULL auto_increment,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT pk_breeder PRIMARY KEY (id)
);

CREATE TABLE strain (
	id INTEGER NOT NULL auto_increment,
	breeder_id INTEGER NOT NULL,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT pk_strain PRIMARY KEY (id),
	CONSTRAINT fk_breeder FOREIGN KEY (breeder_id)
		REFERENCES breeder (id)
);

CREATE TABLE grow_cycle (
	id INTEGER NOT NULL auto_increment,
	type VARCHAR(255) NOT NULL,
	start DATE NOT NULL,
	duration INTEGER,
	CONSTRAINT pk_grow_cycle PRIMARY KEY (id)
);

CREATE TABLE plant (
	id INTEGER NOT NULL auto_increment,
	strain_id INTEGER NOT NULL,
	mother_id INTEGER NOT NULL,
	cycle_id INTEGER NOT NULL,
	CONSTRAINT pk_plant PRIMARY KEY (id),
	CONSTRAINT fk_strain FOREIGN KEY (strain_id)
		REFERENCES strain (id),
	CONSTRAINT fk_mother FOREIGN KEY (mother_id)
		REFERENCES plant (id),
	CONSTRAINT fk_cycle FOREIGN KEY (cycle_id)
		REFERENCES grow_cycle (id)
);