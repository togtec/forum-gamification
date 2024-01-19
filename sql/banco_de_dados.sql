-------------------------------------------------------------
-- comandos para criar o banco de dados no SGBD PostgreSQL --
------------------------------------------------------------- 

-- cria o usuário ita 
CREATE ROLE ita WITH
LOGIN
PASSWORD 'admin';

-- cria o banco de dados
CREATE DATABASE forum_gamification_db
    WITH 
    OWNER = ita;
    
COMMENT ON DATABASE forum_gamification_db
    IS 'Banco de dados utilizado no projeto C3W4B_ForumGamification
Curso: Desenvolvimento Ágil com Java Avançado
Por: ITA/Coursera';





-- cria a tabela usuario
-- obs: esta tabela não tem sequence
CREATE TABLE IF NOT EXISTS usuario (
    login TEXT NOT NULL,
    email TEXT NOT NULL,
    nome TEXT NOT NULL,
    senha TEXT NOT NULL,
    pontos INTEGER NOT NULL,
    status TEXT NOT NULL,
    nivel TEXT NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (login)
);

ALTER TABLE IF EXISTS usuario
    OWNER to ita;
    
    
    
-- cria a tabela forum
CREATE SEQUENCE IF NOT EXISTS forum_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE forum_id_seq
    OWNER TO ita;

