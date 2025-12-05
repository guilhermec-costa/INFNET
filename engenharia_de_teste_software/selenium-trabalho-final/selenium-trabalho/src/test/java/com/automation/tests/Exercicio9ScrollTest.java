package com.automation.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;

import java.time.Duration;

public class Exercicio9ScrollTest extends BaseTest {

  @Test
  public void testarScrollComBotaoArrowESemArrow() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Scroll até o final
    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Subscription']")));

    // Clicar na seta para subir
    driver.findElement(By.id("scrollUp")).click();

    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".carousel-inner")));

    String texto = driver.findElement(By.xpath("//h2[text()='Full-Fledged practice website for Automation Engineers']"))
        .getText();
    Assert.assertTrue(texto.contains("Full-Fledged"));
  }
}