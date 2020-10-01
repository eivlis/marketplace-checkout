package com.sv.checkout.discount;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OverXYPercentDiscountTest {

    @Test
    void testAmountMustNotBeNull() {
        assertThatThrownBy(() -> new OverXYPercentDiscount(null, BigDecimal.TEN))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testPercentageMustNotBeNull() {
        assertThatThrownBy(() -> new OverXYPercentDiscount(new BigDecimal(60), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotApplyDiscountIfFinalValueIsLessThanDiscountAmount() {
        final OverXYPercentDiscount discount = new OverXYPercentDiscount(new BigDecimal(60), BigDecimal.TEN);

        final BigDecimal result = discount.apply(new BigDecimal(59));

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void shouldNotApplyDiscountIfFinalValueIsEqualDiscountAmount() {
        final OverXYPercentDiscount discount = new OverXYPercentDiscount(new BigDecimal(60), BigDecimal.TEN);

        final BigDecimal result = discount.apply(new BigDecimal(60));

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void shouldApplyDiscountIfFinalValueIsMoreThanDiscountAmount() {
        final OverXYPercentDiscount discount = new OverXYPercentDiscount(new BigDecimal(60), BigDecimal.TEN);

        final BigDecimal result = discount.apply(new BigDecimal(61));

        assertThat(result).isEqualTo(new BigDecimal("6.10"));
    }

}