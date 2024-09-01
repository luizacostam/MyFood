package br.ufal.ic.p2.myfood.Exception;

public class PedidoJaExisteException extends Exception {
	public PedidoJaExisteException() {
		super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
	}
}
