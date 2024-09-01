package br.ufal.ic.p2.myfood;

import java.io.IOException;

import br.ufal.ic.p2.myfood.Exception.AtributoInvalidoException;
import br.ufal.ic.p2.myfood.Exception.AtributoNaoExisteException;
import br.ufal.ic.p2.myfood.Exception.CategoriaInvalidaException;
import br.ufal.ic.p2.myfood.Exception.ContaComEsseEmailJaExisteException;
import br.ufal.ic.p2.myfood.Exception.CpfInvalidoException;
import br.ufal.ic.p2.myfood.Exception.CpfNaoPodeSerNuloException;
import br.ufal.ic.p2.myfood.Exception.DonoNaoPodeFazerPedidoException;
import br.ufal.ic.p2.myfood.Exception.EmailInvalidoException;
import br.ufal.ic.p2.myfood.Exception.EmpresaComEsseNomeELocalJaExisteException;
import br.ufal.ic.p2.myfood.Exception.EmpresaComEsseNomeJaExisteException;
import br.ufal.ic.p2.myfood.Exception.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.myfood.Exception.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.myfood.Exception.EnderecoNaoPodeSerNuloException;
import br.ufal.ic.p2.myfood.Exception.FormatoDeEmailInvalidoException;
import br.ufal.ic.p2.myfood.Exception.IndiceInvalidoException;
import br.ufal.ic.p2.myfood.Exception.LoginOuSenhaInvalidosException;
import br.ufal.ic.p2.myfood.Exception.NaoEPossivelAdicionarNoPedidoFechadoException;
import br.ufal.ic.p2.myfood.Exception.NaoEPossivelRemoverDeUmPedidoFechadoException;
import br.ufal.ic.p2.myfood.Exception.NomeInvalidoException;
import br.ufal.ic.p2.myfood.Exception.NomeNaoPodeSerNuloException;
import br.ufal.ic.p2.myfood.Exception.PedidoJaExisteException;
import br.ufal.ic.p2.myfood.Exception.PedidoNaoEncontradoException;
import br.ufal.ic.p2.myfood.Exception.PedidoNaoEstaAbertoException;
import br.ufal.ic.p2.myfood.Exception.ProdutoComEsseNomeJaExisteException;
import br.ufal.ic.p2.myfood.Exception.ProdutoInvalidoException;
import br.ufal.ic.p2.myfood.Exception.ProdutoNaoCadastradoException;
import br.ufal.ic.p2.myfood.Exception.ProdutoNaoEncontradoException;
import br.ufal.ic.p2.myfood.Exception.ProdutoNaoEncontradoNaEmpresaException;
import br.ufal.ic.p2.myfood.Exception.ProdutoNaoPertenceAEssaEmpresaException;
import br.ufal.ic.p2.myfood.Exception.SenhaNaoPodeSerNulaException;
import br.ufal.ic.p2.myfood.Exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.myfood.Exception.UsuarioNaoPodeCriarUmaEmpresaException;
import br.ufal.ic.p2.myfood.Exception.ValorInvalidoException;
import easyaccept.EasyAccept;

public class Facade {
	private final Sistema sistema;
	 
	public Facade() throws NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException {
		this.sistema = new Sistema();
	}
	
	public void zerarSistema() throws IOException {
		this.sistema.zerarSistema();
	}
	
	public void encerrarSistema() {
		this.sistema.encerrarSistema();
	}
	
	public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException {
        return this.sistema.getAtributo(id, atributo);
    }
	
	public int criarUsuario(String nome, String email, String senha, String endereco) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException {
		return this.sistema.criarUsuario(nome, email, senha, endereco);
	}
	
	public int criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException {
		return this.sistema.criarUsuario(nome, email, senha, endereco, cpf);
	}
	
	public int login(String email, String senha) throws UsuarioNaoCadastradoException, LoginOuSenhaInvalidosException {
        return this.sistema.login(email, senha);
    }
	
	public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException {
		return this.sistema.criarEmpresa(tipoEmpresa, donoId, nome, endereco, tipoCozinha);
	}
	
	public String getEmpresasDoUsuario(int donoId) throws Exception {
		return this.sistema.getEmpresasDoUsuario(donoId);
	}
	
	public int getIdEmpresa(int donoId, String nome, int indice) throws Exception {
		return this.sistema.getIdEmpresa(donoId, nome, indice);
	}
	
	public String getAtributoEmpresa(int empresaId, String atributo) throws Exception {
		return this.sistema.getAtributoEmpresa(empresaId, atributo);
	}
	
	public int criarProduto(int empresaId, String nome, float valor, String categoria) throws NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
        return sistema.criarProduto(empresaId, nome, valor, categoria);
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria) throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoNaoEncontradoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoNaoCadastradoException {
        sistema.editarProduto(produtoId, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresaId, String atributo) throws ProdutoNaoEncontradoException, AtributoInvalidoException, ProdutoNaoCadastradoException, AtributoNaoExisteException, EmpresaNaoCadastradaException {
        return sistema.getProduto(empresaId, nome, atributo);
    }

    public String listarProdutos(int empresaId) throws EmpresaNaoEncontradaException {
        return sistema.listarProdutos(empresaId);
    }
    
    public int criarPedido(int clienteId, int empresaId) throws EmpresaNaoEncontradaException, PedidoJaExisteException, EmpresaNaoCadastradaException, DonoNaoPodeFazerPedidoException {
    	return sistema.criarPedido(clienteId, empresaId);
    }
    
    public int getNumeroPedido(int clienteId, int empresaId, int indice) throws PedidoNaoEncontradoException, IndiceInvalidoException, EmpresaNaoCadastradaException {
    	return sistema.getNumeroPedido(clienteId, empresaId, indice);
    }
    
    public void adicionarProduto(int numeroPedido, int produtoId) throws ProdutoNaoEncontradoNaEmpresaException, PedidoNaoEncontradoException, EmpresaNaoCadastradaException, ProdutoNaoEncontradoException, EmpresaNaoEncontradaException, PedidoNaoEstaAbertoException, ProdutoNaoPertenceAEssaEmpresaException, NaoEPossivelAdicionarNoPedidoFechadoException {
    	sistema.adicionarProduto(numeroPedido, produtoId);
    }
    
    public String getPedidos(int numeroPedido, String atributo) throws AtributoNaoExisteException, AtributoInvalidoException {
    	return sistema.getPedidos(numeroPedido, atributo);
    }
    
    public void fecharPedido(int numeroPedido) throws PedidoNaoEncontradoException {
    	sistema.fecharPedido(numeroPedido);
    }
    
    public void removerProduto(int numeroPedido, String nomeProduto) throws ProdutoNaoEncontradoException, ProdutoInvalidoException, NaoEPossivelRemoverDeUmPedidoFechadoException {
    	sistema.removerProduto(numeroPedido, nomeProduto);
    }
}
