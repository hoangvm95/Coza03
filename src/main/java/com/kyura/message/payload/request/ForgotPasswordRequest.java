package com.kyura.message.payload.request;

import com.kyura.message.common.ROLE;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

}
