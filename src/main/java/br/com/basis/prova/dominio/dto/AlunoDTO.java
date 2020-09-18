package br.com.basis.prova.dominio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@Getter
@Setter
public class AlunoDTO implements Serializable { // DTO usado para salvar e editar um aluno

	private static final long serialVersionUID = 1L;
	private Integer id;
    private String nome;
    private String cpf;
    private String matricula;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;    
    private List<DisciplinaDTO> disciplinas = new ArrayList<>();

}
