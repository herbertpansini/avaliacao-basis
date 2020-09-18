package br.com.basis.prova.dominio.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	@JsonProperty("aluno")
	private AlunoListagemDTO alunoDTO;
	@JsonProperty("disciplina")
	private DisciplinaListagemDTO disciplinaDTO;
	private Double nota;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data;
}