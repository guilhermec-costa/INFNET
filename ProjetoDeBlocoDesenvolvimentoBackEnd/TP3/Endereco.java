public class Endereco {
    private Long id;
    private Long clienteId;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
    private String cidade;
    private String estado;
    
    public Endereco() {}
    
    public Endereco(Long id, Long clienteId, String logradouro, String numero, 
                   String cep, String cidade, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String toCSV() {
        return id + "," + clienteId + "," + logradouro + "," + numero + "," + 
               (complemento != null ? complemento : "") + "," + cep + "," + cidade + "," + estado;
    }
    
    public static Endereco fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        Endereco endereco = new Endereco();
        endereco.setId(Long.parseLong(fields[0]));
        endereco.setClienteId(Long.parseLong(fields[1]));
        endereco.setLogradouro(fields[2]);
        endereco.setNumero(fields[3]);
        endereco.setComplemento(fields[4].isEmpty() ? null : fields[4]);
        endereco.setCep(fields[5]);
        endereco.setCidade(fields[6]);
        endereco.setEstado(fields[7]);
        return endereco;
    }
}