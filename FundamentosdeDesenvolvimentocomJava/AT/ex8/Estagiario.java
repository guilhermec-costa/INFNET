package ex8;

class Estagiario extends Funcionario {
    public Estagiario(String nome, double salarioBase) {
        super(nome, salarioBase);
    }
    
    @Override
    public double calcularSalario() {
        return salarioBase * 0.90;
    }
    
    @Override
    public void exibirInfo() {
        System.out.printf("Estagiário: %s | Salário Base: R$ %.2f | Salário Final (com desconto 10%%): R$ %.2f%n", 
                         nome, salarioBase, calcularSalario());
    }
}