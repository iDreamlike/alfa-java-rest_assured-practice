package ru.alfabank.practice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientRequest {

    private String clientName;
    private String clientEmail;

}
