package com.automation.tests;

import com.automation.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class Exercicio8CarrinhoECheckout extends BaseTest {

  // Fecha qualquer anúncio ou iframe chato que aparecer
  private void matarAnuncios() {
    try {
      // Fecha iframes do Google Ads
      for (WebElement iframe : driver.findElements(By.tagName("iframe"))) {
        try {
          driver.switchTo().frame(iframe);
          driver.findElements(By.cssSelector("*"))
              .stream()
              .filter(e -> {
                String txt = e.getText() + e.getAttribute("id") + e.getAttribute("class");
                return txt.toLowerCase().contains("close") || txt.contains("×") || txt.contains("dismiss");
              })
              .findFirst()
              .ifPresent(e -> ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e));
          driver.switchTo().defaultContent();
        } catch (Exception ignored) {
        }
      }

      // Fecha overlays comuns
      String[] closes = {
          "//button[contains(text(),'×') or contains(text(),'Close')]",
          "//a[contains(@id,'dismiss') or contains(@class,'close')]",
          "//div[contains(@class,'close') or contains(@id,'close')]"
      };
      for (String xpath : closes) {
        driver.findElements(By.xpath(xpath))
            .forEach(e -> ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e));
      }
    } catch (Exception ignored) {
    }
  }

  // Adiciona um produto ao carrinho de forma segura
  private void adicionarProdutoAoCarrinho(int productId) {
    matarAnuncios();

    By botao = By.xpath("//a[@data-product-id='" + productId + "' and contains(@class,'add-to-cart')][1]");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(botao));

    // Scroll até o botão (importante!)
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);

    // Clique via JavaScript = nunca mais ElementClickIntercepted
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

    // Fecha o modal "Added!" → Continue Shopping
    try {
      By continueBtn = By.xpath("//button[normalize-space(.)='Continue Shopping']");
      wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    } catch (Exception e) {

    }
    ;

    // Espera um pouquinho para o carrinho atualizar
    try {
      Thread.sleep(1500);
    } catch (InterruptedException ignored) {
    }
  }

  @Test
  public void adicionarProdutosAoCarrinhoEValidarQuantidade() {
    adicionarProdutoAoCarrinho(1); // Blue Top
    adicionarProdutoAoCarrinho(2); // Men Tshirt

    // Vai para o carrinho
    driver.findElement(By.linkText("Cart")).click();
    matarAnuncios(); // às vezes tem propaganda no carrinho também

    // === Verifica quantidade 1 ===
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    String quantidade = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//button[@class='disabled' and text()='1']"))).getText();
    Assert.assertEquals(quantidade, "1");

    // === Muda quantidade para 4 ===
    WebElement campoQuantidade = driver.findElement(By.cssSelector("input[name='quantity']"));
    campoQuantidade.clear();
    campoQuantidade.sendKeys("4");

    driver.findElement(By.cssSelector("button.cart_quantity_up")).click();

    // Espera aparecer o número 4
    wait.until(ExpectedConditions.textToBePresentInElementLocated(
        By.xpath("//td[@class='cart_quantity']//button"), "4"));

    String novaQuantidade = driver.findElement(By.xpath("//button[text()='4']")).getText();
    Assert.assertEquals(novaQuantidade, "4");

    System.out.println("Exercício 8 - Carrinho e quantidade validados com sucesso!");
  }
}