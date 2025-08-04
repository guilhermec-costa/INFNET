package ex8;

class Funcionario {
    protected String nome;
    protected double salarioBase;
    
    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }
    
    public double calcularSalario() {
        return salarioBase;
    }
    
    public void exibirInfo() {
        System.out.printf("Funcionário: %s | Salário: R$ %.2f%n", nome, calcularSalario());
    }
}