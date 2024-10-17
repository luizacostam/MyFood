package br.ufal.ic.p2.myfood.Exception;

public class NaoEUmEntregadorValidoException extends Exception {
    public NaoEUmEntregadorValidoException() {
        super("Nao e um entregador valido");
    }
}
