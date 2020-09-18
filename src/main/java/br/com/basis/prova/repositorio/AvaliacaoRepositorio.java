package br.com.basis.prova.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.Disciplina;

@Repository
public interface AvaliacaoRepositorio extends  JpaRepository<Avaliacao, Integer>, JpaSpecificationExecutor<Avaliacao> {
	
	List<Avaliacao> findByAluno(Aluno aluno);
	
	List<Avaliacao> findByDisciplina(Disciplina disciplina);
}