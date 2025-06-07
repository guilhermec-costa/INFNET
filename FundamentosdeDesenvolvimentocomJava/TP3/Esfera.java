package TP3;

// EXERCÍCIO 10 - Classe Esfera
class Esfera {
    double raio; // O raio é fundamental pois determina o tamanho da esfera
                 // e é usado para calcular volume e área superficial
    
    // EXERCÍCIO 11 - Método para calcular volume
    double calcularVolume() {
        return (4.0 / 3.0) * Math.PI * (raio * raio * raio);
    }
    
    void exibirInformacoes() {
        System.out.println("=== ESFERA ===");
        System.out.println("Raio: " + raio);
        System.out.println("Volume: " + calcularVolume());
        System.out.println("==============");
    }
}