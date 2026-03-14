import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private Client client;
    private List<Item> items;
    private double discountRate;
    private final EmailService emailService;
    private final InvoiceFormatter invoiceFormatter;

    public Order() {
        this.items = new ArrayList<>();
        this.discountRate = 0.1;
        this.emailService = new EmailService();
        this.invoiceFormatter = new InvoiceFormatter();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        items.add(item);
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public void printInvoice() {
        invoiceFormatter.printInvoice(client.getName(), discountRate, items);
    }

    public void sendEmail() {
        String message = "Pedido recebido! Obrigado pela compra.";
        emailService.sendEmail(client.getEmail(), message);
    }

    public double calculateTotal() {
        double subtotal = calculateSubtotal();
        double discount = subtotal * discountRate;
        return subtotal - discount;
    }

    private double calculateSubtotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
}
