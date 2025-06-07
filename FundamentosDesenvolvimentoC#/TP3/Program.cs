using System;

// =============================================================================
// EXERCÍCIO 1: Conceitos de Classe, Objeto, Campos e Métodos
// =============================================================================

/*
 * CONCEITOS FUNDAMENTAIS:
 * 
 * CLASSE: É um modelo ou template que define características e comportamentos
 * que um grupo de objetos similares irá possuir. É como uma "planta baixa".
 * 
 * OBJETO: É uma instância de uma classe, ou seja, uma "materialização" da classe
 * com valores específicos. É como uma casa construída a partir da planta baixa.
 * 
 * CAMPOS (ATRIBUTOS): São as características ou propriedades que uma classe possui.
 * Representam o "estado" do objeto.
 * 
 * MÉTODOS: São as ações ou comportamentos que uma classe pode executar.
 * Representam o que o objeto "pode fazer".
 */

// Exemplo prático: Classe Carro
public class Carro
{
    // CAMPOS (ATRIBUTOS) - características do carro
    public string marca;
    public string modelo;
    public int ano;
    public double velocidadeAtual;

    // MÉTODOS - comportamentos do carro
    public void Acelerar(double incremento)
    {
        velocidadeAtual += incremento;
        Console.WriteLine($"Acelerando... Velocidade atual: {velocidadeAtual} km/h");
    }

    public void Frear(double decremento)
    {
        velocidadeAtual -= decremento;
        if (velocidadeAtual < 0) velocidadeAtual = 0;
        Console.WriteLine($"Freando... Velocidade atual: {velocidadeAtual} km/h");
    }

    public void ExibirInformacoes()
    {
        Console.WriteLine($"Carro: {marca} {modelo} ({ano}) - Velocidade: {velocidadeAtual} km/h");
    }
}

// =============================================================================
// EXERCÍCIO 2: Criando a Classe "Ingresso"
// =============================================================================

public class Ingresso
{
    // Atributos essenciais para venda de ingressos
    public string nomeDoShow;        // Nome do evento - importante para identificação
    public double preco;             // Valor do ingresso - essencial para vendas
    public int quantidadeDisponivel; // Controle de estoque - evita overselling
    
    /*
     * IMPORTÂNCIA DOS ATRIBUTOS:
     * - nomeDoShow: Identifica qual evento o ingresso representa
     * - preco: Define o valor comercial e permite cálculos financeiros
     * - quantidadeDisponivel: Controla o estoque e evita venda excessiva
     */
}

// =============================================================================
// EXERCÍCIO 3: Métodos Básicos da Classe "Ingresso"
// =============================================================================

public class IngressoComMetodos
{
    public string nomeDoShow;
    public double preco;
    public int quantidadeDisponivel;

    // Método para alterar preço
    public void AlterarPreco(double novoPreco)
    {
        preco = novoPreco;
    }

    // Método para alterar quantidade
    public void AlterarQuantidade(int novaQuantidade)
    {
        quantidadeDisponivel = novaQuantidade;
    }

    // Método para exibir informações
    public void ExibirInformacoes()
    {
        Console.WriteLine($"Show: {nomeDoShow}");
        Console.WriteLine($"Preço: R$ {preco:F2}");
        Console.WriteLine($"Quantidade Disponível: {quantidadeDisponivel}");
        Console.WriteLine("----------------------------------------");
    }
}

// =============================================================================
// EXERCÍCIO 5: Criando Métodos de Propriedade (Getters e Setters)
// =============================================================================

public class IngressoCompleto
{
    private string nomeDoShow;
    private double preco;
    private int quantidadeDisponivel;

