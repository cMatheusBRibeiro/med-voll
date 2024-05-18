CREATE TABLE pacientes(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100) NULL,
    numero VARCHAR(20) NULL,
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    CONSTRAINT pk_pacientes_id
        PRIMARY KEY (id),
    CONSTRAINT unq_pacientes_email
        UNIQUE (email),
    CONSTRAINT unq_pacientes_cpf
        UNIQUE (cpf)
)ENGINE=INNODB;