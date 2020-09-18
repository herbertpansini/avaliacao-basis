package br.com.basis.prova.servico;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AlunoDetalhadoMapper;
import br.com.basis.prova.servico.mapper.AlunoListagemMapper;
import br.com.basis.prova.servico.mapper.AlunoMapper;
import br.com.basis.prova.util.ValidadorData;

@Service
@Transactional
public class AlunoServico {

    private AlunoMapper alunoMapper;
    
    @Autowired
    private AlunoListagemMapper alunoListagemMapper;
    
    @Autowired
    private AlunoDetalhadoMapper alunoDetalhadoMapper;
    
    private AlunoRepositorio alunoRepositorio;

    public AlunoServico(AlunoMapper alunoMapper, AlunoRepositorio alunoRepositorio) {
        this.alunoMapper = alunoMapper;
        this.alunoRepositorio = alunoRepositorio;
    }

    public AlunoDTO salvar(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.toEntity(alunoDTO);
        if (this.validarAluno(aluno)) {
        	this.alunoRepositorio.save(aluno);
    		return this.alunoMapper.toDto(aluno);
        }
        return null;
    }
    
    private boolean validarAluno(Aluno aluno) {    	
    	
    	this.validarCPF(aluno);
    	
    	this.validarNome(aluno.getNome());
    	
    	this.validarDataDeNascimento(aluno.getDataNascimento());
    	
    	this.validarMatricula(aluno);
    	
    	return true;
    }
    
    private void validarCPF(Aluno aluno) {
    	
    	if (aluno.getCpf() == null || aluno.getCpf().isEmpty()) {
    		throw new RegraNegocioException("CPF é obrigatório.");
    	}
    	
    	if (aluno.getCpf().length() != 11 || !aluno.getCpf().matches("^[0-9]+$")) {
    		throw new RegraNegocioException("CPF deve conter 11 caracteres numéricos.");
    	}
    	
    	if (verificarCPF(aluno)) {
            throw new RegraNegocioException("CPF já existe.");
        }
    }
    
    private void validarNome(String nome) {
    	
    	if (nome == null || nome.isEmpty()) {
    		throw new RegraNegocioException("Nome é obrigatório.");
    	}
    	
    	if (nome.length() < 4 || nome.length() > 50) {
    		throw new RegraNegocioException("Nome deve conter de 4 à 50 caracteres.");
    	}
    }
    
    private void validarDataDeNascimento(LocalDate dataNascimento) {
    	
    	if (!ValidadorData.validarData(dataNascimento)) {
    		throw new RegraNegocioException("Data de Nascimento formato inválido.");
    	}
    }
    
    private void validarMatricula(Aluno aluno) {
    	if (aluno.getMatricula() == null || aluno.getMatricula().isEmpty()) {
    		throw new RegraNegocioException("Matrícula é obrigatório.");
    	}
    	
    	if (aluno.getMatricula().length() != 6) {
    		throw new RegraNegocioException("Matrícula deve conter 6 caracteres.");
    	}
    	
    	if (verificarMatricula(aluno)) {
    		throw new RegraNegocioException("Matrícula já existe.");
    	}
    }
    
    private boolean verificarMatricula(Aluno aluno) {
    	Aluno alunoExiste = alunoRepositorio.findByMatricula(aluno.getMatricula()).orElse(null);
        return !(alunoExiste == null || alunoExiste.getId().equals(aluno.getId()));
    }
    
    private boolean verificarCPF(Aluno aluno) {
        Aluno alunoCpf = alunoRepositorio.findByCpf(aluno.getCpf());
        return !(alunoCpf == null || alunoCpf.getId().equals(aluno.getId()));
    }

    public void excluir(String matricula) {
    	Aluno aluno = this.alunoRepositorio.findByMatricula(matricula).orElse(null);
    	if (aluno != null) {
	    	if (aluno.getDisciplinas() == null || aluno.getDisciplinas().size() == 0) {
	    		this.alunoRepositorio.deleteById(aluno.getId());
	    	}
    	}
    }

    public List<AlunoListagemDTO> consultar() {
        return alunoListagemMapper.toDto(this.alunoRepositorio.findAll());
    }

    public AlunoDetalhadoDTO detalhar(Integer id) {    	
    	return this.alunoDetalhadoMapper.toDto(this.alunoRepositorio.findById(id).orElse(null));
    }
}
