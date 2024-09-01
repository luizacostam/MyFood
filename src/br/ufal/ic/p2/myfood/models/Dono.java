package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.Exception.*;

import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Dono extends Usuario{
	private String cpf;
	private String endereco;

	public Dono(int id, String nome, String email, String senha, String endereco, String cpf) {
		super(id, nome, email, senha);
		this.cpf = cpf;
		this.endereco = endereco;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
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
            case "cpf" -> {
            	return this.getCpf();
            }
            case "endereco" -> {
            	return this.getEndereco();
            }
        }
        return atributo;
    }
}