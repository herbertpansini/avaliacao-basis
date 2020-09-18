package br.com.basis.prova.dominio.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
    private Integer ativa;
    @JsonProperty("professor")
    private ProfessorDTO professorDTO;
}