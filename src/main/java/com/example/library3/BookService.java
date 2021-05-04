package com.example.library3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<BookEntity> fetchAll() {
        return bookRepository.findAll();
    }

    public BookEntity findByTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }
}
