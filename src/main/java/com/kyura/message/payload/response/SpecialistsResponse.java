package com.kyura.message.payload.response;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.PackageClinic;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class SpecialistsResponse {

    private Long id;
    private String name;
    private String thumbnail;
    private String description;
    private ACTIVE_STATUS status;
    private Set<PackageClinic> listPackageClinics;
}
