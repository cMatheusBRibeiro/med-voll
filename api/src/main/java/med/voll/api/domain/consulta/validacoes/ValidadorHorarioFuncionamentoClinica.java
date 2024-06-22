package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var dataConsulta = dadosAgendamentoConsulta.data();

        var isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var isAntesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var isDepoisDoFechamentoDaClinica = dataConsulta.getHour() > 18;

        if (isDomingo || isAntesDaAberturaDaClinica || isDepoisDoFechamentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica!");
        }
    }

}
