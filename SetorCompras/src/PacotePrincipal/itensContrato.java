package PacotePrincipal;

public class itensContrato {
    private Integer item;
    private String especificacao;
    private String medida;
    private Integer quantidadeItem;
    private Double valorUnitario;
    private Double valorTotal;
    
    public itensContrato(Integer item, String especificacao, String medida, Integer quantidadeItem, Double valorUnitario, Double valorTotal) {
        this.item = item;
        this.especificacao = especificacao;
        this.medida = medida;
        this.quantidadeItem = quantidadeItem;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }
    
    // Getters e Setters
    public Integer getItem() {
        return item;
    }
    
    public void setItem(Integer item) {
        this.item = item;
    }
    
    public String getEspecificacao() {
        return especificacao;
    }
    
    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }
    
    public String getMedida() {
        return medida;
    }
    
    public void setMedida(String medida) {
        this.medida = medida;
    }
    
    public Integer getQuantidadeItem() {
        return quantidadeItem;
    }
    
    public void setQuantidadeItem(Integer quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }
    
    public Double getValorUnitario() {
        return valorUnitario;
    }
    
    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    public Double getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
