package com.infnet.ex2;

public class MathFunctions {

  private final MathLogger logger;

  public MathFunctions(MathLogger logger) {
    this.logger = logger;
  }

  public MathFunctions() {
    this.logger = null;
  }

  public int multiplyByTwo(int number) {
    if (logger != null) {
      logger.log("MultiplyByTwo", new int[] { number });
    }
    return number * 2;
  }

  public int[] generateMultiplicationTable(int number, int limit) {
    if (limit < 0) {
      throw new IllegalArgumentException("Limit cannot be negative");
    }

    int[] result = new int[limit];
    for (int i = 0; i < limit; i++) {
      result[i] = number * (i + 1);
    }

    if (logger != null) {
      logger.log("GenerateMultiplicationTable", new int[] { number, limit });
    }

    return result;
  }

  public boolean isPrime(int number) {
    if (number <= 1) {
      if (logger != null) {
        logger.log("IsPrime", new int[] { number });
      }
      return false;
    }

    for (int i = 2; i <= Math.sqrt(number); i++) {
      if (number % i == 0) {
        if (logger != null) {
          logger.log("IsPrime", new int[] { number });
        }
        return false;
      }
    }

    if (logger != null) {
      logger.log("IsPrime", new int[] { number });
    }
    return true;
  }

  public double calculateAverage(int[] numbers) {
    if (numbers == null || numbers.length == 0) {
      throw new IllegalArgumentException("Array cannot be null or empty.");
    }

    double sum = 0;
    for (int num : numbers) {
      sum += num;
    }

    double average = sum / numbers.length;

    if (logger != null) {
      logger.log("CalculateAverage", numbers);
    }

    return average;
  }
}

interface MathLogger {
  void log(String operation, int[] inputs);
}