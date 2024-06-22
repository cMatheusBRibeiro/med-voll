package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorPacienteSemConsultaNoMesmoDia implements ValidadorAgendamentoConsulta {

    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var primeiroHorario = dadosAgendamentoConsulta.data().withHour(7);
        var ultimoHorario = dadosAgendamentoConsulta.data().withHour(18);
        var isPacientePossuiOutraConsultaNoDia = this.consultaRepository.existsByPacienteIdAndDataBetween(
            dadosAgendamentoConsulta.idPaciente(),
            primeiroHorario,
            ultimoHorario
        );
        if (isPacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia!");
        }
    }

}
