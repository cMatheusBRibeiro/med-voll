package med.voll.api.domain.paciente;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(

        @NotBlank
        @JsonAlias({"nome"})
        String nome,

        @NotBlank
        @Email
        @JsonAlias({"email"})
        String email,

        @NotBlank
        @JsonAlias({"telefone"})
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
        @JsonAlias({"cpf"})
        String cpf,

        @Embedded
        @Valid
        @JsonAlias({"endereco"})
        DadosEndereco endereco
) { }
