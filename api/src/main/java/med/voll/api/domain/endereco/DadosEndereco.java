package med.voll.api.domain.endereco;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(

    @NotBlank
    @JsonAlias({"logradouro"})
    String logradouro,

    @NotBlank
    @JsonAlias({"bairro"})
    String bairro,

    @NotBlank
    @Pattern(regexp = "\\d{8}")
    @JsonAlias({"cep"})
    String cep,

    @NotBlank
    @JsonAlias({"cidade"})
    String cidade,

    @NotBlank
    @JsonAlias({"uf", "estado"})
    String uf,

    @JsonAlias({"complemento"})
    String complemento,

    @JsonAlias({"numero"})
    String numero

) {
    public DadosEndereco(Endereco endereco) {
        this(
            endereco.getLogradouro(),
            endereco.getBairro(),
            endereco.getCep(),
            endereco.getCidade(),
            endereco.getUf(),
            endereco.getComplemento(),
            endereco.getNumero()
        );
    }
}
