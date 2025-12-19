package com.usermanagement.tests.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * Classe base para todos os testes automatizados.
 * Gerencia a inicialização e encerramento do WebDriver.
 */
public class BaseTest {

  protected WebDriver driver;
  protected static final String BASE_URL = "file:///home/guichina/dev/infnet/Engenharia_Disciplinada_de_Software/TP2/index.html";
  protected static final int DEFAULT_TIMEOUT = 10;

  /**
   * Configura o WebDriver antes de cada método de teste.
   * 
   * @param browser Nome do navegador (chrome, firefox, edge)
   */
  @BeforeMethod
  @Parameters("browser")
  public void setUp(@Optional("chrome") String browser) {
    driver = createDriver(browser);
    configureDriver();
    navigateToApplication();
  }

  /**
   * Cria instância do WebDriver baseado no navegador especificado.
   * 
   * @param browser Nome do navegador
   * @return Instância configurada do WebDriver
   * @throws IllegalArgumentException se o navegador não for suportado
   */
  private WebDriver createDriver(String browser) {
    WebDriver webDriver;

    switch (browser.toLowerCase()) {
      case "chrome":
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-popup-blocking");
        webDriver = new ChromeDriver(chromeOptions);
        break;

      case "firefox":
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        webDriver = new FirefoxDriver(firefoxOptions);
        break;

      case "edge":
        WebDriverManager.edgedriver().setup();
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-maximized");
        webDriver = new EdgeDriver(edgeOptions);
        break;

      default:
        throw new IllegalArgumentException("Navegador não suportado: " + browser);
    }

    return webDriver;
  }

  /**
   * Configura timeouts e comportamentos padrão do driver.
   */
  private void configureDriver() {
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
  }

  /**
   * Navega para a aplicação e garante que a página está carregada.
   */
  private void navigateToApplication() {
    driver.get(BASE_URL);
  }

  /**
   * Encerra o WebDriver após cada teste.
   * Garante que recursos sejam liberados adequadamente.
   */
  @AfterMethod
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  /**
   * Retorna a instância atual do WebDriver.
   * 
   * @return WebDriver atual
   */
  protected WebDriver getDriver() {
    return driver;
  }
}