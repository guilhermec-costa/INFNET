package parte3;

public class PricingService {

    public enum CustomerType {
        REGULAR(1, "Regular", 0.10),
        PREMIUM(2, "Premium", 0.15),
        STANDARD(0, "Padrão", 0.0);

        private final int code;
        private final String name;
        private final double discountPercentage;

        CustomerType(int code, String name, double discountPercentage) {
            this.code = code;
            this.name = name;
            this.discountPercentage = discountPercentage;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public double getDiscountPercentage() {
            return discountPercentage;
        }

        public static CustomerType fromCode(int code) {
            for (CustomerType type : values()) {
                if (type.code == code) {
                    return type;
                }
            }
            return STANDARD;
        }
    }

    private static final double HOLIDAY_ADDITIONAL_DISCOUNT = 0.05;

    public double calculatePrice(double basePrice, int customerTypeCode, boolean isHoliday) {
        CustomerType customerType = CustomerType.fromCode(customerTypeCode);
        
        double customerDiscount = getCustomerDiscount(customerType);
        double holidayDiscount = getHolidayDiscount(isHoliday);
        
        double totalDiscount = customerDiscount + holidayDiscount;
        
        double finalPrice = applyDiscount(basePrice, totalDiscount);
        
        return finalPrice;
    }

    private double getCustomerDiscount(CustomerType customerType) {
        return customerType.getDiscountPercentage();
    }

    private double getHolidayDiscount(boolean isHoliday) {
        if (isHoliday) {
            return HOLIDAY_ADDITIONAL_DISCOUNT;
        }
        return 0.0;
    }

    private double applyDiscount(double basePrice, double discountPercentage) {
        return basePrice * (1 - discountPercentage);
    }

    public static void main(String[] args) {
        PricingService service = new PricingService();
        
        System.out.println("=== Calculadora de Preços ===\n");
        
        double basePrice = 100.0;
        
        System.out.println("Preço base: R$ " + basePrice);
        System.out.println();
        
        System.out.println("--- Cliente Regular ---");
        System.out.printf("Sem feriado: R$ %.2f%n", service.calculatePrice(basePrice, 1, false));
        System.out.printf("Com feriado: R$ %.2f%n", service.calculatePrice(basePrice, 1, true));
        
        System.out.println("\n--- Cliente Premium ---");
        System.out.printf("Sem feriado: R$ %.2f%n", service.calculatePrice(basePrice, 2, false));
        System.out.printf("Com feriado: R$ %.2f%n", service.calculatePrice(basePrice, 2, true));
        
        System.out.println("\n--- Cliente Padrão ---");
        System.out.printf("Sem feriado: R$ %.2f%n", service.calculatePrice(basePrice, 0, false));
        System.out.printf("Com feriado: R$ %.2f%n", service.calculatePrice(basePrice, 0, true));
    }
}
