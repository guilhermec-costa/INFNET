package TP3;
// ========== EXERCÍCIOS 2, 3, 5 e 6 - CLASSE PRODUTO ==========

class Produto {
    // EXERCÍCIO 2 - Atributos da classe Produto
    String nome;           // Nome do produto - essencial para identificação
    double preco;          // Preço - fundamental para vendas e controle financeiro  
    int quantidadeEmEstoque; // Quantidade - crucial para controle de estoque
    
    // EXERCÍCIO 6 - Construtor
    public Produto(String nome, double preco, int quantidadeEmEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
    
    // EXERCÍCIO 3 - Métodos básicos de manipulação
    void alterarPreco(double novoPreco) {
        this.preco = novoPreco;
        System.out.println("Preço alterado para: R$ " + novoPreco);
    }
    
    void alterarQuantidade(int novaQuantidade) {
        this.quantidadeEmEstoque = novaQuantidade;
        System.out.println("Quantidade em estoque alterada para: " + novaQuantidade);
    }
    
    void exibirInformacoes() {
        System.out.println("=== INFORMAÇÕES DO PRODUTO ===");
        System.out.println("Nome: " + nome);
        System.out.println("Preço: R$ " + preco);
        System.out.println("Quantidade em Estoque: " + quantidadeEmEstoque);
        System.out.println("===============================");
    }
    
    // EXERCÍCIO 5 - Getters (métodos de leitura)
    String getNome() {
        return nome;
    }
    
    double getPreco() {
        return preco;
    }
    
    int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }
    
    // EXERCÍCIO 5 - Setters (métodos de atualização)
    void setNome(String novoNome) {
        this.nome = novoNome;
    }
    
    void setPreco(double novoPreco) {
        this.preco = novoPreco;
    }
    
    void setQuantidadeEmEstoque(int novaQuantidade) {
        this.quantidadeEmEstoque = novaQuantidade;
    }
}
