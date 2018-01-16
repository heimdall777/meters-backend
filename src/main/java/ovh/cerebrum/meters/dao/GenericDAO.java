package ovh.cerebrum.meters.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {

    Optional<T> create(T entity);

    Optional<List<T>> getAll();

    Optional<T> findById(Long id);

    void update(T entity);
}
