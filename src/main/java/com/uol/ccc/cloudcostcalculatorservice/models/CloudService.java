package com.uol.ccc.cloudcostcalculatorservice.models;

import java.util.List;

import lombok.Data;

@Data
public class CloudService {
    private String serviceCode;
    private List<String> attributes;

    public CloudService(String serviceCode, List<String> attributes) {
        this.serviceCode = serviceCode;
        this.attributes = attributes;
    }

}
