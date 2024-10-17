package br.ufal.ic.p2.myfood.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufal.ic.p2.myfood.EmpresaService;
import br.ufal.ic.p2.myfood.Exception.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.myfood.Exception.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.myfood.Sistema;
import br.ufal.ic.p2.myfood.models.*;

public class UtilsFileWriter {
    private Sistema sistema;
    private EmpresaService empresaService;

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
            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                usuariosData.append(cliente.getId()).append(":")
                        .append(cliente.getNome()).append(":")
                        .append(cliente.getEmail()).append(":")
                        .append(cliente.getSenha()).append(":")
                        .append(cliente.getEndereco()).append(";");
            } else if (usuario instanceof Dono) {
                Dono dono = (Dono) usuario;
                usuariosData.append(dono.getId()).append(":")
                        .append(dono.getNome()).append(":")
                        .append(dono.getEmail()).append(":")
                        .append(dono.getSenha()).append(":")
                        .append(dono.getEndereco()).append(":")
                        .append(dono.getCpf()).append(";");
            } else if (usuario instanceof Entregador) {
                Entregador entregador = (Entregador) usuario;
                usuariosData.append(entregador.getId()).append(":")
                        .append(entregador.getNome()).append(":")
                        .append(entregador.getEmail()).append(":")
                        .append(entregador.getSenha()).append(":")
                        .append(entregador.getEndereco()).append(":")
                        .append(entregador.getVeiculo()).append(":")
                        .append(entregador.getPlaca()).append(";");
            }
        }
        escreverArquivo("usuarios.txt", usuariosData.toString());
    }


    public static void salvarRestaurante(List<Empresa> empresas) {
        StringBuilder empresasData = new StringBuilder();
        for (Empresa empresa : empresas) {
            if (empresa instanceof Mercado) {
                Mercado mercado = (Mercado) empresa;
                empresasData.append(mercado.getId()).append(":")
                        .append(mercado.getDono().getId()).append(":")
                        .append(mercado.getNome()).append(":")
                        .append(mercado.getEndereco()).append(":")
                        .append("mercado").append(":")
                        .append(mercado.getAbre()).append(":")
                        .append(mercado.getFecha()).append(":")
                        .append(mercado.getTipoMercado()).append(";");
            } else if (empresa instanceof Farmacia) {
                Farmacia farmacia = (Farmacia) empresa;
                empresasData.append(farmacia.getId()).append(":")
                        .append(farmacia.getDono().getId()).append(":")
                        .append(farmacia.getNome()).append(":")
                        .append(farmacia.getEndereco()).append(":")
                        .append("farmacia").append(":")
                        .append(farmacia.isAberto24Horas()).append(":")
                        .append(farmacia.getNumeroFuncionarios()).append(";");
            } else {
                empresasData.append(empresa.getId()).append(":")
                        .append(empresa.getDono().getId()).append(":")
                        .append(empresa.getNome()).append(":")
                        .append(empresa.getEndereco()).append(":")
                        .append(empresa.getTipoCozinha()).append(";");
            }
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

    public static void salvarPedido(Map<Integer, Pedido> pedidos, Sistema sistema, EmpresaService empresaService) {
        StringBuilder pedidosData = new StringBuilder();
        for (Pedido pedido : pedidos.values()) {
            int usuarioId;
            int empresaId;
            try {
                usuarioId = sistema.getUsuarioByNome(pedido.getCliente());
                empresaId = empresaService.getEmpresaByName(pedido.getEmpresa()).getId();
            } catch (UsuarioNaoCadastradoException | EmpresaNaoEncontradaException e) {
                System.out.println("Erro ao salvar pedido: " + e.getMessage());
                continue;
            }

            List<Integer> produtosIds = pedido.getProdutos().stream()
                    .map(Produto::getId)
                    .toList();

            String produtosIdsStr = produtosIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            pedidosData.append(pedido.getNumero()).append(":")
                    .append(usuarioId).append(":")
                    .append(empresaId).append(":")
                    .append(pedido.getEstado());

            if (!produtosIdsStr.isEmpty()) {
                pedidosData.append(":").append(produtosIdsStr);
            }

            pedidosData.append(";");
        }

        escreverArquivo("pedidos.txt", pedidosData.toString());
    }

    public static void salvarEntregadoresPorEmpresa(Map<Integer, Set<Integer>> entregadoresPorEmpresa) {
        StringBuilder entregadoresData = new StringBuilder();
        for (Map.Entry<Integer, Set<Integer>> entry : entregadoresPorEmpresa.entrySet()) {
            int empresaId = entry.getKey();
            Set<Integer> entregadores = entry.getValue();
            String entregadoresIds = entregadores.stream().map(String::valueOf).collect(Collectors.joining(","));
            entregadoresData.append(empresaId).append(":").append(entregadoresIds).append(";");
        }
        escreverArquivo("entregadores_por_empresa.txt", entregadoresData.toString());
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

    public static void persistirDados4(Map<Integer, Pedido> dados, Sistema sistema, EmpresaService empresaService) {
        salvarPedido(dados, sistema, empresaService);
    }


    public static void limparArquivos() throws IOException{
        Files.write(Paths.get("./database/usuarios.txt"), new byte[0]);
        Files.write(Paths.get("./database/empresas.txt"), new byte[0]);
        Files.write(Paths.get("./database/produtos.txt"), new byte[0]);
        Files.write(Paths.get("./database/pedidos.txt"), new byte[0]);
    }

}
