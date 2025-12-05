package com.automation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

  public static void tirarScreenshot(WebDriver driver, String nomeTeste) {
    TakesScreenshot ts = (TakesScreenshot) driver;
    File source = ts.getScreenshotAs(OutputType.FILE);
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String caminho = "screenshots/" + nomeTeste + "_" + timestamp + ".png";

    try {
      FileUtils.copyFile(source, new File(caminho));
      System.out.println("Screenshot salvo em: " + caminho);
    } catch (IOException e) {
      System.out.println("Erro ao salvar screenshot: " + e.getMessage());
    }
  }
}