package com.automation.tests;

import com.automation.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Exercicio4LoginTests extends BaseTest {

  private String emailRegistrado;
  private final String senha = "MinhaSenha123";

  @Test(priority = 1)
    public void registrarNovoUsuarioEfazerLogin() {
        driver.findElement(By.linkText("Signup / Login")).click();

        // === CADASTRO ===
        String nome = "Aluno Teste";
        emailRegistrado = "aluno" + System.currentTimeMillis() + "@teste.com";

        driver.findElement(By.name("name")).sendKeys(nome);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(emailRegistrado);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys(senha);

        driver.findElement(By.id("newsletter")).click();

        driver.findElement(By.id("first_name")).sendKeys("Aluno");
        driver.findElement(By.id("last_name")).sendKeys("Teste");
        driver.findElement(By.id("address1")).sendKeys("Rua Automação, 123");
        driver.findElement(By.id("state")).sendKeys("California");
        driver.findElement(By.id("city")).sendKeys("Los Angeles");
        driver.findElement(By.id("zipcode")).sendKeys("90001");
        driver.findElement(By.id("mobile_number")).sendKeys("11988887777");

        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//b[text()='Account Created!']")).isDisplayed());
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        try { driver.findElement(By.cssSelector("a[aria-label='dismiss']")).click(); } catch (Exception ignored) {}

        String textoLogado = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(text(),'Logged in as')]")))
                .getText();

        Assert.assertTrue(textoLogado.contains("Logged in as"), "Login falhouve falha!");

        driver.findElement(By.linkText("Logout")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

  @Test(priority = 2, dependsOnMethods = "registrarNovoUsuarioE fazerLogin")
  public void loginComCredenciaisCorretasUsandoUsuarioCriado() {
    driver.findElement(By.linkText("Signup / Login")).click();

    driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(emailRegistrado);
    driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(senha);
    driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

    String texto = new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(text(),'Logged in as')]")))
        .getText();

    Assert.assertTrue(texto.contains("Logged in as"));
  }

  @Test(priority = 3)
  public void loginComSenhaIncorreta() {
    driver.findElement(By.linkText("Signup / Login")).click();

    driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("qualquercoisa@teste.com");
    driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("senhaerrada");
    driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

    String erro = new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(text(),'Your email or password is incorrect')]")))
        .getText();

    Assert.assertEquals(erro, "Your email or password is incorrect!");
  }
}