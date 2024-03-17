package ru.alfabank.homework;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public Comment() {
    }
}
