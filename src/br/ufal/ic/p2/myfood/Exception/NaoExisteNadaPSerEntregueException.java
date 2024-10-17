package br.ufal.ic.p2.myfood.Exception;

public class NaoExisteNadaPSerEntregueException extends Exception {
    public NaoExisteNadaPSerEntregueException() {
        super("Nao existe nada para ser entregue com esse id");
    }
}
