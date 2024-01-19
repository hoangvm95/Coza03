package com.kyura.message.payload.request;

import com.kyura.message.common.ROLE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String fullname;

    @Size(max = 20)
    private String phone;

    @Size(max = 50)
    private String avatar;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max=10)
    private String gender;

    private Date date_of_birth;

    private String address;

    @NotEmpty
    private Set<ROLE> roles;

    public SignupRequest( String email, String fullname, String phone, String gender, Date date_of_birth, Set<ROLE> roles, String password,String username) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.roles = roles;
    }
    public SignupRequest( String email, String phone, Set<ROLE> roles, String password,String username) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }
}
