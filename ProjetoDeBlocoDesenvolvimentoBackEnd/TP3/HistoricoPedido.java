import java.util.Date;

public class HistoricoPedido {
    private Long id;
    private Long pedidoId;
    private String statusAnterior;
    private String statusNovo;
    private Date dataAlteracao;
    private String observacao;
    
    public HistoricoPedido() {}
    
    public HistoricoPedido(Long id, Long pedidoId, String statusAnterior, String statusNovo, String observacao) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.dataAlteracao = new Date();
        this.observacao = observacao;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    
    public String getStatusAnterior() { return statusAnterior; }
    public void setStatusAnterior(String statusAnterior) { this.statusAnterior = statusAnterior; }
    
    public String getStatusNovo() { return statusNovo; }
    public void setStatusNovo(String statusNovo) { this.statusNovo = statusNovo; }
    
    public Date getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(Date dataAlteracao) { this.dataAlteracao = dataAlteracao; }
    
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    
    public String toCSV() {
        return id + "," + pedidoId + "," + 
               (statusAnterior != null ? statusAnterior : "") + "," + statusNovo + "," + 
               dataAlteracao.getTime() + "," + (observacao != null ? observacao : "");
    }
    
    public static HistoricoPedido fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        HistoricoPedido historico = new HistoricoPedido();
        historico.setId(Long.parseLong(fields[0]));
        historico.setPedidoId(Long.parseLong(fields[1]));
        historico.setStatusAnterior(fields[2].isEmpty() ? null : fields[2]);
        historico.setStatusNovo(fields[3]);
        historico.setDataAlteracao(new Date(Long.parseLong(fields[4])));
        historico.setObservacao(fields[5].isEmpty() ? null : fields[5]);
        return historico;
    }
}