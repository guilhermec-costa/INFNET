package TP3;

// EXERCÍCIO 4 - Classe de teste para Produto
class AppProduto {
    public static void main(String[] args) {
        System.out.println("=== TESTE DA CLASSE PRODUTO ===\n");
        
        // Criando um produto usando o construtor (Exercício 6)
        Produto produto1 = new Produto("Arroz 5kg", 12.50, 100);
        
        // Exibindo informações iniciais
        produto1.exibirInformacoes();
        
        // Testando métodos de atualização
        System.out.println("\n--- Atualizando preço ---");
        produto1.alterarPreco(13.75);
        
        System.out.println("\n--- Atualizando quantidade ---");  
        produto1.alterarQuantidade(80);
        
        // Exibindo informações após mudanças
        System.out.println("\n--- Informações após alterações ---");
        produto1.exibirInformacoes();
        
        // Testando getters e setters (Exercício 5)
        System.out.println("\n=== TESTANDO GETTERS E SETTERS ===");
        System.out.println("Nome atual: " + produto1.getNome());
        System.out.println("Preço atual: R$ " + produto1.getPreco());
        
        produto1.setPreco(3.75);
        System.out.println("Novo preço após setter: R$ " + produto1.getPreco());
    }
}

/*
EXERCÍCIO 5 - EXPLICAÇÃO DOS GETTERS E SETTERS:

Os métodos getters e setters são úteis porque:
1. ORGANIZAÇÃO: Centralizam a forma de acessar e modificar atributos
2. CONTROLE: Permitem adicionar validações antes de alterar valores
3. FLEXIBILIDADE: Facilitam mudanças futuras no código sem quebrar outras partes
4. ENCAPSULAMENTO: Mesmo sem modificadores de acesso, criam uma interface clara
5. MANUTENÇÃO: Tornam o código mais legível e fácil de manter

EXERCÍCIO 6 - VANTAGENS DO CONSTRUTOR:
O construtor facilita a criação de objetos porque:
1. INICIALIZAÇÃO GARANTIDA: Todos os atributos recebem valores no momento da criação
2. MENOS CÓDIGO: Evita múltiplas chamadas de métodos set
3. OBJETO VÁLIDO: Garante que o objeto seja criado em um estado consistente
4. CLAREZA: Deixa explícito quais valores são obrigatórios para criar o objeto
*/