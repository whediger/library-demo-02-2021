package com.example.library3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import javax.transaction.Transactional;
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
@Transactional
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
        .andExpect(jsonPath("[0].id").isNotEmpty())
        .andExpect(jsonPath("[0].title").value("zero to one"))
        .andExpect(jsonPath("[0].author").value("Blake Masters"))
        .andExpect(jsonPath("[0].reviews").isArray())
        .andDo(document("Books", responseFields(
                fieldWithPath("[0].id").description("ID of Book"),
                fieldWithPath("[0].title").description("Title of the Book"),
                fieldWithPath("[0].author").description("Author of the Book"),
                fieldWithPath("[0].reviews").description("Reviews of book")
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

    @Test
    public void getRootPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Welcome to the Library"));
    }

    @Test
    public void reviewBook() throws Exception {
        BookDto nineteenEightyFour = new BookDto("1984", "George Orwell");
        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(nineteenEightyFour))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        ReviewDto reviewDto = new ReviewDto(5, "Really made me think about everything around me", "1984");
        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stars").value(5))
                .andExpect(jsonPath("$.review").value("Really made me think about everything around me"))
                .andExpect(jsonPath("$.bookTitle").value("1984"));
    }

    @Test
    public void getReviews() throws Exception {
        BookDto nineteenEightyFour = new BookDto("1984", "George Orwell");
        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(nineteenEightyFour))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        ReviewDto reviewDto1 = new ReviewDto(5, "Really made me think about everything around me", "1984");
        mockMvc.perform(post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stars").value(5))
                .andExpect(jsonPath("$.review").value("Really made me think about everything around me"))
                .andExpect(jsonPath("$.bookTitle").value("1984"));
        ReviewDto reviewDto2 = new ReviewDto(4, "A bit long", "1984");
        mockMvc.perform(post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stars").value(4))
                .andExpect(jsonPath("$.review").value("A bit long"))
                .andExpect(jsonPath("$.bookTitle").value("1984"));

        mockMvc.perform(get("/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].stars").value(5))
                .andExpect(jsonPath("[1].stars").value(4));

//        mockMvc.perform(get("/books")
//        ).andExpect(status().isOk())
//                .andExpect(jsonPath("length()").value(1))
//                .andExpect(jsonPath("[0].id").isNotEmpty())
//                .andExpect(jsonPath("[0].title").value("1984"))
//                .andExpect(jsonPath("[0].reviews").isArray())
//                .andExpect(jsonPath("[0].reviews.length()").value(2))
//                .andExpect(jsonPath("[0].reviews[0]").value(5));
        mockMvc.perform(get("/books/1984")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("1984"))
                .andExpect(jsonPath("$.reviews").isArray())
                .andExpect(jsonPath("$.reviews.length()").value(2))
                .andExpect(jsonPath("$.reviews[0]").value(5));
    }
}