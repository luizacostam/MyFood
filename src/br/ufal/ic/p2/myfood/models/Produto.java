package br.ufal.ic.p2.myfood.models;

public class Produto {
    private int id;
    private String nome;
    private float valor;
    private String categoria;
    private int empresaId;

    public Produto(int id, String nome, float valor, String categoria, int empresaId) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.empresaId = empresaId;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
