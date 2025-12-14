package com.example.kmoveteste.models;

public class Corrida {

    private int id;
    private int usuarioId;
    private String origem;
    private String destino;
    private double valor;
    private double km;
    private String data;
    private String tipoCombustivel;

    public Corrida() {}


    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public double getValor() { return valor; }
    public double getKm() { return km; }
    public String getData() { return data; }
    public String getTipoCombustivel() { return tipoCombustivel; } // NOVO


    public void setId(int id) { this.id = id; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setOrigem(String origem) { this.origem = origem; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setValor(double valor) { this.valor = valor; }
    public void setKm(double km) { this.km = km; }
    public void setData(String data) { this.data = data; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; } // NOVO
}
