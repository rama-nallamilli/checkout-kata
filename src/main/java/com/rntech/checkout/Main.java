package com.rntech.checkout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rntech.checkout.basket.domain.Basket;
import com.rntech.checkout.pricing.PricingRule;
import com.rntech.checkout.pricing.domain.ItemPrice;
import com.rntech.checkout.pricing.rules.GroupDiscountOffers;
import com.rntech.checkout.sku.ItemSku;

public class Main {

  public static void main(String[] args) {


    final PricingRule noDiscountPricingRule = (unitPriceInPence, numOrdered) -> unitPriceInPence * numOrdered;
    final Checkout checkout = new Checkout(noDiscountPricingRule);

    final Map<ItemSku, ItemPrice> rules = new HashMap<>();
    rules.put(new ItemSku("A"), new ItemPrice(50, GroupDiscountOffers.threeFor130));
    rules.put(new ItemSku("B"), new ItemPrice(30, GroupDiscountOffers.twoFor45));
    rules.put(new ItemSku("C"), new ItemPrice(20));
    rules.put(new ItemSku("D"), new ItemPrice(15));

    final List<ItemSku> basketItems = Arrays.stream(args)
        .map(ItemSku::new)
        .collect(Collectors.toList());

    final Integer totalPrice = checkout.checkout(new Basket(basketItems), rules);
    System.out.printf("%nTotal Price = %dp%n%n", totalPrice);
  }

}
