package com.automationpractice.model;
import lombok.*;
import org.openqa.selenium.WebElement;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
        private String name;
        private String sku;
        private float currentPrice;
        private int reduction;
        private float oldPrice;
        private int quantity;
        private char size;
        private String color;
        private String imageURL;
        private float totalPrice;

    public Product(String name, String sku, int quantity, char size, String color, String imageURL, float totalPrice){
            this.name = name;
            this.sku = sku;
            this.quantity = quantity;
            this.size = size;
            this.color = color;
            this.imageURL = imageURL;
            this.totalPrice = totalPrice;
    }
}
