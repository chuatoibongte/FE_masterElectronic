package com.example.mater_electronic.utils;

public class FormatUtil {
    public static String formatPrice(double price) {
        return String.format("đ"+"%,.0f", price);  // 1000000 -> 1.000.000
    }
}