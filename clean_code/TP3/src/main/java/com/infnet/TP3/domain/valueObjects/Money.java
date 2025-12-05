package com.infnet.TP3.domain.valueObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * EXERCÍCIOS 3 e 4: Money (Value Object imutável)
 *
 * Representa valores monetários com BigDecimal para evitar erros de precisão.
 *
 * Por que BigDecimal?
 * - double/float geram imprecisão (ex.: 0.1 + 0.2 ≠ 0.3)
 * - BigDecimal é exato e permite controle de arredondamento
 * - Essencial para cálculos financeiros corretos
 *
 * Garantias da classe:
 * - Valor sempre válido (não negativo, escala adequada)
 * - Imutabilidade (operações retornam novos objetos)
 * - Facilita rastreamento e auditoria
 */

public final class Money {

  private final BigDecimal amount;
  private final Currency currency;

  private static final int DEFAULT_SCALE = 2;
  private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

  private Money(BigDecimal amount, Currency currency) {
    this.amount = amount.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    this.currency = currency;
  }

  /**
   * EXERCÍCIO 3: Validação na criação
   * Factory method com validações
   */
  public static Money of(BigDecimal amount, Currency currency) {
    if (amount == null) {
      throw new IllegalArgumentException("Valor não pode ser nulo");
    }
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Valor não pode ser negativo: " + amount);
    }
    if (currency == null) {
      throw new IllegalArgumentException("Moeda não pode ser nula");
    }
    return new Money(amount, currency);
  }

  /**
   * Conveniência para criar Money em BRL
   */
  public static Money brl(String amount) {
    return of(new BigDecimal(amount), Currency.getInstance("BRL"));
  }

  public static Money brl(double amount) {
    return of(BigDecimal.valueOf(amount), Currency.getInstance("BRL"));
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Currency getCurrency() {
    return currency;
  }

  /**
   * EXERCÍCIO 2: Operações sem efeitos colaterais
   *
   * Todas as operações retornam novos objetos Money — o original nunca muda.
   *
   * Benefícios:
   * - Thread-safe
   * - Histórico claro de operações
   * - Sem risco de estado inconsistente
   * - Facilita auditoria
   */
  public Money add(Money other) {
    validateSameCurrency(other);
    return new Money(this.amount.add(other.amount), this.currency);
  }

  public Money subtract(Money other) {
    validateSameCurrency(other);
    BigDecimal result = this.amount.subtract(other.amount);
    if (result.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Resultado não pode ser negativo");
    }
    return new Money(result, this.currency);
  }

  public Money multiply(BigDecimal factor) {
    if (factor.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Fator não pode ser negativo");
    }
    return new Money(this.amount.multiply(factor), this.currency);
  }

  public Money divide(BigDecimal divisor) {
    if (divisor.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Divisor deve ser positivo");
    }
    return new Money(this.amount.divide(divisor, DEFAULT_SCALE, DEFAULT_ROUNDING),
        this.currency);
  }

  /**
   * Aplica desconto percentual
   * 
   * @param percentage percentual de desconto (ex: 10 para 10%)
   */
  public Money applyDiscount(BigDecimal percentage) {
    if (percentage.compareTo(BigDecimal.ZERO) < 0 ||
        percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
      throw new IllegalArgumentException("Percentual deve estar entre 0 e 100");
    }
    BigDecimal factor = BigDecimal.ONE.subtract(
        percentage.divide(BigDecimal.valueOf(100), 4, DEFAULT_ROUNDING));
    return multiply(factor);
  }

  /**
   * EXERCÍCIO 3: Teste conceitual de imutabilidade
   * 
   * Teste conceitual de imutabilidade.
   *
   * Aplicar desconto retorna novo objeto; o original permanece inalterado.
   * Exemplo:
   * Money original = Money.brl("100.00");
   * Money comDesconto = original.applyDiscount(BigDecimal.valueOf(10));
   * original = 100.00, comDesconto = 90.00
   */
  private void validateSameCurrency(Money other) {
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException(
          String.format("Moedas incompatíveis: %s e %s",
              this.currency, other.currency));
    }
  }

  public boolean isGreaterThan(Money other) {
    validateSameCurrency(other);
    return this.amount.compareTo(other.amount) > 0;
  }

  public boolean isLessThan(Money other) {
    validateSameCurrency(other);
    return this.amount.compareTo(other.amount) < 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Money money = (Money) o;
    return amount.compareTo(money.amount) == 0 &&
        currency.equals(money.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }

  @Override
  public String toString() {
    return String.format("%s %s", currency.getSymbol(), amount);
  }
}