package br.ufal.ic.p2.myfood.Exception;

public class NaoEPossivelAdicionarNoPedidoFechadoException extends Exception {
	public NaoEPossivelAdicionarNoPedidoFechadoException() {
		super("Nao e possivel adcionar produtos a um pedido fechado");
	}
}
