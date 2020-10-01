package com.sv.checkout;

import com.sv.checkout.discount.BasketDiscount;
import com.sv.checkout.discount.ItemsDiscount;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionalRulesTest {

    @Test
    void shouldReturnNoRulesIfDiscountArrayIsNull() {
        final PromotionalRules promotionalRules = PromotionalRules.create(null);

        assertThat(promotionalRules.getBasketDiscount().isEmpty()).isTrue();
        assertThat(promotionalRules.getItemsDiscounts().isEmpty()).isTrue();
    }

    @Test
    void shouldCreateWithNoRules() {

        final PromotionalRules promotionalRules = PromotionalRules.create();

        assertThat(promotionalRules.getBasketDiscount().isEmpty()).isTrue();
        assertThat(promotionalRules.getItemsDiscounts().isEmpty()).isTrue();
    }

    @Test
    void shouldCreateWithItemsRule() {
        final PromotionalRules promotionalRules = PromotionalRules.create(itemsDiscount());

        assertThat(promotionalRules.getItemsDiscounts().isEmpty()).isFalse();
    }

    @Test
    void shouldCreateWithBasketRule() {
        final PromotionalRules promotionalRules = PromotionalRules.create(basketDiscount());

        assertThat(promotionalRules.getBasketDiscount().isEmpty()).isFalse();
    }

    @Test
    void shouldCreateWithMultipleDiscounts() {
        final PromotionalRules promotionalRules = PromotionalRules.create(basketDiscount(), itemsDiscount(), itemsDiscount());

        assertThat(promotionalRules.getBasketDiscount().size()).isEqualTo(1L);
        assertThat(promotionalRules.getItemsDiscounts().size()).isEqualTo(2L);
    }

    private ItemsDiscount itemsDiscount() {
        return basket -> null;
    }

    private BasketDiscount basketDiscount() {
        return basket -> null;
    }


}