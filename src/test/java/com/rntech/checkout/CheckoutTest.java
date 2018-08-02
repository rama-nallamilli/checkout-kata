package com.rntech.checkout;

import com.rntech.checkout.basket.domain.Basket;
import com.rntech.checkout.pricing.PricingRule;
import com.rntech.checkout.pricing.domain.ItemPrice;
import com.rntech.checkout.pricing.rules.GroupDiscountOffers;
import com.rntech.checkout.sku.ItemSku;
import com.rntech.checkout.sku.SkuNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {
    private final PricingRule noDiscountPricingRule = (unitPriceInPence, numOrdered) -> unitPriceInPence * numOrdered;

    @Test
    void checkout_shouldCalculateTotalPriceForMultipleItems() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("A"), new ItemPrice(50, GroupDiscountOffers.threeFor130));
        rules.put(new ItemSku("B"), new ItemPrice(30, GroupDiscountOffers.twoFor45));
        rules.put(new ItemSku("C"), new ItemPrice(20));
        rules.put(new ItemSku("D"), new ItemPrice(15));

        List<ItemSku> basket = Arrays
                .stream(new String[]{"A", "A", "A", "B", "B", "C", "D"})
                .map(ItemSku::new)
                .collect(Collectors.toList());

        int totalPrice = checkout.checkout(new Basket(basket), rules);
        assertEquals(210, totalPrice);
    }

    @Test
    void checkout_shouldReturnZeroForEmptyBasket() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("A"), new ItemPrice(50, GroupDiscountOffers.threeFor130));

        List<ItemSku> basket = Collections.emptyList();

        int totalPrice = checkout.checkout(new Basket(basket), rules);
        assertEquals(0, totalPrice);
    }

    @Test
    void checkout_shouldThrowExceptionForUnknownSku() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("A"), new ItemPrice(50, GroupDiscountOffers.threeFor130));
        rules.put(new ItemSku("B"), new ItemPrice(30, GroupDiscountOffers.twoFor45));

        List<ItemSku> basket = Arrays
                .stream(new String[]{"A", "B", "Z"})
                .map(ItemSku::new)
                .collect(Collectors.toList());

        Throwable ex = assertThrows(SkuNotFoundException.class, () ->
                checkout.checkout(new Basket(basket), rules));

        assertEquals("Could not find SKU for Z", ex.getMessage());
    }
}