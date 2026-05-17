package com.guilhermechina.onibusapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OnibusApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveExecutarFluxoCompletoDasPassagens() throws Exception {
        mockMvc.perform(get("/passagens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

        mockMvc.perform(get("/passagens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passageiro").value("Ana Souza"));

        mockMvc.perform(post("/passagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "passageiro": "Diego Alves",
                                  "assento": 10,
                                  "origem": "Salvador",
                                  "destino": "Recife",
                                  "data": "2026-06-01",
                                  "status": "CONFIRMADA"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.destino").value("Recife"));

        mockMvc.perform(post("/passagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "passageiro": "Teste Duplicado",
                                  "assento": 10,
                                  "origem": "Salvador",
                                  "destino": "Maceio",
                                  "data": "2026-06-02",
                                  "status": "PENDENTE"
                                }
                                """))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put("/passagens/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "passageiro": "Diego Alves Atualizado",
                                  "assento": 11,
                                  "origem": "Salvador",
                                  "destino": "Recife",
                                  "data": "2026-06-03",
                                  "status": "EMBARCADA"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passageiro").value("Diego Alves Atualizado"))
                .andExpect(jsonPath("$.assento").value(11));

        mockMvc.perform(get("/passagens/busca").param("destino", "Recife"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(4));

        mockMvc.perform(delete("/passagens/4"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404QuandoIdNaoExistir() throws Exception {
        mockMvc.perform(get("/passagens/999"))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/passagens/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "passageiro": "Inexistente",
                                  "assento": 99,
                                  "origem": "A",
                                  "destino": "B",
                                  "data": "2026-06-10",
                                  "status": "PENDENTE"
                                }
                                """))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/passagens/999"))
                .andExpect(status().isNotFound());
    }
}
