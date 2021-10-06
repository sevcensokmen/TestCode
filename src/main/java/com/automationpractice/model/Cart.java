package com.automationpractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private float cartTotal;
    private float shipping;
    private List<Product> products;

    public Cart(List<Product> products){
        this.products = products;
    }

}
