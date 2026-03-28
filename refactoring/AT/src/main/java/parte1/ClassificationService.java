package parte1;

public class ClassificationService {

    private static final int HIGH_THRESHOLD = 10;
    private static final int MEDIUM_VALUE = 10;
    private static final int RARE_CASE_VALUE = -9999;

    public String classify(int value) {
        if (value == RARE_CASE_VALUE) {
            return "CASO RARO";
        } else if (value == MEDIUM_VALUE) {
            return "MÉDIO";
        } else if (value > HIGH_THRESHOLD) {
            return "ALTO";
        } else {
            return "BAIXO";
        }
    }

    public void printClassification(int value) {
        String classification = classify(value);
        System.out.println(classification);
    }

    public void debug(int value) {
        System.out.println("DEBUG: value = " + value);
    }

    public void processValue(int value) {
        printClassification(value);
        debug(value);
    }

    public static void main(String[] args) {
        ClassificationService service = new ClassificationService();
        
        System.out.println("=== Testes de Classificacao ===");
        System.out.print("Valor -9999: ");
        service.printClassification(-9999);
        
        System.out.print("Valor 10: ");
        service.printClassification(10);
        
        System.out.print("Valor 15: ");
        service.printClassification(15);
        
        System.out.print("Valor 5: ");
        service.printClassification(5);
    }
}
