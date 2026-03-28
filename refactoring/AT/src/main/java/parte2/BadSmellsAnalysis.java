package parte2;

public class BadSmellsAnalysis {
    
    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("ANÁLISE DE CODE SMELLS - Código Original Invoice");
        System.out.println("=====================================================\n");
        
        System.out.println("1. CAMPOS PÚBLICOS (Inveja de Dados + Agregação de Dados)");
        System.out.println("   - Campos clientName, clientEmail, amount, type são públicos");
        System.out.println("   - Violação do encapsulamento");
        System.out.println("   - Solução: Usar campos privados com getters/setters\n");
        
        System.out.println("2. OBSESSÃO POR PRIMITIVOS");
        System.out.println("   - Campo 'type' é um int primitivo");
        System.out.println("   - Usado em múltiplos if-else para tomada de decisão");
        System.out.println("   - Solução: Criar enum InvoiceType\n");
        
        System.out.println("3. DECISÕES CONDICIONAIS (Switch Statements)");
        System.out.println("   - Múltiplos if-else comparando type == 1, type == 2, etc.");
        System.out.println("   - Repetido em 3 lugares diferentes no código");
        System.out.println("   - Solução: Usar enum com polimorfismo ou método abstrato\n");
        
        System.out.println("4. CÓDIGO DUPLICADO");
        System.out.println("   - Lógica de verificação de tipo repetida 3x");
        System.out.println("   - String de formatação de nota repetida");
        System.out.println("   - Solução: Extrair métodos reutilizáveis\n");
        
        System.out.println("5. CLASSE DEUS (ou Especulação Genérica)");
        System.out.println("   - Classe faz múltiplas coisas: valida email, processa, formata, envia");
        System.out.println("   - Mistura de responsabilidades");
        System.out.println("   - Solução: Separar em classes menores (SRP)\n");
        
        System.out.println("6. VERIFICAÇÕES NULAS REPETIDAS");
        System.out.println("   - Verificação 'clientEmail == null && !clientEmail.contains(\"@\")'");
        System.out.println("   - Poderia usar Optional ou Objects.requireNonNull");
        System.out.println("   - Solução: Criar método de validação encapsulado\n");
        
        System.out.println("7. NÚMEROS MÁGICOS");
        System.out.println("   - Valores como 1, 2, -1 sem significado");
        System.out.println("   - Solução: Usar constantes nomeadas ou enum\n");
        
        System.out.println("8. GRUPOS DE DADOS (Data Clumps)");
        System.out.println("   - clientName e clientEmail sempre usados juntos");
        System.out.println("   - Solução: Criar classe Client\n");
        
        System.out.println("=====================================================");
        System.out.println("REFATORAÇÃO ESCOLHIDA: InvoiceType Enum");
        System.out.println("=====================================================\n");
        
        System.out.println("Problema escolhido: OBSESSÃO POR PRIMITIVOS + DECISÕES CONDICIONAIS");
        System.out.println("Solução implementada: Criação do enum InvoiceType\n");
        
        System.out.println("Antes:");
        System.out.println("  if (type == 1) { ... }");
        System.out.println("  else if (type == 2) { ... }\n");
        
        System.out.println("Depois:");
        System.out.println("  public enum InvoiceType { SIMPLE, WITH_TAX, ... }");
        System.out.println("  System.out.println(type.getDescription());\n");
        
        System.out.println("Benefícios:");
        System.out.println("  - Código mais legível");
        System.out.println("  - Type safety em tempo de compilação");
        System.out.println("  - Fácil adição de novos tipos");
        System.out.println("  - Eliminação de duplicação\n");
        
        System.out.println("=====================================================");
        System.out.println("EXECUTANDO CÓDIGO REFATORADO");
        System.out.println("=====================================================\n");
        
        InvoiceRefactored.main(args);
    }
}
