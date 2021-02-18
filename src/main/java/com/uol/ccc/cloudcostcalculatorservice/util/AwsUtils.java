package com.uol.ccc.cloudcostcalculatorservice.util;

import software.amazon.awssdk.services.pricing.model.Filter;
import software.amazon.awssdk.services.pricing.model.FilterType;

public class AwsUtils {
    private AwsUtils() {
    }

    public static Filter createFilter(String field, String value) {
        return Filter.builder().type(FilterType.TERM_MATCH).field(field).value(value).build();
    }
}
