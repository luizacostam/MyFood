package br.ufal.ic.p2.myfood.Exception;

public class ProdutoNaoEncontradoNaEmpresaException extends Exception {
	public ProdutoNaoEncontradoNaEmpresaException() {
		super("O produto nao pertence a essa empresa");
	}
}