    // Construtor (Exercício 6)
    public IngressoCompleto(string nomeDoShow, double preco, int quantidadeDisponivel)
    {
        this.nomeDoShow = nomeDoShow;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    // Getters (métodos de leitura)
    public string GetNomeDoShow()
    {
        return nomeDoShow;
    }

    public double GetPreco()
    {
        return preco;
    }

    public int GetQuantidadeDisponivel()
    {
        return quantidadeDisponivel;
    }

    // Setters (métodos de escrita)
    public void SetNomeDoShow(string novoNome)
    {
        nomeDoShow = novoNome;
    }

    public void SetPreco(double novoPreco)
    {
        preco = novoPreco;
    }

    public void SetQuantidadeDisponivel(int novaQtd)
    {
        quantidadeDisponivel = novaQtd;
    }

    // Métodos de atualização (Exercício 3)
    public void AlterarPreco(double novoPreco)
    {
        preco = novoPreco;
    }

    public void AlterarQuantidade(int novaQuantidade)
    {
        quantidadeDisponivel = novaQuantidade;
    }

    public void ExibirInformacoes()
    {
        Console.WriteLine($"Show: {nomeDoShow}");
        Console.WriteLine($"Preço: R$ {preco:F2}");
        Console.WriteLine($"Quantidade Disponível: {quantidadeDisponivel}");
        Console.WriteLine("----------------------------------------");
    }
}

// =============================================================================
// EXERCÍCIO 7: Modelando uma Matrícula
// =============================================================================

public class Matricula
{
    public string NomeDoAluno;
    public string Curso;
    public int NumeroMatricula;
    public string Situacao;
    public string DataInicial;
}

// =============================================================================
// EXERCÍCIO 8: Criando Métodos na Classe de Matrícula
// =============================================================================

public class MatriculaCompleta
{
    public string NomeDoAluno;
    public string Curso;
    public int NumeroMatricula;
    public string Situacao;
    public string DataInicial;

    // Método para trancar matrícula
    public void Trancar()
    {
        Situacao = "Trancada";
    }

    // Método para reativar matrícula
    public void Reativar()
    {
        Situacao = "Ativa";
    }

    // Método para exibir informações
    public void ExibirInformacoes()
    {
        Console.WriteLine($"Aluno: {NomeDoAluno}");
        Console.WriteLine($"Curso: {Curso}");
        Console.WriteLine($"Matrícula: {NumeroMatricula}");
        Console.WriteLine($"Situação: {Situacao}");
        Console.WriteLine($"Data Inicial: {DataInicial}");
        Console.WriteLine("----------------------------------------");
    }
}

// =============================================================================
// EXERCÍCIO 10: Definindo Classes de Formas Geométricas
// =============================================================================

public class Circulo
{
    public double Raio;
    
    /*
     * O atributo Raio é fundamental pois:
     * - É a única medida necessária para definir completamente um círculo
     * - A partir dele podemos calcular área, perímetro, etc.
     */
}

public class Esfera
{
    public double Raio;
    
    /*
     * O atributo Raio é fundamental pois:
     * - É a única medida necessária para definir completamente uma esfera
     * - A partir dele podemos calcular área da superfície, volume, etc.
     */
}

// =============================================================================
// EXERCÍCIO 11: Criando Métodos de Cálculo
// =============================================================================

public class CirculoComCalculos
{
    public double Raio;

    public double CalcularArea()
    {
        return Math.PI * (Raio * Raio);
    }
}

public class EsferaComCalculos
{
    public double Raio;

    public double CalcularVolume()
    {
        return (4.0 / 3.0) * Math.PI * (Raio * Raio * Raio);
    }
}

// =============================================================================
// CLASSES DE TESTE INDIVIDUAIS (conforme solicitado nos exercícios)
// =============================================================================

public class AppIngresso
{
    public static void TestarIngresso()
    {
        Console.WriteLine("=== EXERCÍCIO 4: Testando a Classe Ingresso ===");
        
        // Criando objeto da classe Ingresso
        IngressoComMetodos ingresso = new IngressoComMetodos();
        
        // Atribuindo valores iniciais
        ingresso.nomeDoShow = "Rock in Rio 2025";
        ingresso.preco = 150.00;
        ingresso.quantidadeDisponivel = 1000;
        
        Console.WriteLine("Estado inicial:");
        ingresso.ExibirInformacoes();
        
        // Alterando preço e quantidade
        ingresso.AlterarPreco(180.00);
        ingresso.AlterarQuantidade(750);
        
        Console.WriteLine("Após alterações:");
        ingresso.ExibirInformacoes();
    }

    public static void TestarGettersSetters()
    {
        Console.WriteLine("=== EXERCÍCIO 5: Testando Getters e Setters ===");
        
        IngressoCompleto ingresso = new IngressoCompleto("Lollapalooza 2025", 200.0, 500);
        
        Console.WriteLine("Valores iniciais:");
        Console.WriteLine($"Nome: {ingresso.GetNomeDoShow()}");
        Console.WriteLine($"Preço: R$ {ingresso.GetPreco():F2}");
        Console.WriteLine($"Quantidade: {ingresso.GetQuantidadeDisponivel()}");
        
        // Alterando valores usando setters
        ingresso.SetPreco(250.0);
        ingresso.SetQuantidadeDisponivel(300);
        
        Console.WriteLine("\nApós alterações com setters:");
        Console.WriteLine($"Preço: R$ {ingresso.GetPreco():F2}");
        Console.WriteLine($"Quantidade: {ingresso.GetQuantidadeDisponivel()}");
        Console.WriteLine();
    }

