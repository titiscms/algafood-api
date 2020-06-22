ALTER TABLE restaurante ADD ativo TINYINT(1) NOT NULL;
UPDATE restaurante set ativo = true;