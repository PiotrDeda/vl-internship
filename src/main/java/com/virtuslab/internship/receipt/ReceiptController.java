package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.ActiveDiscounts;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ReceiptController {

    @GetMapping("/receipt")
    public Receipt receipt(@RequestBody List<String> productNames) {
        var basket = new Basket();
        try {
            basket.addProductsFromNames(productNames);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ActiveDiscounts.applyDiscounts(new ReceiptGenerator().generate(basket));
    }
}
