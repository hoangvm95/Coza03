package com.kyura.message.payload.request;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.Specialists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePackageClinicRequest {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String age;

    private String gender;

    private ACTIVE_STATUS status;

    private Long specialistsId;
}
