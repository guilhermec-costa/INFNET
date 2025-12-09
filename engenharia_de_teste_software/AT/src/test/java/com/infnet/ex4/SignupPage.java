package com.infnet.ex4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object para a página de Cadastro (Signup)
 */
public class SignupPage {

  private WebDriver driver;
  private WebDriverWait wait;

  @FindBy(css = "a[href='/login']")
  private WebElement signupLoginLink;

  @FindBy(css = "input[data-qa='signup-name']")
  private WebElement signupNameField;

  @FindBy(css = "input[data-qa='signup-email']")
  private WebElement signupEmailField;

  @FindBy(css = "button[data-qa='signup-button']")
  private WebElement signupButton;

  @FindBy(id = "id_gender1")
  private WebElement genderMr;

  @FindBy(id = "id_gender2")
  private WebElement genderMrs;

  @FindBy(css = "input[data-qa='password']")
  private WebElement passwordField;

  @FindBy(css = "select[data-qa='days']")
  private WebElement daySelect;

  @FindBy(css = "select[data-qa='months']")
  private WebElement monthSelect;

  @FindBy(css = "select[data-qa='years']")
  private WebElement yearSelect;

  @FindBy(css = "input[data-qa='first_name']")
  private WebElement firstNameField;

  @FindBy(css = "input[data-qa='last_name']")
  private WebElement lastNameField;

  @FindBy(css = "input[data-qa='company']")
  private WebElement companyField;

  @FindBy(css = "input[data-qa='address']")
  private WebElement addressField;

  @FindBy(css = "input[data-qa='address2']")
  private WebElement address2Field;

  @FindBy(css = "select[data-qa='country']")
  private WebElement countrySelect;

  @FindBy(css = "input[data-qa='state']")
  private WebElement stateField;

  @FindBy(css = "input[data-qa='city']")
  private WebElement cityField;

  @FindBy(css = "input[data-qa='zipcode']")
  private WebElement zipcodeField;

  @FindBy(css = "input[data-qa='mobile_number']")
  private WebElement mobileNumberField;

  @FindBy(css = "button[data-qa='create-account']")
  private WebElement createAccountButton;

  @FindBy(xpath = "//h2[@class='title text-center']/b[contains(text(), 'Account Created!')]")
  private WebElement accountCreatedMessage;

  @FindBy(css = "a[data-qa='continue-button']")
  private WebElement continueButton;

  public SignupPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
  }

  public void navigateToSignupPage() {
    driver.get("https://automationexercise.com");
    wait.until(ExpectedConditions.elementToBeClickable(signupLoginLink));
    signupLoginLink.click();
    wait.until(ExpectedConditions.visibilityOf(signupNameField));
  }

  public void fillInitialSignupForm(String name, String email) {
    wait.until(ExpectedConditions.visibilityOf(signupNameField));
    signupNameField.clear();
    signupNameField.sendKeys(name);

    signupEmailField.clear();
    signupEmailField.sendKeys(email);
  }

  public void clickSignupButton() {
    wait.until(ExpectedConditions.elementToBeClickable(signupButton));
    signupButton.click();
  }

  public void fillAccountInformation(String password, String day, String month, String year) {
    wait.until(ExpectedConditions.elementToBeClickable(genderMr));
    genderMr.click();

    wait.until(ExpectedConditions.visibilityOf(passwordField));
    passwordField.sendKeys(password);

    new Select(daySelect).selectByValue(day);
    new Select(monthSelect).selectByValue(month);
    new Select(yearSelect).selectByValue(year);
  }

  public void fillAddressInformation(String firstName, String lastName, String company,
      String address, String country, String state,
      String city, String zipcode, String mobile) {
    firstNameField.sendKeys(firstName);
    lastNameField.sendKeys(lastName);
    companyField.sendKeys(company);
    addressField.sendKeys(address);

    new Select(countrySelect).selectByValue(country);

    stateField.sendKeys(state);
    cityField.sendKeys(city);
    zipcodeField.sendKeys(zipcode);
    mobileNumberField.sendKeys(mobile);
  }

  public void clickCreateAccountButton() {
    wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
    createAccountButton.click();
  }

  public boolean isAccountCreatedMessageDisplayed() {
    try {
      wait.until(ExpectedConditions.visibilityOf(accountCreatedMessage));
      return accountCreatedMessage.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public void clickContinueButton() {
    wait.until(ExpectedConditions.elementToBeClickable(continueButton));
    continueButton.click();
  }

  public void completeSignup(String name, String email, String password,
      String firstName, String lastName, String mobile) {
    fillInitialSignupForm(name, email);
    clickSignupButton();

    fillAccountInformation(password, "15", "5", "1990");
    fillAddressInformation(firstName, lastName, "Test Company",
        "123 Test Street", "India", "Test State",
        "Test City", "12345", mobile);

    clickCreateAccountButton();
  }
}