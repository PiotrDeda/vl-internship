package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public abstract class Discount {

    public String NAME = "GenericDiscount";

    Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = calculateDiscount(receipt);
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    abstract BigDecimal calculateDiscount(Receipt receipt);

    abstract boolean shouldApply(Receipt receipt);
}
