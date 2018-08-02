package com.rntech.checkout.sku;

public class SkuNotFoundException extends RuntimeException {

  public SkuNotFoundException(ItemSku sku) {
    super("Could not find SKU for " + sku.toString());
  }

}
