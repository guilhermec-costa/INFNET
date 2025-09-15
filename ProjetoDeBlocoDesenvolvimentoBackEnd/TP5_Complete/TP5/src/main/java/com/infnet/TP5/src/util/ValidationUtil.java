package com.infnet.TP5.src.util;

import com.infnet.TP5.src.exception.ValidationException;

public class ValidationUtil {
    
    public static void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new ValidationException(fieldName + " não pode ser nulo");
        }
    }
    
    public static void validateNotEmpty(String str, String fieldName) {
        if (str == null || str.trim().isEmpty()) {
            throw new ValidationException(fieldName + " não pode estar vazio");
        }
    }
    
    public static void validateEmail(String email) {
        validateNotEmpty(email, "Email");
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("Email deve ter formato válido");
        }
    }
    
    public static void validatePositive(long value, String fieldName) {
        if (value <= 0) {
            throw new ValidationException(fieldName + " deve ser maior que zero");
        }
    }
    
    public static void validatePositiveOrZero(int value, String fieldName) {
        if (value < 0) {
            throw new ValidationException(fieldName + " não pode ser negativo");
        }
    }
}