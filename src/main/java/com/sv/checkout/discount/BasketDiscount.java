package com.sv.checkout.discount;

import java.math.BigDecimal;

/**
 * This type of discount is:
 *  applied after {@link com.sv.checkout.discount.ItemsDiscount}
 *  applied across all basket
 *
 */
public interface BasketDiscount extends Discount {

    BigDecimal apply(BigDecimal basketTotal);
}
