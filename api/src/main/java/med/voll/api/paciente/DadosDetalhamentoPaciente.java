package med.voll.api.paciente;

import med.voll.api.endereco.DadosEndereco;

public record DadosDetalhamentoPaciente(
    Long id,
    String nome,
    String email,
    String cpf,
    String telefone,
    DadosEndereco endereco
) {
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(
            paciente.getId(),
            paciente.getNome(),
            paciente.getEmail(),
            paciente.getCpf(),
            paciente.getTelefone(),
            new DadosEndereco(paciente.getEndereco())
        );
    }
}
