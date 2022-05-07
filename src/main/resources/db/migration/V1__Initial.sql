create table bereich
(
    id          varchar(255) not null,
    description varchar(255),
    name        varchar(255) not null,
    primary key (id)
) engine=INNODB;

create table einsatzkonzentration
(
    id   varchar(255)     not null,
    max  double precision not null,
    min  double precision not null,
    name varchar(255)     not null,
    soll double precision not null,
    primary key (id)
) engine=INNODB;

create table kuehlschmierstoff
(
    id              varchar(255)     not null,
    datanblatt_name varchar(255),
    datenblatt      longblob,
    hersteller      varchar(255),
    name            varchar(255)     not null,
    nitrat          double precision not null,
    nitrit          double precision not null,
    ph_max          double precision not null,
    ph_min          double precision not null,
    refraktometer   double precision not null,
    wasserhaerte    double precision not null,
    primary key (id)
) engine=INNODB;

create table maschine
(
    id                       varchar(255)     not null,
    cm_entspricht_liter      double precision not null,
    letzter_emulsionswechsel datetime,
    name                     varchar(255)     not null,
    tank_volumen_liter       double precision not null,
    wasserstand_max_cm       double precision not null,
    wasserstand_min_cm       double precision not null,
    bereich_id               varchar(255)     not null,
    einsatzkonzentration_id  varchar(255)     not null,
    kuehlschmierstoff_id     varchar(255)     not null,
    primary key (id)
) engine=INNODB;

create table messung
(
    id                  varchar(255) not null,
    bemerkung           varchar(255),
    locked              bit          not null,
    nitrit1             double precision,
    nitrit2             double precision,
    oel_nachgefuellt    double precision,
    ph1                 double precision,
    ph2                 double precision,
    pruef_datum         datetime     not null,
    rm1                 double precision,
    rm2                 double precision,
    timestamp           datetime     not null,
    wasser_nachgefuellt double precision,
    wasserstand_cm      double precision,
    maschine_id         varchar(255) not null,
    pruefer_id          varchar(255) not null,
    primary key (id)
) engine=INNODB;

create table user
(
    id                 varchar(255) not null,
    account_expire     datetime,
    comment            varchar(255),
    credentials_expire datetime,
    email              varchar(255) not null,
    enabled            bit          not null,
    first_name         varchar(255),
    last_name          varchar(255),
    locked             bit          not null,
    password           varchar(255) not null,
    role               varchar(255) not null,
    theme              varchar(255),
    primary key (id)
) engine=INNODB;

alter table einsatzkonzentration
    add constraint UK_rumhenqax5y6l08tsiitcn39l unique (NAME);

alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table maschine
    add constraint FKdtivoj25w1creriabmfno67ty
        foreign key (bereich_id)
            references bereich (id);

alter table maschine
    add constraint FKng21f4d0hvq22xmcwdcd3rm1u
        foreign key (einsatzkonzentration_id)
            references einsatzkonzentration (id);

alter table maschine
    add constraint FKirw44r3d10puwxcw9uydrhqro
        foreign key (kuehlschmierstoff_id)
            references kuehlschmierstoff (id);

alter table messung
    add constraint FK32w0nf26nn5yv65mpc9bqj3us
        foreign key (maschine_id)
            references maschine (id);

alter table messung
    add constraint FKfxlgh735ynwr1wf16cvfqm2oy
        foreign key (pruefer_id)
            references user (id);