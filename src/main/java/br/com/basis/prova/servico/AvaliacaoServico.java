package br.com.basis.prova.servico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.AvaliacaoDTO;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.repositorio.AvaliacaoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AlunoListagemMapper;
import br.com.basis.prova.servico.mapper.AvaliacaoMapper;
import br.com.basis.prova.servico.mapper.DisciplinaListagemMapper;
import br.com.basis.prova.util.ValidadorData;

@Service
@Transactional
public class AvaliacaoServico {
	
	@Autowired
	private AvaliacaoMapper avaliacaoMapper;
	
	@Autowired
	private AlunoListagemMapper alunoMapper;
	
	@Autowired
	private DisciplinaListagemMapper disciplinaMapper;
	
	@Autowired
	private AvaliacaoRepositorio avaliacaoRepositorio;
	
	@Autowired
	private AlunoRepositorio alunoRepositorio;

	public AvaliacaoDTO salvar(AvaliacaoDTO avaliacaoDTO) {
		Avaliacao avaliacao = this.avaliacaoMapper.toEntity(avaliacaoDTO);
		avaliacao.setAluno(this.alunoMapper.toEntity(avaliacaoDTO.getAlunoDTO()));
		avaliacao.setDisciplina(this.disciplinaMapper.toEntity(avaliacaoDTO.getDisciplinaDTO()));
        if (this.validarAvaliacao(avaliacao)) {
        	this.avaliacaoRepositorio.save(avaliacao);
        	AvaliacaoDTO result = this.avaliacaoMapper.toDto(avaliacao);
        	result.setAlunoDTO(avaliacaoDTO.getAlunoDTO());
        	result.setDisciplinaDTO(avaliacaoDTO.getDisciplinaDTO());
        	return result;
        }
        return null;
    }
    
    private boolean validarAvaliacao(Avaliacao avaliacao) {   
    	
    	this.validarAluno(avaliacao.getAluno());
    	
    	this.validarDisciplina(avaliacao);
    	
    	this.validarNota(avaliacao.getNota());
    	
    	this.validarData(avaliacao.getData());
    	
    	return true;
    }
    
    private void validarAluno(Aluno aluno) {
    	if (aluno == null || aluno.getId() <= 0) {
    		throw new RegraNegocioException("Aluno é obrigatório.");
    	}
    	
    	if (this.alunoRepositorio.findById(aluno.getId()) == null) {
    		throw new RegraNegocioException("Aluno não encontrado.");
    	}
    }
    
    private void validarDisciplina(Avaliacao avaliacao) {
    	if (avaliacao.getDisciplina() == null || avaliacao.getDisciplina().getId() <= 0) {
    		throw new RegraNegocioException("Disciplina é obrigatório.");
    	}
    	
    	boolean flag = false;
    	Aluno aluno = this.alunoRepositorio.findById(avaliacao.getAluno().getId()).orElse(null);
    	if (aluno != null) {
    		if (aluno.getDisciplinas() != null && aluno.getDisciplinas().size() > 0) {
    			for (Disciplina disciplina : aluno.getDisciplinas()) {
    				if (avaliacao.getDisciplina().getId() == disciplina.getId()) {
    					flag = true;
    					break;
    				}
    			}
    		}    		
    	}
    	
    	if (!flag) {
    		throw new RegraNegocioException("Aluno não cursa esta disciplina.");
    	}
    }
    
    private void validarNota(Double nota) {
    	if (nota < 0) {
    		throw new RegraNegocioException("Nota inválida.");
    	}
    }
    
    private void validarData(LocalDate data) {
    	if (!ValidadorData.validarData(data)) {
    		throw new RegraNegocioException("Data formato inválido.");
    	}
    }

    public void excluir(Integer id) {
    	this.avaliacaoRepositorio.deleteById(id);
    }
    
    private List<AvaliacaoDTO> popularAvaliacaoDTO(List<Avaliacao> avaliacoes) {
    	List<AvaliacaoDTO> avaliacoesDTO = new ArrayList<>();
    	if (avaliacoes != null && avaliacoes.size() > 0) {
	    	for(Avaliacao avaliacao : avaliacoes) {
	    		AvaliacaoDTO avaliacaoDTO = this.avaliacaoMapper.toDto(avaliacao);
	    		avaliacaoDTO.setAlunoDTO(this.alunoMapper.toDto(avaliacao.getAluno()));
	    		avaliacaoDTO.setDisciplinaDTO(this.disciplinaMapper.toDto(avaliacao.getDisciplina()));	    		
	    		avaliacoesDTO.add(avaliacaoDTO);
	    	} 
	    	return avaliacoesDTO;
    	}    	
        return null;
    }
    
    public List<AvaliacaoDTO> consultar() {    	
    	List<Avaliacao> avaliacoes = this.avaliacaoRepositorio.findAll();
        return this.popularAvaliacaoDTO(avaliacoes);
    }
    
    public List<AvaliacaoDTO> consultarPorAluno(Integer idAluno) {
    	Aluno aluno = new Aluno();
    	aluno.setId(idAluno);
    	
    	List<Avaliacao> avaliacoes = this.avaliacaoRepositorio.findByAluno(aluno);
        return this.popularAvaliacaoDTO(avaliacoes);
    }
    
    public List<AvaliacaoDTO> consultarPorDisciplina(Integer idDisciplina) {
    	Disciplina disciplina = new Disciplina();
    	disciplina.setId(idDisciplina);
    	
    	List<Avaliacao> avaliacoes = this.avaliacaoRepositorio.findByDisciplina(disciplina);
        return this.popularAvaliacaoDTO(avaliacoes);
    }

    public AvaliacaoDTO detalhar(Integer id) {    	
    	Avaliacao avaliacao = this.avaliacaoRepositorio.findById(id).orElse(null);
    	if (avaliacao != null) {
    		AvaliacaoDTO avaliacaoDTO = this.avaliacaoMapper.toDto(avaliacao);
    		avaliacaoDTO.setAlunoDTO(this.alunoMapper.toDto(avaliacao.getAluno()));
    		avaliacaoDTO.setDisciplinaDTO(this.disciplinaMapper.toDto(avaliacao.getDisciplina()));
    		return avaliacaoDTO;
    	}
    	return null;
    }
}