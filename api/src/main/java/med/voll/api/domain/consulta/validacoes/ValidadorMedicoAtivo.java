package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (dadosAgendamentoConsulta.idMedico() == null) {
            return;
        }

        var medicoAtivo = this.medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());

        if (!medicoAtivo) {
            throw new ValidacaoException("Não pode agendar consulta com um médico desativado!");
        }
    }

}
