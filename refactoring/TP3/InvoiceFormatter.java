public class InvoiceFormatter {
    public void printInvoice(String clientName, double discountRate, Iterable<Item> items) {
        double total = calculateSubtotal(items);
        double discount = calculateDiscount(total, discountRate);
        double finalTotal = total - discount;

        printHeader(clientName);
        printItems(items);
        printTotals(total, discount, finalTotal);
    }

    private double calculateSubtotal(Iterable<Item> items) {
        double total = 0;
        for (Item item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    private double calculateDiscount(double amount, double rate) {
        return amount * rate;
    }

    private void printHeader(String clientName) {
        System.out.println("Cliente: " + clientName);
    }

    private void printItems(Iterable<Item> items) {
        for (Item item : items) {
            System.out.println(item.getQuantity() + "x " + item.getProduct() + " - R$" + item.getUnitPrice());
        }
    }

    private void printTotals(double subtotal, double discount, double finalTotal) {
        System.out.println("Subtotal: R$" + subtotal);
        System.out.println("Desconto: R$" + discount);
        System.out.println("Total final: R$" + finalTotal);
    }
}
