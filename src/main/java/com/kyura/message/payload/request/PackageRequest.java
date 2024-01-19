package com.kyura.message.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String thumbnail;
    private BigDecimal price;
    private String age;
    private String gender;
    private long specialistId;
}
