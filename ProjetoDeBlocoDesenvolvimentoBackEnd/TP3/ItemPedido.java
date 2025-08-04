import java.math.BigDecimal;

public class ItemPedido {
    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    
    public ItemPedido() {}
    
    public ItemPedido(Long id, Long pedidoId, Long produtoId, Integer quantidade, BigDecimal precoUnitario) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    
    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(new BigDecimal(quantidade));
    }
    
    public String toCSV() {
        return id + "," + pedidoId + "," + produtoId + "," + quantidade + "," + precoUnitario;
    }
    
    public static ItemPedido fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        ItemPedido item = new ItemPedido();
        item.setId(Long.parseLong(fields[0]));
        item.setPedidoId(Long.parseLong(fields[1]));
        item.setProdutoId(Long.parseLong(fields[2]));
        item.setQuantidade(Integer.parseInt(fields[3]));
        item.setPrecoUnitario(new BigDecimal(fields[4]));
        return item;
    }
}