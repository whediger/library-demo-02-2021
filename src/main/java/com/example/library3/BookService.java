package com.example.library3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void create(BookDto bookDto) {
        bookRepository.save(
                new BookEntity(bookDto.getTitle(),
                                bookDto.getAuthor()));
    }

    public List<BookDto> fetchAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookEntity -> {
                    return new BookDto(bookEntity.getTitle(), bookEntity.getAuthor());
                })
                .collect(Collectors.toList());
    }
}
