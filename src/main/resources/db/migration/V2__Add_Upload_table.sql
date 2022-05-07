CREATE TABLE maschine_uploads
(
    maschine_id VARCHAR(255) NOT NULL,
    uploads_id  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_maschine_uploads PRIMARY KEY (maschine_id, uploads_id)
);

CREATE TABLE upload
(
    id       VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NULL,
    content_type     VARCHAR(255) NULL,
    content BLOB NULL,
    CONSTRAINT pk_upload PRIMARY KEY (id)
);

ALTER TABLE maschine_uploads
    ADD CONSTRAINT uc_maschine_uploads_uploads UNIQUE (uploads_id);

ALTER TABLE maschine_uploads
    ADD CONSTRAINT fk_masupl_on_maschine FOREIGN KEY (maschine_id) REFERENCES maschine (id);

ALTER TABLE maschine_uploads
    ADD CONSTRAINT fk_masupl_on_upload FOREIGN KEY (uploads_id) REFERENCES upload (id);