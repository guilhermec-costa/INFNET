package com.usermanagement.tests;

import com.usermanagement.pages.UserFormPage;
import com.usermanagement.pages.UserTablePage;
import com.usermanagement.tests.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Suite de testes para operações CRUD de usuários.
 * Valida fluxos completos de cadastro, listagem, edição e exclusão.
 */
public class UserCrudTest extends BaseTest {

  private UserFormPage formPage;
  private UserTablePage tablePage;

  @BeforeMethod
  public void inicializarPages() {
    formPage = new UserFormPage(driver);
    tablePage = new UserTablePage(driver);
  }

  /**
   * Testa cadastro de usuário com todos os campos preenchidos.
   * Valida: criação, mensagem de sucesso e exibição na tabela.
   */
  @Test(priority = 1, description = "Cadastrar usuário com todos os campos")
  public void testCadastrarUsuarioCompleto() {
    String nome = "João Silva";
    String email = "joao.silva@email.com";
    String telefone = "(11) 98765-4321";
    String idade = "30";
    String status = "ativo";

    formPage.cadastrarUsuario(nome, email, telefone, idade, status);

    Assert.assertTrue(formPage.isAlertaSucessoVisivel(),
        "Alerta de sucesso deveria estar visível");
    Assert.assertEquals(formPage.obterMensagemSucesso(),
        "Usuário cadastrado com sucesso!");
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nome),
        "Usuário deveria estar na tabela");
    Assert.assertTrue(tablePage.buscarUsuarioPorEmail(email),
        "E-mail do usuário deveria estar na tabela");
  }

  /**
   * Testa cadastro com apenas campos obrigatórios.
   */
  @Test(priority = 2, description = "Cadastrar usuário com campos obrigatórios")
  public void testCadastrarUsuarioCamposObrigatorios() {
    String nome = "Maria Santos";
    String email = "maria.santos@email.com";
    String idade = "25";
    String status = "ativo";

    formPage.preencherCamposObrigatorios(nome, email, idade, status);
    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaSucessoVisivel());
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nome));
  }

  /**
   * Testa listagem inicial quando não há usuários.
   */
  @Test(priority = 3, description = "Verificar estado inicial da tabela vazia")
  public void testTabelaVaziaInicial() {
    Assert.assertTrue(tablePage.isTabelaVazia(),
        "Tabela deveria estar vazia inicialmente");
    Assert.assertEquals(tablePage.obterTextoEstadoVazio(),
        "Nenhum usuário cadastrado");
  }

  /**
   * Testa edição de usuário existente.
   * Valida alteração de dados e atualização na tabela.
   */
  @Test(priority = 4, description = "Editar usuário existente")
  public void testEditarUsuario() {
    formPage.cadastrarUsuario("Pedro Costa", "pedro@email.com",
        "(21) 99999-8888", "35", "ativo");

    tablePage.clicarEditarPrimeiroUsuario();

    Assert.assertEquals(formPage.obterTituloFormulario(), "Editar Usuário");
    Assert.assertEquals(formPage.obterTextoBotaoSubmit(), "Atualizar Usuário");
    Assert.assertTrue(formPage.isBotaoCancelarVisivel());

    String novoNome = "Pedro Costa Atualizado";
    formPage.preencherNome(novoNome);
    formPage.clicarSubmit();

    Assert.assertTrue(formPage.isAlertaSucessoVisivel());
    Assert.assertEquals(formPage.obterMensagemSucesso(),
        "Usuário atualizado com sucesso!");
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(novoNome));
  }

  /**
   * Testa exclusão de usuário.
   * Valida confirmação e remoção da tabela.
   */
  @Test(priority = 5, description = "Excluir usuário")
  public void testExcluirUsuario() {
    String nome = "Ana Lima";
    formPage.cadastrarUsuario(nome, "ana@email.com",
        "(31) 98888-7777", "28", "ativo");

    int quantidadeInicial = tablePage.obterQuantidadeUsuarios();

    tablePage.clicarExcluirPrimeiroUsuario();
    tablePage.aceitarDialogoConfirmacao();

    Assert.assertTrue(formPage.isAlertaSucessoVisivel());
    Assert.assertFalse(tablePage.buscarUsuarioPorNome(nome),
        "Usuário não deveria estar na tabela após exclusão");
    Assert.assertEquals(tablePage.obterQuantidadeUsuarios(),
        quantidadeInicial - 1, "Quantidade de usuários deveria diminuir");
  }

  /**
   * Testa cancelamento de exclusão.
   */
  @Test(priority = 6, description = "Cancelar exclusão de usuário")
  public void testCancelarExclusao() {
    String nome = "Carlos Souza";
    formPage.cadastrarUsuario(nome, "carlos@email.com",
        "(41) 97777-6666", "40", "ativo");

    int quantidadeInicial = tablePage.obterQuantidadeUsuarios();

    tablePage.clicarExcluirPrimeiroUsuario();
    tablePage.rejeitarDialogoConfirmacao();

    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nome),
        "Usuário deveria continuar na tabela");
    Assert.assertEquals(tablePage.obterQuantidadeUsuarios(),
        quantidadeInicial, "Quantidade não deveria mudar");
  }

  /**
   * Testa cadastro de múltiplos usuários.
   */
  @Test(priority = 7, description = "Cadastrar múltiplos usuários")
  public void testCadastrarMultiplosUsuarios() {
    String[][] usuarios = {
        { "Usuário 1", "usuario1@email.com", "(11) 91111-1111", "25", "ativo" },
        { "Usuário 2", "usuario2@email.com", "(11) 92222-2222", "30", "inativo" },
        { "Usuário 3", "usuario3@email.com", "(11) 93333-3333", "35", "pendente" }
    };

    for (String[] usuario : usuarios) {
      formPage.cadastrarUsuario(usuario[0], usuario[1], usuario[2],
          usuario[3], usuario[4]);
    }

    Assert.assertEquals(tablePage.obterQuantidadeUsuarios(), usuarios.length);
    for (String[] usuario : usuarios) {
      Assert.assertTrue(tablePage.buscarUsuarioPorNome(usuario[0]));
    }
  }

  /**
   * Testa cancelamento de edição.
   */
  @Test(priority = 8, description = "Cancelar edição de usuário")
  public void testCancelarEdicao() {
    String nomeOriginal = "Fernanda Oliveira";
    formPage.cadastrarUsuario(nomeOriginal, "fernanda@email.com",
        "(51) 96666-5555", "27", "ativo");

    tablePage.clicarEditarPrimeiroUsuario();
    formPage.preencherNome("Nome Alterado");
    formPage.clicarCancelar();

    Assert.assertEquals(formPage.obterTituloFormulario(),
        "Cadastrar Novo Usuário");
    Assert.assertEquals(formPage.obterValorNome(), "",
        "Campo nome deveria estar vazio");
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nomeOriginal),
        "Nome original deveria permanecer na tabela");
  }

  /**
   * Testa fluxo completo: criar, editar e excluir.
   */
  @Test(priority = 9, description = "Fluxo completo CRUD")
  public void testFluxoCompletoCRUD() {
    String nomeInicial = "Teste CRUD";
    formPage.cadastrarUsuario(nomeInicial, "crud@email.com",
        "(61) 95555-4444", "32", "ativo");
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nomeInicial));

    String nomeTabela = tablePage.obterNomePrimeiroUsuario();
    Assert.assertEquals(nomeTabela, nomeInicial);

    tablePage.clicarEditarPrimeiroUsuario();
    String nomeAtualizado = "Teste CRUD Atualizado";
    formPage.preencherNome(nomeAtualizado);
    formPage.clicarSubmit();
    Assert.assertTrue(tablePage.buscarUsuarioPorNome(nomeAtualizado));

    tablePage.clicarExcluirUsuarioPorNome(nomeAtualizado);
    tablePage.aceitarDialogoConfirmacao();
    Assert.assertFalse(tablePage.buscarUsuarioPorNome(nomeAtualizado));
  }

  /**
   * Testa mudança de status do usuário.
   */
  @Test(priority = 10, description = "Alterar status do usuário")
  public void testAlterarStatus() {
    String nome = "Teste Status";
    formPage.cadastrarUsuario(nome, "status@email.com",
        "(71) 94444-3333", "29", "ativo");

    tablePage.clicarEditarPrimeiroUsuario();
    formPage.selecionarStatus("inativo");
    formPage.clicarSubmit();

    String statusAtual = tablePage.obterStatusUsuarioPorNome(nome);
    Assert.assertEquals(statusAtual.toLowerCase(), "inativo");
  }
}
