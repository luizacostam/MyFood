package br.ufal.ic.p2.myfood.Exception;

public class NaoEPossivelRemoverDeUmPedidoFechadoException extends Exception {
	public NaoEPossivelRemoverDeUmPedidoFechadoException() {
		super("Nao e possivel remover produtos de um pedido fechado");
	}
}
