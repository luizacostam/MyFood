package br.ufal.ic.p2.myfood.Exception;

public class EntregadorJaCadastradoException extends Exception {
    public EntregadorJaCadastradoException() {
        super("Entregador ja esta cadastrado nessa empresa");
    }
}
