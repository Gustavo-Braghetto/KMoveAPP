package com.example.kmoveteste.models;

public class Despesa {

    private int id;
    private int usuarioId;
    private String descricao;
    private double valor;
    private String data;

    public Despesa() {}


    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public String getData() { return data; }


    public void setId(int id) { this.id = id; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor) { this.valor = valor; }
    public void setData(String data) { this.data = data; }
}
