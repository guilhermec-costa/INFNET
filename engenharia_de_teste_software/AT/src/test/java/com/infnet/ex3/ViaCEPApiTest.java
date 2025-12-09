package com.infnet.ex3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da API ViaCEP")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ViaCEPApiTest {

  private static final String BASE_URL = "https://viacep.com.br/ws";

  @BeforeAll
  static void setup() {
    RestAssured.baseURI = BASE_URL;
  }

  @Test
  @Order(1)
  @DisplayName("Deve retornar endereço para CEP válido")
  void deveRetornarEnderecoParaCepValido() {
    given()
        .when()
        .get("/01310100/json/")
        .then()
        .statusCode(200)
        .body("cep", equalTo("01310-100"))
        .body("logradouro", not(emptyOrNullString()))
        .body("bairro", not(emptyOrNullString()))
        .body("localidade", not(emptyOrNullString()))
        .body("uf", not(emptyOrNullString()));
  }

  @Test
  @DisplayName("Deve aceitar CEP com 8 dígitos (formato correto)")
  void deveAceitarCepCom8Digitos() {
    given()
        .when()
        .get("/01310100/json/")
        .then()
        .statusCode(200);
  }

  @ParameterizedTest
  @ValueSource(strings = { "0131010", "123456", "01310" })
  @DisplayName("Deve rejeitar CEP com menos de 8 dígitos")
  void deveRejeitarCepComMenosDe8Digitos(String cepInvalido) {
    Response response = given()
        .when()
        .get("/" + cepInvalido + "/json/");

    // Aceita tanto erro 400 quanto resposta com campo "erro": true
    assertTrue(response.statusCode() == 400);
  }

  @ParameterizedTest
  @ValueSource(strings = { "00000000", "11111111", "99999999" })
  @DisplayName("Deve tratar CEPs com padrões repetitivos")
  void deveTratarCepsComPadroesRepetitivos(String cep) {
    Response response = given()
        .when()
        .get("/" + cep + "/json/");

    // Pode retornar erro ou CEP não encontrado
    assertTrue(response.statusCode() == 200 || response.statusCode() == 400);

    if (response.statusCode() == 200) {
      Boolean erro = response.jsonPath().getBoolean("erro");
      assertTrue(erro != null && erro);
    }
  }

  @Test
  @DisplayName("Deve rejeitar CEP com letras")
  void deveRejeitarCepComLetras() {
    Response response = given()
        .when()
        .get("/0131010a/json/");

    assertTrue(response.statusCode() == 400);
  }

  @Test
  @DisplayName("Deve rejeitar CEP com caracteres especiais")
  void deveRejeitarCepComCaracteresEspeciais() {
    Response response = given()
        .when()
        .get("/01310-10/json/");

    assertTrue(response.statusCode() == 400);
  }

  @Test
  @DisplayName("Deve rejeitar CEP vazio")
  void deveRejeitarCepVazio() {
    given()
        .when()
        .get("//json/")
        .then()
        .statusCode(400);
  }

  // ===== TESTES DE CONSULTA POR ENDEREÇO =====

  @Test
  @DisplayName("Deve retornar resultados para busca por endereço completo")
  void deveRetornarResultadosParaBuscaPorEndereco() {
    given()
        .when()
        .get("/SP/Sao Paulo/Avenida Paulista/json/")
        .then()
        .statusCode(200)
        .body("$", not(empty()))
        .body("[0].cep", not(emptyOrNullString()))
        .body("[0].logradouro", containsStringIgnoringCase("Paulista"));
  }

  @Test
  @DisplayName("Deve retornar resultados para busca sem acentuação")
  void deveRetornarResultadosParaBuscaSemAcentuacao() {
    given()
        .when()
        .get("/SP/Sao Paulo/Avenida Paulista/json/")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
  }

  @Test
  @DisplayName("Deve retornar vazio para logradouro inexistente")
  void deveRetornarVazioParaLogradouroInexistente() {
    Response response = given()
        .when()
        .get("/SP/Sao Paulo/Rua Inexistente Xyz 123/json/");

    if (response.statusCode() == 200) {
      assertTrue(response.jsonPath().getList("$").isEmpty());
    }
  }

  // ===== TABELA DE DECISÃO =====

  /**
   * Tabela de Decisão para Consulta por Endereço
   * 
   * Condições:
   * 1. UF válida (SP, RJ, MG, etc)
   * 2. Cidade existe
   * 3. Cidade com acentuação correta
   * 4. Logradouro existe
   * 
   * Regras:
   * R1: UF=V, Cidade=V, Acento=S, Log=V -> Sucesso 200 com dados
   * R2: UF=V, Cidade=V, Acento=N, Log=V -> Sucesso 200 com dados
   * R3: UF=V, Cidade=V, Acento=S, Log=I -> Sucesso 200 vazio
   * R4: UF=V, Cidade=I, Acento=N/A, Log=N/A -> Erro 400 ou vazio
   * R5: UF=I, Cidade=N/A, Acento=N/A, Log=N/A -> Erro 400
   */

  @Test
  @DisplayName("R1: UF válida + Cidade válida + Acentuação correta + Logradouro válido")
  void tabelaDecisao_R1() {
    given()
        .when()
        .get("/SP/São Paulo/Avenida Paulista/json/")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
  }

  @Test
  @DisplayName("R2: UF válida + Cidade válida + Sem acentuação + Logradouro válido")
  void tabelaDecisao_R2() {
    given()
        .when()
        .get("/SP/Sao Paulo/Avenida Paulista/json/")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
  }

  @Test
  @DisplayName("R3: UF válida + Cidade válida + Acentuação correta + Logradouro inexistente")
  void tabelaDecisao_R3() {
    Response response = given()
        .when()
        .get("/SP/São Paulo/Rua Totalmente Inexistente 999/json/");

    assertTrue(response.statusCode() == 200);
    if (response.statusCode() == 200) {
      assertTrue(response.jsonPath().getList("$").isEmpty() ||
          response.jsonPath().getList("$").size() == 0);
    }
  }

  @Test
  @DisplayName("R4: UF válida + Cidade inexistente")
  void tabelaDecisao_R4() {
    Response response = given()
        .when()
        .get("/SP/Cidade Inexistente XYZ/Rua Qualquer/json/");

    assertTrue(response.statusCode() == 400 ||
        (response.statusCode() == 200 &&
            response.jsonPath().getList("$").isEmpty()));
  }

  @Test
  @DisplayName("R5: UF inválida")
  void tabelaDecisao_R5() {
    Response response = given()
        .when()
        .get("/XX/Sao Paulo/Avenida Paulista/json/");

    assertTrue(response.statusCode() == 400 ||
        (response.statusCode() == 200 &&
            response.jsonPath().getList("$").isEmpty()));
  }

  // ===== TESTES DE FORMATO DE RESPOSTA =====

  @Test
  @DisplayName("Deve retornar JSON válido")
  void deveRetornarJsonValido() {
    given()
        .when()
        .get("/01310100/json/")
        .then()
        .statusCode(200)
        .contentType("application/json;charset=UTF-8");
  }

  @Test
  @DisplayName("Resposta deve conter todos os campos esperados")
  void respostaDeveConterTodosCamposEsperados() {
    given()
        .when()
        .get("/01310100/json/")
        .then()
        .statusCode(200)
        .body("$", hasKey("cep"))
        .body("$", hasKey("logradouro"))
        .body("$", hasKey("complemento"))
        .body("$", hasKey("bairro"))
        .body("$", hasKey("localidade"))
        .body("$", hasKey("uf"))
        .body("$", hasKey("ibge"))
        .body("$", hasKey("gia"))
        .body("$", hasKey("ddd"))
        .body("$", hasKey("siafi"));
  }

  // ===== TESTES DE PERFORMANCE E DISPONIBILIDADE =====

  @Test
  @DisplayName("Deve responder em tempo razoável (< 3 segundos)")
  void deveResponderEmTempoRazoavel() {
    long startTime = System.currentTimeMillis();

    given()
        .when()
        .get("/01310100/json/")
        .then()
        .statusCode(200);

    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    assertTrue(duration < 3000,
        "API demorou mais de 3 segundos: " + duration + "ms");
  }
}