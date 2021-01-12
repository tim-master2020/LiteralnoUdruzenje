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
values (-7,'mina', 'maras','mina', 'tamaraa.jancic@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'WRITER');
INSERT INTO user_authority (user_id,authority_id) values (-7,1);

insert into users (id,firstname, lastname,username, email, password,last_password_reset_date,city,country,is_active_account,role)
values (-10,'svetlana', 'svetlanic','svetlana', 'svetlana@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'WRITER');
INSERT INTO user_authority (user_id,authority_id) values (-10,1);

insert into users (id,firstname, lastname,username, email, password,last_password_reset_date,city,country,is_active_account,role)
values (-8,'pera', 'peric','pera', 'reservetableapp@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'EDITOR');
INSERT INTO user_authority (user_id,authority_id) values (-8,3);

insert into users (id,firstname, lastname,username, email, password,last_password_reset_date,city,country,is_active_account,role)
values (-9,'ana', 'maric','ana', 'reader1@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'READER');
INSERT INTO user_authority (user_id,authority_id) values (-9,2);

insert into users (id,firstname, lastname,username, email, password,last_password_reset_date,city,country,is_active_account,role)
values (-11,'jana', 'maric','jana', 'reader2@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12','2012-09-17 18:47:52.69','Novi Sad','Srbija',true,'READER');
INSERT INTO user_authority (user_id,authority_id) values (-9,2);

--Readers
insert into reader(id,beta_reader) values (-6,false);
insert into reader(id,beta_reader) values (-9,true);
insert into reader(id,beta_reader) values (-11,true);

--Writers
insert into writer(id,is_verified) values (-7,true);
insert into writer(id,is_verified) values (-10,true );

--beta reader genres
insert into reader_beta_genres(reader_id, beta_genres_id) values (-9,-3);
insert into reader_beta_genres(reader_id, beta_genres_id) values (-9,-2);
insert into reader_beta_genres(reader_id, beta_genres_id) values (-11,-2);

--Books
insert into book(id,name,number_of_pages,pdf_name,publisher,isbn,year,synopsis,place,genre_id)
values(-10,'JeleninaKnjiga',120,'JeleninaKnjiga','Jelena books','abc',1997,'Jelenina autobiografija','NOVI SAD',-1);

insert into book(id,name,number_of_pages,pdf_name,publisher,isbn,year,synopsis,place,genre_id)
values(-11,'TamarinaKnjiga',120,'TamarinaKnjiga','Tamara books','abcd',1997,'Tamarina autobiografija','NOVI SAD',-2);

insert into book(id,name,number_of_pages,pdf_name,publisher,isbn,year,synopsis,place,genre_id)
values(-12,'MininaKnjiga',120,'MininaKnjiga','Mina books','abcde',1997,'Minina autobiografija','NOVI SAD',-3);