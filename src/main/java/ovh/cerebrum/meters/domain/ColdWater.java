package ovh.cerebrum.meters.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ColdWater implements MeterEntity {

    private Long id;

    @NonNull
    private Double quantity;

    @NonNull
    private LocalDate readDate;

    @NonNull
    private Long unitId;

    @NonNull
    private Long userId;
}
