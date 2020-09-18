package br.com.basis.prova.servico.mapper;

import org.mapstruct.Mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;

@Mapper(componentModel = "spring")
public interface AlunoListagemMapper extends EntityMapper<AlunoListagemDTO, Aluno> {
}
