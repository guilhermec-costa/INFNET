package parte6;

public class PdfDocument extends Document {
    
    @Override
    public void print() {
        printHeader("PDF");
    }
}
