package TP3;

// EXERCÍCIO 10 - Classe Círculo
class Circulo {
    double raio; // O raio é fundamental pois determina o tamanho do círculo
                 // e é usado em todos os cálculos relacionados (área, perímetro)
    
    // EXERCÍCIO 11 - Método para calcular área
    double calcularArea() {
        return Math.PI * (raio * raio);
    }
    
    void exibirInformacoes() {
        System.out.println("=== CÍRCULO ===");
        System.out.println("Raio: " + raio);
        System.out.println("Área: " + calcularArea());
        System.out.println("===============");
    }
}