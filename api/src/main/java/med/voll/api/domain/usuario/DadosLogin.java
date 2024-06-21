package med.voll.api.domain.usuario;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosLogin(
        @JsonAlias({"login"})
        String login,

        @JsonAlias({"senha"})
        String senha
) { }
