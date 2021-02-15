package com.uol.ccc.cloudcostcalculatorservice.controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.uol.ccc.cloudcostcalculatorservice.models.CloudService;
import com.uol.ccc.cloudcostcalculatorservice.models.ServerInstanceProduct;
import com.uol.ccc.cloudcostcalculatorservice.services.impl.AwsPricingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aws")
public class AwsRestController {
    @Autowired
    AwsPricingService awsPricingService;

    @GetMapping("/services")
    ResponseEntity<List<CloudService>> getServices() {
        awsPricingService.getServices().stream().forEach(service -> {
            System.out.println(service);
        });
        return ResponseEntity.ok(awsPricingService.getServices());
    }

    @GetMapping("/products")
    ResponseEntity<List<JsonNode>> getProducts() {
        return ResponseEntity.ok(awsPricingService.getProducts());
    }

}
