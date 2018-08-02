package com.rntech.checkout.basket.domain;

import java.util.List;

import com.rntech.checkout.sku.ItemSku;

public class Basket {
    private final List<ItemSku> items;

    public Basket(List<ItemSku> items) {
        this.items = items;
    }

    public List<ItemSku> getItems() {
        return items;
    }

}
