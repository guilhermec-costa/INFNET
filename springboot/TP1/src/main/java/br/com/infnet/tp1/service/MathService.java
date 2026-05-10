package br.com.infnet.tp1.service;

import br.com.infnet.tp1.exception.DivisionByZeroException;
import java.math.BigDecimal;
import java.math.MathContext;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;

    public BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (BigDecimal.ZERO.compareTo(b) == 0) {
            throw new DivisionByZeroException("Não é permitido dividir por zero.");
        }

        return a.divide(b, MATH_CONTEXT);
    }

    public BigDecimal power(BigDecimal a, BigDecimal b) {
        double result = Math.pow(a.doubleValue(), b.doubleValue());
        return BigDecimal.valueOf(result);
    }
}

