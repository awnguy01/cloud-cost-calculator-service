package com.uol.ccc.cloudcostcalculatorservice.models;

import java.util.Map;

import lombok.Data;

@Data
public class ServerInstanceProduct {
    Map<String, Object> product;
    String serviceCode;
}
