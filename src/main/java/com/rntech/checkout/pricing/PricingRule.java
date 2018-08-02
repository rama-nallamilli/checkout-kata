package com.rntech.checkout.pricing;

public interface PricingRule {
    Integer getTotalPrice(Integer unitPriceInPence, Integer totalOrdered);
}