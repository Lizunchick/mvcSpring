

CREATE TABLE DOT(
    ID SERIAL PRIMARY KEY,
    IDfigure INT,
    Xvalue REAL,
    Yvalue REAL
);

CREATE TABLE FIGURE(
                    ID SERIAL PRIMARY KEY,
                    IDtype INT
);

alter table DOT
    add constraint fk_dot
        foreign key (IDfigure)
            references FIGURE(id);

CREATE TABLE TYPE(
    ID SERIAL PRIMARY KEY,
    NAME varchar(10)
);
alter table FIGURE
    add constraint fk_fig
        foreign key (IDtype)
            references TYPE(id);



INSERT INTO TYPE VALUES (1,'POINT');
INSERT INTO TYPE VALUES (2,'POLYLINE');
INSERT INTO TYPE VALUES (3,'POLYGON');