package com.infnet.ex5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Cobertura - BubbleSort")
class BubbleSortTest {
    
    private BubbleSort bubbleSort;
    
    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }
    
    @Test
    @DisplayName("Deve lançar exceção para array null")
    void deveLancarExcecaoParaArrayNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> bubbleSort.sort((int[]) null));
        
        assertEquals("Array cannot be null", exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve lançar exceção para array genérico null")
    void deveLancarExcecaoParaArrayGenericoNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> bubbleSort.sort((Integer[]) null));
        
        assertEquals("Array cannot be null", exception.getMessage());
    }
    
    
    @Test
    @DisplayName("Deve retornar array vazio sem modificações")
    void deveRetornarArrayVazio() {
        int[] array = {};
        int[] resultado = bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(resultado));
        assertEquals(0, resultado.length);
    }
    
    @Test
    @DisplayName("Deve retornar array com um elemento sem modificações")
    void deveRetornarArrayComUmElemento() {
        int[] array = {42};
        int[] resultado = bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(resultado));
        assertEquals(1, resultado.length);
        assertEquals(42, resultado[0]);
    }
    
    
    @Test
    @DisplayName("Deve ordenar array com dois elementos já ordenados")
    void deveOrdenarArrayDoisElementosOrdenado() {
        int[] array = {1, 2};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{1, 2}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array com dois elementos desordenados (uma troca)")
    void deveOrdenarArrayDoisElementosDesordenado() {
        int[] array = {2, 1};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{1, 2}, array);
    }
    
    
    @Test
    @DisplayName("Deve executar múltiplas passagens para array desordenado")
    void deveExecutarMultiplasPassagens() {
        int[] array = {5, 4, 3, 2, 1};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }
    
    
    @Test
    @DisplayName("Deve realizar trocas quando elemento atual é maior que próximo")
    void deveRealizarTrocasQuandoNecessario() {
        int[] array = {3, 1, 2};
        int swaps = bubbleSort.countSwaps(array);
        
        assertTrue(swaps > 0, "Deve ter realizado pelo menos uma troca");
    }
    
    @Test
    @DisplayName("Não deve realizar trocas quando array já está ordenado")
    void naoDeveRealizarTrocasArrayOrdenado() {
        int[] array = {1, 2, 3, 4, 5};
        int swaps = bubbleSort.countSwaps(array);
        
        assertEquals(0, swaps, "Não deve ter realizado trocas");
    }
    
    @Test
    @DisplayName("Deve terminar cedo se não houver trocas (otimização)")
    void deveTerminarCedoSeNaoHouverTrocas() {
        int[] array = {1, 2, 3, 4, 5};
        int comparisons = bubbleSort.countComparisons(array);
        
        assertEquals(4, comparisons, "Deve fazer apenas uma passagem completa");
    }
    
    @Test
    @DisplayName("Deve continuar executando enquanto houver trocas")
    void deveContinuarEnquantoHouverTrocas() {
        int[] array = {5, 4, 3, 2, 1};
        int comparisons = bubbleSort.countComparisons(array);
        
        assertTrue(comparisons > 4, "Deve fazer múltiplas passagens");
    }
    
    
    @Test
    @DisplayName("Deve ordenar array com números positivos")
    void deveOrdenarArrayPositivos() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{11, 12, 22, 25, 34, 64, 90}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array com números negativos")
    void deveOrdenarArrayNegativos() {
        int[] array = {-5, -2, -8, -1, -9};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{-9, -8, -5, -2, -1}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array com mix de positivos e negativos")
    void deveOrdenarArrayMixPositivosNegativos() {
        int[] array = {3, -1, 5, -3, 0, 2};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{-3, -1, 0, 2, 3, 5}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array com elementos duplicados")
    void deveOrdenarArrayComDuplicados() {
        int[] array = {5, 2, 8, 2, 9, 1, 5, 5};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{1, 2, 2, 5, 5, 5, 8, 9}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array com todos elementos iguais")
    void deveOrdenarArrayTodosIguais() {
        int[] array = {7, 7, 7, 7, 7};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        for (int num : array) {
            assertEquals(7, num);
        }
    }
    
    
    @Test
    @DisplayName("Deve ordenar array já ordenado (melhor caso)")
    void deveOrdenarArrayJaOrdenado() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array em ordem reversa (pior caso)")
    void deveOrdenarArrayOrdemReversa() {
        int[] array = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array com valores extremos (Integer.MAX_VALUE e MIN_VALUE)")
    void deveOrdenarArrayComValoresExtremos() {
        int[] array = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, 100, -100};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertEquals(Integer.MIN_VALUE, array[0]);
        assertEquals(Integer.MAX_VALUE, array[array.length - 1]);
    }
    
    
    @ParameterizedTest
    @MethodSource("provideDifferentArrays")
    @DisplayName("Deve ordenar diversos tipos de arrays corretamente")
    void deveOrdenarDiversosTiposArrays(int[] input, int[] expected) {
        bubbleSort.sort(input);
        
        assertTrue(bubbleSort.isSorted(input));
        assertArrayEquals(expected, input);
    }
    
    private static Stream<Arguments> provideDifferentArrays() {
        return Stream.of(
            Arguments.of(new int[]{3, 1, 4, 1, 5, 9, 2, 6}, 
                        new int[]{1, 1, 2, 3, 4, 5, 6, 9}),
            Arguments.of(new int[]{100, 50, 25, 75}, 
                        new int[]{25, 50, 75, 100}),
            Arguments.of(new int[]{-10, -20, -5, -15}, 
                        new int[]{-20, -15, -10, -5}),
            Arguments.of(new int[]{0, 0, 0}, 
                        new int[]{0, 0, 0}),
            Arguments.of(new int[]{2, 1}, 
                        new int[]{1, 2})
        );
    }
    
    
    @Test
    @DisplayName("isSorted deve retornar true para array ordenado")
    void isSortedDeveRetornarTrueParaOrdenado() {
        assertTrue(bubbleSort.isSorted(new int[]{1, 2, 3, 4, 5}));
    }
    
    @Test
    @DisplayName("isSorted deve retornar false para array não ordenado")
    void isSortedDeveRetornarFalseParaNaoOrdenado() {
        assertFalse(bubbleSort.isSorted(new int[]{1, 3, 2, 4, 5}));
    }
    
    @Test
    @DisplayName("isSorted deve retornar true para array null")
    void isSortedDeveRetornarTrueParaNull() {
        assertTrue(bubbleSort.isSorted((int[]) null));
    }
    
    @Test
    @DisplayName("isSorted deve retornar true para array vazio")
    void isSortedDeveRetornarTrueParaVazio() {
        assertTrue(bubbleSort.isSorted(new int[]{}));
    }
    
    @Test
    @DisplayName("isSorted deve retornar true para array com um elemento")
    void isSortedDeveRetornarTrueParaUmElemento() {
        assertTrue(bubbleSort.isSorted(new int[]{42}));
    }
    
    
    @Test
    @DisplayName("Deve ordenar array genérico de Integers")
    void deveOrdenarArrayGenericoIntegers() {
        Integer[] array = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new Integer[]{11, 12, 22, 25, 34, 64, 90}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array genérico de Strings")
    void deveOrdenarArrayGenericoStrings() {
        String[] array = {"zebra", "apple", "mango", "banana"};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new String[]{"apple", "banana", "mango", "zebra"}, array);
    }
    
    @Test
    @DisplayName("Deve ordenar array genérico de Doubles")
    void deveOrdenarArrayGenericoDoubles() {
        Double[] array = {3.14, 1.41, 2.71, 0.5};
        bubbleSort.sort(array);
        
        assertTrue(bubbleSort.isSorted(array));
        assertArrayEquals(new Double[]{0.5, 1.41, 2.71, 3.14}, array);
    }
    
    
    @Test
    @DisplayName("Deve contar comparações corretamente para array pequeno")
    void deveContarComparacoesArrayPequeno() {
        int[] array = {3, 2, 1};
        int comparisons = bubbleSort.countComparisons(array);
        
        assertTrue(comparisons >= 3, "Deve fazer pelo menos 3 comparações");
    }
    
    @Test
    @DisplayName("Deve contar trocas corretamente")
    void deveContarTrocasCorretamente() {
        int[] array = {3, 2, 1};
        int swaps = bubbleSort.countSwaps(array);
        
        assertEquals(3, swaps);
    }
    
    @Test
    @DisplayName("Deve ter zero trocas para array já ordenado")
    void deveTermZeroTrocasArrayOrdenado() {
        int[] array = {1, 2, 3};
        int swaps = bubbleSort.countSwaps(array);
        
        assertEquals(0, swaps);
    }
    
    
    @Test
    @DisplayName("Bubble Sort é estável - mantém ordem relativa de elementos iguais")
    void bubbleSortEhEstavel() {
        int[] array = {3, 2, 3, 1};
        bubbleSort.sort(array);
        
        assertArrayEquals(new int[]{1, 2, 3, 3}, array);
    }
    
    
    @Test
    @DisplayName("Deve ordenar array grande em tempo razoável")
    void deveOrdenarArrayGrandeEmTempoRazoavel() {
        int[] array = new int[1000];
        Random random = new Random(42);
        
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10000);
        }
        
        long startTime = System.currentTimeMillis();
        bubbleSort.sort(array);
        long endTime = System.currentTimeMillis();
        
        assertTrue(bubbleSort.isSorted(array));
        
        long duration = endTime - startTime;
        assertTrue(duration < 5000, 
            "Ordenação demorou muito tempo: " + duration + "ms");
    }
    
    @Test
    @DisplayName("Melhor caso (já ordenado) deve ser mais rápido que pior caso")
    void melhorCasoDevSerMaisRapido() {
        int[] melhorCaso = new int[100];
        int[] piorCaso = new int[100];
        
        for (int i = 0; i < 100; i++) {
            melhorCaso[i] = i;
            piorCaso[i] = 99 - i;
        }
        
        int comparisonsMelhorCaso = bubbleSort.countComparisons(melhorCaso.clone());
        int comparisonsPiorCaso = bubbleSort.countComparisons(piorCaso.clone());
        
        assertTrue(comparisonsMelhorCaso < comparisonsPiorCaso,
            "Melhor caso deve fazer menos comparações que pior caso");
    }
}