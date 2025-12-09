package com.infnet.ex2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.lifecycle.BeforeProperty;

/**
 * Testes baseados em propriedades usando jqwik
 * Valida comportamentos esperados em diferentes cenários
 */
class MathFunctionsPropertyTest {

  private MathFunctions mathFunctions;

  @BeforeProperty
  void setUp() {
    mathFunctions = new MathFunctions();
  }

  // ===== PROPRIEDADE 1: MultiplyByTwo sempre retorna número par =====

  @Property
  @Label("MultiplyByTwo sempre retorna um número par")
  void multiplyByTwoAlwaysReturnsEvenNumber(@ForAll @IntRange(min = -1000000, max = 1000000) int number) {
    int result = mathFunctions.multiplyByTwo(number);

    // Propriedade: resultado sempre é par (divisível por 2)
    assertEquals(0, result % 2,
        String.format("Resultado %d não é par para entrada %d", result, number));
  }

  @Property
  @Label("MultiplyByTwo deve retornar exatamente o dobro")
  void multiplyByTwoReturnsExactlyDouble(@ForAll @IntRange(min = -1000000, max = 1000000) int number) {
    int result = mathFunctions.multiplyByTwo(number);
    assertEquals(number * 2, result);
  }

  @Property
  @Label("MultiplyByTwo deve preservar o sinal (positivo/negativo)")
  void multiplyByTwoPreservesSign(@ForAll @IntRange(min = 1, max = 1000000) int number) {
    int result = mathFunctions.multiplyByTwo(number);
    assertTrue(result > 0, "Resultado deve ser positivo para entrada positiva");
  }

  @Property
  @Label("MultiplyByTwo de número negativo deve ser negativo")
  void multiplyByTwoNegativeInputGivesNegativeResult(@ForAll @IntRange(min = -1000000, max = -1) int number) {
    int result = mathFunctions.multiplyByTwo(number);
    assertTrue(result < 0, "Resultado deve ser negativo para entrada negativa");
  }

  @Property
  @Label("MultiplyByTwo de zero deve ser zero")
  void multiplyByTwoZeroInputGivesZero() {
    int result = mathFunctions.multiplyByTwo(0);
    assertEquals(0, result, "Resultado deve ser zero para entrada zero");
  }

  // ===== PROPRIEDADE 2: GenerateMultiplicationTable - todos elementos são
  // múltiplos =====

  @Property
  @Label("Todos elementos da tabuada são múltiplos do número original")
  void multiplicationTableElementsAreMultiples(
      @ForAll @IntRange(min = 1, max = 100) int number,
      @ForAll @IntRange(min = 1, max = 20) int limit) {

    int[] table = mathFunctions.generateMultiplicationTable(number, limit);

    for (int i = 0; i < table.length; i++) {
      int element = table[i];
      // Propriedade: cada elemento é múltiplo do número
      assertEquals(0, element % number,
          String.format("Elemento %d na posição %d não é múltiplo de %d",
              element, i, number));
    }
  }

  @Property
  @Label("Tabuada deve ter comprimento correto")
  void multiplicationTableHasCorrectLength(
      @ForAll @IntRange(min = 1, max = 100) int number,
      @ForAll @IntRange(min = 0, max = 50) int limit) {

    int[] table = mathFunctions.generateMultiplicationTable(number, limit);
    assertEquals(limit, table.length);
  }

  @Property
  @Label("Elementos da tabuada devem estar em ordem crescente (para números positivos)")
  void multiplicationTableIsInAscendingOrder(
      @ForAll @IntRange(min = 1, max = 100) int number,
      @ForAll @IntRange(min = 2, max = 20) int limit) {

    int[] table = mathFunctions.generateMultiplicationTable(number, limit);

    // Para números positivos, elementos devem estar em ordem crescente
    if (number > 0) {
      for (int i = 1; i < table.length; i++) {
        assertTrue(table[i] > table[i - 1],
            String.format("Elementos devem estar em ordem crescente para número positivo %d", number));
      }
    }
    // Para números negativos, elementos devem estar em ordem decrescente
    else if (number < 0) {
      for (int i = 1; i < table.length; i++) {
        assertTrue(table[i] < table[i - 1],
            String.format("Elementos devem estar em ordem decrescente para número negativo %d", number));
      }
    }
    // Para zero, todos elementos devem ser zero
    else {
      for (int element : table) {
        assertEquals(0, element, "Tabuada de zero deve conter apenas zeros");
      }
    }
  }

  @Property
  @Label("Primeiro elemento da tabuada deve ser o próprio número")
  void multiplicationTableFirstElementIsNumber(
      @ForAll int number,
      @ForAll @IntRange(min = 1, max = 20) int limit) {

    int[] table = mathFunctions.generateMultiplicationTable(number, limit);
    assertEquals(number, table[0]);
  }

  // ===== PROPRIEDADE 3: IsPrime - números primos não têm divisores =====

