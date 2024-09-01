package br.ufal.ic.p2.myfood.models;

import java.util.ArrayList;
import java.util.List;

import br.ufal.ic.p2.myfood.Exception.ProdutoInvalidoException;
import br.ufal.ic.p2.myfood.Exception.ProdutoNaoEncontradoException;

public class Pedido {
    private static int nextNumero = 1;

    private int numero;
    private String cliente;
    private String empresa;
    private String estado;
    private List<Produto> produtos;
    private float valor;

    public Pedido(String cliente, String empresa) {
        this.numero = nextNumero++;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = "aberto";
        this.produtos = new ArrayList<>();
        this.valor = 0;
    }

    public int getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public float getValor() {
        return valor;
    }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
        this.valor += produto.getValor();
    }

    public void removerProduto(String nomeProduto) throws ProdutoNaoEncontradoException, ProdutoInvalidoException {
    	if (nomeProduto == null || nomeProduto.isEmpty()) {
    		throw new ProdutoInvalidoException();
    	}
    	boolean produtoRemovido = false;
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equals(nomeProduto)) {
                this.valor -= produtos.get(i).getValor();
                produtos.remove(i);
                produtoRemovido = true;
                break;
            }
        }

        if (!produtoRemovido) {
            throw new ProdutoNaoEncontradoException();
        }
    }
}
