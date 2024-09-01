package br.ufal.ic.p2.myfood.Exception;

public class PedidoNaoEstaAbertoException extends Exception {
	public PedidoNaoEstaAbertoException() {
		super("Nao existe pedido em aberto");
	}
}
