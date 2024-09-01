package br.ufal.ic.p2.myfood;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import br.ufal.ic.p2.myfood.Exception.AtributoInvalidoException;
import br.ufal.ic.p2.myfood.Exception.AtributoNaoExisteException;
import br.ufal.ic.p2.myfood.Exception.CategoriaInvalidaException;
import br.ufal.ic.p2.myfood.Exception.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.myfood.Exception.NomeInvalidoException;
import br.ufal.ic.p2.myfood.Exception.ProdutoComEsseNomeJaExisteException;
import br.ufal.ic.p2.myfood.Exception.ProdutoNaoCadastradoException;
import br.ufal.ic.p2.myfood.Exception.ProdutoNaoEncontradoException;
import br.ufal.ic.p2.myfood.Exception.ValorInvalidoException;
import br.ufal.ic.p2.myfood.models.Produto;

public class ProdutoService {
    private Map<Integer, Produto> produtos = new HashMap<>();
    private int nextId = 1;

    public int criarProduto(int empresaId, String nome, float valor, String categoria) throws NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (valor < 0) {
            throw new ValorInvalidoException();
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new CategoriaInvalidaException();
        }

        boolean produtoExiste = produtos.values().stream()
            .anyMatch(p -> p.getNome().equals(nome) && p.getEmpresaId() == empresaId);

        if (produtoExiste) {
            throw new ProdutoComEsseNomeJaExisteException();
        }

        Produto produto = new Produto(nextId++, nome, valor, categoria, empresaId);
        produtos.put(produto.getId(), produto);
        return produto.getId();
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria) throws ProdutoNaoEncontradoException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoNaoCadastradoException {
        Produto produto = produtos.get(produtoId);

        if (produto == null) {
            throw new ProdutoNaoCadastradoException();
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (valor < 0) {
            throw new ValorInvalidoException();
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new CategoriaInvalidaException();
        }

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
    }

    public String getProdutoAtributo(int empresaId, String nome, String atributo) throws ProdutoNaoEncontradoException, AtributoInvalidoException, ProdutoNaoCadastradoException, AtributoNaoExisteException, EmpresaNaoCadastradaException {
    	Produto produto = produtos.values().stream()
            .filter(p -> p.getNome().equals(nome) && p.getEmpresaId() == empresaId)
            .findFirst()
            .orElse(null);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }

        switch (atributo.toLowerCase()) {
            case "nome":
                return produto.getNome();
            case "valor":
            	return String.format(Locale.US, "%.2f", produto.getValor());
            case "categoria":
                return produto.getCategoria();
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String listarProdutos(int empresaId) {
        return produtos.values().stream()
            .filter(p -> p.getEmpresaId() == empresaId)
            .map(Produto::getNome)
            .collect(Collectors.joining(", ", "{[", "]}"));
    }

    public Produto getProdutoById(int produtoId) throws ProdutoNaoEncontradoException {
        Produto produto = produtos.get(produtoId);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }
        return produto;
    }
}
