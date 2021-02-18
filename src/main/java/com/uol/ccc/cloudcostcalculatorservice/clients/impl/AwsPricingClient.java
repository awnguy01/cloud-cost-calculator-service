package com.uol.ccc.cloudcostcalculatorservice.clients.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uol.ccc.cloudcostcalculatorservice.clients.CloudClient;
import com.uol.ccc.cloudcostcalculatorservice.models.AwsAvailableParameters;
import com.uol.ccc.cloudcostcalculatorservice.models.CloudService;
import com.uol.ccc.cloudcostcalculatorservice.models.InstancePriceParameters;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.pricing.PricingClient;
import software.amazon.awssdk.services.pricing.model.DescribeServicesRequest;
import software.amazon.awssdk.services.pricing.model.GetAttributeValuesRequest;
import software.amazon.awssdk.services.pricing.model.GetProductsRequest;
import software.amazon.awssdk.services.pricing.paginators.GetAttributeValuesIterable;

@Component
@Slf4j
public class AwsPricingClient implements CloudClient {
    private static final String EC2_SERVICE_CODE = "AmazonEC2";

    private static PricingClient client;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private AwsPricingClient() {
        client = PricingClient.builder().region(Region.US_EAST_1).build();
    }

    @Override
    public List<CloudService> getServices() {
        return client.describeServices().services().stream().map(service -> {
            return new CloudService(service.serviceCode(), service.attributeNames());
        }).collect(Collectors.toList());
    }

    @Override
    public List<JsonNode> getServerInstanceProducts(InstancePriceParameters params) {
        List<JsonNode> products = new ArrayList<>();

        GetProductsRequest request = GetProductsRequest.builder().serviceCode(EC2_SERVICE_CODE)
                .filters(params.getAllFilters()).build();

        client.getProductsPaginator(request).iterator().forEachRemaining(res -> {
            res.priceList().stream().forEach(product -> {
                try {
                    products.add(objectMapper.readTree(product));
                } catch (JsonProcessingException e) {
                    log.error("Error processing server instance JSON object", e);
                }
            });
        });

        return products;
    }

    public AwsAvailableParameters getFilterParameters() {
        AwsAvailableParameters params = new AwsAvailableParameters();

        params.setOperatingSystem(getAllAttributeValues("operatingSystem"));
        params.setMemory(getAllAttributeValues("memory"));
        params.setVcpu(getAllAttributeValues("vcpu"));
        params.setInstanceType(getAllAttributeValues("instanceType"));
        params.setMaxVolumeSize(getAllAttributeValues("maxVolumeSize"));
        params.setVolumeType(getAllAttributeValues("volumeType"));

        return params;
    }

    public List<String> getAllAttributes() {
        List<String> attributes = new ArrayList<>();
        DescribeServicesRequest request = DescribeServicesRequest.builder().serviceCode("AmazonEC2").build();
        client.describeServicesPaginator(request).iterator().forEachRemaining(res -> {
            attributes.addAll(res.services().get(0).attributeNames());
        });

        Collections.sort(attributes);

        return attributes;
    }

    private static List<String> getAllAttributeValues(String attribute) {
        List<String> valueList = new ArrayList<>();

        GetAttributeValuesRequest request = GetAttributeValuesRequest.builder().serviceCode(EC2_SERVICE_CODE)
                .attributeName(attribute).build();
        GetAttributeValuesIterable responses = client.getAttributeValuesPaginator(request);

        responses.iterator().forEachRemaining(res -> {
            res.attributeValues().stream().forEach(val -> {
                valueList.add(val.value());
            });
        });

        Collections.sort(valueList);

        return valueList;
    }
}
