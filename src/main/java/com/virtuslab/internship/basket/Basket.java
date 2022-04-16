package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private final List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProductsFromNames(List<String> productNames) {
        var db = new ProductDb();
        for (var name : productNames)
            addProduct(db.getProduct(name));
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
