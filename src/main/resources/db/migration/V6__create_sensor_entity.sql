create table maschine_sensoren (
    maschine_id varchar(255) not null,
    sensoren_id varchar(255) not null,
    primary key (maschine_id, sensoren_id)
);
create table sensor (
    id varchar(255) not null,
    ip varchar(255),
    mac varchar(255),
    maschine_id varchar(255),
    primary key (id)
);
alter table maschine_sensoren
    drop index if exists UK_5u5k5xp1lq73gvbl2pc3xux3;

alter table maschine_sensoren
    add constraint UK_5u5k5xp1lq73gvbl2pc3xux3 unique (sensoren_id);

alter table maschine_sensoren
    add constraint FKjc1riu1wkf7l1xv4a2udtjxpu
        foreign key (sensoren_id)
            references sensor (id);

alter table maschine_sensoren
    add constraint FKmiiy4l0o923gdotgkddrx550x
        foreign key (maschine_id)
            references maschine (id);
alter table sensor
    add constraint FKlrafjv35k11fgsorklw2ldc6
        foreign key (maschine_id)
            references maschine (id);