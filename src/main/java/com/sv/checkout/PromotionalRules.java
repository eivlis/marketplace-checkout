package com.sv.checkout;

import com.sv.checkout.discount.BasketDiscount;
import com.sv.checkout.discount.Discount;
import com.sv.checkout.discount.ItemsDiscount;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class PromotionalRules {

    private final List<Discount> rules;

    private PromotionalRules(List<Discount> rules) {
        this.rules = rules;
    }

    public List<ItemsDiscount> getItemsDiscounts() {
        return rules.stream()
                .filter(ItemsDiscount.class::isInstance)
                .map(ItemsDiscount.class::cast)
                .collect(Collectors.toList());
    }

    public List<BasketDiscount> getBasketDiscount() {
        return rules.stream()
                .filter(BasketDiscount.class::isInstance)
                .map(BasketDiscount.class::cast)
                .collect(Collectors.toList());
    }

    public static PromotionalRules create(Discount... discounts) {
        List<Discount> discountList = (discounts == null) ? Collections.emptyList() : Arrays.asList(discounts);
        return new PromotionalRules(discountList);
    }
}
