package br.com.infnet.tp1.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MathControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAddUsingGet() throws Exception {
        mockMvc.perform(get("/api/math/add")
                        .param("a", "10")
                        .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("addition"))
                .andExpect(jsonPath("$.result").value(15));
    }

    @Test
    void shouldSubtractUsingPost() throws Exception {
        mockMvc.perform(post("/api/math/subtract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "a": 10,
                                  "b": 3
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("subtraction"))
                .andExpect(jsonPath("$.result").value(7));
    }

    @Test
    void shouldMultiplyUsingGet() throws Exception {
        mockMvc.perform(get("/api/math/multiply")
                        .param("a", "4")
                        .param("b", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("multiplication"))
                .andExpect(jsonPath("$.result").value(24));
    }

    @Test
    void shouldDivideUsingPost() throws Exception {
        mockMvc.perform(post("/api/math/divide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "a": 20,
                                  "b": 4
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("division"))
                .andExpect(jsonPath("$.result").value(5));
    }

    @Test
    void shouldPowerUsingGet() throws Exception {
        mockMvc.perform(get("/api/math/power")
                        .param("a", "2")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("power"))
                .andExpect(jsonPath("$.result").value(8.0));
    }

    @Test
    void shouldReturnBadRequestWhenDividingByZero() throws Exception {
        mockMvc.perform(get("/api/math/divide")
                        .param("a", "10")
                        .param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Não é permitido dividir por zero."));
    }
}

