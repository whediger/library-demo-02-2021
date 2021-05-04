package com.example.library3;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

    BookService bookService;
    ReviewService reviewService;

    public LibraryController(BookService bookService, ReviewService reviewService){
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public String rootPage() {
        return "{\"message\": \"Welcome to the Library\"}";
    }

    @PostMapping("books")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBooks(@RequestBody BookDto bookDto){
        this.bookService.create(bookDto);
    }

    @GetMapping("books")
    public List<BookEntity> getBooks(){
        return this.bookService.fetchAll();
    }

    @GetMapping("books/{bookTitle}")
    public BookEntity getBookByName(@PathVariable String bookTitle) {
        return this.bookService.findByTitle(bookTitle);
    }

    @PostMapping("reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto addReview(@RequestBody ReviewDto reviewDto) {
        return this.reviewService.create(reviewDto);
    }

    @GetMapping("reviews")
    public List<ReviewDto> getReviews() {
        return this.reviewService.fetchAll();
    }
}
