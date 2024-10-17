package br.ufal.ic.p2.myfood.Exception;

public class ProdutoNaoEstaSendoPreparadoException extends Exception {
    public ProdutoNaoEstaSendoPreparadoException() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }
}
