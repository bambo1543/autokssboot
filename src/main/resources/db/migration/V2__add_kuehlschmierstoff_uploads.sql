CREATE TABLE kuehlschmierstoff_uploads
(
    kuehlschmierstoff_id VARCHAR(255) NOT NULL,
    uploads_id           VARCHAR(255) NOT NULL,
    CONSTRAINT pk_kuehlschmierstoff_uploads PRIMARY KEY (kuehlschmierstoff_id, uploads_id)
);

ALTER TABLE kuehlschmierstoff_uploads
    ADD CONSTRAINT uc_kuehlschmierstoff_uploads_uploads UNIQUE (uploads_id);

ALTER TABLE kuehlschmierstoff_uploads
    ADD CONSTRAINT fk_kueupl_on_kuehlschmierstoff FOREIGN KEY (kuehlschmierstoff_id) REFERENCES kuehlschmierstoff (id);

ALTER TABLE kuehlschmierstoff_uploads
    ADD CONSTRAINT fk_kueupl_on_upload FOREIGN KEY (uploads_id) REFERENCES upload (id);

ALTER TABLE kuehlschmierstoff
    DROP COLUMN datanblatt_name;

ALTER TABLE kuehlschmierstoff
    DROP COLUMN datenblatt;