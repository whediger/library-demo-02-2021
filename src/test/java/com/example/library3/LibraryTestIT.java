package com.example.library3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryTestIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void addBooks() throws Exception {
        BookDto bookDto = new BookDto("zero to one", "Blake Masters");

        mockMvc.perform(post("/books")
            .content(objectMapper.writeValueAsString(bookDto))
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        mockMvc.perform(get("/books")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("length()").value(1))
        .andExpect(jsonPath("[0].title").value("zero to one"))
        .andExpect(jsonPath("[0].author").value("Blake Masters"));
    }
}
