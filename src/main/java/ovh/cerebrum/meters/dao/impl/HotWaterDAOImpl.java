package ovh.cerebrum.meters.dao.impl;

import ovh.cerebrum.meters.dao.AbstractMeterDAO;
import ovh.cerebrum.meters.domain.HotWater;

public class HotWaterDAOImpl extends AbstractMeterDAO<HotWater> {

    private static final String TABLE_NAME = "hot_water";

    @Override
    protected Class<HotWater> getClazz() {
        return HotWater.class;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
