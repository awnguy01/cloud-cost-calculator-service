package com.uol.ccc.cloudcostcalculatorservice.clients.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uol.ccc.cloudcostcalculatorservice.clients.CloudClient;
import com.uol.ccc.cloudcostcalculatorservice.models.CloudService;
import com.uol.ccc.cloudcostcalculatorservice.models.ServerInstanceProduct;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.pricing.PricingClient;
import software.amazon.awssdk.services.pricing.model.Filter;
import software.amazon.awssdk.services.pricing.model.GetProductsRequest;
import software.amazon.awssdk.services.pricing.model.GetProductsResponse;

@Component
@Slf4j
public class AwsPricingClient implements CloudClient {

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
    public List<JsonNode> getServerInstanceProducts() {
        // Filter filter = Filter.builder().field("productFamily").build();
        String nextToken = null;
        List<JsonNode> products = new ArrayList<>();
        try {
            do {
                GetProductsResponse productsResponse = client.getProducts(
                        GetProductsRequest.builder().serviceCode("AmazonEC2").nextToken(nextToken).build());
                for (String productStr : productsResponse.priceList()) {
                    products.add(objectMapper.readTree(productStr));
                }
                nextToken = productsResponse.nextToken();
                System.out.println(nextToken);
            } while (nextToken != null);
        } catch (JsonProcessingException e) {
            log.error("Error processing server instance JSON object", e);
        }
        return products;
    }
}
