package ex8;

class Gerente extends Funcionario {
    public Gerente(String nome, double salarioBase) {
        super(nome, salarioBase);
    }
    
    @Override
    public double calcularSalario() {
        return salarioBase * 1.20;
    }
    
    @Override
    public void exibirInfo() {
        System.out.printf("Gerente: %s | Salário Base: R$ %.2f | Salário Final (com bônus 20%%): R$ %.2f%n", 
                         nome, salarioBase, calcularSalario());
    }
}