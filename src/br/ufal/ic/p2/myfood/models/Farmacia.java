package br.ufal.ic.p2.myfood.models;

public class Farmacia extends Empresa {
    private boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia(int id, String nome, String endereco, Usuario dono, boolean aberto24Horas, int numeroFuncionarios) {
        super(id, nome, endereco, null, dono);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public boolean isAberto24Horas() {
        return aberto24Horas;
    }
    public void setAberto24Horas(boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
    }
    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }
    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

}
