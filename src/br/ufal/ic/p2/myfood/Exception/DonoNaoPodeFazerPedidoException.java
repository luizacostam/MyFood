package br.ufal.ic.p2.myfood.Exception;

public class DonoNaoPodeFazerPedidoException extends Exception {
	public DonoNaoPodeFazerPedidoException() {
		super("Dono de empresa nao pode fazer um pedido");
	}
}
