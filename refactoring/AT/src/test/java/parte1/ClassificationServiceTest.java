package parte1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClassificationServiceTest {

    private final ClassificationService service = new ClassificationService();

    @Test
    void testClassify_RareCase_ReturnsRareCase() {
        String result = service.classify(-9999);
        assertEquals("CASO RARO", result);
    }

    @Test
    void testClassify_MediumValue_ReturnsMedium() {
        String result = service.classify(10);
        assertEquals("MÉDIO", result);
    }

    @Test
    void testClassify_HighValue_ReturnsHigh() {
        String result = service.classify(15);
        assertEquals("ALTO", result);
    }

    @Test
    void testClassify_LowValue_ReturnsLow() {
        String result = service.classify(5);
        assertEquals("BAIXO", result);
    }

    @Test
    void testClassify_BoundaryValue_ExactlyTen_IsMedium() {
        String result = service.classify(10);
        assertEquals("MÉDIO", result);
    }

    @Test
    void testClassify_BoundaryValue_Eleven_IsHigh() {
        String result = service.classify(11);
        assertEquals("ALTO", result);
    }

    @Test
    void testClassify_NegativeValue_ReturnsLow() {
        String result = service.classify(-1);
        assertEquals("BAIXO", result);
    }
}
