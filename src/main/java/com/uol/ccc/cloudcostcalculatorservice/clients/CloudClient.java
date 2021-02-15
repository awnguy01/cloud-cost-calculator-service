package com.uol.ccc.cloudcostcalculatorservice.clients;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.uol.ccc.cloudcostcalculatorservice.models.CloudService;
import com.uol.ccc.cloudcostcalculatorservice.models.ServerInstanceProduct;

public interface CloudClient {
    List<CloudService> getServices();

    List<JsonNode> getServerInstanceProducts();
}
