package ovh.cerebrum.meters.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Unit {

    private Long id;

    @NonNull
    private String unitCd;

    @NonNull
    private String name;
}
