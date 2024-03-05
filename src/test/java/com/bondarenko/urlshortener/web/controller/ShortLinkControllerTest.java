package com.bondarenko.urlshortener.web.controller;

import com.bondarenko.urlshortener.service.ShortLinkGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortLinkController.class)
public class ShortLinkControllerTest {

    private final List<String> SHORT_LINKS = new ArrayList<>(List.of("0000", "1000", "2000", "3000", "4000"));
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShortLinkGenerator shortLinkGenerator;

    @Test
    @DisplayName("should generate short link successfully")
    void shouldGenerateShortLinkSuccessfully() throws Exception {

        when(shortLinkGenerator.generateShortLink()).thenReturn(SHORT_LINKS);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/short-link/generate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("""
                                [
                                "0000",
                                "1000",
                                "2000",
                                "3000",
                                "4000"
                                ]
                                """))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return not found response when generate short links with invalid url")
    void shouldReturnNotFoundResponse_WhenGenerateShortLinksWithInvalidUrl() throws Exception {

        when(shortLinkGenerator.generateShortLink()).thenReturn(SHORT_LINKS);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/short-link/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}