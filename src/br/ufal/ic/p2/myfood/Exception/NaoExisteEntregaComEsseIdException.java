package br.ufal.ic.p2.myfood.Exception;

public class NaoExisteEntregaComEsseIdException extends Exception {
    public NaoExisteEntregaComEsseIdException() {
        super("Nao existe entrega com esse id");
    }
}
