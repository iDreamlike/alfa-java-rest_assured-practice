package ru.alfabank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequest {

    private int bookId;
    private String customerName;
}
