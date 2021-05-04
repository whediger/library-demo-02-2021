package com.example.library3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    ReviewService reviewService;

    @Test
    void create() {
        ReviewDto reviewDto = new ReviewDto(5, "Made me think", "1984");
        BookEntity bookEntity = new BookEntity("1984", "George Orwell");
        when(bookRepository.findByTitle("1984")).thenReturn(bookEntity);
        reviewService.create(reviewDto);
        verify(reviewRepository).save(
                new ReviewEntity(5, "Made me think", bookEntity)
        );
    }

    @Test
    void fetchAll() {
        BookEntity bookEntity = new BookEntity("1984", "George Orwell");
        ReviewEntity reviewEntity = new ReviewEntity(5, "Made me think", bookEntity);
        when(reviewRepository.findAll()).thenReturn(
                List.of(reviewEntity)
        );

        List<ReviewDto> actual = reviewService.fetchAll();

        assertThat(actual).isEqualTo(
                List.of(
                        new ReviewDto(5, "Made me think", "1984")
                )
        );

    }

}
