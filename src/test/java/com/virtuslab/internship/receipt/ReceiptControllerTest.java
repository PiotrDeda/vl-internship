package com.virtuslab.internship.receipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.ActiveDiscounts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mvc;

    void genericReceiptControllerTest(List<String> productNames) throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productNames);
        var expectedBasket = new Basket();
        expectedBasket.addProductsFromNames(productNames);
        var expectedReceipt = ActiveDiscounts.applyDiscounts(new ReceiptGenerator().generate(expectedBasket));

        // When
        var result = mvc.perform(get("/receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertEquals(objectMapper.writeValueAsString(expectedReceipt), result.getResponse().getContentAsString());
    }

    @Test
    void shouldReturnReceiptForGivenProducts() throws Exception {
        genericReceiptControllerTest(List.of("Cheese"));
    }

    @Test
    void shouldReturnReceiptForGivenProductsWithTenPercentDiscount() throws Exception {
        genericReceiptControllerTest(List.of("Cheese", "Steak"));
    }

    @Test
    void shouldReturnReceiptForGivenProductsWithThreeCerealsDiscount() throws Exception {
        genericReceiptControllerTest(List.of("Bread", "Cereals", "Cereals"));
    }

    @Test
    void shouldReturnReceiptForGivenProductsWithMultipleDiscounts() throws Exception {
        genericReceiptControllerTest(List.of("Cheese", "Steak", "Bread", "Cereals", "Cereals"));
    }

    @Test
    void shouldReturnEmptyReceiptForEmptyList() throws Exception {
        genericReceiptControllerTest(List.of());
    }

    @Test
    void shouldReturnBadRequestForNonexistentItem() throws Exception {
        // Given
        var productNames = List.of("Apple", "ItemDoesntExist", "Apple");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productNames);

        // When
        mvc.perform(get("/receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }
}
