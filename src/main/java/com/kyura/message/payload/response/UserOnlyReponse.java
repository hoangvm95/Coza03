package com.kyura.message.payload.response;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.common.ROLE;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserOnlyReponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Date date_of_birth;
    private String gender;
    private ACTIVE_STATUS status;
    private Instant createdAt;

}
