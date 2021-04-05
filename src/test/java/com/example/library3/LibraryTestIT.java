package com.example.library3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.beans.BeanProperty;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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
        ).andExpect(status().isCreated())
        .andDo(document("Add Book"));
        mockMvc.perform(get("/books")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("length()").value(1))
        .andExpect(jsonPath("[0].title").value("zero to one"))
        .andExpect(jsonPath("[0].author").value("Blake Masters"))
        .andDo(document("Books", responseFields(
                fieldWithPath("[0].title").description("Title of the Book"),
                fieldWithPath("[0].author").description("Author of the Book")
        )));
    }

    @Test
    public void getBookByTitle() throws Exception {
        BookDto zeroToOne = new BookDto("zero to one", "Blake Masters");
        BookDto nineteenEightyFour = new BookDto("1984", "George Orwell");
        BookDto warAndPeace = new BookDto("War and Peace", "Leo Tolstoy");

        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(zeroToOne))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(nineteenEightyFour))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(warAndPeace))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        mockMvc.perform(get(String.format("/books/%s", nineteenEightyFour.getTitle()))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("1984"))
                .andExpect(jsonPath("$.author").value("George Orwell"));
    }
}
