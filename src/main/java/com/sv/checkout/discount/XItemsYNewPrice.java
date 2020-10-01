package com.sv.checkout.discount;

import com.sv.checkout.Item;

import java.math.BigDecimal;
import java.util.List;

public class XItemsYNewPrice implements ItemsDiscount {

    private final String code;

    private final int count;

    private final BigDecimal newPrice;

    public XItemsYNewPrice(String code, int count, BigDecimal newPrice) {
        this.code = code;
        this.count = count;
        this.newPrice = newPrice;
    }

    @Override
    public BigDecimal apply(List<Item> basket) {
        final long countInBasket = basket.stream()
                .map(it -> it.code())
                .filter(c -> c == code)
                .count();

        final BigDecimal itemPrice = basket.stream()
                .filter(it -> it.code() == code)
                .map(it -> it.price())
                .findFirst()
                .orElse(BigDecimal.ZERO);

        if (countInBasket >= count) {
            return (itemPrice.subtract(newPrice)).multiply(new BigDecimal(countInBasket));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
