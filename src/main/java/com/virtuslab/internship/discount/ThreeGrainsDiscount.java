package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class ThreeGrainsDiscount extends Discount {

    public ThreeGrainsDiscount() {
        NAME = "ThreeGrainsDiscount";
    }

    @Override
    BigDecimal calculateDiscount(Receipt receipt) {
        return receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
    }

    @Override
    boolean shouldApply(Receipt receipt) {
        return receipt.entries().stream()
                .filter(x -> x.product().type().equals(Product.Type.GRAINS))
                .mapToInt(ReceiptEntry::quantity)
                .sum() >= 3;
    }
}
