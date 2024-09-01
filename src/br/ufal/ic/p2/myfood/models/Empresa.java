package br.ufal.ic.p2.myfood.models;

public class Empresa {
	private int id;
	private String nome;
	private String endereco;
	private String tipoCozinha;
	private Usuario dono;
	
	public Empresa(int id, String nome, String endereco, String tipoCozinha, Usuario dono) {
		this.setId(id);
		this.setNome(nome);
		this.endereco = endereco;
		this.tipoCozinha = tipoCozinha;
		this.dono = dono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTipoCozinha() {
		return tipoCozinha;
	}

	public void setTipoCozinha(String tipoCozinha) {
		this.tipoCozinha = tipoCozinha;
	}
	
	public Usuario getDono() {
		return dono;
	}
	
}
