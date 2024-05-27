package med.voll.api.medico;

import med.voll.api.endereco.DadosEndereco;

public record DadosDetalhamentoMedico(
    Long id,
    String nome,
    String crm,
    String telefone,
    Especialidade especialidade,
    DadosEndereco endereco
) {
    public DadosDetalhamentoMedico(Medico medico) {
        this(
            medico.getId(),
            medico.getNome(),
            medico.getCrm(),
            medico.getTelefone(),
            medico.getEspecialidade(),
            new DadosEndereco(medico.getEndereco())
        );
    }
}
