package ovh.cerebrum.meters.dao.impl;

import ovh.cerebrum.meters.dao.AbstractMeterDAO;
import ovh.cerebrum.meters.domain.Gas;

public class GasDAOImpl extends AbstractMeterDAO<Gas> {

    private static final String TABLE_NAME = "gas";

    @Override
    protected Class<Gas> getClazz() {
        return Gas.class;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
