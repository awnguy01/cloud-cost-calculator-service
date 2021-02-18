package com.uol.ccc.cloudcostcalculatorservice.models;

import java.util.Arrays;
import java.util.List;

import com.uol.ccc.cloudcostcalculatorservice.util.AwsUtils;

import lombok.Getter;
import software.amazon.awssdk.services.pricing.model.Filter;

@Getter
public class InstancePriceParameters {
    private Filter osFilter;
    private Filter memoryFilter;
    private Filter vcpuFilter;

    public List<Filter> getAllFilters() {
        return Arrays.asList(osFilter, memoryFilter, vcpuFilter);
    }

    public void setOperatingSystemFilter(String operatingSystem) {
        osFilter = AwsUtils.createFilter("operatingSystem", operatingSystem);
    }

    public void setMemoryFilter(String memory) {
        osFilter = AwsUtils.createFilter("memory", memory);
    }

    public void setVcpuFilter(String vcpu) {
        osFilter = AwsUtils.createFilter("vcpu", vcpu);
    }

}
