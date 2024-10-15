package br.ufal.ic.p2.myfood.models;

public class Entregador extends Usuario {
    private String endereco;
    private String veiculo;
    private String placa;

    public Entregador(int id, String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(id, nome, email, senha);
        this.endereco = endereco;
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getVeiculo() {
        return veiculo;
    }
    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAtributo(String atributo) {
        switch(atributo) {
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
            case "veiculo" -> {
                return this.getVeiculo();
            }
            case "placa" -> {
                return this.getPlaca();
            }
        }
        return atributo;
    }
}
