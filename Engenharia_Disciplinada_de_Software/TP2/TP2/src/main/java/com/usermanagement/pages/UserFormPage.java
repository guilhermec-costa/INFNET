package com.usermanagement.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object para o formulário de usuários.
 * Encapsula todos os elementos e ações do formulário.
 */
public class UserFormPage extends BasePage {

  private final By formTitle = By.id("formTitle");
  private final By nomeInput = By.id("nome");
  private final By emailInput = By.id("email");
  private final By telefoneInput = By.id("telefone");
  private final By idadeInput = By.id("idade");
  private final By statusSelect = By.id("status");
  private final By submitButton = By.id("btnSubmit");
  private final By cancelButton = By.id("btnCancel");
  private final By userIdHidden = By.id("userId");
  private final By alertSuccess = By.cssSelector(".alert-success");
  private final By alertError = By.cssSelector(".alert-error");

  public UserFormPage(WebDriver driver) {
    super(driver);
  }

  /**
   * Preenche o formulário completo de usuário.
   * 
   * @param nome     Nome completo
   * @param email    E-mail
   * @param telefone Telefone
   * @param idade    Idade
   * @param status   Status do usuário
   */
  public void preencherFormulario(String nome, String email, String telefone,
      String idade, String status) {
    type(nomeInput, nome);
    type(emailInput, email);
    type(telefoneInput, telefone);
    type(idadeInput, idade);
    selecionarStatus(status);
  }

  /**
   * Preenche apenas campos obrigatórios.
   * 
   * @param nome   Nome completo
   * @param email  E-mail
   * @param idade  Idade
   * @param status Status
   */
  public void preencherCamposObrigatorios(String nome, String email,
      String idade, String status) {
    type(nomeInput, nome);
    type(emailInput, email);
    type(idadeInput, idade);
    selecionarStatus(status);
  }

  /**
   * Preenche campo nome.
   * 
   * @param nome Nome a ser preenchido
   */
  public void preencherNome(String nome) {
    type(nomeInput, nome);
  }

  /**
   * Preenche campo e-mail.
   * 
   * @param email E-mail a ser preenchido
   */
  public void preencherEmail(String email) {
    type(emailInput, email);
  }

  /**
   * Preenche campo telefone.
   * 
   * @param telefone Telefone a ser preenchido
   */
  public void preencherTelefone(String telefone) {
    type(telefoneInput, telefone);
  }

  /**
   * Preenche campo idade.
   * 
   * @param idade Idade a ser preenchida
   */
  public void preencherIdade(String idade) {
    type(idadeInput, idade);
  }

  /**
   * Seleciona status no dropdown.
   * 
   * @param status Status a ser selecionado
   */
  public void selecionarStatus(String status) {
    Select selectElement = new Select(driver.findElement(statusSelect));
    selectElement.selectByValue(status);
  }

  /**
   * Clica no botão de submissão do formulário.
   */
  public void clicarSubmit() {
    click(submitButton);
  }

  /**
   * Clica no botão cancelar.
   */
  public void clicarCancelar() {
    click(cancelButton);
  }

  /**
   * Submete formulário completo com dados válidos.
   * 
   * @param nome     Nome completo
   * @param email    E-mail
   * @param telefone Telefone
   * @param idade    Idade
   * @param status   Status
   */
  public void cadastrarUsuario(String nome, String email, String telefone,
      String idade, String status) {
    preencherFormulario(nome, email, telefone, idade, status);
    clicarSubmit();
  }

  /**
   * Obtém título do formulário.
   * 
   * @return Texto do título
   */
  public String obterTituloFormulario() {
    return getText(formTitle);
  }

  /**
   * Obtém texto do botão de submit.
   * 
   * @return Texto do botão
   */
  public String obterTextoBotaoSubmit() {
    return getText(submitButton);
  }

  /**
   * Verifica se botão cancelar está visível.
   * 
   * @return true se botão está visível
   */
  public boolean isBotaoCancelarVisivel() {
    return isElementVisible(cancelButton);
  }

  /**
   * Obtém mensagem de sucesso exibida.
   * 
   * @return Texto da mensagem de sucesso
   */
  public String obterMensagemSucesso() {
    waitUtils.waitForElementVisible(alertSuccess);
    return getText(alertSuccess);
  }

  /**
   * Obtém mensagem de erro exibida.
   * 
   * @return Texto da mensagem de erro
   */
  public String obterMensagemErro() {
    waitUtils.waitForElementVisible(alertError);
    return getText(alertError);
  }

  /**
   * Verifica se alerta de sucesso está visível.
   * 
   * @return true se alerta está visível
   */
  public boolean isAlertaSucessoVisivel() {
    return isElementVisible(alertSuccess);
  }

  /**
   * Verifica se alerta de erro está visível.
   * 
   * @return true se alerta está visível
   */
  public boolean isAlertaErroVisivel() {
    return isElementVisible(alertError);
  }

  /**
   * Limpa formulário programaticamente.
   */
  public void limparFormulario() {
    executeScript("document.getElementById('userForm').reset();");
  }

  /**
   * Obtém valor do campo nome.
   * 
   * @return Valor do campo
   */
  public String obterValorNome() {
    return getAttribute(nomeInput, "value");
  }

  /**
   * Obtém valor do campo email.
   * 
   * @return Valor do campo
   */
  public String obterValorEmail() {
    return getAttribute(emailInput, "value");
  }

  /**
   * Obtém valor do campo telefone.
   * 
   * @return Valor do campo
   */
  public String obterValorTelefone() {
    return getAttribute(telefoneInput, "value");
  }

  /**
   * Obtém valor do campo idade.
   * 
   * @return Valor do campo
   */
  public String obterValorIdade() {
    return getAttribute(idadeInput, "value");
  }

  /**
   * Verifica se formulário está em modo de edição.
   * 
   * @return true se está editando
   */
  public boolean isEdicaoAtiva() {
    String userId = getAttribute(userIdHidden, "value");
    return userId != null && !userId.isEmpty();
  }
}
