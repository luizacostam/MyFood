package br.ufal.ic.p2.myfood.models;

public class Cliente extends Usuario {
    private String endereco;
    
    public Cliente(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha);
        this.endereco = endereco;
    }
    
    public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
    
    @Override
    public String getAtributo(String atributo) {
    	switch (atributo) {
	        case "nome" -> {
	            return this.getNome();
	        }
	        case "email" -> {
	            return this.getEmail();
	        }
	        case "senha" -> {
	            return this.getSenha();
	        }
	        case "endereco" -> {
	        	return this.getEndereco();
	        }
	    }
	    return atributo;
    }
}