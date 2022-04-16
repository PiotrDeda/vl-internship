package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public class TenPercentDiscount extends Discount {

    public TenPercentDiscount() {
        NAME = "TenPercentDiscount";
    }

    @Override
    BigDecimal calculateDiscount(Receipt receipt) {
        return receipt.totalPrice().multiply(BigDecimal.valueOf(0.9));
    }

    boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) >= 0;
    }
}
