package br.com.basis.prova.servico.mapper;

import org.mapstruct.Mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;

@Mapper(componentModel = "spring", uses = {ProfessorMapper.class})
public interface DisciplinaMapper extends EntityMapper<DisciplinaDTO, Disciplina> {
}
