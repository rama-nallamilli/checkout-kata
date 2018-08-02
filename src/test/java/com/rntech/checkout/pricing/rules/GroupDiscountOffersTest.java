package com.rntech.checkout.pricing.rules;

import com.rntech.checkout.pricing.PricingRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupDiscountOffersTest {

  @Test
  void threeFor130_shouldNotGiveDiscountForTwo() {
    Integer unitPrice = 50;
    PricingRule rule = GroupDiscountOffers.threeFor130;

    int price = rule.getTotalPrice(unitPrice, 2);
    assertEquals(100, price);
  }

  @Test
  void threeFor130_shouldGiveDiscountForThree() {
    Integer unitPrice = 50;
    PricingRule rule = GroupDiscountOffers.threeFor130;

    int price = rule.getTotalPrice(unitPrice, 3);
    assertEquals(130, price);
  }

  @Test
  void threeFor130_shouldGiveDiscountForTen() {
    Integer unitPrice = 50;
    PricingRule rule = GroupDiscountOffers.threeFor130;

    int price = rule.getTotalPrice(unitPrice, 10);
    assertEquals(130 * 3 + 50, price);
  }

  @Test
  void threeFor130_shouldReturnZeroForNoUnits() {
    Integer unitPrice = 50;
    PricingRule rule = GroupDiscountOffers.threeFor130;

    int price = rule.getTotalPrice(unitPrice, 0);
    assertEquals(0, price);
  }


  @Test
  void twoFor45_shouldNotGiveDiscountForOne() {
    Integer unitPrice = 30;
    PricingRule rule = GroupDiscountOffers.twoFor45;

    int price = rule.getTotalPrice(unitPrice, 1);
    assertEquals(30, price);
  }

  @Test
  void twoFor45_shouldGiveDiscountForTwo() {
    Integer unitPrice = 30;
    PricingRule rule = GroupDiscountOffers.twoFor45;

    int price = rule.getTotalPrice(unitPrice, 2);
    assertEquals(45, price);
  }

  @Test
  void twoFor45_shouldGiveDiscountForNine() {
    Integer unitPrice = 30;
    PricingRule rule = GroupDiscountOffers.twoFor45;

    int price = rule.getTotalPrice(unitPrice, 9);
    assertEquals(45 * 4 + 30, price);
  }

  @Test
  void twoFor45_shouldReturnZeroForNoUnits() {
    Integer unitPrice = 30;
    PricingRule rule = GroupDiscountOffers.twoFor45;

    int price = rule.getTotalPrice(unitPrice, 0);
    assertEquals(0, price);
  }
}