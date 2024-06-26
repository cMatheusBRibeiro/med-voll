package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoSemConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var isMedicoPossuiConsultaNoMesmoHorario = this.consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(
            dadosAgendamentoConsulta.idMedico(),
            dadosAgendamentoConsulta.data()
        );
        if (isMedicoPossuiConsultaNoMesmoHorario) {
            throw new ValidacaoException("O médico já possui consulta nesse horário!");
        }
    }

}
