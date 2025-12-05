package com.automation.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.utils.ScreenshotUtil;

public class Exercicio7ScreenshotTest extends BaseTest {

    @Test
    public void validarPaginaTestCasesEScreenshot() {
        driver.findElement(By.linkText("Test Cases")).click();

        String titulo = driver.findElement(By.cssSelector("h2.title")).getText();
        org.testng.Assert.assertTrue(titulo.contains("TEST CASES"));

        ScreenshotUtil.tirarScreenshot(driver, "TestCasesPage");

        var elemento = driver.findElement(By.cssSelector(".col-sm-9"));
        var file = elemento.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
    }
}
