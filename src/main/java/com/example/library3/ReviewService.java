package com.example.library3;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    ReviewRepository reviewRepository;
    BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public ReviewDto create(ReviewDto reviewDto) {
        BookEntity bookEntity = this.bookRepository.findByTitle(reviewDto.getBookTitle());
        this.reviewRepository.save(new ReviewEntity(reviewDto.getStars(), reviewDto.getReview(), bookEntity));
        return reviewDto;
    }

    public List<ReviewEntity> fetchAll() {
        return this.reviewRepository.findAll();
    }
}
