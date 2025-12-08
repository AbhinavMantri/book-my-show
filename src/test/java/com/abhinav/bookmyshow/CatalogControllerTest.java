package com.abhinav.bookmyshow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listsCities() throws Exception {
        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("blr"));
    }

    @Test
    void filtersMoviesByCityAndQuery() throws Exception {
        mockMvc.perform(get("/api/movies")
                        .param("city", "blr")
                        .param("q", "KGF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("KGF Chapter 2"));
    }

    @Test
    void showsVenuesForMovie() throws Exception {
        mockMvc.perform(get("/api/movies/mv-kgf2/venues")
                        .param("city", "blr"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].screens[0].shows[0].format").value("IMAX"));
    }
}
