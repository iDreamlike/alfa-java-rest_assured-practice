package ru.alfabank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientRequest {

    private String clientName;
    private String clientEmail;

}
