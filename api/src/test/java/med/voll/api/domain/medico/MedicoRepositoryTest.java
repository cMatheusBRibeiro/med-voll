package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Deveria devolver null quando o único médico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario01() {
        // Given ou Arrange
        var proximaSegundaAs10 = LocalDate.now().with(
            TemporalAdjusters.next(DayOfWeek.MONDAY)
        ).atTime(10, 0);
        var medico = this.cadastrarMedico(
            "Medico",
            "medico@voll.med",
            "123456",
            Especialidade.CARDIOLOGIA
        );
        var paciente = this.cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        this.cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        // When ou Act
        var medicoLivre = this.medicoRepository.escolherMedicoAleatorioLivreNaData(
            Especialidade.CARDIOLOGIA,
            proximaSegundaAs10
        );

        // Then ou Assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver o único médico cadastrado disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario02() {
        // Given ou Arrange
        var proximaSegundaAs10 = LocalDate.now().with(
                TemporalAdjusters.next(DayOfWeek.MONDAY)
        ).atTime(10, 0);
        var medico = this.cadastrarMedico(
            "Medico",
            "medico@voll.med",
            "123456",
            Especialidade.CARDIOLOGIA
        );

        // When ou Act
        var medicoLivre = this.medicoRepository.escolherMedicoAleatorioLivreNaData(
            Especialidade.CARDIOLOGIA,
            proximaSegundaAs10
        );

        // Then ou Assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria devolver null pois o único médico cadastrado disponível na data é de outra especialidade")
    void escolherMedicoAleatorioLivreNaDataCenario03() {
        // Given ou Arrange
        var proximaSegundaAs10 = LocalDate.now().with(
                TemporalAdjusters.next(DayOfWeek.MONDAY)
        ).atTime(10, 0);
        this.cadastrarMedico(
                "Medico",
                "medico@voll.med",
                "123456",
                Especialidade.GINECOLOGIA
        );

        // When ou Act
        var medicoLivre = this.medicoRepository.escolherMedicoAleatorioLivreNaData(
                Especialidade.CARDIOLOGIA,
                proximaSegundaAs10
        );

        // Then ou Assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver o segundo médico pois o primeiro médico já possui consulta marcada")
    void escolherMedicoAleatorioLivreNaDataCenario04() {
        // Given ou Arrange
        var proximaSegundaAs10 = LocalDate.now().with(
                TemporalAdjusters.next(DayOfWeek.MONDAY)
        ).atTime(10, 0);
        var primeiroMedico = this.cadastrarMedico(
                "Primeiro Medico",
                "primeiro.medico@voll.med",
                "123456",
                Especialidade.CARDIOLOGIA
        );
        var segundoMedico = this.cadastrarMedico(
                "Segundo Medico",
                "segundo.medico@voll.med",
                "456789",
                Especialidade.CARDIOLOGIA
        );
        var paciente = this.cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        this.cadastrarConsulta(primeiroMedico, paciente, proximaSegundaAs10);

        // When ou Act
        var medicoLivre = this.medicoRepository.escolherMedicoAleatorioLivreNaData(
                Especialidade.CARDIOLOGIA,
                proximaSegundaAs10
        );

        // Then ou Assert
        assertThat(medicoLivre).isEqualTo(segundoMedico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        this.testEntityManager.persist(
            new Consulta(null, medico, paciente, data, null)
        );
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(this.dadosMedico(nome, email, crm, especialidade));
        this.testEntityManager.persist(medico);
        return medico;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
            nome,
            email,
            "61999999999",
            crm,
            especialidade,
            this.dadosEndereco()
        );
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(this.dadosPaciente(nome, email, cpf));
        this.testEntityManager.persist(paciente);
        return paciente;
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
            nome,
            email,
            "61999999999",
            cpf,
            this.dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
            "rua xpto",
            "bairro",
            "00000000",
            "Brasilia",
            "DF",
            null,
            null
        );
    }
}