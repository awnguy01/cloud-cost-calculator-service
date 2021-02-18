package com.uol.ccc.cloudcostcalculatorservice.clients;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.uol.ccc.cloudcostcalculatorservice.models.CloudService;
import com.uol.ccc.cloudcostcalculatorservice.models.InstancePriceParameters;

public interface CloudClient {
    public List<CloudService> getServices();

    public List<JsonNode> getServerInstanceProducts(InstancePriceParameters params);
}
