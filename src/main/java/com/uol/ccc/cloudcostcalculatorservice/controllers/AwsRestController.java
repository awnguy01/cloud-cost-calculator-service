package com.uol.ccc.cloudcostcalculatorservice.controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.uol.ccc.cloudcostcalculatorservice.clients.impl.AwsPricingClient;
import com.uol.ccc.cloudcostcalculatorservice.models.AwsAvailableParameters;
import com.uol.ccc.cloudcostcalculatorservice.models.CloudService;
import com.uol.ccc.cloudcostcalculatorservice.models.InstancePriceParameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aws")
public class AwsRestController {
    @Autowired
    AwsPricingClient awsPricingClient;

    @GetMapping("/services")
    ResponseEntity<List<CloudService>> getServices() {
        awsPricingClient.getServices().stream().forEach(service -> {
            System.out.println(service);
        });
        return ResponseEntity.ok(awsPricingClient.getServices());
    }

    @GetMapping("/products")
    ResponseEntity<List<JsonNode>> getProducts(@RequestParam String operatingSystem, @RequestParam String memory,
            @RequestParam String vcpu) {
        InstancePriceParameters params = new InstancePriceParameters();

        params.setOperatingSystemFilter(operatingSystem);
        params.setMemoryFilter(memory);
        params.setVcpuFilter(vcpu);

        return ResponseEntity.ok(awsPricingClient.getServerInstanceProducts(params));
    }

    @GetMapping("/attributes")
    ResponseEntity<List<String>> getAttributes() {
        return ResponseEntity.ok(awsPricingClient.getAllAttributes());
    }

    @GetMapping("/parameters")
    ResponseEntity<AwsAvailableParameters> getParameters() {
        return ResponseEntity.ok(awsPricingClient.getFilterParameters());
    }

}
