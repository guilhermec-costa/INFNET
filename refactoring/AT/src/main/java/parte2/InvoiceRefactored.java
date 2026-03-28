package parte2;

public class InvoiceRefactored {
    private String clientName;
    private String clientEmail;
    private double amount;
    private InvoiceType type;

    public InvoiceRefactored(String clientName, String clientEmail, double amount, InvoiceType type) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.amount = amount;
        this.type = type;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public double getAmount() {
        return amount;
    }

    public InvoiceType getType() {
        return type;
    }

    public void process() {
        if (!isValidEmail(clientEmail)) {
            System.out.println("Email inválido. Falha no envio.");
            return;
        }

        System.out.println(type.getDescription());
        printInvoice();
        sendInvoiceByEmail();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private void printInvoice() {
        System.out.println("--- NOTA FISCAL ---");
        System.out.println("Cliente: " + clientName);
        System.out.println("Valor: R$ " + amount);
        System.out.println("Tipo: " + type.getDisplayName());
        System.out.println("---------------------");
    }

    private void sendInvoiceByEmail() {
        System.out.println("Enviando nota fiscal para: " + clientEmail);
        String nota = "--- NOTA FISCAL ---\n" +
                "Cliente: " + clientName + "\n" +
                "Valor: R$ " + amount + "\n" +
                "Tipo: " + type.getDisplayName() + "\n" +
                "---------------------";
        sendEmail(clientEmail, nota);
    }

    private void sendEmail(String email, String conteudo) {
        System.out.println("Enviando email para: " + email);
        System.out.println("Conteúdo:\n" + conteudo);
    }

    public static void main(String[] args) {
        System.out.println("=== Teste Nota Fiscal Simples ===");
        InvoiceRefactored invoice1 = new InvoiceRefactored(
            "João Silva", "joao@email.com", 100.00, InvoiceType.SIMPLE
        );
        invoice1.process();

        System.out.println("\n=== Teste Nota Fiscal com Imposto ===");
        InvoiceRefactored invoice2 = new InvoiceRefactored(
            "Maria Santos", "maria@empresa.com", 250.00, InvoiceType.WITH_TAX
        );
        invoice2.process();

        System.out.println("\n=== Teste Email Inválido ===");
        InvoiceRefactored invoice3 = new InvoiceRefactored(
            "Pedro", "invalid", 50.00, InvoiceType.SIMPLE
        );
        invoice3.process();
    }
}
