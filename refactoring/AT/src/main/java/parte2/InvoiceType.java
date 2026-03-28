package parte2;

public enum InvoiceType {
    SIMPLE(1, "Nota fiscal simples", "Simples"),
    WITH_TAX(2, "Nota fiscal com imposto", "Com imposto"),
    PHANTOM(-1, "Nota fiscal fantasma", "Fantasma"),
    UNKNOWN(0, "Tipo desconhecido", "Desconhecido");

    private final int code;
    private final String description;
    private final String displayName;

    InvoiceType(int code, String description, String displayName) {
        this.code = code;
        this.description = description;
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static InvoiceType fromCode(int code) {
        for (InvoiceType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static InvoiceType fromCodeOrDefault(int code) {
        for (InvoiceType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
