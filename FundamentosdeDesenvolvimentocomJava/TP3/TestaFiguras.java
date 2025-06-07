package TP3;

// EXERCÍCIO 12 - Classe de teste para as figuras geométricas
class TestaFiguras {
    public static void main(String[] args) {
        System.out.println("=== TESTE DE FIGURAS GEOMÉTRICAS ===\n");
        
        // Instanciando um círculo
        Circulo circulo = new Circulo();
        circulo.raio = 3.0;
        
        // Instanciando uma esfera
        Esfera esfera = new Esfera();
        esfera.raio = 5.0;
        
        // Testando cálculos do círculo
        System.out.println("--- TESTANDO CÍRCULO ---");
        circulo.exibirInformacoes();
        System.out.println("Área calculada: " + circulo.calcularArea());
        
        // Testando cálculos da esfera
        System.out.println("\n--- TESTANDO ESFERA ---");
        esfera.exibirInformacoes();
        System.out.println("Volume calculado: " + esfera.calcularVolume());
        
        // Teste adicional com valores diferentes
        System.out.println("\n--- TESTE ADICIONAL ---");
        Circulo circulo2 = new Circulo();
        circulo2.raio = 7.5;
        System.out.println("Círculo com raio 7.5 - Área: " + circulo2.calcularArea());
        
        Esfera esfera2 = new Esfera();
        esfera2.raio = 2.8;
        System.out.println("Esfera com raio 2.8 - Volume: " + esfera2.calcularVolume());
    }
}