package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;

import java.util.ArrayList;
import java.util.List;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for (var product : basket.getProducts()) {
            var entry = receiptEntries.stream().filter(x -> x.product().equals(product)).findAny();
            if (entry.isPresent())
                receiptEntries.set(
                        receiptEntries.indexOf(entry.get()),
                        new ReceiptEntry(product, entry.get().quantity() + 1)
                );
            else
                receiptEntries.add(new ReceiptEntry(product, 1));
        }
        return new Receipt(receiptEntries);
    }
}
