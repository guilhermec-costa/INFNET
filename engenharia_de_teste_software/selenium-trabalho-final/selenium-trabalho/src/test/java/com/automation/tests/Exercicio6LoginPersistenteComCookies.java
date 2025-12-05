package com.automation.tests;

import com.automation.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class Exercicio6LoginPersistenteComCookies extends BaseTest {

  private static final String COOKIE_FILE = "cookies.data";
  private String emailCriado;
  private final String senha = "Teste@2025";

  @Test(priority = 1)
  public void registrarUsuarioESalvarCookies() {
    driver.findElement(By.linkText("Signup / Login")).click();

    // === REGISTRO AUTOMÁTICO ===
    String nome = "Aluno Automação";
    emailCriado = "aluno" + System.currentTimeMillis() + "@temp.com";

    driver.findElement(By.name("name")).sendKeys(nome);
    driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(emailCriado);
    driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

    driver.findElement(By.id("id_gender1")).click();
    driver.findElement(By.id("password")).sendKeys(senha);

    driver.findElement(By.id("days")).findElement(By.xpath("//option[@value='10']")).click();
    driver.findElement(By.id("months")).findElement(By.xpath("//option[@value='5']")).click();
    driver.findElement(By.id("years")).findElement(By.xpath("//option[@value='1995']")).click();

    driver.findElement(By.id("newsletter")).click();

    driver.findElement(By.id("first_name")).sendKeys("Aluno");
    driver.findElement(By.id("last_name")).sendKeys("Automação");
    driver.findElement(By.id("address1")).sendKeys("Av. Teste, 999");
    driver.findElement(By.id("country")).findElement(By.xpath("//option[.='United States']")).click();
    driver.findElement(By.id("state")).sendKeys("California");
    driver.findElement(By.id("city")).sendKeys("Los Angeles");
    driver.findElement(By.id("zipcode")).sendKeys("90210");
    driver.findElement(By.id("mobile_number")).sendKeys("11999999999");

    driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

    // Confirma conta criada
    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']")));

    driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

    // Fecha possível anúncio
    try {
      driver.findElement(By.cssSelector("[aria-label='Close'], [id^='dismiss']")).click();
    } catch (Exception ignored) {
    }

    // === SALVA OS COOKIES DO USUÁRIO RECÉM-CRIADO ===
    Set<Cookie> cookies = driver.manage().getCookies();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(COOKIE_FILE))) {
      for (Cookie ck : cookies) {
        String expiry = ck.getExpiry() == null ? "null" : ck.getExpiry().toString();
        writer.write(ck.getName() + ";" +
            ck.getValue() + ";" +
            ck.getDomain() + ";" +
            ck.getPath() + ";" +
            expiry + ";" +
            ck.isSecure() + ";" +
            ck.isHttpOnly());
        writer.newLine();
      }
      System.out.println("Cookies salvos com sucesso! Total: " + cookies.size());
    } catch (IOException e) {
      throw new RuntimeException("Erro ao salvar cookies", e);
    }

    // Confirma que está logado
    String logado = new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(text(),'Logged in as')]")))
        .getText();
    Assert.assertTrue(logado.contains("Logged in as"));
  }

  @Test(priority = 2, dependsOnMethods = "registrarUsuarioESalvarCookies")
    public void reutilizarCookiesSemFazerLoginNovamente() {
        driver.manage().deleteAllCookies();
        driver.get("https://automationexercise.com");

        try (BufferedReader reader = new BufferedReader(new FileReader(COOKIE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length < 6) continue;

                String name = parts[0];
                String value = parts[1];
                String domain = parts[2];
                String path = parts[3];
                String expiryStr = parts[4];
                boolean secure = Boolean.parseBoolean(parts[5]);
                boolean httpOnly = parts.length > 6 && Boolean.parseBoolean(parts[6]);

                Date expiry = null;
                if (expiryStr != null && !expiryStr.equals("null") && !expiryStr.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                        expiry = sdf.parse(expiryStr);
                    } catch (ParseException e) {
                        try {
                            SimpleDateFormat sdf2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                            expiry = sdf2.parse(expiryStr);
                        } catch (ParseException ignored) { }
                    }
                }

                Cookie cookie = new Cookie.Builder(name, value)
                        .domain(domain)
                        .path(path)
                        .expiresOn(expiry)
                        .isSecure(secure)
                        .isHttpOnly(httpOnly)
                        .build();

                driver.manage().addCookie(cookie);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar cookies", e);
        }

        driver.navigate().refresh();

        boolean logado = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(text(),'Logged in as')]")))
                .isDisplayed();

        Assert.assertTrue(logado, "Falha ao reutilizar cookies! Usuário não está logado.");
        System.out.println("Login persistente com cookies FUNCIONOU 100%!");
    }
}