package ovh.cerebrum.meters.dao;

import lombok.extern.slf4j.Slf4j;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ovh.cerebrum.meters.domain.MeterEntity;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractMeterDAO<T extends MeterEntity> implements MeterDAO<T> {

    private Class<T> clazz;

    private Sql2o sql2o;

    @Override
    public Optional<T> create(T entity) {
        clazz = getClazz();

        log.debug("Creating new entity " + clazz.getSimpleName() + " created.");

        try (Connection conn = sql2o.beginTransaction()) {
            Long id = conn.createQuery(getInsertQuery(), true)
                    .addParameter("quantity", entity.getQuantity())
                    .addParameter("read_date", entity.getReadDate())
                    .addParameter("unit_id", entity.getUnitId())
                    .addParameter("user_id", entity.getUserId())
                    .executeUpdate()
                    .getKey(Long.class);

            conn.commit();

            if (id == null) {
                return Optional.empty();
            } else {
                log.debug(clazz.getSimpleName() + " created with id: " + id);
                entity.setId(id);
                return Optional.of(entity);
            }
        }
    }

    @Override
    public Optional<List<T>> getAll() {
        clazz = getClazz();

        log.debug("Get all records for entity " + clazz.getSimpleName());

        try (Connection conn = sql2o.open()) {
            List<T> entity = conn.createQuery(getSelectQuery()).executeAndFetch(clazz);

            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        clazz = getClazz();

        log.debug("Find record " + clazz.getSimpleName() + "for id: " + id);
        try (Connection conn = sql2o.open()) {
            List<T> entities = conn.createQuery(getSelectQuery("id=:id"))
                    .addParameter("id", id)
                    .executeAndFetch(clazz);

            if (entities == null || entities.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(entities.get(0));
            }
        }
    }

    @Override
    public void update(T entity) {
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery(getUpdateQuery())
                    .addParameter("quantity", entity.getQuantity())
                    .addParameter("read_date", entity.getReadDate())
                    .addParameter("unit_id", entity.getUnitId())
                    .addParameter("user_id", entity.getUserId())
                    .addParameter("id", entity.getId())
                    .executeUpdate();

            conn.commit();
        }
    }

    @Inject
    public void setSql2o(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    private String getInsertQuery() {
        return "INSERT INTO " +
                getTableName() +
                "(quantity, read_date, unit_id, user_id) VALUES(:quantity, :read_date, :unit_id, :user_id)";
    }

    private String getSelectQuery() {
        return getSelectQuery("");
    }

    private String getSelectQuery(String whereCondition) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
                .append(getTableName());

        if (!whereCondition.isEmpty()) {
            sb.append(" WHERE ")
                    .append(whereCondition);
        }

        return sb.toString();
    }

    private String getUpdateQuery() {
        return "update " +
                getTableName() +
                " set quantity=:quantity, read_date=:read_date, unit_id=:unit_id, user_id=:user_id where id=:id";
    }

    protected abstract Class<T> getClazz();

    protected abstract String getTableName();
}
