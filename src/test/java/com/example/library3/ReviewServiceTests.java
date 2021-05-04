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
        BookEntity bookEntity = new BookEntity("1984", "George Orwell");
        ReviewDto reviewDto = new ReviewDto(5, "Really made me think about everything around me", "1984");
        when(bookRepository.findByTitle("1984")).thenReturn(
                bookEntity
        );

        reviewService.create(reviewDto);
        verify(reviewRepository).save(
                new ReviewEntity(5, "Really made me think about everything around me", bookEntity)
        );
    }

    @Test
    void fetchAll() {
        BookEntity bookEntity = new BookEntity("1984", "George Orwell");
        ReviewDto reviewDto = new ReviewDto(5, "Really made me think about everything around me", "1984");
        ReviewEntity reviewEntity = new ReviewEntity(5, "Really made me think about everything around me", bookEntity);

        when(reviewRepository.findAll()).thenReturn(
                List.of(
                        reviewEntity
                )
        );

        List<ReviewDto> actual = reviewService.fetchAll();

        assertThat(actual).isEqualTo(
                List.of(
                        reviewDto
                )
        );

    }
}
