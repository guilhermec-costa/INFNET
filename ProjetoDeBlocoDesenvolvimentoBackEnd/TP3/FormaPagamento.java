public class FormaPagamento {
    private Long id;
    private Long clienteId;
    private String tipo;
    private String descricao;
    private Boolean ativo;
    
    public FormaPagamento() {}
    
    public FormaPagamento(Long id, Long clienteId, String tipo, String descricao, Boolean ativo) {
        this.id = id;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.descricao = descricao;
        this.ativo = ativo;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    
    public String toCSV() {
        return id + "," + clienteId + "," + tipo + "," + descricao + "," + ativo;
    }
    
    public static FormaPagamento fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        FormaPagamento forma = new FormaPagamento();
        forma.setId(Long.parseLong(fields[0]));
        forma.setClienteId(Long.parseLong(fields[1]));
        forma.setTipo(fields[2]);
        forma.setDescricao(fields[3]);
        forma.setAtivo(Boolean.parseBoolean(fields[4]));
        return forma;
    }
}