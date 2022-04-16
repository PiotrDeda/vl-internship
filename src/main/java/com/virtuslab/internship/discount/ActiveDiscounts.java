package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public class ActiveDiscounts {

    private static final Discount[] discounts = {new ThreeGrainsDiscount(), new TenPercentDiscount()};

    public static Receipt applyDiscounts(Receipt receipt) {
        for (var discount : discounts)
            receipt = discount.apply(receipt);
        return receipt;
    }
}
