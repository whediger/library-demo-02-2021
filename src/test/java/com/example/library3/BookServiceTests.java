package com.example.library3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    BookRepository mockBookRepository;

    @InjectMocks
    BookService subject;

    @Test
    void create() {
       BookDto bookDto = new BookDto("1984", "George Orwell");
       subject.create(bookDto);
       verify(mockBookRepository).save(
               new BookEntity("1984", "George Orwell")
       );
    }
}
