package ovh.cerebrum.meters.dao.impl;

import ovh.cerebrum.meters.dao.UnitDAO;
import ovh.cerebrum.meters.domain.Unit;

import java.util.List;
import java.util.Optional;

public class UnitDAOImpl implements UnitDAO {
    @Override
    public Optional<Unit> create(Unit entity) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Unit>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Unit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Unit entity) {

    }
}
