package br.ufal.ic.p2.myfood.Exception;

public class ProdutoComEsseNomeJaExisteException extends Exception {
	public ProdutoComEsseNomeJaExisteException() {
		super("Ja existe um produto com esse nome para essa empresa");
	}
}
