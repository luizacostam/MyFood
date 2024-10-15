package br.ufal.ic.p2.myfood.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import br.ufal.ic.p2.myfood.Sistema;
import br.ufal.ic.p2.myfood.Exception.CategoriaInvalidaException;
import br.ufal.ic.p2.myfood.Exception.ContaComEsseEmailJaExisteException;
import br.ufal.ic.p2.myfood.Exception.CpfInvalidoException;
import br.ufal.ic.p2.myfood.Exception.CpfNaoPodeSerNuloException;
import br.ufal.ic.p2.myfood.Exception.EmailInvalidoException;
import br.ufal.ic.p2.myfood.Exception.EmpresaComEsseNomeELocalJaExisteException;
import br.ufal.ic.p2.myfood.Exception.EmpresaComEsseNomeJaExisteException;
import br.ufal.ic.p2.myfood.Exception.EnderecoNaoPodeSerNuloException;
import br.ufal.ic.p2.myfood.Exception.FormatoDeEmailInvalidoException;
import br.ufal.ic.p2.myfood.Exception.NomeInvalidoException;
import br.ufal.ic.p2.myfood.Exception.NomeNaoPodeSerNuloException;
import br.ufal.ic.p2.myfood.Exception.ProdutoComEsseNomeJaExisteException;
import br.ufal.ic.p2.myfood.Exception.SenhaNaoPodeSerNulaException;
import br.ufal.ic.p2.myfood.Exception.UsuarioNaoPodeCriarUmaEmpresaException;
import br.ufal.ic.p2.myfood.Exception.ValorInvalidoException;
import br.ufal.ic.p2.myfood.models.Dono;
import br.ufal.ic.p2.myfood.models.Usuario;

public class UtilsFileReader {
	public static void lerArquivos(Sistema sistema) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
		lerArquivo("usuarios", sistema);
		lerArquivo("empresas", sistema);
		lerArquivo("produtos", sistema);
	}
	public static void lerArquivo(String arquivo, Sistema sistema) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
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
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo " + arquivo);
		}
	}

	private static void lerUsuarios(Sistema sistema, String[] dados) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException {
		String[] dados_atuais = null;
		String[] var3 = dados;
		int var4 = dados.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			String dado = var3[var5];
			dados_atuais = dado.split(":");
			String id = dados_atuais[0];
			String nome = dados_atuais[1];
			String email = dados_atuais[2];
			String senha = dados_atuais[3];
			String endereco = dados_atuais[4];

			if(nome.endsWith("Dono")) {
				String cpf = dados_atuais[5];
				sistema.criarUsuario(nome, email, senha, endereco, cpf);
			} else {
				sistema.criarUsuario(nome, email, senha, endereco);
			}
		}

	}

	private static void lerEmpresas(Sistema sistema, String[] dados) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException {
		String[] dados_atuais = null;
		String[] var3 = dados;
		int var4 = dados.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			String dado = var3[var5];
			dados_atuais = dado.split(":");
			String id = dados_atuais[0];
			String donoId = dados_atuais[1];
			String nome = dados_atuais[2];
			String endereco = dados_atuais[3];
			String tipoEmpresa = dados_atuais[4];

			int dono = Integer.valueOf(donoId);
			Usuario usuario = sistema.getUsuarioById(dono);

			if(usuario instanceof Dono) {
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
}