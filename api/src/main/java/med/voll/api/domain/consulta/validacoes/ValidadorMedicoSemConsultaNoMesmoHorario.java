package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoSemConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {

    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var isMedicoPossuiConsultaNoMesmoHorario = this.consultaRepository.existsByMedicoIdAndData(
            dadosAgendamentoConsulta.idMedico(),
            dadosAgendamentoConsulta.data()
        );
        if (isMedicoPossuiConsultaNoMesmoHorario) {
            throw new ValidacaoException("O médico já possui consulta nesse horário!");
        }
    }

}
