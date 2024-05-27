package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = this.repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> detalharMedico(@PathVariable Long id) {
        var medico = this.repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrarMedico(
        @RequestBody @Valid DadosCadastroMedico dadosCadastroMedico,
        UriComponentsBuilder uriComponentsBuilder
    ) {
        var medico = new Medico(dadosCadastroMedico);
        this.repository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        var medico = this.repository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizarInformacoes(dadosAtualizacaoMedico);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirMedico(@PathVariable Long id) {
        var medico = this.repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

}
