package parte6;

public abstract class Document {
    
    public abstract void print();
    
    protected void printHeader(String formatType) {
        System.out.println("Printing " + formatType);
    }
}
