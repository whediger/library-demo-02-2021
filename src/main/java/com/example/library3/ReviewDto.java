package com.example.library3;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {
    int stars;
    String review;
    String bookTitle;

    public ReviewDto(int stars, String review, String bookTitle) {
        this.stars = stars;
        this.review = review;
        this.bookTitle = bookTitle;
    }
}