    public static void TestarConstrutores()
    {
        Console.WriteLine("=== EXERCÍCIO 6: Testando Construtores ===");
        
        // Instanciando com construtor
        IngressoCompleto ingresso = new IngressoCompleto("Festival de Inverno", 120.0, 800);
        
        Console.WriteLine("Objeto criado com construtor:");
        ingresso.ExibirInformacoes();
        
        Console.WriteLine("VANTAGEM DOS CONSTRUTORES:");
        Console.WriteLine("- Inicialização obrigatória de valores essenciais");
        Console.WriteLine("- Menos linhas de código na criação de objetos");
        Console.WriteLine("- Garante que o objeto seja criado em estado válido");
        Console.WriteLine();
    }
}

public class TestaMatricula
{
    public static void TestarMatricula()
    {
        Console.WriteLine("=== EXERCÍCIO 9: Testando a Classe de Matrícula ===");
        
        // Instanciando objeto Matricula
        MatriculaCompleta matricula = new MatriculaCompleta();
        
        // Atribuindo valores
        matricula.NomeDoAluno = "João Silva";
        matricula.Curso = "Ciência da Computação";
        matricula.NumeroMatricula = 202412345;
        matricula.Situacao = "Ativa";
        matricula.DataInicial = "01/03/2024";
        
        Console.WriteLine("Estado inicial da matrícula:");
        matricula.ExibirInformacoes();
        
        Console.WriteLine("Trancando matrícula...");
        matricula.Trancar();
        matricula.ExibirInformacoes();
        
        Console.WriteLine("Reativando matrícula...");
        matricula.Reativar();
        matricula.ExibirInformacoes();
    }
}

public class TestaFiguras
{
    public static void TestarFiguras()
    {
        Console.WriteLine("=== EXERCÍCIO 12: Testando as Classes de Figuras ===");
        
        // Instanciando círculo
        CirculoComCalculos circulo = new CirculoComCalculos();
        circulo.Raio = 3.0;
        
        // Instanciando esfera
        EsferaComCalculos esfera = new EsferaComCalculos();
        esfera.Raio = 5.0;
        
        // Calculando e exibindo resultados
        double areaCirculo = circulo.CalcularArea();
        double volumeEsfera = esfera.CalcularVolume();
        
        Console.WriteLine($"Círculo com raio {circulo.Raio}:");
        Console.WriteLine($"Área = {areaCirculo:F2}");
        
        Console.WriteLine($"\nEsfera com raio {esfera.Raio}:");
        Console.WriteLine($"Volume = {volumeEsfera:F2}");
        Console.WriteLine();
    }
}

// =============================================================================
// CLASSE PRINCIPAL - COORDENA EXECUÇÃO DE TODOS OS EXERCÍCIOS
// =============================================================================

public class Program
{
    public static void Main(string[] args)
    {
        Console.WriteLine("========================================");
        Console.WriteLine("   EXERCÍCIOS DE POO EM C# - COMPLETO");
        Console.WriteLine("========================================\n");

        // EXERCÍCIO 1: Demonstração de conceitos básicos
        Console.WriteLine("=== EXERCÍCIO 1: Conceitos Básicos (Classe Carro) ===");
        
        // Criando OBJETO da CLASSE Carro
        Carro meuCarro = new Carro();
        
        // Definindo valores dos CAMPOS (atributos)
        meuCarro.marca = "Toyota";
        meuCarro.modelo = "Corolla";
        meuCarro.ano = 2023;
        meuCarro.velocidadeAtual = 0;
        
        // Chamando MÉTODOS do objeto
        meuCarro.ExibirInformacoes();
        meuCarro.Acelerar(50);
        meuCarro.Acelerar(30);
        meuCarro.Frear(20);
        meuCarro.ExibirInformacoes();
        Console.WriteLine();

        // Executando testes dos demais exercícios
        AppIngresso.TestarIngresso();
        AppIngresso.TestarGettersSetters();
        AppIngresso.TestarConstrutores();
        TestaMatricula.TestarMatricula();
        TestaFiguras.TestarFiguras();

        Console.WriteLine("========================================");
        Console.WriteLine("     TODOS OS EXERCÍCIOS CONCLUÍDOS!");
        Console.WriteLine("========================================");
        
        Console.ReadKey();
    }
}