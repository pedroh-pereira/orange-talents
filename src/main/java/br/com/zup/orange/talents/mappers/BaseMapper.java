package br.com.zup.orange.talents.mappers;

public interface BaseMapper<M, D> {
  M toEntity(D dto);
  D toDTO(M entity);
}
