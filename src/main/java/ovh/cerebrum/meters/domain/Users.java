package ovh.cerebrum.meters.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Users {

    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime created_at;
}
