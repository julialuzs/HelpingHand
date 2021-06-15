insert into question(correct_answer, answer_options, description, sign, id_lesson)
values( 'Bom dia','Boa noite;Bom dia;Bom feriado;Tudo bem?','Qual a tradução para este sinal?', 'BOM DIA', 1);

insert into question(correct_answer, answer_options, description, sign, id_lesson)
values('Boa noite', 'Boa noite;Boa tarde;Bom dia;Bom almoço', 'Qual a tradução para este sinal?', 'BOA NOITE', 1);

insert into question(correct_answer, answer_options, description, sign, id_lesson)
values('Boa tarde','Boa noite;Boa tarde;Bom dia;Bom almoço','Qual a tradução para este sinal?', 'BOA TARDE', 1);

insert into question(correct_answer, answer_options, description, sign, id_lesson)
values('Tudo bem?','Boa noite;Boa tarde;Bom dia;Tudo bem?','Qual a tradução para este sinal?', 'TUDO BEM?', 1);

INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('Qual número é esse?', 'Números', 'UM', '2', 'Um;Dois;Três;Quatro', 'Um');
INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('E este?', 'Números', 'TRÊS', '2', 'Dez;Um;Três;Treze', 'Três');
INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('Sabe qual é este número?', 'Números', 'DOZE', '2', 'Dois;Dez;Doze;Onze', 'Doze');

INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('Qual a tradução para esta cor?', 'Cores', 'AZUL', '4', 'Vermelho;Branco;Azul;Rosa', 'Azul');
INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('Como é esta cor em Português?', 'Cores', 'PRETO', '4', 'Verde;Azul;Preto;Roxo', 'Preto');
INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('E esta cor?', 'Cores', 'BRANCO', '4', 'Branco;Roxo;Vermelho;Cinza', 'Branco');

INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('Este sinal representa qual letra do alfabeto?', 'Alfabeto', 'A', '3', 'A;B;C;D', 'A');
INSERT INTO `helpinghand`.`question` (`description`, `module`, `sign`, `id_lesson`, `answer_options`, `correct_answer`) VALUES ('Este sinal representa qual letra do alfabeto?', 'Alfabeto', 'H', '3', 'H;E;K;L', 'H');

select * from question;