package TP3;
// ========== EXERCÍCIO 1 - CONCEITOS BÁSICOS ==========

/*
EXPLICAÇÃO DOS CONCEITOS:

CLASSE: É um modelo ou "molde" que define as características (atributos) e 
comportamentos (métodos) que os objetos desse tipo terão. É como uma planta 
baixa de uma casa - define como ela será, mas não é a casa em si.

OBJETO: É uma instância de uma classe, ou seja, é a "materialização" da classe. 
Usando a analogia da casa, o objeto seria a casa construída baseada na planta baixa.

CAMPOS/ATRIBUTOS: São as características ou propriedades que um objeto possui. 
No exemplo de uma casa, seriam: cor, número de quartos, tamanho, etc.

MÉTODOS: São as ações ou comportamentos que um objeto pode executar. 
Continuando com a analogia da casa, seriam: acender luz, abrir porta, etc.
*/

// Exemplo prático dos conceitos:
class Carro {
    // CAMPOS (ATRIBUTOS) - características do carro
    String marca;
    String modelo;
    int ano;
    double velocidade;
    
    // MÉTODO - comportamento do carro
    void acelerar(double incremento) {
        velocidade += incremento;
        System.out.println("Acelerando... Velocidade atual: " + velocidade + " km/h");
    }
    
    void frear() {
        velocidade = 0;
        System.out.println("Freando... Carro parado.");
    }
}