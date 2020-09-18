package br.com.basis.prova.dominio;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AVALIACAO")
@Getter
@Setter
@NoArgsConstructor
public class Avaliacao {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;
	
	@ManyToOne
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;
	
	private Double nota;	
	
	private LocalDate data;
}
