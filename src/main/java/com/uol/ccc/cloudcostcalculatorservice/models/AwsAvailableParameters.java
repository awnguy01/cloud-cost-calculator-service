package com.uol.ccc.cloudcostcalculatorservice.models;

import java.util.List;

import lombok.Data;

@Data
public class AwsAvailableParameters {
    private List<String> operatingSystem;
    private List<String> memory;
    private List<String> vcpu;
    private List<String> instanceType;
    private List<String> maxVolumeSize;
    private List<String> volumeType;
}
