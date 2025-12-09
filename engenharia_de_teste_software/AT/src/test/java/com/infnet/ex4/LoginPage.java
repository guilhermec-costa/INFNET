package com.infnet.ex4;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object para a página de Login
 */
public class LoginPage {

  private WebDriver driver;
  private WebDriverWait wait;

  @FindBy(css = "a[href='/login']")
  private WebElement loginLink;

  @FindBy(css = "input[data-qa='login-email']")
  private WebElement emailField;

  @FindBy(css = "input[data-qa='login-password']")
  private WebElement passwordField;

  @FindBy(css = "button[data-qa='login-button']")
  private WebElement loginButton;

  @FindBy(xpath = "//p[contains(text(), 'Your email or password is incorrect')]")
  private WebElement errorMessage;

  @FindBy(xpath = "//a[contains(text(), ' Logged in as ')]")
  private WebElement loggedInAsText;

  @FindBy(css = "a[href='/logout']")
  private WebElement logoutLink;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
  }

  public void navigateToLoginPage() {
    driver.get("https://automationexercise.com");
    wait.until(ExpectedConditions.elementToBeClickable(loginLink));
    loginLink.click();
    wait.until(ExpectedConditions.visibilityOf(emailField));
  }

  public void fillEmail(String email) {
    wait.until(ExpectedConditions.visibilityOf(emailField));
    emailField.clear();
    emailField.sendKeys(email);
  }

  public void fillPassword(String password) {
    wait.until(ExpectedConditions.visibilityOf(passwordField));
    passwordField.clear();
    passwordField.sendKeys(password);
  }

  public void clickLoginButton() {
    wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    loginButton.click();
  }

  public void login(String email, String password) {
    fillEmail(email);
    fillPassword(password);
    clickLoginButton();
  }

  public boolean isErrorMessageDisplayed() {
    try {
      wait.until(ExpectedConditions.visibilityOf(errorMessage));
      return errorMessage.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public String getErrorMessage() {
    wait.until(ExpectedConditions.visibilityOf(errorMessage));
    return errorMessage.getText();
  }

  public boolean isLoggedIn() {
    try {
      wait.until(ExpectedConditions.visibilityOf(loggedInAsText));
      return loggedInAsText.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public String getLoggedInUsername() {
    wait.until(ExpectedConditions.visibilityOf(loggedInAsText));
    return loggedInAsText.getText();
  }

  public void logout() {
    if (isLogoutLinkPresent()) {
      wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
      logoutLink.click();
    }
  }

  public boolean isLogoutLinkPresent() {
    try {
      return logoutLink.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}