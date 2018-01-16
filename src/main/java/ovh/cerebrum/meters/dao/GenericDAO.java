package ovh.cerebrum.meters.dao;

import ovh.cerebrum.meters.domain.MeterEntity;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T extends MeterEntity> {

    Optional<T> create(T entity);

    Optional<List<T>> getAll();

    Optional<T> findById(Long id);

    void update(T entity);
}
