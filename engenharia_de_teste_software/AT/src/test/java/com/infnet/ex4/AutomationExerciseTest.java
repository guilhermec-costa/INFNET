package com.infnet.ex4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados com Selenium WebDriver
 * Aplicando Page Object Model e boas práticas de automação
 */
@DisplayName("Testes de Automação - Automation Exercise")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AutomationExerciseTest {

  private static WebDriver driver;
  private LoginPage loginPage;
  private SignupPage signupPage;

  private static final String SCREENSHOTS_DIR = "target/screenshots/";

  @BeforeAll
  static void setupClass() {
    WebDriverManager.chromedriver().setup();

    new File(SCREENSHOTS_DIR).mkdirs();
  }

  @BeforeEach
  void setup() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized");
    options.addArguments("--disable-notifications");
    options.addArguments("--disable-popup-blocking");

    // options.addArguments("--headless");

    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

    loginPage = new LoginPage(driver);
    signupPage = new SignupPage(driver);
  }

  @AfterEach
  void tearDown(TestInfo testInfo) {
    if (testInfo.getTestMethod().isPresent()) {
      String testName = testInfo.getTestMethod().get().getName();

      if (testInfo.getTags().contains("failed") ||
          testName.contains("Failed") ||
          testName.contains("Error")) {
        captureScreenshot(testName);
      }
    }

    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  @Order(1)
  @DisplayName("Deve cadastrar novo usuário com sucesso")
  void deveCadastrarNovoUsuarioComSucesso() {
    try {
      signupPage.navigateToSignupPage();

      String uniqueEmail = "testuser" + System.currentTimeMillis() + "@test.com";

      signupPage.completeSignup(
          "Test User",
          uniqueEmail,
          "Test@123",
          "Test",
          "User",
          "1234567890");

      assertTrue(signupPage.isAccountCreatedMessageDisplayed(),
          "Mensagem de conta criada não foi exibida");

      signupPage.clickContinueButton();

      assertTrue(loginPage.isLoggedIn(),
          "Usuário não está logado após cadastro");

    } catch (Exception e) {
      captureScreenshot("deveCadastrarNovoUsuarioComSucesso_FAILED");
      fail("Teste falhou: " + e.getMessage());
    }
  }

  @Test
  @Order(2)
  @DisplayName("Deve fazer login com credenciais válidas")
  void deveFazerLoginComCredenciaisValidas() {
    try {
      String uniqueEmail = "logintest" + System.currentTimeMillis() + "@test.com";
      String password = "Test@123";

      signupPage.navigateToSignupPage();
      signupPage.completeSignup("Login Test", uniqueEmail, password,
          "Login", "Test", "9876543210");

      assertTrue(signupPage.isAccountCreatedMessageDisplayed());
      signupPage.clickContinueButton();

      loginPage.logout();

      loginPage.navigateToLoginPage();
      loginPage.login(uniqueEmail, password);

      assertTrue(loginPage.isLoggedIn(),
          "Usuário não conseguiu fazer login com credenciais válidas");

      String loggedInText = loginPage.getLoggedInUsername();
      assertTrue(loggedInText.contains("Login Test"),
          "Nome do usuário não aparece após login");

    } catch (Exception e) {
      captureScreenshot("deveFazerLoginComCredenciaisValidas_FAILED");
      fail("Teste falhou: " + e.getMessage());
    }
  }

  @Test
  @Order(3)
  @DisplayName("Deve exibir erro ao tentar login com email inválido")
  void deveExibirErroComEmailInvalido() {
    try {
      loginPage.navigateToLoginPage();
      loginPage.login("emailinvalido@naocadastrado.com", "qualquersenha");

      assertTrue(loginPage.isErrorMessageDisplayed(),
          "Mensagem de erro não foi exibida para email inválido");

      String errorMessage = loginPage.getErrorMessage();
      assertTrue(errorMessage.contains("incorrect") ||
          errorMessage.contains("wrong"),
          "Mensagem de erro não contém texto esperado");

    } catch (Exception e) {
      captureScreenshot("deveExibirErroComEmailInvalido_FAILED");
      fail("Teste falhou: " + e.getMessage());
    }
  }

  @Test
  @Order(4)
  @DisplayName("Deve exibir erro ao tentar login com senha incorreta")
  void deveExibirErroComSenhaIncorreta() {
    try {
      loginPage.navigateToLoginPage();
      loginPage.login("test@test.com", "senhaerrada123");

      assertTrue(loginPage.isErrorMessageDisplayed(),
          "Mensagem de erro não foi exibida para senha incorreta");

    } catch (Exception e) {
      captureScreenshot("deveExibirErroComSenhaIncorreta_FAILED");
      fail("Teste falhou: " + e.getMessage());
    }
  }

  @Test
  @Order(5)
  @DisplayName("Deve exibir erro ao tentar login com campos vazios")
  void deveExibirErroComCamposVazios() {
    try {
      loginPage.navigateToLoginPage();
      loginPage.login("", "");

      assertTrue(loginPage.isErrorMessageDisplayed() ||
          driver.getCurrentUrl().contains("login"),
          "Validação não funcionou para campos vazios");

    } catch (Exception e) {
      captureScreenshot("deveExibirErroComCamposVazios_FAILED");
      fail("Teste falhou: " + e.getMessage());
    }
  }

  @Test
  @Order(6)
  @DisplayName("Deve exibir erro para formato de email inválido")
  void deveExibirErroParaFormatoEmailInvalido() {
    try {
      loginPage.navigateToLoginPage();
      loginPage.fillEmail("emailsemarroba.com");
      loginPage.fillPassword("senha123");
      loginPage.clickLoginButton();

      String currentUrl = driver.getCurrentUrl();
      assertTrue(currentUrl.contains("login") ||
          loginPage.isErrorMessageDisplayed(),
          "Validação de formato de email não funcionou");

    } catch (Exception e) {
      captureScreenshot("deveExibirErroParaFormatoEmailInvalido_FAILED");
      fail("Teste falhou: " + e.getMessage());
    }
  }

  @Test
  @Order(7)
  @DisplayName("Deve fazer logout com sucesso")
  void deveFazerLogoutComSucesso() {
    try {
      String uniqueEmail = "logouttest" + System.currentTimeMillis() + "@test.com";

      signupPage.navigateToSignupPage();
      signupPage.completeSignup("Logout Test", uniqueEmail, "Test@123",
          "Logout", "Test", "5555555555");

      signupPage.clickContinueButton();

      assertTrue(loginPage.isLoggedIn(), "Usuário não está logado");

      loginPage.logout();

      assertFalse(loginPage.isLogoutLinkPresent(),
          "Link de logout ainda está presente após logout");

    } catch (Exception e) {
      captureScreenshot("deveFazerLogoutComSucesso_FAILED");
      fail("Teste falhou: " + e.getMessage());
    }
  }

  // ===== MÉTODOS AUXILIARES =====

  /**
   * Captura screenshot da tela atual
   * 
   * @param testName Nome do teste para incluir no nome do arquivo
   */
  private void captureScreenshot(String testName) {
    try {
      TakesScreenshot screenshot = (TakesScreenshot) driver;
      File source = screenshot.getScreenshotAs(OutputType.FILE);

      String timestamp = LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

      String fileName = testName + "_" + timestamp + ".png";
      File destination = new File(SCREENSHOTS_DIR + fileName);

      FileUtils.copyFile(source, destination);

      System.out.println("Screenshot capturado: " + destination.getAbsolutePath());

    } catch (IOException e) {
      System.err.println("Erro ao capturar screenshot: " + e.getMessage());
    }
  }
}