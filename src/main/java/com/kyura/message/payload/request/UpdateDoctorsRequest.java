package com.kyura.message.payload.request;

import com.kyura.message.common.ROLE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDoctorsRequest {

    private Long id;
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

//    private String password;

//    private String avatar;

    private String desc;

    private MultipartFile file;
}
