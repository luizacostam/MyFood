package br.ufal.ic.p2.myfood.Exception;

public class EntregaNaoExisteException extends Exception {
    public EntregaNaoExisteException() {
        super("Entrega nao existe");
    }
}
