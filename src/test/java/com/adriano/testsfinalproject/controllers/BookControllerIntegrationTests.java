package com.adriano.testsfinalproject.controllers;

import com.adriano.testsfinalproject.TestsFinalProjectApplication;
import com.adriano.testsfinalproject.helpers.BookFactory;
import com.adriano.testsfinalproject.models.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest(classes = TestsFinalProjectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    private Book book;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        book = BookFactory.fakeBook("Meu Livro");
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Deve adicionar um livro utilizando MockMvc - Teste Integração")
    void mustAddBookMockMvc() throws Exception {
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Meu Livro"));
    }

    @Test
    @DisplayName("Deve remover um livro utilizando MockMvc - Teste Integração")
    void mustExcludeBookMockMvc() throws Exception {
        String bookJson = objectMapper.writeValueAsString(book);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        String savedId = JsonPath.parse(responseJson).read("$.id");

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", savedId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Deve retornar erro se tentar remover um livro que não existe utilizando MockMvc - Teste Integração")
    void mustReturnErrorExcludeBookNotExistsMockMvc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", "wrongId"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erro").value("Id não encontrado"));
    }

    @Test
    @DisplayName("Deve retornar erro se tentar salvar um livro sem título utilizando MockMvc - Teste Integração")
    void mustReturnErrorSaveBookWithoutTitleMockMvc() throws Exception {
        book.setTitle(null);
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("O título é obrigatório"));
    }
}
