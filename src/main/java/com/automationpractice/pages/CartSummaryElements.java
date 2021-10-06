package com.automationpractice.pages;

public class CartSummaryElements {
    public static final String PRODUCT_IMAGE = ".cart_item .cart_product img";
    public static final String NAME_LABEL = ".cart_item .product-name a";
    public static final String SKU_LABEL = ".cart_item .cart_ref";
    public static final String COLOR_AND_SIZE_LABEL = ".cart_item .cart_description small:nth-child(3) a";
    public static final String QUANTITY_LABEL = ".cart_item .cart_quantity";
    public static final String QUANTITY_INPUT = "input:nth-child(2)";
    public static final String ITEM_TOTAL_PRICE_LABEL = ".cart_item .cart_total span";
    public static final String TOTAL_PRICE_PRODUCTS_LABEL = "#total_product";
    public static final String TOTAL_SHIPPING_LABEL = "#total_shipping";
    public static final String TOTAL_PRICE_WITHOUT_TAX_LABEL = "#total_price_without_tax";
    public static final String TOTAL_TAX_LABEL = "#total_tax";
    public static final String TOTAL_FINAL_PRICE = "#total_price";
    public static final String PROCEED_TO_CHECKOUT_BUTTON = ".cart_navigation a.button[title='Proceed to checkout']";
    public static final String PRODUCT_INFO_BOX = "#cart_summary tbody";
}