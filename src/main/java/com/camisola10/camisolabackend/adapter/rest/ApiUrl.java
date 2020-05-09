package com.camisola10.camisolabackend.adapter.rest;

public interface ApiUrl {
    String API = "/api";

    String AUTH = API + "/auth";
    String SIGN_UP = AUTH + "/sign-up";

    String PRODUCTS = API + "/products";
    String SETTINGS = API + "/settings";
    String ORDERS = API + "/orders";
}
