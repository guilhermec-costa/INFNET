package com.usermanagement.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object para a tabela de usuários.
 * Encapsula operações de leitura e ações na tabela.
 */
public class UserTablePage extends BasePage {

  private final By userTable = By.id("userTable");
  private final By tableRows = By.cssSelector("#userTableBody tr");
  private final By emptyState = By.cssSelector(".empty-state");

  public UserTablePage(WebDriver driver) {
    super(driver);
  }

  /**
   * Obtém número total de usuários na tabela.
   * 
   * @return Quantidade de usuários
   */
  public int obterQuantidadeUsuarios() {
    if (isTabelaVazia()) {
      return 0;
    }
    List<WebElement> rows = driver.findElements(tableRows);
    return rows.size();
  }

  /**
   * Verifica se tabela está vazia.
   * 
   * @return true se tabela está vazia
   */
  public boolean isTabelaVazia() {
    return isElementVisible(emptyState);
  }

  /**
   * Obtém texto exibido quando tabela está vazia.
   * 
   * @return Texto do estado vazio
   */
  public String obterTextoEstadoVazio() {
    if (isTabelaVazia()) {
      return getText(emptyState);
    }
    return "";
  }

  /**
   * Busca usuário na tabela pelo nome.
   * 
   * @param nome Nome do usuário
   * @return true se usuário foi encontrado
   */
  public boolean buscarUsuarioPorNome(String nome) {
    if (isTabelaVazia()) {
      return false;
    }

    List<WebElement> rows = driver.findElements(tableRows);
    for (WebElement row : rows) {
      if (row.getText().contains(nome)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Busca usuário na tabela pelo e-mail.
   * 
   * @param email E-mail do usuário
   * @return true se usuário foi encontrado
   */
  public boolean buscarUsuarioPorEmail(String email) {
    if (isTabelaVazia()) {
      return false;
    }

    List<WebElement> rows = driver.findElements(tableRows);
    for (WebElement row : rows) {
      if (row.getText().contains(email)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Clica no botão editar do primeiro usuário da tabela.
   */
  public void clicarEditarPrimeiroUsuario() {
    By editButton = By.cssSelector("#userTableBody tr:first-child .btn-edit");
    click(editButton);
  }

  /**
   * Clica no botão excluir do primeiro usuário da tabela.
   */
  public void clicarExcluirPrimeiroUsuario() {
    By deleteButton = By.cssSelector("#userTableBody tr:first-child .btn-danger");
    click(deleteButton);
  }

  /**
   * Clica no botão editar de usuário específico pelo nome.
   * 
   * @param nome Nome do usuário
   */
  public void clicarEditarUsuarioPorNome(String nome) {
    WebElement row = encontrarLinhaPorNome(nome);
    if (row != null) {
      WebElement editButton = row.findElement(By.cssSelector(".btn-edit"));
      editButton.click();
    }
  }

  /**
   * Clica no botão excluir de usuário específico pelo nome.
   * 
   * @param nome Nome do usuário
   */
  public void clicarExcluirUsuarioPorNome(String nome) {
    WebElement row = encontrarLinhaPorNome(nome);
    if (row != null) {
      WebElement deleteButton = row.findElement(By.cssSelector(".btn-danger"));
      deleteButton.click();
    }
  }

  /**
   * Encontra linha da tabela pelo nome do usuário.
   * 
   * @param nome Nome do usuário
   * @return WebElement da linha ou null se não encontrado
   */
  private WebElement encontrarLinhaPorNome(String nome) {
    if (isTabelaVazia()) {
      return null;
    }

    List<WebElement> rows = driver.findElements(tableRows);
    for (WebElement row : rows) {
      if (row.getText().contains(nome)) {
        return row;
      }
    }
    return null;
  }

  /**
   * Obtém ID do primeiro usuário da tabela.
   * 
   * @return ID do usuário
   */
  public String obterIdPrimeiroUsuario() {
    if (isTabelaVazia()) {
      return null;
    }

    By firstUserId = By.cssSelector("#userTableBody tr:first-child td:first-child");
    return getText(firstUserId);
  }

  /**
   * Obtém nome do primeiro usuário da tabela.
   * 
   * @return Nome do usuário
   */
  public String obterNomePrimeiroUsuario() {
    if (isTabelaVazia()) {
      return null;
    }

    By firstUserName = By.cssSelector("#userTableBody tr:first-child td:nth-child(2)");
    return getText(firstUserName);
  }

  /**
   * Obtém e-mail do primeiro usuário da tabela.
   * 
   * @return E-mail do usuário
   */
  public String obterEmailPrimeiroUsuario() {
    if (isTabelaVazia()) {
      return null;
    }

    By firstUserEmail = By.cssSelector("#userTableBody tr:first-child td:nth-child(3)");
    return getText(firstUserEmail);
  }

  /**
   * Obtém status de usuário específico pelo nome.
   * 
   * @param nome Nome do usuário
   * @return Status do usuário
   */
  public String obterStatusUsuarioPorNome(String nome) {
    WebElement row = encontrarLinhaPorNome(nome);
    if (row != null) {
      WebElement statusCell = row.findElement(By.cssSelector("td:nth-child(6)"));
      return statusCell.getText();
    }
    return null;
  }

  /**
   * Aceita diálogo de confirmação JavaScript.
   */
  public void aceitarDialogoConfirmacao() {
    waitFor(500); // Aguarda diálogo aparecer
    driver.switchTo().alert().accept();
  }

  /**
   * Rejeita diálogo de confirmação JavaScript.
   */
  public void rejeitarDialogoConfirmacao() {
    waitFor(500); // Aguarda diálogo aparecer
    driver.switchTo().alert().dismiss();
  }

  /**
   * Verifica se tabela está visível.
   * 
   * @return true se tabela está visível
   */
  public boolean isTabelaVisivel() {
    return isElementVisible(userTable);
  }

  /**
   * Obtém todos os nomes de usuários da tabela.
   * 
   * @return Lista com nomes dos usuários
   */
  public java.util.List<String> obterTodosNomes() {
    java.util.List<String> nomes = new java.util.ArrayList<>();

    if (!isTabelaVazia()) {
      List<WebElement> rows = driver.findElements(tableRows);
      for (WebElement row : rows) {
        WebElement nameCell = row.findElement(By.cssSelector("td:nth-child(2)"));
        nomes.add(nameCell.getText());
      }
    }

    return nomes;
  }
}