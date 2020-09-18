package br.com.basis.prova.dominio.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.basis.prova.util.CalculadorIdade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorListagemDTO implements Serializable { // DTO usado para consulta simples de professores
    
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String nome;
    private String matricula;
    private String area;
    @JsonIgnore
    private LocalDate dataNascimento;
    
    public Integer getIdade() {    	
        return CalculadorIdade.calcularIdade(this.dataNascimento);
    }
}