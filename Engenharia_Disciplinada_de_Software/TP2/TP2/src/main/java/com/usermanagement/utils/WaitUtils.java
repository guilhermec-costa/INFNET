package com.usermanagement.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe utilitária para gerenciar esperas explícitas.
 * Fornece métodos para aguardar condições específicas da interface.
 */
public class WaitUtils {

  private final WebDriver driver;
  private final WebDriverWait wait;
  private static final int DEFAULT_WAIT_TIME = 10;

  public WaitUtils(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
  }

  /**
   * Aguarda elemento estar visível na página.
   * 
   * @param locator Localizador do elemento
   * @return Elemento visível
   */
  public WebElement waitForElementVisible(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  /**
   * Aguarda elemento estar clicável.
   * 
   * @param locator Localizador do elemento
   * @return Elemento clicável
   */
  public WebElement waitForElementClickable(By locator) {
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  /**
   * Aguarda elemento estar presente no DOM.
   * 
   * @param locator Localizador do elemento
   * @return Elemento presente
   */
  public WebElement waitForElementPresent(By locator) {
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  /**
   * Aguarda elemento desaparecer da página.
   * 
   * @param locator Localizador do elemento
   * @return true se elemento desapareceu
   */
  public boolean waitForElementInvisible(By locator) {
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  /**
   * Aguarda texto específico estar presente no elemento.
   * 
   * @param locator Localizador do elemento
   * @param text    Texto esperado
   * @return true se texto está presente
   */
  public boolean waitForTextPresent(By locator, String text) {
    return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
  }

  /**
   * Aguarda alerta estar presente.
   * 
   * @return true se alerta está presente
   */
  public boolean waitForAlertPresent() {
    try {
      wait.until(ExpectedConditions.alertIsPresent());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Aguarda tempo específico (usar com moderação).
   * 
   * @param milliseconds Tempo em milissegundos
   */
  public void waitForTime(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Espera foi interrompida", e);
    }
  }

  /**
   * Aguarda elemento conter atributo específico.
   * 
   * @param locator   Localizador do elemento
   * @param attribute Nome do atributo
   * @param value     Valor esperado
   * @return true se atributo contém o valor
   */
  public boolean waitForAttributeContains(By locator, String attribute, String value) {
    return wait.until(ExpectedConditions.attributeContains(locator, attribute, value));
  }
}