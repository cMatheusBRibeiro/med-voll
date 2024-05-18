package med.voll.api.medico;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,
        String nome,
        String telefone,

        @Embedded
        DadosEndereco endereco

) { }
