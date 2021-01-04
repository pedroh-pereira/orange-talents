package br.com.zup.orange.talents.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.orange.talents.exceptions.RegisterNotFoundException;
import br.com.zup.orange.talents.mappers.BaseMapper;

public abstract class GenericService<MODEL, DTO, ID, MAPPER extends BaseMapper<MODEL, DTO>, REPOSITORY extends JpaRepository<MODEL, ID>> {

  @Autowired
  protected REPOSITORY repository;

  @Autowired
  protected MAPPER mapper;

  protected abstract void executeBeforeSave(DTO dto) throws Exception;

  protected abstract void checkRegisterExists(final ID id) throws RegisterNotFoundException;

  public DTO save(DTO dto) throws Exception {
    executeBeforeSave(dto);
    return mapper.toDTO(repository.save(mapper.toEntity(dto)));
  }

  public Page<DTO> findAll(Pageable pageable) {
    return repository.findAll(pageable).map(mapper::toDTO);
  }

  public abstract Page<DTO> findFilters(DTO dto, Pageable pageable);

  public DTO update(final DTO dto, final ID id) throws RegisterNotFoundException {
    checkRegisterExists(id);
    return mapper.toDTO(repository.saveAndFlush(mapper.toEntity(dto)));
  }

  public void delete(ID id) throws RegisterNotFoundException {
    checkRegisterExists(id);
    repository.deleteById(id);
  }
}
