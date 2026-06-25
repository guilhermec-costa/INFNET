package br.com.infnet.hospital.controller;

import br.com.infnet.hospital.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PacienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacienteRepository pacienteRepository;

    private String authHeader;

    @BeforeEach
    void setUp() {
        pacienteRepository.deleteAll();
        authHeader = basicAuth("testuser", "testpass");
    }

    @Test
    void deveCadastrarPacientePelaApi() throws Exception {
        mockMvc.perform(post("/pacientes")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Paciente API",
                                  "cpf": "99988877766",
                                  "dataNascimento": "1991-03-12",
                                  "telefone": "21988887777"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Paciente API"))
                .andExpect(jsonPath("$.cpf").value("99988877766"));
    }

    @Test
    void deveBuscarPacienteCadastrado() throws Exception {
        String locationPayload = """
                {
                  "nome": "Busca API",
                  "cpf": "99988877765",
                  "dataNascimento": "1994-06-22",
                  "telefone": "21911113333"
                }
                """;

        String response = mockMvc.perform(post("/pacientes")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationPayload))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = response.replaceAll(".*\"id\":(\\d+).*", "$1");

        mockMvc.perform(get("/pacientes/{id}", id).header("Authorization", authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Long.parseLong(id)))
                .andExpect(jsonPath("$.nome").value("Busca API"));
    }

    @Test
    void deveListarTodosPacientes() throws Exception {
        mockMvc.perform(post("/pacientes")
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "nome": "Primeiro Paciente",
                          "cpf": "99988877764",
                          "dataNascimento": "1990-01-10",
                          "telefone": "21900001111"
                        }
                        """)).andExpect(status().isCreated());

        mockMvc.perform(post("/pacientes")
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "nome": "Segundo Paciente",
                          "cpf": "99988877763",
                          "dataNascimento": "1995-04-18",
                          "telefone": "21900002222"
                        }
                        """)).andExpect(status().isCreated());

        mockMvc.perform(get("/pacientes").header("Authorization", authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].nome").exists());
    }

    @Test
    void deveExcluirPaciente() throws Exception {
        String response = mockMvc.perform(post("/pacientes")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Excluir Paciente",
                                  "cpf": "99988877762",
                                  "dataNascimento": "1987-09-30",
                                  "telefone": "21933334444"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = response.replaceAll(".*\"id\":(\\d+).*", "$1");

        mockMvc.perform(delete("/pacientes/{id}", id).header("Authorization", authHeader))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pacientes/{id}", id).header("Authorization", authHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveNegarAcessoSemAutenticacao() throws Exception {
        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRestringirActuatorEnvParaAdmin() throws Exception {
        mockMvc.perform(get("/actuator/env").header("Authorization", authHeader))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/actuator/env").header("Authorization", basicAuth("testadmin", "adminpass")))
                .andExpect(status().isOk());
    }

    private String basicAuth(String username, String password) {
        String credentials = username + ":" + password;
        String token = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + token;
    }
}
