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
        ReviewEntity reviewEntity = new ReviewEntity(reviewDto.getStars(), reviewDto.getReview(), bookEntity);
        this.reviewRepository.save(reviewEntity);
        return reviewDto;
    }

    public List<ReviewDto> fetchAll() {
        return this.reviewRepository.findAll()
                .stream()
                .map(reviewEntity -> {
                    return new ReviewDto(reviewEntity.getStars(), reviewEntity.getReview(), reviewEntity.getBookEntity().getTitle());
                })
                .collect(Collectors.toList());
    }
}
