package com.kyura.message.payload.request;

import com.kyura.message.common.TypeImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompositeRequest {
    private Long id;

    private Long idTarget;

    private TypeImage idType;

    private MultipartFile file;

}
