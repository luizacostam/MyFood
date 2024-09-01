package br.ufal.ic.p2.myfood.Exception;

public class EmpresaComEsseNomeELocalJaExisteException extends Exception {
	public EmpresaComEsseNomeELocalJaExisteException() {
		super("Proibido cadastrar duas empresas com o mesmo nome e local");
	}
}
