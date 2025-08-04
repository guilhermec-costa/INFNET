import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nome, senha;
        boolean senhaValida = false;
        
        System.out.print("Digite seu nome: ");
        nome = scanner.nextLine();
        
        while (!senhaValida) {
            System.out.print("Digite uma senha: ");
            senha = scanner.nextLine();
            
            String resultado = validarSenha(senha);
            if (resultado.equals("VÁLIDA")) {
                System.out.println("Senha cadastrada com sucesso para " + nome + "!");
                senhaValida = true;
            } else {
                System.out.println("Erro: " + resultado);
                System.out.println("Tente novamente.");
            }
        }
        
        scanner.close();
    }
    
    public static String validarSenha(String senha) {
        if (senha.length() < 8) {
            return "A senha deve ter no mínimo 8 caracteres.";
        }
        
        boolean temMaiuscula = false;
        boolean temNumero = false;
        boolean temEspecial = false;
        
        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            } else if (Character.isDigit(c)) {
                temNumero = true;
            } else if ("@#$%&*!?".indexOf(c) != -1) {
                temEspecial = true;
            }
        }
        
        if (!temMaiuscula) {
            return "A senha deve conter pelo menos uma letra maiúscula.";
        }
        if (!temNumero) {
            return "A senha deve conter pelo menos um número.";
        }
        if (!temEspecial) {
            return "A senha deve conter pelo menos um caractere especial (@, #, $, %, &, *, !, ?).";
        }
        
        return "VÁLIDA";
    }
}