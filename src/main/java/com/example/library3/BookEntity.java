package com.example.library3;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;
    String author;

    @OneToMany(mappedBy = "bookEntity")
    Set<ReviewEntity> reviews;

    public BookEntity(String title, String author) {
        super();
        this.title = title;
        this.author = author;
    }
}
