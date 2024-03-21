package ru.alfabank.practice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ClientRequest {

    private String clientName;
    private String clientEmail;

}
