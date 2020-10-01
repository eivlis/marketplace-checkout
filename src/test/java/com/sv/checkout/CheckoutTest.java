package com.sv.checkout;

import com.sv.checkout.discount.Discount;
import com.sv.checkout.discount.OverXYPercentDiscount;
import com.sv.checkout.discount.XItemsYNewPrice;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutTest {

    private Checkout checkout;

    @Test()
    void shouldNotScanNullableItem() {
        checkout = new Checkout(PromotionalRules.create());
        checkout.scan(null);

        assertThat(checkout.total()).isEqualTo(0);
    }

    @Nested
    class CheckoutWithoutDiscount {

        @BeforeEach
        void setup() {
            checkout = new Checkout(PromotionalRules.create());
        }

        @Test
        void shouldCalculateTotal() {
            checkout.scan(travelCardHolder());

            assertThat(checkout.total()).isEqualTo(9.25D);
        }

        @Test
        void shouldCalculateTotalWithMultipleItems() {
            checkout.scan(travelCardHolder());
            checkout.scan(personalisedCufflinks());
            checkout.scan(kidsTShirt());

            assertThat(checkout.total()).isEqualTo(74.2D);
        }
    }

    @Nested
    class CheckoutWithDiscount {
        private Checkout checkout;

        @BeforeEach
        void setup() {
            checkout = new Checkout(PromotionalRules.create(over6010PercentOff(), travelCardHolderDiscount()));
        }

        @Test
        void shouldApplyOver6010PercentOffDiscount() {

            checkout.scan(travelCardHolder());
            checkout.scan(personalisedCufflinks());
            checkout.scan(kidsTShirt());

            assertThat(checkout.total()).isEqualTo(66.78);
        }

        @Test
        void shouldApplyNewPriceForTravelCardDiscount() {

            checkout.scan(travelCardHolder());
            checkout.scan(kidsTShirt());
            checkout.scan(travelCardHolder());

            assertThat(checkout.total()).isEqualTo(36.95);
        }

        @Test
        void shouldApplyBothDiscounts() {

            checkout.scan(travelCardHolder());
            checkout.scan(personalisedCufflinks());
            checkout.scan(travelCardHolder());
            checkout.scan(kidsTShirt());

            assertThat(checkout.total()).isEqualTo(73.76);
        }
    }

    private static Item travelCardHolder() {
        return new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
    }

    private static Item personalisedCufflinks() {
        return new Item("002", "Personalised cufflinks", new BigDecimal("45"));
    }

    private static Item kidsTShirt() {
        return new Item("003", "Kids T-shirt", new BigDecimal("19.95"));
    }

    private static Discount over6010PercentOff() {
        return new OverXYPercentDiscount(new BigDecimal("60"), new BigDecimal("10"));
    }

    private static Discount travelCardHolderDiscount() {
        return new XItemsYNewPrice("001", 2, new BigDecimal("8.5"));
    }


}