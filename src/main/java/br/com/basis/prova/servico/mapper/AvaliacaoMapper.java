package br.com.basis.prova.servico.mapper;

import org.mapstruct.Mapper;

import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.dto.AvaliacaoDTO;

@Mapper(componentModel = "spring", uses = {AlunoListagemMapper.class, DisciplinaListagemMapper.class})
public interface AvaliacaoMapper extends EntityMapper<AvaliacaoDTO, Avaliacao> {

}