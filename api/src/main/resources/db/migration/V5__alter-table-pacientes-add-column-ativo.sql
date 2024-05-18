ALTER TABLE pacientes ADD COLUMN ativo TINYINT(1) NOT NULL;
UPDATE pacientes SET ativo = 1;