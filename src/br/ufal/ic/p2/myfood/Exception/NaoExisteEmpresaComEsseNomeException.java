package br.ufal.ic.p2.myfood.Exception;

public class NaoExisteEmpresaComEsseNomeException extends Exception {
	public NaoExisteEmpresaComEsseNomeException() {
		super("Nao existe empresa com esse nome");
	}
}
