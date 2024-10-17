package br.ufal.ic.p2.myfood.Exception;

public class NaoExistePedidoParaEntregaException extends Exception {
    public NaoExistePedidoParaEntregaException() {
        super("Nao existe pedido para entrega");
    }
}
