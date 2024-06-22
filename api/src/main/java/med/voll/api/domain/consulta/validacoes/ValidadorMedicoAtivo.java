package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;

public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta {

    private MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (dadosAgendamentoConsulta.idMedico() == null) {
            return;
        }

        var medicoAtivo = this.medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());

        if (!medicoAtivo) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }
    }

}
