package ovh.cerebrum.meters.domain;

import java.time.LocalDate;

public interface MeterEntity {

    Long getId();

    Double getQuantity();

    LocalDate getReadDate();

    Long getUnitId();

    Long getUserId();

    void setId(Long id);

    void setQuantity(Double quantity);

    void setReadDate(java.time.LocalDate readDate);

    void setUnitId(Long unitId);

    void setUserId(Long userId);
}
