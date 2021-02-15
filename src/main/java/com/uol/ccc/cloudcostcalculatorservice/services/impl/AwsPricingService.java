package com.uol.ccc.cloudcostcalculatorservice.services.impl;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.uol.ccc.cloudcostcalculatorservice.clients.impl.AwsPricingClient;
import com.uol.ccc.cloudcostcalculatorservice.models.CloudService;
import com.uol.ccc.cloudcostcalculatorservice.models.ServerInstanceProduct;
import com.uol.ccc.cloudcostcalculatorservice.services.PricingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwsPricingService implements PricingService {
    @Autowired
    AwsPricingClient awsPricingClient;

    @Override
    public List<CloudService> getServices() {
        return awsPricingClient.getServices();
    }

    @Override
    public List<JsonNode> getProducts() {
        return awsPricingClient.getServerInstanceProducts();
    }
}
