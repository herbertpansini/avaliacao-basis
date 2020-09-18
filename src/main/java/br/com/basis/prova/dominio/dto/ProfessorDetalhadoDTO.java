package br.com.basis.prova.dominio.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorDetalhadoDTO implements Serializable {	
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String nome;
    private String matricula;    
    private List<DisciplinaListagemDTO> disciplinas = new ArrayList<>();
}
