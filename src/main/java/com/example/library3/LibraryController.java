package com.example.library3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class LibraryController {

    ArrayList<BookDto> books;

    public LibraryController(){
        books = new ArrayList<BookDto>();
    }

    @PostMapping("books")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBooks(@RequestBody BookDto bookDto){
        books.add(bookDto);
    }

    @GetMapping("books")
    public ArrayList<BookDto> getBooks(){
        return this.books;
    }
}
