import java.math.BigDecimal;

public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private Integer estoque;
    
    public Produto() {}
    
    public Produto(Long id, String nome, String descricao, BigDecimal preco, String categoria, Integer estoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.estoque = estoque;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    
    public String toCSV() {
        return id + "," + nome + "," + descricao + "," + preco + "," + categoria + "," + estoque;
    }
    
    public static Produto fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        Produto produto = new Produto();
        produto.setId(Long.parseLong(fields[0]));
        produto.setNome(fields[1]);
        produto.setDescricao(fields[2]);
        produto.setPreco(new BigDecimal(fields[3]));
        produto.setCategoria(fields[4]);
        produto.setEstoque(Integer.parseInt(fields[5]));
        return produto;
    }
}