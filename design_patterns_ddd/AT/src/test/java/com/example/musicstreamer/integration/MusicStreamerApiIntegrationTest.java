package com.example.musicstreamer.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MusicStreamerApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldExecuteMainFlowThroughApi() throws Exception {
        String accountResponse = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "ownerName": "Marina"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode accountJson = objectMapper.readTree(accountResponse);
        String accountId = accountJson.get("id").asText();

        mockMvc.perform(post("/accounts/{accountId}/cards", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "holderName": "Marina",
                                  "cardNumber": "5555444433332222",
                                  "active": true,
                                  "expiresAt": "2027-12-31"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.card.lastFourDigits").value("2222"));

        mockMvc.perform(post("/accounts/{accountId}/subscriptions", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "planType": "PREMIUM"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.active").value(true));

        String trackResponse = mockMvc.perform(post("/tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Ainda Bem",
                                  "artist": "Marisa Monte",
                                  "album": "O Que Você Quer Saber de Verdade"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String trackId = objectMapper.readTree(trackResponse).get("id").asText();

        mockMvc.perform(post("/accounts/{accountId}/favorites/{trackId}", accountId, trackId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/accounts/{accountId}/favorites", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trackId));

        String playlistResponse = mockMvc.perform(post("/accounts/{accountId}/playlists", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Descobertas"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String playlistId = objectMapper.readTree(playlistResponse).get("id").asText();

        mockMvc.perform(post("/accounts/{accountId}/playlists/{playlistId}/tracks/{trackId}", accountId, playlistId, trackId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackIds[0]").value(trackId));

        mockMvc.perform(post("/transactions/authorizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountId": "%s",
                                  "merchant": "Spotify Premium",
                                  "amount": 39.90,
                                  "occurredAt": "%s"
                                }
                                """.formatted(accountId, OffsetDateTime.parse("2026-06-21T15:00:00Z"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved").value(true))
                .andExpect(jsonPath("$.violations").isEmpty());
    }
}
