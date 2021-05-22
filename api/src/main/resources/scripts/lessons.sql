insert into lesson (difficulty, module, points, status) values ('Básico', 'Saudações', '100', 'Em progresso');
insert into lesson (difficulty, module, points, status) values ('Básico', 'Alfabeto', '100', 'Em progresso');
insert into lesson (difficulty, module, points, status) values ('Básico', 'Números', '100', 'Em progresso');
insert into lesson (difficulty, module, points, status) values ('Básico', 'Cores', '100', 'Em progresso');

insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Calendário', '100', 'Bloqueado');
insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Alimentos', '100', 'Bloqueado');
insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Natureza', '100', 'Bloqueado');
insert into lesson (difficulty, module, points, status) values ('Intermediário', 'Sentimentos', '100', 'Bloqueado');

UPDATE `helpinghand`.`lesson` SET `image_id` = '1000170' WHERE (`id_lesson` = '1');
UPDATE `helpinghand`.`lesson` SET `image_id` = '1000171' WHERE (`id_lesson` = '2');
UPDATE `helpinghand`.`lesson` SET `image_id` = '1000165' WHERE (`id_lesson` = '3');
UPDATE `helpinghand`.`lesson` SET `image_id` = '1000166' WHERE (`id_lesson` = '4');
UPDATE `helpinghand`.`lesson` SET `image_id` = '1000298' WHERE (`id_lesson` = '5');
UPDATE `helpinghand`.`lesson` SET `image_id` = '1000301' WHERE (`id_lesson` = '6');
UPDATE `helpinghand`.`lesson` SET `image_id` = '1000302' WHERE (`id_lesson` = '7');
UPDATE `helpinghand`.`lesson` SET `image_id` = '1000300' WHERE (`id_lesson` = '8');
INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('9', 'Avançado', 'Verbos', '100', 'Bloqueado', '1000006');
INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('10', 'Avançado', 'Adjetivos', '100', 'Bloqueado', '1000007');
INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('11', 'Avançado', 'Pronomes', '100', 'Bloqueado', '1000004');
INSERT INTO `helpinghand`.`lesson` (`id_lesson`, `difficulty`, `module`, `points`, `status`, `image_id`) VALUES ('12', 'Avançado', 'Profissões', '100', 'Bloqueado', '1000005');
