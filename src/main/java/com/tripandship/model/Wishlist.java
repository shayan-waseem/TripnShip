package com.tripandship.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private String userId;
    private List<String> packageIds = new ArrayList<>();

    // Getters & Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<String> getPackageIds() { return packageIds; }
    public void setPackageIds(List<String> packageIds) { this.packageIds = packageIds; }
}