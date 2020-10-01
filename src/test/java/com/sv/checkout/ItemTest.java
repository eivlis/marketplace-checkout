package com.sv.checkout;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemTest {

    @Test
    void testCodeMustNotBeNull() {
        assertThatThrownBy(() -> new Item(null, "item name", BigDecimal.ONE))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testNameMustNotBeNull() {
        assertThatThrownBy(() -> new Item("001", null, BigDecimal.ONE))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testPriceMustNotBeNull() {
        assertThatThrownBy(() -> new Item("001", "item name", null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testGetCode() {
        final Item item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        assertThat(item.code())
                .isEqualTo("001");
    }

    @Test
    void testGetName() {
        final Item item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        assertThat(item.name())
                .isEqualTo("Travel Card Holder");
    }

    @Test
    void testGetPrice() {
        final Item item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        assertThat(item.price())
                .isEqualTo(new BigDecimal("9.25"));
    }

}