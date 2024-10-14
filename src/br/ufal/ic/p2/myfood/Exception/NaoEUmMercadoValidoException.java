package br.ufal.ic.p2.myfood.Exception;

public class NaoEUmMercadoValidoException extends Exception {
    public NaoEUmMercadoValidoException() {
        super("Nao e um mercado valido");
    }
}
