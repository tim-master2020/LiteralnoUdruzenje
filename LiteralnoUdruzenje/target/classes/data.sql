--Genres
insert into genre(id,name) values (-1,'Comedy');
insert into genre(id,name) values (-2,'Drama');
insert into genre(id,name) values (-3,'Young adult');
insert into genre(id,name) values (-4,'Kids');
insert into genre(id,name) values (-5,'Horror');
insert into genre(id,name) values (-6,'History');

--Authorities
INSERT INTO authority (id,name) values (1,'WRITER');
INSERT INTO authority (id,name) values (2,'READER');
INSERT INTO authority (id,name) values (3,'EDITOR');

--Users
insert into users (id,firstname, lastname,username, email, password,last_password_reset_date,city,country,is_active_account,role)
values (-6,'jelena', 'bojanic','jelena', 'admin@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'READER');
INSERT INTO user_authority (user_id,authority_id) values (-6,2);


insert into users (id,firstname, lastname,username, email, password,last_password_reset_date,city,country,is_active_account,role)
values (-7,'mina', 'maras','mina', 'jelena.bojanic97@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'WRITER');
INSERT INTO user_authority (user_id,authority_id) values (-7,1);

insert into users (id,firstname, lastname,username, email, password,last_password_reset_date,city,country,is_active_account,role)
values (-8,'pera', 'peric','pera', 'reservetableapp@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'EDITOR');
INSERT INTO user_authority (user_id,authority_id) values (-8,3);

--Readers
insert into reader(id,beta_reader) values (-6,false);

--Writers
insert into writer(id,is_verified) values (-7,true );