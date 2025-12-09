package com.infnet.ex5;

/**
 * Implementação do algoritmo Bubble Sort
 * Baseado em: https://github.com/TheAlgorithms/Java
 */
public class BubbleSort {

  /**
   * Ordena um array de inteiros usando Bubble Sort
   * 
   * @param array Array a ser ordenado
   * @return Array ordenado
   */
  public int[] sort(int[] array) {
    if (array == null) {
      throw new IllegalArgumentException("Array cannot be null");
    }

    if (array.length <= 1) {
      return array;
    }

    int size = array.length;

    for (int i = 1; i < size; i++) {
      boolean swapped = false;

      for (int j = 0; j < size - i; j++) {
        if (array[j] > array[j + 1]) {
          swap(array, j, j + 1);
          swapped = true;
        }
      }

      if (!swapped) {
        break;
      }
    }

    return array;
  }

  /**
   * Ordena um array de Comparable usando Bubble Sort (versão genérica)
   * 
   * @param array Array a ser ordenado
   * @param <T>   Tipo que implementa Comparable
   * @return Array ordenado
   */
  public <T extends Comparable<T>> T[] sort(T[] array) {
    if (array == null) {
      throw new IllegalArgumentException("Array cannot be null");
    }

    if (array.length <= 1) {
      return array;
    }

    int size = array.length;

    for (int i = 1; i < size; i++) {
      boolean swapped = false;

      for (int j = 0; j < size - i; j++) {
        if (greater(array[j], array[j + 1])) {
          swap(array, j, j + 1);
          swapped = true;
        }
      }

      if (!swapped) {
        break;
      }
    }

    return array;
  }

  /**
   * Troca dois elementos no array de inteiros
   * 
   * @param array Array onde a troca será feita
   * @param i     Índice do primeiro elemento
   * @param j     Índice do segundo elemento
   */
  private void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  /**
   * Troca dois elementos no array genérico
   * 
   * @param array Array onde a troca será feita
   * @param i     Índice do primeiro elemento
   * @param j     Índice do segundo elemento
   */
  private <T> void swap(T[] array, int i, int j) {
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  /**
   * Compara se o primeiro elemento é maior que o segundo
   * 
   * @param a Primeiro elemento
   * @param b Segundo elemento
   * @return true se a > b, false caso contrário
   */
  private <T extends Comparable<T>> boolean greater(T a, T b) {
    return a.compareTo(b) > 0;
  }

  /**
   * Verifica se um array está ordenado
   * 
   * @param array Array a ser verificado
   * @return true se ordenado, false caso contrário
   */
  public boolean isSorted(int[] array) {
    if (array == null || array.length <= 1) {
      return true;
    }

    for (int i = 0; i < array.length - 1; i++) {
      if (array[i] > array[i + 1]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verifica se um array genérico está ordenado
   * 
   * @param array Array a ser verificado
   * @return true se ordenado, false caso contrário
   */
  public <T extends Comparable<T>> boolean isSorted(T[] array) {
    if (array == null || array.length <= 1) {
      return true;
    }

    for (int i = 0; i < array.length - 1; i++) {
      if (array[i].compareTo(array[i + 1]) > 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Conta o número de comparações necessárias para ordenar
   * Útil para análise de complexidade
   * 
   * @param array Array a ser analisado
   * @return Número de comparações
   */
  public int countComparisons(int[] array) {
    if (array == null || array.length <= 1) {
      return 0;
    }

    int comparisons = 0;
    int size = array.length;
    int[] temp = array.clone();

    for (int i = 1; i < size; i++) {
      boolean swapped = false;

      for (int j = 0; j < size - i; j++) {
        comparisons++;
        if (temp[j] > temp[j + 1]) {
          int t = temp[j];
          temp[j] = temp[j + 1];
          temp[j + 1] = t;
          swapped = true;
        }
      }

      if (!swapped) {
        break;
      }
    }

    return comparisons;
  }

  /**
   * Conta o número de trocas necessárias para ordenar
   * 
   * @param array Array a ser analisado
   * @return Número de trocas
   */
  public int countSwaps(int[] array) {
    if (array == null || array.length <= 1) {
      return 0;
    }

    int swaps = 0;
    int size = array.length;
    int[] temp = array.clone();

    for (int i = 1; i < size; i++) {
      boolean swapped = false;

      for (int j = 0; j < size - i; j++) {
        if (temp[j] > temp[j + 1]) {
          int t = temp[j];
          temp[j] = temp[j + 1];
          temp[j + 1] = t;
          swaps++;
          swapped = true;
        }
      }

      if (!swapped) {
        break;
      }
    }

    return swaps;
  }
}