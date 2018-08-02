package com.rntech.checkout.pricing.domain;

import java.util.Objects;
import java.util.Optional;

import com.rntech.checkout.pricing.PricingRule;


public class ItemPrice {
  private final Integer unitPriceInPence;
  private final PricingRule pricingRule;


  public ItemPrice(Integer unitPriceInPence, PricingRule pricingRule) {
    this.unitPriceInPence = unitPriceInPence;
    this.pricingRule = pricingRule;
  }

  public ItemPrice(Integer unitPriceInPence) {
    this.unitPriceInPence = unitPriceInPence;
    this.pricingRule = null;
  }

  public Optional<PricingRule> getPricingRule() {
    return Optional.ofNullable(pricingRule);
  }

  public Integer getUnitPriceInPence() {
    return unitPriceInPence;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemPrice itemPrice = (ItemPrice) o;
    return Objects.equals(pricingRule, itemPrice.pricingRule) &&
        Objects.equals(unitPriceInPence, itemPrice.unitPriceInPence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pricingRule, unitPriceInPence);
  }

}
