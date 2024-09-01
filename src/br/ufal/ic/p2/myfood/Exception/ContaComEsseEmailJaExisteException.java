package br.ufal.ic.p2.myfood.Exception;

public class ContaComEsseEmailJaExisteException extends Exception {
	public ContaComEsseEmailJaExisteException() {
		super("Conta com esse email ja existe");
	}
}
