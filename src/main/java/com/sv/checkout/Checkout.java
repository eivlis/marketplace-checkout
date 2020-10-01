package com.sv.checkout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Checkout {

    private final PromotionalRules promotionalRules;
    private final List<Item> items;

    public Checkout(PromotionalRules promotionalRules) {
        this.promotionalRules = Objects.requireNonNull(promotionalRules, "promotional rules must not be null");
        this.items = new ArrayList<>();
    }

    public void scan(Item item) {
        if (item != null)
            items.add(item);
    }

    public Double total() {
        final List<Item> copyOfBasket = new ArrayList<>(items);
        return calculateTotalBeforeDiscounts(copyOfBasket)
                .subtract(calculateTotalDiscount(copyOfBasket))
                .setScale(2, BigDecimal.ROUND_HALF_EVEN)
                .doubleValue();
    }

    private BigDecimal calculateTotalBeforeDiscounts(List<Item> copyOfItems) {
        return copyOfItems.stream()
                .map(Item::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalDiscount(List<Item> copyOfItems) {
        final BigDecimal totalBeforeDiscount = calculateTotalBeforeDiscounts(copyOfItems);
        final BigDecimal itemsDiscount = calculateItemsDiscount(copyOfItems);
        final BigDecimal basketDiscount = calculateTheBestBasketDiscount(totalBeforeDiscount.subtract(itemsDiscount));

        return itemsDiscount.add(basketDiscount);
    }

    private BigDecimal calculateItemsDiscount(List<Item> copyOfBasket) {
        return promotionalRules.getItemsDiscounts()
                .stream()
                .map(it -> it.apply(copyOfBasket))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTheBestBasketDiscount(BigDecimal total) {
        return promotionalRules
                .getBasketDiscount()
                .stream()
                .map(it -> it.apply(total))
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }
}
