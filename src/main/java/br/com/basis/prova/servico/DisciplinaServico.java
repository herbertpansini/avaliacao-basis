package br.com.basis.prova.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaListagemDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.DisciplinaDetalhadaMapper;
import br.com.basis.prova.servico.mapper.DisciplinaListagemMapper;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;

@Service
@Transactional
public class DisciplinaServico {

    private DisciplinaRepositorio disciplinaRepositorio;
    private DisciplinaMapper disciplinaMapper;

    @Autowired
    private DisciplinaListagemMapper disciplinaListagemMapper;
    
    @Autowired
    private DisciplinaDetalhadaMapper disciplinaDetalhadaMapper;
    
    @Autowired
    private ProfessorMapper professorMapper;
    
    @Autowired
    private ProfessorRepositorio professorRepositorio;

    public DisciplinaServico(DisciplinaMapper disciplinaMapper, DisciplinaRepositorio disciplinaRepositorio) {
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaRepositorio = disciplinaRepositorio;
    }

    public DisciplinaDTO salvar(DisciplinaDTO disciplinaDTO) {
    	Disciplina disciplina = this.disciplinaMapper.toEntity(disciplinaDTO);
    	disciplina.setProfessor(this.professorMapper.toEntity(disciplinaDTO.getProfessorDTO()));
        if (this.validarDisciplina(disciplina)) {        
        	this.disciplinaRepositorio.save(disciplina);
        	DisciplinaDTO result = this.disciplinaMapper.toDto(disciplina);
        	result.setProfessorDTO(disciplinaDTO.getProfessorDTO());
        	return result;
        }
    	return null;
    }
    
    private boolean validarDisciplina(Disciplina disciplina) {
    	
    	this.validarNome(disciplina);
    	
    	this.validarDescricao(disciplina.getDescricao());
    	
    	this.validarCargaHoraria(disciplina.getCargaHoraria());
    	
    	this.validarProfessor(disciplina.getProfessor());
    	
    	return true;
    }
    
    private void validarNome(Disciplina disciplina) {
    	
    	if (disciplina.getNome() == null || disciplina.getNome().isEmpty()) {
    		throw new RegraNegocioException("nome é obrigatório");
    	}
    	
    	if (disciplina.getNome().length() < 4 || disciplina.getNome().length() > 50) {
    		throw new RegraNegocioException("Nome deve ter de 4 à 50 caracteres.");
    	}
    	
    	if (this.verificarNome(disciplina)) {
    		throw new RegraNegocioException("Disciplina já existe");
    	}
    }
    
    private boolean verificarNome(Disciplina disciplina) {
        Disciplina disciplinaExiste = this.disciplinaRepositorio.findByNome(disciplina.getNome());
        return !(disciplinaExiste == null || disciplinaExiste.getId().equals(disciplina.getId()));
    }
    
    private void validarDescricao(String descricao) {
    	
    	if (descricao == null || descricao.isEmpty()) {
    		throw new RegraNegocioException("Descrição é obrigatório.");
    	}
    	
    	if (descricao.length() > 200) {
    		throw new RegraNegocioException("Nome deve ter de 1 à 200 caracteres.");
    	}
    }
    
    private void validarCargaHoraria(Integer cargaHoraria) {    	
    	if (cargaHoraria <= 0) {
    		throw new RegraNegocioException("Carga Horária é obrigatório.");
    	}
    }
    
    private void validarProfessor(Professor professor) {
    	if (professor == null || professor.getId() <= 0) {
    		throw new RegraNegocioException("Professor é obrigatório.");
    	}
    	
    	if (this.professorRepositorio.findById(professor.getId()) == null) {
    		throw new RegraNegocioException("Professor não encontrado.");
    	}
    }

    public void excluir(Integer id) {
    	this.disciplinaRepositorio.deleteById(id);
    }

    public List<DisciplinaListagemDTO> consultar() {
        return disciplinaListagemMapper.toDto(disciplinaRepositorio.findAll());
    }

    public DisciplinaDetalhadaDTO detalhar(Integer id) {
    	Disciplina disciplina = this.disciplinaRepositorio.findById(id).orElse(null); 
    	DisciplinaDetalhadaDTO disciplinaDetalhadaDTO = null;
    	if (disciplina != null) {
    		disciplinaDetalhadaDTO = disciplinaDetalhadaMapper.toDto(this.disciplinaRepositorio.findById(id).orElse(null));
    		disciplinaDetalhadaDTO.setProfessorDTO(this.professorMapper.toDto(disciplina.getProfessor()));
    	}        
    	return disciplinaDetalhadaDTO;
    }
}
