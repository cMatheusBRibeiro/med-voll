package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadoresAgendamentoConsulta;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (!this.pacienteRepository.existsById(dadosAgendamentoConsulta.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if (
            dadosAgendamentoConsulta.idMedico() != null &&
            !this.medicoRepository.existsById(dadosAgendamentoConsulta.idMedico())
        ) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        this.validadoresAgendamentoConsulta.forEach(v -> v.validar(dadosAgendamentoConsulta));

        var paciente = this.pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());
        var medico = this.escolherMedico(dadosAgendamentoConsulta);

        if (medico == null) {
            throw new ValidacaoException("Não há médicos disponíveis nessa data e horário!");
        }

        var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data(), null);
        this.consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(Long id, DadosCancelamentoConsulta dadosCancelamentoConsulta) {
        if (!this.consultaRepository.existsById(id)) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }
        var consulta = this.consultaRepository.getReferenceById(id);
        consulta.cancelar(dadosCancelamentoConsulta.motivoCancelamento());
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (dadosAgendamentoConsulta.idMedico() != null) {
            return this.medicoRepository.getReferenceById(dadosAgendamentoConsulta.idMedico());
        }

        if (dadosAgendamentoConsulta.especialidade() == null) {
            throw new ValidacaoException("A especialidade é obrigatória quando o médico não for escolhido!");
        }

        return this.medicoRepository.escolherMedicoAleatorioLivreNaData(
            dadosAgendamentoConsulta.especialidade(),
            dadosAgendamentoConsulta.data()
        );
    }
}
