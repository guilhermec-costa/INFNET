package parte6;

import java.util.ArrayList;
import java.util.List;

public class DocumentDemo {
    
    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("DEMONSTRAÇÃO DE POLIMORFISMO - Hierarquia de Documentos");
        System.out.println("=====================================================\n");
        
        System.out.println("=== Problemas da Abordagem Anterior (String type) ===\n");
        
        System.out.println("1. ERROS DE DIGITAÇÃO:");
        System.out.println("   type = \"PDF\"   -> funciona");
        System.out.println("   type = \"pdf\"   -> NÃO funciona (case-sensitive)");
        System.out.println("   type = \"PFD\"   -> erro silencioso\n");
        
        System.out.println("2. MANUTENÇÃO DIFÍCIL:");
        System.out.println("   - Para adicionar novo formato, precisa modificar");
        System.out.println("     a classe com if-else em múltiplos lugares");
        System.out.println("   - Risco de quebrar funcionalidade existente\n");
        
        System.out.println("3. SEM TYPE SAFETY:");
        System.out.println("   - Qualquer string é aceita");
        System.out.println("   - Erros só aparecem em runtime\n");
        
        System.out.println("=====================================================");
        System.out.println("SOLUÇÃO COM HIERARQUIA DE CLASSES");
        System.out.println("=====================================================\n");
        
        List<Document> documents = new ArrayList<>();
        documents.add(new PdfDocument());
        documents.add(new HtmlDocument());
        documents.add(new WordDocument());
        
        System.out.println("--- Imprimindo documentos via polimorfismo ---\n");
        
        for (Document doc : documents) {
            doc.print();
        }
        
        System.out.println("\n--- Benefícios da abordagem refatorada ---\n");
        
        System.out.println("1. COESÃO:");
        System.out.println("   - Cada classe tem uma única responsabilidade");
        System.out.println("   - PdfDocument só sabe imprimir PDF\n");
        
        System.out.println("2. REDUÇÃO DE DUPLICAÇÃO:");
        System.out.println("   - Lógica comum (printHeader) está na classe pai");
        System.out.println("   - Subclasses reutilizam comportamento\n");
        
        System.out.println("3. FACILIDADE DE EXTENSÃO:");
        System.out.println("   - Para novo formato: criar nova classe + compilar");
        System.out.println("   - Classes existentes não precisam ser modificadas");
        System.out.println("   - Open/Closed Principle respeitado\n");
        
        System.out.println("4. TYPE SAFETY:");
        System.out.println("   - Erros capturados em tempo de compilação");
        System.out.println("   - IDE oferece autocomplete\n");
        
        System.out.println("5. FACILIDADE DE TESTES:");
        System.out.println("   - Cada classe pode ser testada isoladamente");
        System.out.println("   - Mocking mais simples\n");
        
        System.out.println("=====================================================");
        System.out.println("FIM DA DEMONSTRAÇÃO");
        System.out.println("=====================================================");
    }
}
