CREATE TABLE persistent_properties
(
    id           VARCHAR(255) NOT NULL,
    key_column   VARCHAR(255) NULL,
    value_column VARCHAR(255) NULL,
    CONSTRAINT pk_persistentproperties PRIMARY KEY (id)
);

ALTER TABLE persistent_properties
    ADD CONSTRAINT uc_persistentproperties_key_column UNIQUE (key_column);