CREATE TABLE IF NOT EXISTS forum (
    id_forum INTEGER NOT NULL DEFAULT nextval('forum_id_seq'::regclass),
    login TEXT NOT NULL,
    data_de_inicio DATE NOT NULL,
    nome TEXT NOT NULL,
    descricao TEXT NOT NULL,
    CONSTRAINT forum_pkey PRIMARY KEY (id_forum),
    CONSTRAINT forum_login_fkey FOREIGN KEY (login)
        REFERENCES usuario (login) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS forum
    OWNER to ita;
    
    
            
-- cria a tabela topico
CREATE SEQUENCE IF NOT EXISTS topico_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE topico_id_seq
    OWNER TO ita;
    
CREATE TABLE IF NOT EXISTS topico (
    id_topico INTEGER NOT NULL DEFAULT nextval('topico_id_seq'::regclass),
    id_forum INTEGER NOT NULL,
    login TEXT NOT NULL,
    data DATE NOT NULL,
    nome TEXT NOT NULL,
    conteudo TEXT NOT NULL,
    CONSTRAINT topico_pkey PRIMARY KEY (id_topico),
    CONSTRAINT topico_id_forum_fkey FOREIGN KEY (id_forum)
        REFERENCES forum (id_forum) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT topico_login_fkey FOREIGN KEY (login)
        REFERENCES usuario (login) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS topico
    OWNER to ita;
    
    
        
-- cria a tabela comentario
CREATE SEQUENCE IF NOT EXISTS comentario_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE comentario_id_seq
    OWNER TO ita;
           
CREATE TABLE IF NOT EXISTS comentario (
    id_comentario INTEGER NOT NULL DEFAULT nextval('comentario_id_seq'::regclass),
    id_topico INTEGER NOT NULL,
    login TEXT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    comentario TEXT NOT NULL,
    CONSTRAINT comentario_pkey PRIMARY KEY (id_comentario),
    CONSTRAINT comentario_id_topico_fkey FOREIGN KEY (id_topico)
        REFERENCES topico (id_topico) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT comentario_login_fkey FOREIGN KEY (login)
        REFERENCES usuario (login) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS comentario
    OWNER to ita;
    

    

    
-- inserir dados nas tabelas
INSERT INTO usuario (login, email, nome, senha, pontos, status, nivel)
VALUES
('paulo.cintra', 'paulo.cintra@globo.com', 'Paulo Cintra', '1234', '135', 'ACTIVE', 'USER'),
('flaviosp', 'flaviosp@bol.com.br', 'Flavio Sampaio', '5678', '1', 'ACTIVE', 'USER'),
('alelima', 'alelima@gmail.com', 'Alessandra Lima', 'ab1234', '25', 'ACTIVE', 'USER'),
('sabcoral', 'sabcoral@gmail.com', 'Sabrina Coral', 'vg3516', '223', 'ACTIVE', 'ADM'),
('braraujo', 'braraujo@gmail.com', 'Bruno Araujo', 'sat3456', '0', 'BLOCKED', 'USER'),
('ibatista', 'ibatista@bol.com.br', 'Igor Batista', 'sds9021', '32', 'ACTIVE', 'USER'),
('tatialc', 'tatialcantara@globo.com', 'Tatiana Alcantara', '1216', '43', 'ACTIVE', 'ADM');

INSERT INTO forum (id_forum, login, data_de_inicio, nome, descricao)
VALUES
(1, 'sabcoral', '2022-05-01', 'Jogo Das Palavras Embaralhadas', 'Fórum destinado a discutir temas relacionados ao Jogo Das Palavras Embaralhadas. O Jogo Das Palavras Embaralhadas é o trabalho final de conclusão do curso Orientação a Objetos com Java. Esse curso é ministrado pelo ITA - Instituto Tecnológico de Aeronáutica - através da plataforma de ensino online Coursera.'),
(2, 'sabcoral', '2022-05-03', 'Dune 2000', 'Fórum destinado a discutir temas relacionados ao Jogo Dune 2000. Dune 2000 é um jogo de estratégia em tempo real, desenvolvido pela Intelligent Games e lançado pela Westwood Studios em 1998 para Microsoft Windows. Mais tarde, foi portado para o PlayStation em 1999. É um remake parcial de Dune II, que é vagamente baseado no universo Dune de Frank Herbert. A história do jogo é semelhante a Dune II, e continua em Emperor: Battle for Dune.'),
(3, 'tatialc', '2022-06-01', 'Doom', 'Fórum destinado a discutir temas relacionados ao Jogo Doom. Doom é um jogo eletrônico de tiro em primeira pessoa desenvolvido pela id Software e publicado pela Bethesda Softworks.');

INSERT INTO topico (id_topico, id_forum, login, data, nome, conteudo)
VALUES
(1, 1, 'alelima', '2022-05-01', 'Implementações de MecanicaDoJogo', 'Olá! Abri este tópico para compartilharmos ideias de implementações da interface MecanicaDoJogo. Os requisitos pedem ao menos duas implementações mas confesso que estou sem ideias!'),
(2, 1, 'paulo.cintra', '2022-05-02', 'Implementações de Embaralhador', 'Alguém tem uma boa ideia para implementar a interface embaralhador?'),
(3, 2, 'flaviosp', '2022-05-03', 'Teclas de Atalho', 'Segue abaixo a lista de teclas de atalho do Dune 2000 descobertas por mim até agora.'),
(4, 2, 'ibatista', '2022-05-04', 'Ordem das construções', 'Alguém sabe qual é a melhor ordem para as construções?'),
(5, 2, 'sabcoral', '2022-05-05', 'Harkonnen 07 Hard', 'Eu consigo passar todas as fases, menos a  Harkonnen 07. No nível de dificuldade Hard parece impossível. Não para de vir inimigo! Alguém está tendo problemas também?');

INSERT INTO comentario (id_comentario, id_topico, login, data_hora, comentario)
VALUES
(1, 1, 'paulo.cintra', '2022-05-01 11:43:01.794', 'Minha ideia é criar uma mecânica em que o jogador não tenha limite de palpites. Dessa forma, o jogador só avança para a palavra seguinte quando acertar a palavra atual. Nessa mecânica, a partida só termina quando o jogador acertar todas as palavras.'),
(2, 1, 'ibatista', '2022-05-01 12:50:00.000', 'Legal! Boa ideia! Mas e se o jogador realmente não souber a palavra. Ele fica preso na palavra em que está para sempre? A partida não acaba nunca?'),
(3, 1, 'flaviosp', '2022-05-01 14:08:35.891', 'Para resolver seria necessário criar um limite de tempo para encerrar a partida. Por exemplo, o jogador pode emitir quantos palpites precisar por no máximo 5 minutos. Nesse caso, a partida encerra quando o jogador acertar todas as palavras, ou então, quando se esgotar o tempo limite.'),
(4, 1, 'alelima', '2022-05-01 22:49:59.122', 'Mas criar um objeto para contar o tempo das partidas pode ser algo complicado! Alguém sabe como fazer isso?'),
(5, 1, 'sabcoral', '2022-05-02 13:50:12.535', 'Uma outra solução seria, por exemplo, permitir que o jogador desista da palavra atual e avance para a palavra seguinte mediante um comando. Como se ele desistisse da palavra atual. Nesse caso ele não fica enrroscado em uma palavra específica, e não precisa criar um limite de tempo da partida. É importante notar que cada vez que ele desisti de adivinhar a palavra atual e avançar para a palavra seguinte, deixa de ganhar os pontos referentes à palavra atual. Ou seja, vai sempre querer acertar todas, e só vai desistir daquelas que realmente não sabe.'),
(6, 1, 'ibatista', '2022-05-02 23:53:03.837', 'Utilizando a ideia acima de criar uma mecânica baseada no número de palpites, estou pensando em criar uma mecânica em que o usuário tenha 3 palpites por palavra. Se ele utilizar os 3 palpites e não adivinhar a palavra 1, por exemplo, a mecânica avança automaticamente para a palavra número 2, que por sua vez também tem 3 palpites. Entretanto, se acertar a palavra 1 já no primeiro palpite, além de avançar para a palavra 2, também ganha um bônus pelos 2 palpites não utilizados.'),
(7, 1, 'flaviosp', '2022-05-03 09:29:10.487', 'Legal! É possível fazer também uma mecânica baseada no número de palpites por partida. Por exemplo: O jogador tem 10 palpites para utilizar na partida inteira. Se ele acertar todas as palavras e ainda sobrarem 4 palpites, por exemplo, ganha um bônus para cada palpite não utilizado. Nesse caso, se acertar todas as palavras com o primeiro palpite, sobram 9, o que garante um bônus gigante.'),
(8, 5, 'flaviosp', '2022-05-06 09:30:03.854', 'Eu também achei desproporcional a dificuldade do nível Hard. É praticamente impossível! Eu também confesso que ainda não consegui passar!'),
(9, 5, 'tatialc', '2022-05-07 03:54:15.563', 'Realmente é muito difícil, mas não impossível. Após jogar 93 vezes eu consegui passar. É necessário treinar até se acostumar com a fase e saber tudo de cabeça que você precisa fazer. Se parar um segundo para pensar já era.'),
(10, 5, 'sabcoral', '2022-05-07 22:45:00.002', 'Qual a ordem de construções que você utilizou?'),
(11, 5, 'tatialc', '2022-05-08 08:22:05.121', 'Primeiro eu construo a energia e a barraca. Então começo a treinar tropas sem parar (infantaria leve). Então construo três refinarias, a fabrica de carros de combate pesado - e ai já começo a fabricar carros de combate pesados sem parar. Quando tiver uns 6 (carros de combate pesados) construo mais três mineradoes. Então a fábrica de carros de combate leve, o centro de alta tecnologia - para fabricar aviões. E por fim o reparo.');