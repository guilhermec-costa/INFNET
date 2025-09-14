package com.infnet.TP3;

public class Main {
    public static void main(String[] args) {
        StatelessApiExercises statelessApi = new StatelessApiExercises();
        SimpleApiExercises simpleApi = new SimpleApiExercises();

        try {
            statelessApi.exercicio1();
            statelessApi.exercicio2();
            statelessApi.exercicio3();
            statelessApi.exercicio4();
            statelessApi.exercicio5();
            statelessApi.exercicio6();
            statelessApi.exercicio7();
            statelessApi.exercicio8();
            statelessApi.exercicio9();
            statelessApi.exercicio10();
            statelessApi.exercicio11();

            simpleApi.exercicio12();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro durante a execução dos exercícios: " + e.getMessage());
            e.printStackTrace();
        }
    }
}