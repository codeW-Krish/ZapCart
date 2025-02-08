package com.example.zapcart.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("products")
    private List<Product> productList;

    @SerializedName("total")
    private int total;

    @SerializedName("skip")
    private int skip;

    @SerializedName("limit")
    private int limit;

    public ProductResponse(List<Product> productList, int total, int skip, int limit) {
        this.productList = productList;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public List<Product> getProducts() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "ProductResponse{products=" + productList + '}';
    }
}
