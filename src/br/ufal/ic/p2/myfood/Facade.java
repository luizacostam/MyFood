package br.ufal.ic.p2.myfood;

import java.io.IOException;
import java.util.List;

import br.ufal.ic.p2.myfood.Exception.*;
import easyaccept.EasyAccept;

public class Facade {
	private final Sistema sistema;
	 
	public Facade() throws NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
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

	public int criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws ContaComEsseEmailJaExisteException, NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SenhaNaoPodeSerNulaException, EmailInvalidoException, FormatoDeEmailInvalidoException, VeiculoInvalidoException, PlacaInvalidoException {
		return this.sistema.criarUsuario(nome, email, senha, endereco, veiculo, placa);
	}
	
	public int login(String email, String senha) throws UsuarioNaoCadastradoException, LoginOuSenhaInvalidosException {
        return this.sistema.login(email, senha);
    }

	public void cadastrarEntregador(int empresaId, int entregadorId) throws EmpresaNaoCadastradaException, UsuarioNaoEUmEntregadorException, EntregadorJaCadastradoException {
        this.sistema.cadastrarEntregador(empresaId, entregadorId);
    }

	public String getEntregadores(int empresaId) throws EmpresaNaoCadastradaException {
		return this.sistema.getEntregadores(empresaId);
	}

	public String getEmpresas(int entregadorId) throws UsuarioNaoEUmEntregadorException {
		return this.sistema.getEmpresas(entregadorId);
	}
	
	public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException {
		return this.sistema.criarEmpresa(tipoEmpresa, donoId, nome, endereco, tipoCozinha);
	}

	public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String abre, String fecha, String tipoMercado) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, FormatoDeHoraInvalidoException, HorarioInvalidoException, TipoDeEmpresaInvalidoException, NomeInvalidoException, EnderecoDaEmpresaInvalidoException, TipoDeMercadoInvalidoException {
		return this.sistema.criarEmpresa(tipoEmpresa, donoId, nome, endereco, abre, fecha, tipoMercado);
	}

	public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws UsuarioNaoPodeCriarUmaEmpresaException, NomeInvalidoException, EmpresaComEsseNomeELocalJaExisteException, EnderecoDaEmpresaInvalidoException, TipoDeEmpresaInvalidoException, EmpresaComEsseNomeJaExisteException {
		return this.sistema.criarEmpresa(tipoEmpresa, donoId, nome, endereco, aberto24Horas, numeroFuncionarios);
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

	public void alterarFuncionamento(int mercado, String abre, String fecha) throws HorarioInvalidoException, EmpresaNaoCadastradaException, FormatoDeHoraInvalidoException, NaoEUmMercadoValidoException {
		sistema.alterarFuncionamento(mercado, abre, fecha);
	}
}
