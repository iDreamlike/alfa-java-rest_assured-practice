package ru.alfabank.homework;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Geo {
    private String lat;
    private String lng;

    public Geo() {
    }

}
