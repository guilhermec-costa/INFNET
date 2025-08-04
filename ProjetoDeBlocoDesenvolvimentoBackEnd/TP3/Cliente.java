import java.util.*;

public class Cliente {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Date dataCadastro;
    
    public Cliente() {}
    
    public Cliente(Long id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = new Date();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }
    
    public String toCSV() {
        return id + "," + nome + "," + email + "," + telefone + "," + dataCadastro.getTime();
    }
    
    public static Cliente fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        Cliente cliente = new Cliente();
        cliente.setId(Long.parseLong(fields[0]));
        cliente.setNome(fields[1]);
        cliente.setEmail(fields[2]);
        cliente.setTelefone(fields[3]);
        cliente.setDataCadastro(new Date(Long.parseLong(fields[4])));
        return cliente;
    }
}