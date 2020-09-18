package br.com.basis.prova.servico.mapper;

import org.mapstruct.Mapper;

import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.ProfessorListagemDTO;

@Mapper(componentModel = "spring", uses = {DisciplinaMapper.class})
public interface ProfessorListagemMapper  extends EntityMapper<ProfessorListagemDTO, Professor> {
}
