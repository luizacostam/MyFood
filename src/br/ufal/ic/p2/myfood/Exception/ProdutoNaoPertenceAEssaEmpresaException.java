package br.ufal.ic.p2.myfood.Exception;

public class ProdutoNaoPertenceAEssaEmpresaException extends Exception {
	public ProdutoNaoPertenceAEssaEmpresaException() {
		super("O produto nao pertence a essa empresa");
	}
}
