package ru.alfabank.practice;

import lombok.Data;

@Data
public class OrderResponse {
    private boolean created;
    private String orderId;
}
