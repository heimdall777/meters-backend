package ovh.cerebrum.meters.dao.impl;

import ovh.cerebrum.meters.dao.AbstractMeterDAO;
import ovh.cerebrum.meters.domain.Heating;

public class HeatingDAOImpl extends AbstractMeterDAO<Heating> {

    private static final String TABLE_NAME = "heating";

    @Override
    protected Class<Heating> getClazz() {
        return Heating.class;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
