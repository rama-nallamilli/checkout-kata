package com.rntech.checkout;

import com.rntech.checkout.basket.domain.Basket;
import com.rntech.checkout.pricing.PricingRule;
import com.rntech.checkout.pricing.domain.ItemPrice;
import com.rntech.checkout.pricing.rules.GroupDiscountOffers;
import com.rntech.checkout.sku.ItemSku;
import com.rntech.checkout.sku.SkuNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckoutTest {
    private final PricingRule noDiscountPricingRule = (unitPriceInPence, numOrdered) -> unitPriceInPence * numOrdered;

    @Test
    void checkout_shouldCalculateTotalPriceForSingleItem() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("A"), new ItemPrice(100));

        Basket basket = createBasket("A");

        int totalPrice = checkout.checkout(basket, rules);
        assertEquals(100, totalPrice);
    }

    @Test
    void checkout_shouldCalculateTotalPriceForItemsWithDiscount() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("D"), new ItemPrice(40, GroupDiscountOffers.twoFor45));

        Basket basket = createBasket("D", "D");

        int totalPrice = checkout.checkout(basket, rules);
        assertEquals(45, totalPrice);
    }

    @Test
    void checkout_shouldCalculateTotalPriceForMixedItems() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("A"), new ItemPrice(50, GroupDiscountOffers.threeFor130));
        rules.put(new ItemSku("B"), new ItemPrice(30, GroupDiscountOffers.twoFor45));
        rules.put(new ItemSku("C"), new ItemPrice(20));
        rules.put(new ItemSku("D"), new ItemPrice(15));

        Basket basket = createBasket("A", "A", "A", "B", "B", "C", "D", "A");

        int totalPrice = checkout.checkout(basket, rules);
        assertEquals(260, totalPrice);
    }

    @Test
    void checkout_shouldReturnZeroForEmptyBasket() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("A"), new ItemPrice(50, GroupDiscountOffers.threeFor130));

        Basket basket = createBasket();

        int totalPrice = checkout.checkout(basket, rules);
        assertEquals(0, totalPrice);
    }

    @Test
    void checkout_shouldThrowExceptionForUnknownSku() {
        Checkout checkout = new Checkout(noDiscountPricingRule);

        Map<ItemSku, ItemPrice> rules = new HashMap<>();
        rules.put(new ItemSku("A"), new ItemPrice(50, GroupDiscountOffers.threeFor130));
        rules.put(new ItemSku("B"), new ItemPrice(30, GroupDiscountOffers.twoFor45));

        Basket basket = createBasket("A", "B", "Z");

        Throwable ex = assertThrows(SkuNotFoundException.class, () ->
                checkout.checkout(basket, rules));

        assertEquals("Could not find SKU for Z", ex.getMessage());
    }

    private Basket createBasket(String...skus) {
        List<ItemSku> items = Arrays.stream(skus)
                .map(ItemSku::new)
                .collect(Collectors.toList());

        return new Basket(items);
    }
}