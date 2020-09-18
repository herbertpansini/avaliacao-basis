package br.com.basis.prova.servico;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.DisciplinaListagemDTO;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.dominio.dto.ProfessorDetalhadoDTO;
import br.com.basis.prova.dominio.dto.ProfessorListagemDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.DisciplinaListagemMapper;
import br.com.basis.prova.servico.mapper.ProfessorDetalhadoMapper;
import br.com.basis.prova.servico.mapper.ProfessorListagemMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import br.com.basis.prova.util.ValidadorData;

@Service
@Transactional
public class ProfessorServico {
	
    private ProfessorRepositorio professorRepositorio;
    private ProfessorMapper professorMapper;
    
    @Autowired
    private ProfessorListagemMapper professorListagemMapper;
    
    @Autowired
    private ProfessorDetalhadoMapper professorDetalhadoMapper;
    
    @Autowired
    private DisciplinaListagemMapper disciplinaListagemMapper;

    @Autowired
    private DisciplinaRepositorio disciplinaRepositorio;

    public ProfessorServico(ProfessorMapper professorMapper, ProfessorRepositorio professorRepositorio) {
        this.professorMapper = professorMapper;
        this.professorRepositorio = professorRepositorio;
    }

    public ProfessorDTO salvar(ProfessorDTO professorDTO) {
    	Professor professor = this.professorMapper.toEntity(professorDTO);    	
    	if (this.ValidarProfessor(professor)) {
    		this.professorRepositorio.save(professor);
    		return this.professorMapper.toDto(professor);
    	}
        return null;
    }
    
    private boolean ValidarProfessor(Professor professor) {
    	
    	this.validarMatricula(professor.getMatricula());
    	
    	this.validarNome(professor.getNome());
    	
    	this.validarArea(professor.getArea());
    	
    	this.validarDataDeNascimento(professor.getDataNascimento());
    	
    	return true;
    }
    
    private void validarMatricula(String matricula) {
    	if (matricula == null || matricula.isEmpty()) {
            throw new RegraNegocioException("Matrícula é obrigatório.");
        }
    	
    	if (matricula.length() != 6) {
    		throw new RegraNegocioException("Matrícula deve conter 6 dígitos.");
    	}
    }
    
    private void validarNome(String nome) {
    	if (nome == null || nome.isEmpty()) {
            throw new RegraNegocioException("Nome é obrigatório.");
        }
    	
    	if (nome.length() > 50) {
    		throw new RegraNegocioException("Nome deve ter de 1 a 50 caracteres.");
    	}
    }
    
    private void validarArea(String area) {
    	if ((area != null && !area.isEmpty()) && area.length() > 200) {
            throw new RegraNegocioException("Área de Atuação deve ter de 1 a 200 caracteres.");
        }
    }
    
    private void validarDataDeNascimento(LocalDate dataNascimento) {
    	if (!ValidadorData.validarData(dataNascimento)) {
    		throw new RegraNegocioException("Data de Nascimento formato inválido.");
    	}
    }

    public void excluir(String matricula) {
        Professor professor = this.professorRepositorio.findByMatricula(matricula).orElse(null);
        if (professor != null) {
        	List<Disciplina> disciplinas = disciplinaRepositorio.findByProfessor(professor);
        	if (disciplinas == null || disciplinas.size() == 0) {
        		this.professorRepositorio.delete(professor);
        	}
        }        
    }

    public List<ProfessorListagemDTO> consultar() {
    	return this.professorListagemMapper.toDto(this.professorRepositorio.findAll());
    }

    public ProfessorDetalhadoDTO detalhar(Integer id) {
    	Professor professor = this.professorRepositorio.findById(id).orElse(null);
    	ProfessorDetalhadoDTO professorDetalhadoDTO = null;
    	if (professor != null) {
    		List<DisciplinaListagemDTO> disciplinasListagemDTO = this.disciplinaListagemMapper.toDto(this.disciplinaRepositorio.findDisciplinasAtivasByProfessor(professor));    		
    		professorDetalhadoDTO = professorDetalhadoMapper.toDto(professor);
    		professorDetalhadoDTO.setDisciplinas(disciplinasListagemDTO);
    	}
    	return professorDetalhadoDTO;
    }
}
