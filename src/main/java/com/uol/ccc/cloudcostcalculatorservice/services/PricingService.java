package com.uol.ccc.cloudcostcalculatorservice.services;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.uol.ccc.cloudcostcalculatorservice.models.ServerInstanceProduct;

public interface PricingService {
    List<?> getServices();

    List<JsonNode> getProducts();
}
