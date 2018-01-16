package ovh.cerebrum.meters.dao.impl;

import ovh.cerebrum.meters.dao.AbstractGenericDAO;
import ovh.cerebrum.meters.domain.Energy;

public class EnergyDAOImpl extends AbstractGenericDAO<Energy> {

    public static final String TABLE_NAME = "energy";

    @Override
    protected Class<Energy> getClazz() {
        return Energy.class;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
