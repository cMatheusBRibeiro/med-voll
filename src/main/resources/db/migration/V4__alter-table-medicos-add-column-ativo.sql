ALTER TABLE medicos ADD COLUMN ativo TINYINT(1) NOT NULL;
UPDATE medicos SET ativo = 1;