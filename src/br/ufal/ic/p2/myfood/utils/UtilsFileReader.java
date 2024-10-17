package br.ufal.ic.p2.myfood.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufal.ic.p2.myfood.EmpresaService;
import br.ufal.ic.p2.myfood.Exception.*;
import br.ufal.ic.p2.myfood.Sistema;
import br.ufal.ic.p2.myfood.models.*;

public class UtilsFileReader {
	public static void lerArquivos(Sistema sistema, EmpresaService empresaService) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
		lerArquivo("usuarios", empresaService, sistema);
		lerArquivo("empresas", empresaService, sistema);
		lerArquivo("produtos", empresaService, sistema);
		lerArquivo("pedidos", empresaService, sistema);
		lerArquivo("entregadores_por_empresa", empresaService, sistema);
	}
	public static void lerArquivo(String arquivo, EmpresaService empresaService, Sistema sistema) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
		File file = new File("./database/" + arquivo + ".txt");

		if (!file.exists()) return;

		String[] dados;
		String linha;

		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			while ((linha = br.readLine()) != null) {
				dados = linha.split(";");
				if(arquivo.equals("usuarios")) lerUsuarios(sistema, dados);
				if(arquivo.equals("empresas")) lerEmpresas(sistema, dados);
				if(arquivo.equals("produtos")) lerProdutos(sistema, dados);
				if(arquivo.equals("pedidos"))  lerPedidos(sistema, empresaService, dados);
				if(arquivo.equals("entregadores_por_empresa")) lerEntregadoresPorEmpresa(sistema);
			}
		} catch (IOException | HorarioInvalidoException | TipoDeMercadoInvalidoException |
                 FormatoDeHoraInvalidoException | EnderecoDaEmpresaInvalidoException | TipoDeEmpresaInvalidoException |
                 EmpresaNaoCadastradaException | PedidoJaExisteException | DonoNaoPodeFazerPedidoException |
                 EmpresaNaoEncontradaException e) {
				System.out.println("Erro ao ler o arquivo " + arquivo + ": " + e.getMessage());
				e.printStackTrace();
			} catch (ProdutoNaoEncontradoException e) {
            throw new RuntimeException(e);
        }

    }

	private static void lerUsuarios(Sistema sistema, String[] dados) {
		for (String dado : dados) {
			if (dado.isEmpty()) {
				continue;
			}

			String[] dadosAtuais = dado.split(":");

			if (dadosAtuais.length < 5) {
				System.out.println("Dados incompletos para usuário: " + dado);
				continue;
			}

			String id = dadosAtuais[0];
			String nome = dadosAtuais[1];
			String email = dadosAtuais[2];
			String senha = dadosAtuais[3];
			String endereco = dadosAtuais[4];

			try {
				if (dadosAtuais.length == 6) {
					// Caso do Dono - Se há 6 atributos, é um dono e o último é o CPF
					String cpf = dadosAtuais[5];
					sistema.criarUsuario(nome, email, senha, endereco, cpf);
				} else if (dadosAtuais.length == 7) {
					// Caso do Entregador - Se há 7 atributos, é um entregador e os últimos são veiculo e placa
					String veiculo = dadosAtuais[5];
					String placa = dadosAtuais[6];
					sistema.criarUsuario(nome, email, senha, endereco, veiculo, placa);
				} else {
					// Caso do Cliente - Se há 5 atributos, é um cliente
					sistema.criarUsuario(nome, email, senha, endereco);
				}
			} catch (Exception e) {
				System.out.println("Erro ao criar usuário: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}



	private static void lerEmpresas(Sistema sistema, String[] dados) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, HorarioInvalidoException, TipoDeMercadoInvalidoException, NomeInvalidoException, FormatoDeHoraInvalidoException, EnderecoDaEmpresaInvalidoException, TipoDeEmpresaInvalidoException {
		if (sistema.getUsuarios().isEmpty()) {
			return;
		}

		String[] dados_atuais = null;
		int var4 = dados.length;

		for (String dado : dados) {
			dados_atuais = dado.split(":");

			String id = dados_atuais[0];
			String donoId = dados_atuais[1];
			String nome = dados_atuais[2];
			String endereco = dados_atuais[3];
			String tipoEmpresa = dados_atuais[4];

			int dono = Integer.parseInt(donoId);
			Usuario usuario = sistema.getUsuarioById(dono);

			if (tipoEmpresa.equals("mercado")) {
				String abre = dados_atuais[5] + ":" + dados_atuais[6];
				String fecha = dados_atuais[7] + ":" + dados_atuais[8];
				String tipoMercado = dados_atuais[9];

				sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
			} else if (tipoEmpresa.equals("farmacia")) {
				String abre24Horas = dados_atuais[5];
				String numeroFuncionarios = dados_atuais[6];

				boolean abre24HorasBoolean = Boolean.parseBoolean(abre24Horas);
				int numeroFuncionariosInt = Integer.parseInt(numeroFuncionarios);

				sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre24HorasBoolean, numeroFuncionariosInt);
			}
			else if (usuario instanceof Dono) {
				sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoEmpresa);
			} else {
				throw new UsuarioNaoPodeCriarUmaEmpresaException();
			}
		}
	}

	private static void lerProdutos(Sistema sistema, String[] dados) throws NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
		String[] dados_atuais = null;
		String[] var3 = dados;
		int var4 = dados.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			String dado = var3[var5];
			dados_atuais = dado.split(":");
			String id = dados_atuais[0];
			String nome = dados_atuais[1];
			String valor = dados_atuais[2];
			String categoria = dados_atuais[3];

			int empresaId = Integer.parseInt(id);
			float preco = Float.parseFloat(valor);
			sistema.criarProduto(empresaId, nome, preco, categoria);

		}
	}

	private static void lerPedidos(Sistema sistema, EmpresaService empresaService, String[] dados) throws EmpresaNaoCadastradaException, PedidoJaExisteException, DonoNaoPodeFazerPedidoException, EmpresaNaoEncontradaException, ProdutoNaoEncontradoException {
		for (String dado : dados) {
			String[] dadosAtuais = dado.split(":");

			if (dadosAtuais.length < 3) {
				continue;
			}

			try {
				int numeroPedido = Integer.parseInt(dadosAtuais[0]);
				int clienteId = Integer.parseInt(dadosAtuais[1]);
				int empresaId = Integer.parseInt(dadosAtuais[2]);
				String estado = dadosAtuais[3];

				List<Produto> produtos = new ArrayList<>();

				if (dadosAtuais.length > 4) {
					String[] produtosIds = dadosAtuais[4].split(",");
					for (String produtoIdStr : produtosIds) {
						if (!produtoIdStr.isEmpty()) {
							int produtoId = Integer.parseInt(produtoIdStr);
							Produto produto = sistema.getProdutoById(produtoId);
							if (produto != null) {
								produtos.add(produto);
							}
						}
					}
				}

				Usuario cliente = sistema.getUsuarioById(clienteId);
				Empresa empresa = empresaService.getEmpresaById(empresaId);

				Pedido pedido = new Pedido(numeroPedido, cliente.getNome(), empresa.getNome(), estado, produtos);

				sistema.adicionarPedido(pedido);
			} catch (NumberFormatException e) {
				System.out.println("Erro ao converter um dos IDs do pedido: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private static void lerEntregadoresPorEmpresa(Sistema sistema) {
		File file = new File("./database/entregadores_por_empresa.txt");

		if (!file.exists()) {
			return;
		}

		String linha;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((linha = br.readLine()) != null) {
				String[] dados = linha.split(";");
				for (String dado : dados) {
					if (dado.isEmpty()) continue;

					String[] empresaEntregadores = dado.split(":");
					if (empresaEntregadores.length != 2) {
						System.out.println("Formato inválido para linha: " + dado);
						continue;
					}

					try {
						int empresaId = Integer.parseInt(empresaEntregadores[0]);
						String[] entregadoresIds = empresaEntregadores[1].split(",");

						Set<Integer> entregadoresSet = new HashSet<>();
						for (String entregadorIdStr : entregadoresIds) {
							if (!entregadorIdStr.trim().isEmpty()) {
								entregadoresSet.add(Integer.parseInt(entregadorIdStr.trim()));
							}
						}

						sistema.getEntregadoresPorEmpresa().put(empresaId, entregadoresSet);
					} catch (NumberFormatException e) {
						System.out.println("Erro ao converter um dos IDs do entregador: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo entregadores_por_empresa.txt: " + e.getMessage());
			e.printStackTrace();
		}
	}
}