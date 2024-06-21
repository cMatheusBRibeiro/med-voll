CREATE TABLE consultas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    medico_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    data DATETIME NOT NULL,
    CONSTRAINT pk_consulta_id
        PRIMARY KEY (id),
    CONSTRAINT fk_consulta_medico_id
        FOREIGN KEY (medico_id)
        REFERENCES medicos (id),
    CONSTRAINT fk_consulta_paciente_id
        FOREIGN KEY (paciente_id)
        REFERENCES pacientes (id)
)ENGINE=INNODB;