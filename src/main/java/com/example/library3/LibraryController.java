package com.example.library3;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @PostMapping("books")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBooks(){
    }

    @GetMapping("books")
    public String getBooks(){return "[{}]"; }
}
