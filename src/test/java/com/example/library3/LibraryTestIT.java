package com.example.library3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryTestIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void addBooks() throws Exception {
        mockMvc.perform(post("/books")
            .content("")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }
}
