package ru.alfabank.practice;

import lombok.Data;

@Data
public class OrderResponse {
    private Boolean created;
    private String orderId;
}
