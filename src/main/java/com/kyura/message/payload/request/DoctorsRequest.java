package com.kyura.message.payload.request;

import com.kyura.message.common.ROLE;
import com.kyura.message.models.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DoctorsRequest {
    @NotBlank
    @Size(max=50)
    private String fullname;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max=20)
    private String phone;

    @NotBlank
    private Date date_of_birth;

    @NotBlank
    @Size(max=10)
    private String gender;

    private Long hospitalsId;

    private Long specialityId;

    private Set<ROLE> role;

    private String password = "sfdgsdfgsg444";

    @NotBlank
    private String username;

//    private String avatar;
    private MultipartFile file;

    private String desc;

}
