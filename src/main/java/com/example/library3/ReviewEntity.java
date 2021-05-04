package com.example.library3;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    int stars;
    String review;

    @ManyToOne
    @JoinColumn(name="book_id")
    BookEntity bookEntity;

    public ReviewEntity(int stars, String review, BookEntity bookEntity) {
        this.stars = stars;
        this.review = review;
        this.bookEntity = bookEntity;
    }
}
