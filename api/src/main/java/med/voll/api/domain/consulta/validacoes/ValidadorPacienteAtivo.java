package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta {

    private PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var pacienteAtivo = this.pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());

        if (!pacienteAtivo) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
    }

}
