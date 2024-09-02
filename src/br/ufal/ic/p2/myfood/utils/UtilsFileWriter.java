package br.ufal.ic.p2.myfood.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufal.ic.p2.myfood.models.Cliente;
import br.ufal.ic.p2.myfood.models.Dono;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Produto;
import br.ufal.ic.p2.myfood.models.Usuario;

public class UtilsFileWriter {

    public static void criarPasta() {
        String caminho = "./database";

        File diretorio = new File(caminho);

        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
    }

    public static void escreverArquivo(String arquivo, String conteudo) {
        try{
        	criarPasta();
            File file = new File("./database/" + arquivo);
            file.createNewFile();

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(conteudo);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo " + arquivo);
        }
    }
    
    public static void salvarUsuario(Map<Integer, Usuario> usuarios) {
        StringBuilder usuariosData = new StringBuilder();
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getClass().equals(Cliente.class)) {
                Cliente cliente = (Cliente) usuario;
                usuariosData.append(cliente.getId()).append(":")
                            .append(cliente.getNome()).append(":")
                            .append(cliente.getEmail()).append(":")
                            .append(cliente.getSenha()).append(":")
                            .append(cliente.getEndereco()).append(";");
            } else if (usuario.getClass().equals(Dono.class)) {
                Dono dono = (Dono) usuario;
                usuariosData.append(dono.getId()).append(":")
                            .append(dono.getNome()).append(":")
                            .append(dono.getEmail()).append(":")
                            .append(dono.getSenha()).append(":")
                            .append(dono.getEndereco()).append(":")
                            .append(dono.getCpf()).append(";");
            }
        }
        escreverArquivo("usuarios.txt", usuariosData.toString());
    }
    
    public static void salvarRestaurante(List<Empresa> empresas) {
    	StringBuilder empresasData = new StringBuilder();
    	for (Empresa empresa : empresas) {
    		Empresa restaurante = (Empresa) empresa;
    		empresasData.append(restaurante.getId()).append(":")
    					.append(restaurante.getDono().getId()).append(":")
    					.append(restaurante.getNome()).append(":")
    					.append(restaurante.getEndereco()).append(":")
    					.append(restaurante.getTipoCozinha()).append(";");
    	}
    	escreverArquivo("empresas.txt", empresasData.toString());
    }
    
    public static void salvarProduto(List<Produto> produtos) {
    	StringBuilder produtosData = new StringBuilder();
    	for (Produto produto : produtos) {
    		Produto item = (Produto) produto;
    		produtosData.append(item.getEmpresaId()).append(":")
    		.append(item.getNome()).append(":")
    		.append(item.getValor()).append(":")
    		.append(item.getCategoria()).append(";");
    	}
    	escreverArquivo("produtos.txt", produtosData.toString());
    }
    
    public static void persistirDados(Map<Integer, Usuario> dados) {
        salvarUsuario(dados);
    }
    
    public static void persistirDados2(List<Empresa> dados) {
    	salvarRestaurante(dados);
    }
    
    public static void persistirDados3(List< Produto> dados) {
    	salvarProduto(dados);
    }
    
    
    public static void limparArquivos() throws IOException{
    	File file = new File("./database/usuarios.txt"); 
    	File fileEmpresas = new File("./database/empresas.txt");
    	File fileProdutos = new File("./database/produtos.txt");
    	if(file.delete() && fileEmpresas.delete() && fileProdutos.delete()){
    	    file.createNewFile();
    	    fileEmpresas.createNewFile();
    	    fileProdutos.createNewFile();
    	}else{
    	    throw new IOException();
    	}
	  } 	

}
