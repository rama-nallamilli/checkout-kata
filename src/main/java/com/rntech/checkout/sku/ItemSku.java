package com.rntech.checkout.sku;

import java.util.Objects;

public class ItemSku {

    private final String sku;

    public ItemSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemSku itemSku = (ItemSku) o;
        return Objects.equals(sku, itemSku.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}
