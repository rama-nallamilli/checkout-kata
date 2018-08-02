package com.rntech.checkout;

import com.rntech.checkout.basket.domain.Basket;
import com.rntech.checkout.pricing.PricingRule;
import com.rntech.checkout.pricing.domain.ItemPrice;
import com.rntech.checkout.sku.ItemSku;
import com.rntech.checkout.sku.SkuNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class Checkout {

  private final PricingRule defaultPricingRule;

  public Checkout(PricingRule defaultPricingRule) {
    this.defaultPricingRule = defaultPricingRule;
  }

  public Integer checkout(Basket basket, Map<ItemSku, ItemPrice> pricingRules) {

    final Set<Map.Entry<ItemSku, Integer>> quantityPerSku = basket.getItems()
        .stream()
        .collect(groupingBy(identity(), summingInt(sku -> 1)))
        .entrySet();

    return quantityPerSku.stream()
        .map(skuWithQuantity -> calculatePrice(skuWithQuantity.getKey(), skuWithQuantity.getValue(), pricingRules))
        .mapToInt(Integer::intValue)
        .sum();
  }

  private Integer calculatePrice(ItemSku sku, Integer quantity, Map<ItemSku, ItemPrice> rules) {
    final ItemPrice pricing = Optional.ofNullable(rules.get(sku))
            .orElseThrow(() -> new SkuNotFoundException(sku));

    final PricingRule rule = pricing.getPricingRule()
            .orElse(defaultPricingRule);

    return rule.getTotalPrice(pricing.getUnitPriceInPence(), quantity);
  }
}
