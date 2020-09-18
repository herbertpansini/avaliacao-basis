package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.basis.prova.util.CalculadorIdade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AlunoListagemDTO implements Serializable { // DTO usado para consulta simples de alunos
    
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String nome;
    private String matricula;
    @JsonIgnore
    private LocalDate dataNascimento;
   
    public Integer getIdade() {    	
        return CalculadorIdade.calcularIdade(this.dataNascimento);
    }

}
