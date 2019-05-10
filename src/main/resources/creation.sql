create table patient (
    id bigint primary key identity,
    name varchar(128),
    surname varchar(128),
    middle_name varchar(128),
    phone_num varchar(18)
);

create table doctor (
    id bigint primary key identity,
    name varchar(128),
    surname varchar(128),
    middle_name varchar(128),
    specialization varchar(256)
);

create table recipe (
    id bigint primary key identity,
    description varchar(256),
    patient_id bigint not null references patient(id) on delete cascade,
    doctor_id bigint not null references doctor(id) on delete cascade,
    creation_date date not null,
    expired int,
    priority varchar(8)
);

INSERT INTO "PUBLIC"."DOCTOR" ("NAME", "SURNAME", "MIDDLE_NAME", "SPECIALIZATION") VALUES ('Doc1', 'Doc1', 'Doc1', 'anything');

INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('p1name', 'p1sur', 'p1mid', '+79093633203');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('p2name', 'p2sur', 'p2mid', '+79093633203');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('p3name', 'p3sur', 'p3mid', '+79093633203');

INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID", "DOCTOR_ID", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('common pills', 0, 0, '2019-05-01', 30, '');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID", "DOCTOR_ID", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('diarrhea killer', 1, 0, '2019-05-01', 60, null);
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID", "DOCTOR_ID", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('vitamins', 1, 0, '2019-05-02', 120, null);
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID", "DOCTOR_ID", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('gastritis', 2, 0, '2019-05-03', 15, null);
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID", "DOCTOR_ID", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('antihistamine', 2, 0, '2019-05-04', 500000, null);

commit;