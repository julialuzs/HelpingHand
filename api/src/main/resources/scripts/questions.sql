insert into question (answer, description, module, sign, id_lesson) values ('Oi', 'O que esse sinal quer dizer?', 'Saudações', 'OI', 1);

insert into question(correct_answer, answer_options, description, module, sign, id_lesson)
values( 'Bom dia','Boa noite;Bom dia;Bom feriado;Tudo bem?','Qual a tradução para este sinal?', 'Saudações', 'BOM DIA', 1);

insert into question(correct_answer, answer_options, description, module, sign, id_lesson)
values('Boa noite', 'Boa noite;Boa tarde;Bom dia;Bom almoço', 'Qual a tradução para este sinal?', 'Saudações', 'BOA NOITE', 1);

insert into question(correct_answer, answer_options, description, module, sign, id_lesson)
values('Boa tarde','Boa noite;Boa tarde;Bom dia;Bom almoço','Qual a tradução para este sinal?', 'Saudações', 'BOA TARDE', 1);

insert into question(correct_answer, answer_options, description, module, sign, id_lesson)
values('Tudo bem?','Boa noite;Boa tarde;Bom dia;Tudo bem?','Qual a tradução para este sinal?', 'Saudações', 'TUDO BEM?', 1);

select * from question;