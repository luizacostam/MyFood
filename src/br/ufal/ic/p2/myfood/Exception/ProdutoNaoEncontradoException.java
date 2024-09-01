package br.ufal.ic.p2.myfood.Exception;

public class ProdutoNaoEncontradoException extends Exception {
	public ProdutoNaoEncontradoException() {
		super("Produto nao encontrado");
	}
}
