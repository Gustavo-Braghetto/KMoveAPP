package com.example.kmoveteste.models;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;

    private double consumo;
    private double precoGasolina;
    private double precoAlcool;
    private double precoDiesel;

    public Usuario() {}


    public Usuario(int id, String nome, String email, String senha,
                   double consumo, double precoGasolina, double precoAlcool, double precoDiesel) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.consumo = consumo;
        this.precoGasolina = precoGasolina;
        this.precoAlcool = precoAlcool;
        this.precoDiesel = precoDiesel;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public double getConsumo() { return consumo; }
    public void setConsumo(double consumo) { this.consumo = consumo; }

    public double getPrecoGasolina() { return precoGasolina; }
    public void setPrecoGasolina(double precoGasolina) { this.precoGasolina = precoGasolina; }

    public double getPrecoAlcool() { return precoAlcool; }
    public void setPrecoAlcool(double precoAlcool) { this.precoAlcool = precoAlcool; }

    public double getPrecoDiesel() { return precoDiesel; }
    public void setPrecoDiesel(double precoDiesel) { this.precoDiesel = precoDiesel; }
}
