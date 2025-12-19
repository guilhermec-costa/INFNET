package com.usermanagement.tests;

import com.usermanagement.pages.UserFormPage;
import com.usermanagement.pages.UserTablePage;
import com.usermanagement.tests.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ValidationTest extends BaseTest {

  private UserFormPage formPage;
  private UserTablePage tablePage;

  @BeforeMethod
  public void inicializarPages() {
    formPage = new UserFormPage(driver);
    tablePage = new UserTablePage(driver);
  }

  /**
   * Testa submissão com campos vazios.
   */
  @Test(priority = 1, description = "Validar campos obrigatórios vazios")
  public void testCamposObrigatoriosVazios() {
    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaErroVisivel(),
        "Alerta de erro deveria estar visível");
    Assert.assertEquals(formPage.obterMensagemErro(),
        "Por favor, corrija os erros no formulário");
    Assert.assertTrue(tablePage.isTabelaVazia(),
        "Nenhum usuário deveria ser cadastrado");
  }

  /**
   * Provider de dados para testes de e-mail inválido.
   */
  @DataProvider(name = "emailsInvalidos")
  public Object[][] emailsInvalidos() {
    return new Object[][] {
        { "email invalido" },
        { "@email.com" },
        { "usuario@" },
        { "usuario.com" },
        { "usuario@@email.com" },
        { "" },
        { " " }
    };
  }

  /**
   * Testa validação de e-mails inválidos.
   */
  @Test(priority = 2, dataProvider = "emailsInvalidos", description = "Validar e-mails inválidos")
  public void testEmailInvalido(String emailInvalido) {
    formPage.preencherNome("Teste Email");
    formPage.preencherEmail(emailInvalido);
    formPage.preencherIdade("25");
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaErroVisivel());
    Assert.assertTrue(tablePage.isTabelaVazia());
  }

  /**
   * Provider de dados para idades inválidas.
   */
  @DataProvider(name = "idadesInvalidas")
  public Object[][] idadesInvalidas() {
    return new Object[][] {
        { "17" }, // Menor que o mínimo
        { "121" }, // Maior que o máximo
        { "0" },
        { "-5" },
        { "150" }
    };
  }

  /**
   * Testa validação de idades inválidas.
   */
  @Test(priority = 3, dataProvider = "idadesInvalidas", description = "Validar idades inválidas")
  public void testIdadeInvalida(String idadeInvalida) {
    formPage.preencherNome("Teste Idade");
    formPage.preencherEmail("teste@email.com");
    formPage.preencherIdade(idadeInvalida);
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaErroVisivel());
  }

  /**
   * Testa nome com menos de 3 caracteres.
   */
  @Test(priority = 4, description = "Validar nome muito curto")
  public void testNomeMuitoCurto() {
    formPage.preencherNome("AB");
    formPage.preencherEmail("teste@email.com");
    formPage.preencherIdade("25");
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaErroVisivel());
  }

  /**
   * Provider de dados para telefones inválidos.
   */
  @DataProvider(name = "telefonesInvalidos")
  public Object[][] telefonesInvalidos() {
    return new Object[][] {
        { "123" },
        { "abc" },
        { "12345" }
    };
  }

  /**
   * Testa validação de telefones inválidos.
   */
  @Test(priority = 5, dataProvider = "telefonesInvalidos", description = "Validar telefones inválidos")
  public void testTelefoneInvalido(String telefoneInvalido) {
    formPage.preencherNome("Teste Telefone");
    formPage.preencherEmail("teste@email.com");
    formPage.preencherTelefone(telefoneInvalido);
    formPage.preencherIdade("25");
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaErroVisivel());
  }

  /**
   * Testa status não selecionado.
   */
  @Test(priority = 6, description = "Validar status não selecionado")
  public void testStatusNaoSelecionado() {
    formPage.preencherNome("Teste Status");
    formPage.preencherEmail("teste@email.com");
    formPage.preencherIdade("25");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaErroVisivel());
  }

  /**
   * Provider de dados para idades válidas no limite.
   */
  @DataProvider(name = "idadesLimite")
  public Object[][] idadesLimite() {
    return new Object[][] {
        { "18" }, // Mínimo permitido
        { "120" } // Máximo permitido
    };
  }

  /**
   * Testa idades nos limites válidos.
   */
  @Test(priority = 7, dataProvider = "idadesLimite", description = "Validar idades no limite")
  public void testIdadeNoLimite(String idade) {
    String nome = "Teste Idade " + idade;
    formPage.preencherNome(nome);
    formPage.preencherEmail("idade" + idade + "@email.com");
    formPage.preencherIdade(idade);
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaSucessoVisivel());
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nome));
  }

  /**
   * Testa cadastro sem telefone (campo opcional).
   */
  @Test(priority = 8, description = "Cadastrar sem telefone")
  public void testCadastroSemTelefone() {
    String nome = "Sem Telefone";
    formPage.preencherNome(nome);
    formPage.preencherEmail("semtelefone@email.com");
    formPage.preencherIdade("30");
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaSucessoVisivel());
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nome));
  }

  /**
   * Testa múltiplas validações simultâneas.
   */
  @Test(priority = 9, description = "Múltiplos erros de validação")
  public void testMultiplosErrosValidacao() {
    formPage.preencherNome("A"); // Muito curto
    formPage.preencherEmail("emailinvalido"); // E-mail inválido
    formPage.preencherIdade("10"); // Idade inválida

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaErroVisivel());
    Assert.assertTrue(tablePage.isTabelaVazia());
  }

  /**
   * Provider de e-mails válidos em diferentes formatos.
   */
  @DataProvider(name = "emailsValidos")
  public Object[][] emailsValidos() {
    return new Object[][] {
        { "usuario@email.com" },
        { "usuario.nome@empresa.com.br" },
        { "usuario+tag@email.com" },
        { "usuario_nome@email.co" },
        { "123@email.com" }
    };
  }

  /**
   * Testa e-mails válidos em diversos formatos.
   */
  @Test(priority = 10, dataProvider = "emailsValidos", description = "Validar e-mails válidos")
  public void testEmailValido(String emailValido) {
    String nome = "Teste Email " + emailValido.substring(0, 5);
    formPage.preencherNome(nome);
    formPage.preencherEmail(emailValido);
    formPage.preencherIdade("25");
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaSucessoVisivel());
    Assert.assertTrue(tablePage.buscarUsuarioPorEmail(emailValido));
  }

  /**
   * Testa todos os status disponíveis.
   */
  @Test(priority = 11, description = "Validar todos os status")
  public void testTodosStatus() {
    String[] statusList = { "ativo", "inativo", "pendente" };

    for (String status : statusList) {
      String nome = "Teste Status " + status;
      formPage.preencherNome(nome);
      formPage.preencherEmail(status + "@email.com");
      formPage.preencherIdade("25");
      formPage.selecionarStatus(status);
      formPage.clicarSubmit();

      Assert.assertTrue(tablePage.buscarUsuarioPorNome(nome));
    }

    Assert.assertEquals(tablePage.obterQuantidadeUsuarios(), statusList.length);
  }

  /**
   * Testa caracteres especiais no nome.
   */
  @Test(priority = 12, description = "Nome com caracteres especiais")
  public void testNomeCaracteresEspeciais() {
    String nome = "João José d'Ávila";
    formPage.preencherNome(nome);
    formPage.preencherEmail("especial@email.com");
    formPage.preencherIdade("30");
    formPage.selecionarStatus("ativo");

    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaSucessoVisivel());
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nome));
  }
}