import java.math.BigDecimal;
import java.util.Date;

public class Pedido {
    private Long id;
    private String numero;
    private Date dataPedido;
    private BigDecimal valorTotal;
    private Long clienteId;
    private Long enderecoId;
    private Long formaPagamentoId;
    
    public Pedido() {}
    
    public Pedido(Long id, String numero, Long clienteId, Long enderecoId, Long formaPagamentoId) {
        this.id = id;
        this.numero = numero;
        this.dataPedido = new Date();
        this.clienteId = clienteId;
        this.enderecoId = enderecoId;
        this.formaPagamentoId = formaPagamentoId;
        this.valorTotal = BigDecimal.ZERO;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    
    public Date getDataPedido() { return dataPedido; }
    public void setDataPedido(Date dataPedido) { this.dataPedido = dataPedido; }
    
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public Long getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Long enderecoId) { this.enderecoId = enderecoId; }
    
    public Long getFormaPagamentoId() { return formaPagamentoId; }
    public void setFormaPagamentoId(Long formaPagamentoId) { this.formaPagamentoId = formaPagamentoId; }
    
    public String toCSV() {
        return id + "," + numero + "," + dataPedido.getTime() + "," + valorTotal + "," + 
               clienteId + "," + enderecoId + "," + formaPagamentoId;
    }
    
    public static Pedido fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        Pedido pedido = new Pedido();
        pedido.setId(Long.parseLong(fields[0]));
        pedido.setNumero(fields[1]);
        pedido.setDataPedido(new Date(Long.parseLong(fields[2])));
        pedido.setValorTotal(new BigDecimal(fields[3]));
        pedido.setClienteId(Long.parseLong(fields[4]));
        pedido.setEnderecoId(Long.parseLong(fields[5]));
        pedido.setFormaPagamentoId(Long.parseLong(fields[6]));
        return pedido;
    }
}