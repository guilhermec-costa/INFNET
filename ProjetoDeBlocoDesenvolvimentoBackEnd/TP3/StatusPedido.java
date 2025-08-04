import java.util.Date;;

public class StatusPedido {
    private Long id;
    private Long pedidoId;
    private String status;
    private Date dataStatus;
    
    public StatusPedido() {}
    
    public StatusPedido(Long id, Long pedidoId, String status) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.dataStatus = new Date();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getDataStatus() { return dataStatus; }
    public void setDataStatus(Date dataStatus) { this.dataStatus = dataStatus; }
    
    public String toCSV() {
        return id + "," + pedidoId + "," + status + "," + dataStatus.getTime();
    }
    
    public static StatusPedido fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(Long.parseLong(fields[0]));
        statusPedido.setPedidoId(Long.parseLong(fields[1]));
        statusPedido.setStatus(fields[2]);
        statusPedido.setDataStatus(new Date(Long.parseLong(fields[3])));
        return statusPedido;
    }
}