create table patient (
    patient_id bigint primary key identity,
    name varchar(128) not null,
    surname varchar(128) not null,
    middle_name varchar(128),
    phone_num varchar(18)
);

create table doctor (
    doctor_id bigint primary key identity,
    name varchar(128),
    surname varchar(128),
    middle_name varchar(128),
    specialization varchar(256)
);

create table recipe (
    recipe_id bigint primary key identity,
    description varchar(256),
    patient_id_ref bigint not null references patient(patient_id) on delete cascade,
    doctor_id_ref bigint not null references doctor(doctor_id) on delete cascade,
    creation_date date not null,
    expired date not null,
    priority varchar(8) not null
);

INSERT INTO "PUBLIC"."DOCTOR" ("NAME", "SURNAME", "MIDDLE_NAME", "SPECIALIZATION") VALUES ('Name1', 'Surname1', 'MiddleName1', 'gastroenterologist');
INSERT INTO "PUBLIC"."DOCTOR" ("NAME", "SURNAME", "MIDDLE_NAME", "SPECIALIZATION") VALUES ('Name2', 'Surname2', 'MiddleName2', 'psychotherapist');
INSERT INTO "PUBLIC"."DOCTOR" ("NAME", "SURNAME", "MIDDLE_NAME", "SPECIALIZATION") VALUES ('Name3', 'Surname2', 'MiddleName3', 'therapist');

INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name1', 'Surname1', 'MiddleName1', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name2', 'Surname2', 'MiddleName2', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name3', 'Surname3', 'MiddleName3', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name4', 'Surname4', 'MiddleName4', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name5', 'Surname5', 'MiddleName5', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name6', 'Surname6', 'MiddleName6', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name7', 'Surname7', 'MiddleName7', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name8', 'Surname8', 'MiddleName8', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name9', 'Surname9', 'MiddleName9', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name10', 'Surname10', 'MiddleName10', '+4402032145110');
INSERT INTO "PUBLIC"."PATIENT" ("NAME", "SURNAME", "MIDDLE_NAME", "PHONE_NUM") VALUES ('Name11', 'Surname11', 'MiddleName11', '+4402032145110');


INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('just pills', 0, 0, '2019-05-01', '2020-05-01', 'NORMALEM');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('diarrhea killer', 1, 0, '2019-05-01', '2020-05-01', 'NORMALEM');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('vitamins', 1, 0, '2019-05-02', '2020-05-01', 'CITO');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('gastritis', 2, 0, '2019-05-03', '2020-05-01', 'STATIM');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('antihistamine', 2, 0, '2019-05-04', '2120-05-01', 'NORMALEM');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('just pills', 3, 1, '2019-05-01', '2020-05-01', 'NORMALEM');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('just pills', 4, 1, '2019-05-01', '2020-05-01', 'NORMALEM');
INSERT INTO "PUBLIC"."RECIPE" ("DESCRIPTION", "PATIENT_ID_REF", "DOCTOR_ID_REF", "CREATION_DATE", "EXPIRED", "PRIORITY") VALUES ('just pills', 5, 2, '2019-05-01', '2020-05-01', 'NORMALEM');

commit;