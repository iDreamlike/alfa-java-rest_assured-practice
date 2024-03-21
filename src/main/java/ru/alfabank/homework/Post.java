package ru.alfabank.homework;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    public Post() {
    }
}
