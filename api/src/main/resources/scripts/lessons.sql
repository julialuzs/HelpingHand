insert into lesson (difficulty, module, points, image_name) values ('Básico', 'Saudações', '25', 'audiobook_1');
insert into lesson (difficulty, module, points, image_name) values ('Básico', 'Alfabeto', '25', 'lesson_1');
insert into lesson (difficulty, module, points, image_name) values ('Básico', 'Números', '25', 'rocket_1');
insert into lesson (difficulty, module, points, image_name) values ('Básico', 'Cores', '25', 'testing');

insert into lesson (difficulty, module, points, image_name) values ('Intermediário', 'Calendário', '50', 'calendar');
insert into lesson (difficulty, module, points, image_name) values ('Intermediário', 'Alimentos', '50', 'apple');
insert into lesson (difficulty, module, points, image_name) values ('Intermediário', 'Natureza', '50', 'globe');
insert into lesson (difficulty, module, points, image_name) values ('Intermediário', 'Sentimentos', '50', 'notes');

insert into lesson (difficulty, module, points, image_name) values ('Avançado', 'Verbos', '60', 'books');
insert into lesson (difficulty, module, points, image_name) values ('Avançado', 'Pronomes', '60', 'radio_button');
insert into lesson (difficulty, module, points, image_name) values ('Avançado', 'Adjetivos', '60', 'apps');
insert into lesson (difficulty, module, points, image_name) values ('Avançado', 'Profissões', '60', 'award');

select * from lesson;

--insert into lesson (difficulty, module, points, status) values ('Básico', 'Saudações', '100', 'Em progresso');
--insert into lesson (difficulty, module, points, status) values ('Básico', 'Alfabeto', '100', 'Em progresso');
--insert into lesson (difficulty, module, points, status) values ('Básico', 'Números', '100', 'Em progresso');
--insert into lesson (difficulty, module, points, status) values ('Básico', 'Cores', '100', 'Em progresso');
--
--insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Calendário', '100', 'Bloqueado');
--insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Alimentos', '100', 'Bloqueado');
--insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Natureza', '100', 'Bloqueado');
--insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Sentimentos', '100', 'Bloqueado');
--
--INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('9', 'Avançado', 'Verbos', '100', 'Bloqueado', '1000006');
--INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('10', 'Avançado', 'Adjetivos', '100', 'Bloqueado', '1000007');
--INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('11', 'Avançado', 'Pronomes', '100', 'Bloqueado', '1000004');
--INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('12', 'Avançado', 'Profissões', '100', 'Bloqueado', '1000005');
--
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'audiobook_1' WHERE (`id_lesson` = '1');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'lesson_1' WHERE (`id_lesson` = '2');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'rocket_1' WHERE (`id_lesson` = '3');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'testing' WHERE (`id_lesson` = '4');
--
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'calendar' WHERE (`id_lesson` = '5');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'apple' WHERE (`id_lesson` = '6');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'globe' WHERE (`id_lesson` = '7');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'notes' WHERE (`id_lesson` = '9');
--
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'books' WHERE (`id_lesson` = '8');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'award' WHERE (`id_lesson` = '12');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'radio_button' WHERE (`id_lesson` = '11');
--UPDATE `helpinghand`.`lesson` SET `image_name` = 'apps' WHERE (`id_lesson` = '10');
