package com.automation.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;

public class Exercicio3RegisterAndContact extends BaseTest {

  @Test(priority = 1)
  public void registrarNovoUsuario() {
    driver.findElement(By.linkText("Signup / Login")).click();

    driver.findElement(By.name("name")).sendKeys("João Silva");
    driver.findElement(By.xpath("//input[@data-qa='signup-email']"))
        .sendKeys("joao" + System.currentTimeMillis() + "@teste.com");
    driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

    driver.findElement(By.id("id_gender1")).click();
    driver.findElement(By.id("password")).sendKeys("123456");
    driver.findElement(By.id("days")).sendKeys("15");
    driver.findElement(By.id("months")).sendKeys("March");
    driver.findElement(By.id("years")).sendKeys("1990");

    driver.findElement(By.id("newsletter")).click();
    driver.findElement(By.id("optin")).click();

    driver.findElement(By.id("first_name")).sendKeys("João");
    driver.findElement(By.id("last_name")).sendKeys("Silva");
    driver.findElement(By.id("address1")).sendKeys("Rua Teste, 123");
    driver.findElement(By.id("country")).sendKeys("United States");
    driver.findElement(By.id("state")).sendKeys("California");
    driver.findElement(By.id("city")).sendKeys("Los Angeles");
    driver.findElement(By.id("zipcode")).sendKeys("90001");
    driver.findElement(By.id("mobile_number")).sendKeys("11999999999");

    driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

    String texto = driver.findElement(By.xpath("//h2[@data-qa='account-created']/b")).getText();
    Assert.assertEquals(texto, "ACCOUNT CREATED!");

    driver.findElement(By.linkText("Continue")).click();
    // Fecha possível ad
    try {
      driver.findElement(By.cssSelector("div[aria-label='Close ad']")).click();
    } catch (Exception e) {
      /* ignora */ }

    String usuarioLogado = driver.findElement(By.xpath("//li/a[contains(text(),'Logged in as')]")).getText();
    Assert.assertTrue(usuarioLogado.contains("João Silva"));
  }

  @Test(priority = 2)
  public void enviarFormularioContato() {
    driver.findElement(By.linkText("Contact us")).click();

    driver.findElement(By.name("name")).sendKeys("Maria Oliveira");
    driver.findElement(By.name("email")).sendKeys("maria@teste.com");
    driver.findElement(By.name("subject")).sendKeys("Dúvida sobre produto");
    driver.findElement(By.id("message")).sendKeys("Olá, gostaria de saber mais sobre o produto X...");

    // Upload de arquivo (opcional)
    driver.findElement(By.name("upload_file"))
        .sendKeys(System.getProperty("user.dir") + "/src/test/resources/test.txt");

    driver.findElement(By.name("submit")).click();

    // Confirma alerta
    driver.switchTo().alert().accept();

    String sucesso = driver.findElement(By.cssSelector(".status.alert-success")).getText();
    Assert.assertTrue(sucesso.contains("Success"));

    driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
    Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));
  }
}