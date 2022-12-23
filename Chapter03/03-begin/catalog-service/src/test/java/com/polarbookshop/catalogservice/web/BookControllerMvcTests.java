package com.polarbookshop.catalogservice.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerMvcTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService service;

  @Test
  void whenGetBookNotExistsThenShouldReturn404() throws Exception {
    String isbn = "73737313940";
    given(service.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);
    mockMvc.perform(get("/books/" + isbn))
        .andExpect(status().isNotFound());
  }
}
