package br.ufal.ic.p2.myfood.Exception;

public class NenhumPedidoDisponivelException extends RuntimeException {
    public NenhumPedidoDisponivelException() {
        super("Nenhum pedido disponivel");
    }
}
