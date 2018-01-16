package ovh.cerebrum.meters.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ovh.cerebrum.meters.dao.UnitDAO;
import ovh.cerebrum.meters.domain.Unit;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UnitDAOImpl implements UnitDAO {

    private Sql2o sql2o;

    @Inject
    public UnitDAOImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Unit> create(Unit unit) {
        log.debug("Creating new unit.");

        try (Connection conn = sql2o.beginTransaction()) {
            Long id = conn.createQuery("INSERT INTO unit(unit_cd, name) VALUES(:unit_cd, :name)", true)
                    .addParameter("unit_cd", unit.getUnitCd())
                    .addParameter("name", unit.getName())
                    .executeUpdate()
                    .getKey(Long.class);

            conn.commit();

            if (id == null) {
                return Optional.empty();
            } else {
                log.debug("Unit created with id: " + id);
                unit.setId(id);
                return Optional.of(unit);
            }
        }
    }

    @Override
    public Optional<List<Unit>> getAll() {
        log.debug("Get all records for unit entity.");

        try (Connection conn = sql2o.open()) {
            List<Unit> units = conn.createQuery("SELECT * FROM unit").executeAndFetch(Unit.class);

            return Optional.ofNullable(units);
        }
    }

    @Override
    public Optional<Unit> findById(Long id) {
        log.debug("Find unit with id: " + id);
        try (Connection conn = sql2o.open()) {
            List<Unit> units = conn.createQuery("SELECT * FROM unit WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetch(Unit.class);

            if (units == null || units.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(units.get(0));
            }
        }
    }

    @Override
    public void update(Unit unit) {
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery("update unit set unit_cd=:unit_cd, name=:name where id=:id")
                    .addParameter("unit_cd", unit.getUnitCd())
                    .addParameter("name", unit.getName())
                    .addParameter("id", unit.getId())
                    .executeUpdate();

            conn.commit();
        }
    }
}
