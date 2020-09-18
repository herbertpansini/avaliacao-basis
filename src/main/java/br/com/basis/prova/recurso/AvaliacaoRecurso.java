package br.com.basis.prova.recurso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.basis.prova.dominio.dto.AvaliacaoDTO;
import br.com.basis.prova.servico.AvaliacaoServico;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoRecurso {
	
	private static final String API_AVALIACOES = "/avaliacoes";

	@Autowired
	private AvaliacaoServico avaliacaoServico;
	
	@PostMapping
    public ResponseEntity<AvaliacaoDTO> salvar(@RequestBody AvaliacaoDTO avaliacaoDTO) throws URISyntaxException {
        AvaliacaoDTO result = avaliacaoServico.salvar(avaliacaoDTO);
        return ResponseEntity.created(new URI(API_AVALIACOES + result.getId())).body(result);
    }

    @PutMapping
    public ResponseEntity<AvaliacaoDTO> editar(@RequestBody AvaliacaoDTO avaliacaoDTO) throws URISyntaxException {
        AvaliacaoDTO result = avaliacaoServico.salvar(avaliacaoDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        avaliacaoServico.excluir(id);
        return ResponseEntity.status(200).build();
    }
    
    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> consultar() {
        return ResponseEntity.ok(avaliacaoServico.consultar());
    }

    @GetMapping("/{id}/aluno")
    public ResponseEntity<List<AvaliacaoDTO>> consultarPorAluno(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(avaliacaoServico.consultarPorAluno(id));
    }
    
    @GetMapping("/{id}/disciplina")
    public ResponseEntity<List<AvaliacaoDTO>> consultarPorDisciplina(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(avaliacaoServico.consultarPorDisciplina(id));
    }

    @GetMapping("/detalhes/{id}")
    public ResponseEntity<AvaliacaoDTO> detalhar(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(avaliacaoServico.detalhar(id));
    }
}