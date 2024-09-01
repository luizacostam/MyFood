package br.ufal.ic.p2.myfood.Exception;

public class EmpresaComEsseNomeJaExisteException extends Exception {
	public EmpresaComEsseNomeJaExisteException() {
		super("Empresa com esse nome ja existe");
	}
}
