package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

    @MockBean
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deveria devolver o código http 400 quando enviado informações inválidas")
    @WithMockUser
    void cadastrarMedicoCenario01() throws Exception {
        var response = this.mockMvc.perform(
            post("/medicos")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 200 quando enviado informações válidas")
    @WithMockUser
    void cadastrarMedicoCenario02() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
            "Medico",
            "medico@med.voll",
            "61999999999",
            "123456",
            Especialidade.CARDIOLOGIA,
            this.dadosEndereco()
        );
        var dadosDetalhamentoMedico = new DadosDetalhamentoMedico(
            null,
            "Medico",
            "123456",
            "61999999999",
            Especialidade.CARDIOLOGIA,
            this.dadosEndereco()
        );

        when(this.medicoRepository.save(any())).thenReturn(new Medico(dadosCadastroMedico));

        var response = this.mockMvc.perform(
            post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.dadosCadastroMedicoJson.write(
                        dadosCadastroMedico
                    ).getJson()
                )
        ).andReturn().getResponse();

        var jsonEsperado = this.dadosDetalhamentoMedicoJson.write(dadosDetalhamentoMedico).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando não enviar o CRM")
    @WithMockUser
    void cadastrarMedicoCenario03() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
            "Medico",
            "medico@med.voll",
            "61999999999",
            null,
            Especialidade.CARDIOLOGIA,
            this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
            post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.dadosCadastroMedicoJson.write(
                        dadosCadastroMedico
                    ).getJson()
                )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando não enviar o e-mail")
    @WithMockUser
    void cadastrarMedicoCenario04() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                null,
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando enviar o e-mail sem o @")
    @WithMockUser
    void cadastrarMedicoCenario05() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medicomed.voll",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando enviar o e-mail sem o voll no final")
    @WithMockUser
    void cadastrarMedicoCenario06() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medico@med.",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando enviar o e-mail sem o nome")
    @WithMockUser
    void cadastrarMedicoCenario07() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "@med.voll",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando enviar o e-mail sem o domínio no final")
    @WithMockUser
    void cadastrarMedicoCenario08() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medico@",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando não enviar o telefone")
    @WithMockUser
    void cadastrarMedicoCenario09() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medico@med.voll",
                null,
                "123456",
                Especialidade.CARDIOLOGIA,
                this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando não enviar a especialidade")
    @WithMockUser
    void cadastrarMedicoCenario10() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medico@med.voll",
                "61999999999",
                "123456",
                null,
                this.dadosEndereco()
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código http 400 quando não enviar o endereço")
    @WithMockUser
    void cadastrarMedicoCenario11() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medico@med.voll",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                null
        );

        var response = this.mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                this.dadosCadastroMedicoJson.write(
                                        dadosCadastroMedico
                                ).getJson()
                        )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
            "Rua xpto",
            "Bairro",
            "00000000",
            "Brasilia",
            "DF",
            null,
            null
        );
    }
}