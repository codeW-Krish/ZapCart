package com.example.zapcart.models;

import java.util.List;

public class ProductResponse {
    public List<Product> productList;
    private int total;
    private int skip;
    private int limit;

    public ProductResponse(List<Product> productList, int total, int limit, int skip) {
        this.productList = productList;
        this.total = total;
        this.skip = limit;
        this.limit = skip;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProducts(){
        return productList;
    }
    @Override
    public String toString() {
        return "ProductResponse{products=" + productList + '}';
    }
}
