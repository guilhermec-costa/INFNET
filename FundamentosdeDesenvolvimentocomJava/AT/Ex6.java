public class Ex6 {
  static class Veiculo {
    private String placa;
    private String modelo;
    private int anoFabricacao;
    private double quilometragem;

    Veiculo(String placa, String modelo, int anoFabricacao, double quilometragem) {
      this.placa = placa;
      this.modelo = modelo;
      this.anoFabricacao = anoFabricacao;
      this.quilometragem = quilometragem;
    }

    public void exibirDetalhes() {
      System.out.println("===== DETALHES DO VEÍCULO =====");
      System.out.println("Placa: " + placa);
      System.out.println("Modelo: " + modelo);
      System.out.println("Ano de Fabricação: " + anoFabricacao);
      System.out.printf("Quilometragem: %.2f km%n", quilometragem);
      System.out.println("===============================");
    }

    public void registrarViagem(double km) {
      if (km > 0) {
        quilometragem += km;
        System.out.printf("Viagem de %.2f km registrada com sucesso!%n", km);
        System.out.printf("Nova quilometragem: %.2f km%n", quilometragem);
      } else {
        System.out.println("Erro: A quilometragem deve ser maior que zero.");
      }
    }
  }

  public static void main(String[] args) {
    Veiculo veiculo1 = new Veiculo("ABC-1234", "Honda Civic", 2020, 25000.0);
    Veiculo veiculo2 = new Veiculo("XYZ-9876", "Toyota Corolla", 2021, 18500.0);

    System.out.println("SISTEMA DE GERENCIAMENTO DE VEÍCULOS");
    System.out.println();

    veiculo1.exibirDetalhes();
    System.out.println();

    veiculo2.exibirDetalhes();
    System.out.println();

    System.out.println("REGISTRANDO VIAGENS:");
    System.out.println();

    System.out.println("Veículo 1 (" + veiculo1.modelo + "):");
    veiculo1.registrarViagem(150.5);
    veiculo1.registrarViagem(89.3);
    System.out.println();

    System.out.println("Veículo 2 (" + veiculo2.modelo + "):");
    veiculo2.registrarViagem(200.0);
    veiculo2.registrarViagem(75.8);
    System.out.println();

    System.out.println("DETALHES ATUALIZADOS:");
    System.out.println();
    veiculo1.exibirDetalhes();
    System.out.println();
    veiculo2.exibirDetalhes();
  }
}