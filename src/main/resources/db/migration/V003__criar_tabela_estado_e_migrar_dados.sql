CREATE TABLE estado (
	id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estado (nome) SELECT DISTINCT estado_nome FROM cidade;

ALTER TABLE cidade ADD COLUMN estado_id BIGINT NOT NULL;

UPDATE cidade c SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.estado_nome);

ALTER TABLE cidade ADD CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE cidade DROP COLUMN estado_nome;

ALTER TABLE cidade CHANGE cidade_nome nome VARCHAR(80) NOT NULL