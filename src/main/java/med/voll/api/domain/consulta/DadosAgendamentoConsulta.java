package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(

    Long idMedico,

    @NotNull
    Long idPaciente,

    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm") // Não é obrigatório, ele somente vai mudar o formato da data que receberá
    LocalDateTime data,

    Especialidade especialidade

) {
}
