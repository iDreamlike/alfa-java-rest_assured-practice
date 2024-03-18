package ru.alfabank.practice;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderRequest {
    private int bookId;
    private String customerName;
}
