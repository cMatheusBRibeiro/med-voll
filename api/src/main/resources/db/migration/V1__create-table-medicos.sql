CREATE TABLE medicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    crm VARCHAR(6) NOT NULL,
    especialidade VARCHAR(100) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100) NULL,
    numero VARCHAR(20) NULL,
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    CONSTRAINT pk_medicos_id
        PRIMARY KEY (id),
    CONSTRAINT unq_medicos_email
        UNIQUE (email),
    CONSTRAINT unq_medicos_crm
        UNIQUE (crm)
)ENGINE=INNODB;