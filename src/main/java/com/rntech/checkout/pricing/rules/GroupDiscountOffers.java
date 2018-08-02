package com.rntech.checkout.pricing.rules;

import com.rntech.checkout.pricing.PricingRule;

public class GroupDiscountOffers {

  public static final PricingRule threeFor130 = (unitPrice, totalOrdered) -> {
    final Integer numRequiredForDiscount = 3;
    final Integer groupDiscountPrice = 130;
    return calculateGroupDiscountPrice(numRequiredForDiscount, groupDiscountPrice, unitPrice, totalOrdered);
  };

  public static final PricingRule twoFor45 = (unitPrice, totalOrdered) -> {
    final Integer numRequiredForDiscount = 2;
    final Integer groupDiscountPrice = 45;
    return calculateGroupDiscountPrice(numRequiredForDiscount, groupDiscountPrice, unitPrice, totalOrdered);
  };

  private static Integer calculateGroupDiscountPrice(
      Integer numRequiredForDiscount,
      Integer groupDiscountPrice,
      Integer unitPrice,
      Integer totalOrdered) {

    final Integer discounted = groupDiscountPrice * (totalOrdered / numRequiredForDiscount);
    final Integer full = unitPrice * (totalOrdered % numRequiredForDiscount);
    return discounted + full;
  }
}