  @Property
  @Label("Números menores ou iguais a 1 não são primos")
  void numbersLessThanOrEqualToOneAreNotPrime(
      @ForAll @IntRange(max = 1) int number) {

    assertFalse(mathFunctions.isPrime(number),
        String.format("Número %d não deveria ser considerado primo", number));
  }

  @Property
  @Label("Números primos conhecidos devem ser identificados corretamente")
  void knownPrimesAreIdentified() {
    int[] knownPrimes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47 };

    for (int prime : knownPrimes) {
      assertTrue(mathFunctions.isPrime(prime),
          String.format("Número %d deveria ser identificado como primo", prime));
    }
  }

  @Property
  @Label("Números compostos conhecidos não devem ser primos")
  void knownCompositesAreNotPrime() {
    int[] composites = { 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25 };

    for (int composite : composites) {
      assertFalse(mathFunctions.isPrime(composite),
          String.format("Número %d não deveria ser identificado como primo", composite));
    }
  }

  @Property
  @Label("Números pares maiores que 2 não são primos")
  void evenNumbersGreaterThanTwoAreNotPrime(
      @ForAll @IntRange(min = 4, max = 1000) int number) {

    Assume.that(number % 2 == 0); // Garante que é par

    assertFalse(mathFunctions.isPrime(number),
        String.format("Número par %d não deveria ser primo", number));
  }

  @Property
  @Label("Se um número é primo, não deve ser divisível por nenhum número entre 2 e sua raiz")
  void primeHasNoDivisors(@ForAll @IntRange(min = 2, max = 500) int number) {
    boolean isPrime = mathFunctions.isPrime(number);

    if (isPrime) {
      // Valida que realmente não tem divisores
      int sqrt = (int) Math.sqrt(number);
      for (int i = 2; i <= sqrt; i++) {
        assertNotEquals(0, number % i,
            String.format("Número primo %d não deveria ser divisível por %d", number, i));
      }
    }
  }

  // ===== PROPRIEDADE 4: CalculateAverage - resultado entre min e max =====

  @Property
  @Label("Média deve estar entre o menor e o maior valor do array")
  void averageIsBetweenMinAndMax(@ForAll("intArrays") int[] numbers) {
    double average = mathFunctions.calculateAverage(numbers);

    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (int num : numbers) {
      if (num < min)
        min = num;
      if (num > max)
        max = num;
    }

    assertTrue(average >= min,
        String.format("Média %.2f deveria ser >= %d", average, min));
    assertTrue(average <= max,
        String.format("Média %.2f deveria ser <= %d", average, max));
  }

  @Property
  @Label("Média de array com valores iguais deve ser o próprio valor")
  void averageOfEqualValuesIsTheValue(
      @ForAll int value,
      @ForAll @IntRange(min = 1, max = 100) int arraySize) {

    int[] numbers = new int[arraySize];
    for (int i = 0; i < arraySize; i++) {
      numbers[i] = value;
    }

    double average = mathFunctions.calculateAverage(numbers);
    assertEquals(value, average, 0.001);
  }

  @Property
  @Label("Média deve ser soma dividida pelo tamanho")
  void averageIsSumDividedByLength(@ForAll("intArrays") int[] numbers) {
    double average = mathFunctions.calculateAverage(numbers);

    long sum = 0;
    for (int num : numbers) {
      sum += num;
    }

    double expectedAverage = (double) sum / numbers.length;
    assertEquals(expectedAverage, average, 0.001);
  }

  @Property
  @Label("Array vazio ou null deve lançar exceção")
  void emptyOrNullArrayThrowsException() {
    assertThrows(IllegalArgumentException.class,
        () -> mathFunctions.calculateAverage(null));

    assertThrows(IllegalArgumentException.class,
        () -> mathFunctions.calculateAverage(new int[0]));
  }

  // ===== TESTES COM MOCK =====

  @Property
  @Label("Logger deve ser chamado quando presente - MultiplyByTwo")
  void loggerIsCalledForMultiplyByTwo(@ForAll int number) {
    MathLogger mockLogger = mock(MathLogger.class);
    MathFunctions mathWithLogger = new MathFunctions(mockLogger);

    mathWithLogger.multiplyByTwo(number);

    verify(mockLogger, times(1)).log(eq("MultiplyByTwo"), any(int[].class));
  }

  @Property
  @Label("Logger deve ser chamado quando presente - GenerateMultiplicationTable")
  void loggerIsCalledForMultiplicationTable(
      @ForAll @IntRange(min = 1, max = 10) int number,
      @ForAll @IntRange(min = 1, max = 10) int limit) {

    MathLogger mockLogger = mock(MathLogger.class);
    MathFunctions mathWithLogger = new MathFunctions(mockLogger);

    mathWithLogger.generateMultiplicationTable(number, limit);

    verify(mockLogger, times(1))
        .log(eq("GenerateMultiplicationTable"), any(int[].class));
  }

  // ===== GERADORES PERSONALIZADOS =====

  @Provide
  Arbitrary<int[]> intArrays() {
    return Arbitraries.integers()
        .between(-1000, 1000)
        .array(int[].class)
        .ofMinSize(1)
        .ofMaxSize(50);
  }
}