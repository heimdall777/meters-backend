package ovh.cerebrum.meters.dao.impl;

import ovh.cerebrum.meters.dao.AbstractMeterDAO;
import ovh.cerebrum.meters.domain.ColdWater;

public class ColdWaterDAOImpl extends AbstractMeterDAO<ColdWater> {

    private static final String TABLE_NAME = "cold_water";

    @Override
    protected Class<ColdWater> getClazz() {
        return ColdWater.class;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
