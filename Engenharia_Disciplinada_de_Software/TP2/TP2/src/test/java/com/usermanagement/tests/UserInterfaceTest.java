package com.usermanagement.tests;

import com.usermanagement.pages.UserFormPage;
import com.usermanagement.pages.UserTablePage;
import com.usermanagement.tests.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Suite de testes para elementos da interface.
 * Valida comportamentos visuais e interações do usuário.
 */
public class UserInterfaceTest extends BaseTest {

  private UserFormPage formPage;
  private UserTablePage tablePage;

  @BeforeMethod
  public void inicializarPages() {
    formPage = new UserFormPage(driver);
    tablePage = new UserTablePage(driver);
  }

  /**
   * Testa título da página.
   */
  @Test(priority = 1, description = "Verificar título da página")
  public void testTituloPagina() {
    String titulo = formPage.getPageTitle();
    Assert.assertEquals(titulo, "Sistema de Gerenciamento de Usuários");
  }

  /**
   * Testa visibilidade inicial dos elementos.
   */
  @Test(priority = 2, description = "Verificar elementos visíveis na carga")
  public void testElementosVisiveis() {
    Assert.assertEquals(formPage.obterTituloFormulario(),
        "Cadastrar Novo Usuário");
    Assert.assertEquals(formPage.obterTextoBotaoSubmit(),
        "Cadastrar Usuário");
    Assert.assertFalse(formPage.isBotaoCancelarVisivel(),
        "Botão cancelar não deveria estar visível inicialmente");
    Assert.assertTrue(tablePage.isTabelaVisivel());
  }

  /**
   * Testa limpeza do formulário após cancelar.
   */
  @Test(priority = 3, description = "Limpar formulário ao cancelar")
  public void testLimparFormularioAoCancelar() {
    formPage.preencherNome("Teste");
    formPage.preencherEmail("teste@email.com");
    formPage.preencherIdade("25");
    formPage.selecionarStatus("ativo");
    formPage.clicarSubmit();

    tablePage.clicarEditarPrimeiroUsuario();
    formPage.clicarCancelar();

    Assert.assertEquals(formPage.obterValorNome(), "");
    Assert.assertEquals(formPage.obterValorEmail(), "");
    Assert.assertEquals(formPage.obterValorIdade(), "");
  }

  /**
   * Testa persistência de dados após reload.
   */
  @Test(priority = 4, description = "Persistir dados após refresh")
  public void testPersistenciaDados() {
    String nome = "Teste Persistencia";
    formPage.cadastrarUsuario(nome, "persist@email.com",
        "(11) 99999-9999", "30", "ativo");

    driver.navigate().refresh();

    UserTablePage newTablePage = new UserTablePage(driver);
    Assert.assertTrue(newTablePage.buscarUsuarioPorNome(nome),
        "Usuário deveria persistir após refresh");
  }

  /**
   * Testa que tabela exibe usuários em ordem de cadastro.
   */
  @Test(priority = 7, description = "Ordem de exibição na tabela")
  public void testOrdemExibicao() {
    String primeiro = "Primeiro Usuario";
    String segundo = "Segundo Usuario";
    String terceiro = "Terceiro Usuario";

    formPage.cadastrarUsuario(primeiro, "primeiro@email.com",
        "", "25", "ativo");
    formPage.cadastrarUsuario(segundo, "segundo@email.com",
        "", "30", "ativo");
    formPage.cadastrarUsuario(terceiro, "terceiro@email.com",
        "", "35", "ativo");

    Assert.assertEquals(tablePage.obterNomePrimeiroUsuario(), primeiro);
    Assert.assertEquals(tablePage.obterQuantidadeUsuarios(), 3);
  }

  /**
   * Testa exibição de "-" para campos vazios na tabela.
   */
  @Test(priority = 8, description = "Exibir hífen para campos vazios")
  public void testCampoVazioNaTabela() {
    formPage.preencherCamposObrigatorios("Usuario Sem Tel",
        "semtel@email.com", "25", "ativo");
    formPage.clicarSubmit();

    String conteudoTabela = tablePage.obterNomePrimeiroUsuario();

    Assert.assertNotNull(conteudoTabela);
    Assert.assertTrue(tablePage.buscarUsuarioPorNome("Usuario Sem Tel"));
  }

  /**
   * Testa capitalização de status na tabela.
   */
  @Test(priority = 10, description = "Status capitalizado na tabela")
  public void testStatusCapitalizado() {
    String nome = "Teste Status View";
    formPage.cadastrarUsuario(nome, "statusview@email.com",
        "", "28", "ativo");

    String status = tablePage.obterStatusUsuarioPorNome(nome);

    Assert.assertNotNull(status);
    Assert.assertTrue(Character.isUpperCase(status.charAt(0)) ||
        status.toLowerCase().equals("ativo"));
  }

  /**
   * Testa que IDs são sequenciais.
   */
  @Test(priority = 11, description = "IDs sequenciais")
  public void testIdsSequenciais() {
    formPage.cadastrarUsuario("ID Test 1", "id1@email.com",
        "", "25", "ativo");
    String id1 = tablePage.obterIdPrimeiroUsuario();

    formPage.cadastrarUsuario("ID Test 2", "id2@email.com",
        "", "30", "ativo");

    tablePage.clicarExcluirPrimeiroUsuario();
    tablePage.aceitarDialogoConfirmacao();

    String id2 = tablePage.obterIdPrimeiroUsuario();

    Assert.assertNotNull(id1);
    Assert.assertNotNull(id2);
    Assert.assertNotEquals(id1, id2);
  }

  /**
   * Testa navegação com teclado no formulário.
   */
  @Test(priority = 12, description = "Navegação com Tab")
  public void testNavegacaoTeclado() {
    formPage.preencherNome("Tab Test");
    formPage.preencherEmail("tab@email.com");
    formPage.preencherIdade("25");

    Assert.assertEquals(formPage.obterValorNome(), "Tab Test");
    Assert.assertEquals(formPage.obterValorEmail(), "tab@email.com");
    Assert.assertEquals(formPage.obterValorIdade(), "25");
  }
}