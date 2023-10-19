package br.com.ufcg.model;
import java.time.LocalDate;

public abstract class Pagamento {
    private LocalDate data;
    private double valor;
    private String tipoPagamento;

    public Pagamento(LocalDate data, double valor){
        this.data = data;
        this.valor = valor;
    }

    public double getValor(){
        return this.valor;
    }

    public LocalDate getData(){
        return this.data;
    }

    public String getTipoPagamento(){
        return this.tipoPagamento;
    }
}
