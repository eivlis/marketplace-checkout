package com.sv.checkout.discount;

import com.sv.checkout.Item;

import java.math.BigDecimal;
import java.util.List;


public interface ItemsDiscount extends Discount {

    BigDecimal apply(List<Item> basket);
}
