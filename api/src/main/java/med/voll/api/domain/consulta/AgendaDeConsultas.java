package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if (!this.pacienteRepository.existsById(dadosAgendamentoConsulta.idPaciente())) {
            throw new ValidacaoException("O id do paciente informado não existe!");
        }

        if (
            dadosAgendamentoConsulta.idMedico() != null &&
            !this.medicoRepository.existsById(dadosAgendamentoConsulta.idMedico())
        ) {
            throw new ValidacaoException("O id do médico informado não existe!");
        }

        var paciente = this.pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());
        var medico = this.escolherMedico(dadosAgendamentoConsulta);
        var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data());
        this.consultaRepository.save(consulta);
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
