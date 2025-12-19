package com.usermanagement.pages;

import com.usermanagement.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Classe base para todos os Page Objects.
 * Segue o padrão Page Object Model (POM).
 */
public abstract class BasePage {

  protected WebDriver driver;
  protected WaitUtils waitUtils;
  protected JavascriptExecutor jsExecutor;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.waitUtils = new WaitUtils(driver);
    this.jsExecutor = (JavascriptExecutor) driver;
    PageFactory.initElements(driver, this);
  }

  /**
   * Clica em elemento com espera explícita.
   * 
   * @param locator Localizador do elemento
   */
  protected void click(By locator) {
    WebElement element = waitUtils.waitForElementClickable(locator);
    element.click();
  }

  /**
   * Preenche campo de texto.
   * 
   * @param locator Localizador do campo
   * @param text    Texto a ser inserido
   */
  protected void type(By locator, String text) {
    WebElement element = waitUtils.waitForElementVisible(locator);
    element.clear();
    element.sendKeys(text);
  }

  /**
   * Obtém texto de elemento.
   * 
   * @param locator Localizador do elemento
   * @return Texto do elemento
   */
  protected String getText(By locator) {
    WebElement element = waitUtils.waitForElementVisible(locator);
    return element.getText();
  }

  /**
   * Verifica se elemento está visível.
   * 
   * @param locator Localizador do elemento
   * @return true se elemento está visível
   */
  protected boolean isElementVisible(By locator) {
    try {
      WebElement element = waitUtils.waitForElementVisible(locator);
      return element.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Verifica se elemento está presente no DOM.
   * 
   * @param locator Localizador do elemento
   * @return true se elemento está presente
   */
  protected boolean isElementPresent(By locator) {
    try {
      driver.findElement(locator);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Executa JavaScript na página.
   * 
   * @param script Script JavaScript
   * @param args   Argumentos do script
   * @return Resultado da execução
   */
  protected Object executeScript(String script, Object... args) {
    return jsExecutor.executeScript(script, args);
  }

  /**
   * Rola página até elemento.
   * 
   * @param locator Localizador do elemento
   */
  protected void scrollToElement(By locator) {
    WebElement element = driver.findElement(locator);
    executeScript("arguments[0].scrollIntoView(true);", element);
  }

  /**
   * Obtém título da página.
   * 
   * @return Título da página
   */
  public String getPageTitle() {
    return driver.getTitle();
  }

  /**
   * Obtém URL atual.
   * 
   * @return URL atual
   */
  protected String getCurrentUrl() {
    return driver.getCurrentUrl();
  }

  /**
   * Aguarda tempo específico.
   * 
   * @param milliseconds Tempo em milissegundos
   */
  protected void waitFor(long milliseconds) {
    waitUtils.waitForTime(milliseconds);
  }

  /**
   * Obtém atributo de elemento.
   * 
   * @param locator       Localizador do elemento
   * @param attributeName Nome do atributo
   * @return Valor do atributo
   */
  protected String getAttribute(By locator, String attributeName) {
    WebElement element = waitUtils.waitForElementVisible(locator);
    return element.getAttribute(attributeName);
  }
}
