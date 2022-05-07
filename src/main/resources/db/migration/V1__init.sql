CREATE TABLE bereich
(
    id            VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_bereich PRIMARY KEY (id)
);

CREATE TABLE einsatzkonzentration
(
    id   VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    min  DOUBLE       NOT NULL,
    max  DOUBLE       NOT NULL,
    soll DOUBLE       NOT NULL,
    CONSTRAINT pk_einsatzkonzentration PRIMARY KEY (id)
);

CREATE TABLE kuehlschmierstoff
(
    id              VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    hersteller      VARCHAR(255) NULL,
    ph_min          DOUBLE       NOT NULL,
    ph_max          DOUBLE       NOT NULL,
    refraktometer   DOUBLE       NOT NULL,
    nitrat          DOUBLE       NOT NULL,
    nitrit          DOUBLE       NOT NULL,
    wasserhaerte    DOUBLE       NOT NULL,
    datanblatt_name VARCHAR(255) NULL,
    datenblatt      BLOB         NULL,
    CONSTRAINT pk_kuehlschmierstoff PRIMARY KEY (id)
);

CREATE TABLE maschine
(
    id                       VARCHAR(255) NOT NULL,
    name                     VARCHAR(255) NOT NULL,
    tank_volumen_liter       DOUBLE       NOT NULL,
    wasserstand_max_cm       DOUBLE       NOT NULL,
    wasserstand_min_cm       DOUBLE       NOT NULL,
    cm_entspricht_liter      DOUBLE       NOT NULL,
    letzter_emulsionswechsel datetime     NULL,
    bereich_id               VARCHAR(255) NOT NULL,
    einsatzkonzentration_id  VARCHAR(255) NOT NULL,
    kuehlschmierstoff_id     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_maschine PRIMARY KEY (id)
);

CREATE TABLE maschine_uploads
(
    maschine_id VARCHAR(255) NOT NULL,
    uploads_id  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_maschine_uploads PRIMARY KEY (maschine_id, uploads_id)
);

CREATE TABLE messung
(
    id                  VARCHAR(255) NOT NULL,
    pruef_datum         datetime     NOT NULL,
    timestamp           datetime     NOT NULL,
    rm1                 DOUBLE       NULL,
    ph1                 DOUBLE       NULL,
    nitrit1             DOUBLE       NULL,
    wasserstand_cm      DOUBLE       NULL,
    wasser_nachgefuellt DOUBLE       NULL,
    oel_nachgefuellt    DOUBLE       NULL,
    rm2                 DOUBLE       NULL,
    ph2                 DOUBLE       NULL,
    nitrit2             DOUBLE       NULL,
    bemerkung           VARCHAR(255) NULL,
    `locked`            BIT(1)       NOT NULL,
    pruefer_id          VARCHAR(255) NOT NULL,
    maschine_id         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_messung PRIMARY KEY (id)
);

CREATE TABLE upload
(
    id           VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    content      BLOB         NOT NULL,
    CONSTRAINT pk_upload PRIMARY KEY (id)
);

CREATE TABLE user
(
    id                 VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    first_name         VARCHAR(255) NULL,
    last_name          VARCHAR(255) NULL,
    comment            VARCHAR(255) NULL,
    enabled            BIT(1)       NOT NULL,
    `locked`           BIT(1)       NOT NULL,
    account_expire     datetime     NULL,
    credentials_expire datetime     NULL,
    `role`             VARCHAR(255) NOT NULL,
    theme              VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE einsatzkonzentration
    ADD CONSTRAINT uc_einsatzkonzentration_name UNIQUE (name);

ALTER TABLE maschine_uploads
    ADD CONSTRAINT uc_maschine_uploads_uploads UNIQUE (uploads_id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE maschine
    ADD CONSTRAINT FK_MASCHINE_ON_BEREICH FOREIGN KEY (bereich_id) REFERENCES bereich (id);

ALTER TABLE maschine
    ADD CONSTRAINT FK_MASCHINE_ON_EINSATZKONZENTRATION FOREIGN KEY (einsatzkonzentration_id) REFERENCES einsatzkonzentration (id);

ALTER TABLE maschine
    ADD CONSTRAINT FK_MASCHINE_ON_KUEHLSCHMIERSTOFF FOREIGN KEY (kuehlschmierstoff_id) REFERENCES kuehlschmierstoff (id);

ALTER TABLE messung
    ADD CONSTRAINT FK_MESSUNG_ON_MASCHINE FOREIGN KEY (maschine_id) REFERENCES maschine (id);

ALTER TABLE messung
    ADD CONSTRAINT FK_MESSUNG_ON_PRUEFER FOREIGN KEY (pruefer_id) REFERENCES user (id);

ALTER TABLE maschine_uploads
    ADD CONSTRAINT fk_masupl_on_maschine FOREIGN KEY (maschine_id) REFERENCES maschine (id);

ALTER TABLE maschine_uploads
    ADD CONSTRAINT fk_masupl_on_upload FOREIGN KEY (uploads_id) REFERENCES upload (id);