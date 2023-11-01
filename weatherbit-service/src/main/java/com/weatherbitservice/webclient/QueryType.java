package com.weatherbitservice.webclient;

public enum QueryType {
    CITY("city"),
    ZIPCODE("postal_code");

    private final String queryValue;

    QueryType(String queryValue) {
        this.queryValue = queryValue;
    }

    public String getValue() {
        return queryValue;
    }
}
