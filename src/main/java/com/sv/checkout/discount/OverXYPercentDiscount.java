package com.sv.checkout.discount;

import java.math.BigDecimal;
import java.util.Objects;

public class OverXYPercentDiscount implements BasketDiscount {

    private final BigDecimal amount;

    private final BigDecimal percentage;

    public OverXYPercentDiscount(BigDecimal amount, BigDecimal percentage) {
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.percentage = Objects.requireNonNull(percentage, "percentage must not be null");
    }

    @Override
    public BigDecimal apply(BigDecimal basketTotal) {
        return (basketTotal.compareTo(amount) == 1) ?
                basketTotal
                        .divide(new BigDecimal(100))
                        .multiply(percentage)
                : BigDecimal.ZERO;
    }
}
