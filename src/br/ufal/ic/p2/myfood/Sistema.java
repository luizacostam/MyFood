package br.ufal.ic.p2.myfood;

import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;

import br.ufal.ic.p2.myfood.Exception.*;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.utils.UtilsFileReader;
import br.ufal.ic.p2.myfood.utils.UtilsFileWriter;

public class Sistema {
	private Map<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();
	private Map<Integer, Pedido> pedidos = new HashMap<Integer, Pedido>();
    private Map<Integer, Set<Integer>> entregadoresPorEmpresa;
    private Map<Integer, Entrega> entregas = new HashMap<>();
    private Integer nextId = 1;
	private EmpresaService empresaService;
	private ProdutoService produtoService;
	
	 
	public Sistema() throws NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException{
        this.usuarios = new HashMap<>();
        this.produtoService = new ProdutoService();
		this.empresaService = new EmpresaService();
        this.pedidos = new HashMap<>();
        this.entregadoresPorEmpresa = new HashMap<>();
		UtilsFileWriter.criarPasta();
		UtilsFileReader.lerArquivos(this, empresaService);
	}
	
	public void zerarSistema() throws IOException {
        this.usuarios = new HashMap<>();
        this.nextId = 1;
        this.empresaService.zerarEmpresas();
	    this.produtoService.zerarProdutos(produtoService.getProdutos());
        this.pedidos = new HashMap<>();
        this.entregadoresPorEmpresa = new HashMap<>();
        UtilsFileWriter.limparArquivos();
    }
	
	public void encerrarSistema() {
		UtilsFileWriter.criarPasta();
		UtilsFileWriter.persistirDados(this.usuarios);
		UtilsFileWriter.persistirDados2(this.empresaService.getEmpresas());
		UtilsFileWriter.persistirDados3(this.produtoService.getProdutos());
        UtilsFileWriter.persistirDados4(this.pedidos,this, empresaService);
        UtilsFileWriter.salvarEntregadoresPorEmpresa(this.entregadoresPorEmpresa);
	}
	
	public Integer criarUsuario(String nome, String email, String senha, String endereco) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException{
		if (nome == null || nome.isEmpty()) {
			throw new NomeNaoPodeSerNuloException();
		}
		if (endereco == null || endereco.isEmpty()) {
			throw new EnderecoNaoPodeSerNuloException();
		}
		if (senha == null || senha.isEmpty()) {
			throw new SenhaNaoPodeSerNulaException();
		}
		validarEmail(email);
		if (usuarios.values().stream().anyMatch(u -> u.getEmail().equals(email))) {
            throw new ContaComEsseEmailJaExisteException();
        }
		Integer currId = nextId++;
		Cliente cliente = new Cliente(currId, nome, email, senha, endereco);
		usuarios.put(currId, cliente);
		
		return currId;
	}
	
	public Integer criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EnderecoNaoPodeSerNuloException, NomeNaoPodeSerNuloException, CpfNaoPodeSerNuloException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, SenhaNaoPodeSerNulaException, CpfInvalidoException {
		if (nome == null || nome.isEmpty()) {
			throw new NomeNaoPodeSerNuloException();
		}
		if (endereco == null || endereco.isEmpty()) {
			throw new EnderecoNaoPodeSerNuloException();
		}
		if (cpf == null || cpf.isEmpty()) {
			throw new CpfNaoPodeSerNuloException();
		} 
		validarCpf(cpf);
		if (senha == null || senha.isEmpty()) {
			throw new SenhaNaoPodeSerNulaException();
		}
		validarEmail(email);
		if (usuarios.values().stream().anyMatch(u -> u.getEmail().equals(email))) {
            throw new ContaComEsseEmailJaExisteException();
        }
		Integer currId = nextId++;
		Dono dono = new Dono(currId, nome, email, senha, endereco, cpf);
		usuarios.put(currId, dono);
		
		return currId;
	}

    public Integer criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws NomeNaoPodeSerNuloException, EnderecoNaoPodeSerNuloException, SenhaNaoPodeSerNulaException, ContaComEsseEmailJaExisteException, EmailInvalidoException, FormatoDeEmailInvalidoException, VeiculoInvalidoException, PlacaInvalidoException {
        if (nome == null || nome.isEmpty()) {
            throw new NomeNaoPodeSerNuloException();
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoNaoPodeSerNuloException();
        }
        validarEmail(email);
        if (senha == null || senha.isEmpty()) {
            throw new SenhaNaoPodeSerNulaException();
        }
        if (veiculo == null || veiculo.isEmpty()) {
            throw new VeiculoInvalidoException();
        }
        if (placa == null || placa.isEmpty()) {
            throw new PlacaInvalidoException();
        }
        if (usuarios.values().stream().anyMatch(u -> u.getEmail().equals(email))) {
            throw new ContaComEsseEmailJaExisteException();
        }
        Integer currId = nextId++;
        Entregador entregador = new Entregador(currId, nome, email, senha, endereco, veiculo, placa);
        usuarios.put(currId, entregador);
        return currId;
    }
	
	private void validarEmail(String email) throws EmailInvalidoException, FormatoDeEmailInvalidoException {
		if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new EmailInvalidoException();
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            throw new FormatoDeEmailInvalidoException();
        }
    }
	
	private void validarCpf(String cpf) throws CpfInvalidoException {
	    if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
	        throw new CpfInvalidoException();
	    }
	}
	
	public Usuario getUsuarioById(int id) {
		return this.usuarios.get(id);
	}

    public int getUsuarioByNome(String nome) throws UsuarioNaoCadastradoException {
        Map <Integer, Usuario> usuarios = this.usuarios;
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getNome().equals(nome)) {
                return usuario.getId();
            }
        }
        throw new UsuarioNaoCadastradoException();
    }


    public Integer login(String email, String senha) throws LoginOuSenhaInvalidosException {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
            	Integer id = usuario.getId();
            	if (id != null) {
            		return id;
            	} else {
            		throw new LoginOuSenhaInvalidosException();
            	}
            }
        }
        throw new LoginOuSenhaInvalidosException();
    }

    public void cadastrarEntregador(int empresaId, int entregadorId) throws EmpresaNaoCadastradaException, EntregadorJaCadastradoException, UsuarioNaoEUmEntregadorException {
        Empresa empresa = empresaService.getEmpresaById(empresaId);

        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }

        Usuario entregador = usuarios.get(entregadorId);

        if (entregador == null || !(entregador instanceof Entregador)) {
            throw new UsuarioNaoEUmEntregadorException();
        }

        entregadoresPorEmpresa.putIfAbsent(empresaId, new HashSet<>());

        Set<Integer> entregadores = entregadoresPorEmpresa.get(empresaId);

        if (entregadores.contains(entregadorId)) {
            throw new EntregadorJaCadastradoException();
        }

        entregadores.add(entregadorId);
    }

    public String getEntregadores(int empresaId) throws EmpresaNaoCadastradaException {
        Empresa empresa = empresaService.getEmpresaById(empresaId);
        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }

        Set<Integer> entregadoresIds = entregadoresPorEmpresa.get(empresaId);
        if (entregadoresIds == null || entregadoresIds.isEmpty()) {
            return "{[]}";
        }

        List<String> emails = new ArrayList<>();

        for (Integer entregadorId : entregadoresIds) {
            Usuario entregador = usuarios.get(entregadorId);
            if (entregador instanceof Entregador) {
                emails.add(entregador.getEmail());
            }
        }

        StringBuilder sb = new StringBuilder("{[");
        for (int i = 0; i < emails.size(); i++) {
            sb.append(emails.get(i));
            if (i < emails.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");

        return sb.toString();
    }

    public String getAtributo(int id, String atributo) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.getAtributo(atributo);
    }
	
	public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException {
        Usuario dono = usuarios.get(donoId);
		return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String abre, String fecha, String tipoMercado) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, FormatoDeHoraInvalidoException, HorarioInvalidoException, TipoDeEmpresaInvalidoException, NomeInvalidoException, EnderecoDaEmpresaInvalidoException, TipoDeMercadoInvalidoException {
        Usuario dono = usuarios.get(donoId);
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws UsuarioNaoPodeCriarUmaEmpresaException, NomeInvalidoException, EmpresaComEsseNomeELocalJaExisteException, EnderecoDaEmpresaInvalidoException, TipoDeEmpresaInvalidoException, EmpresaComEsseNomeJaExisteException {
        Usuario dono = usuarios.get(donoId);
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public String getEmpresas(int entregadorId) throws UsuarioNaoEUmEntregadorException, EmpresaNaoCadastradaException {
        Usuario entregador = usuarios.get(entregadorId);
        if (entregador == null || !(entregador instanceof Entregador)) {
            throw new UsuarioNaoEUmEntregadorException();
        }

        List<String> empresasVinculadas = new ArrayList<>();

        for (Map.Entry<Integer, Set<Integer>> entry : entregadoresPorEmpresa.entrySet()) {
            Integer empresaId = entry.getKey();
            Set<Integer> entregadores = entry.getValue();

            if (entregadores.contains(entregadorId)) {
                Empresa empresa = empresaService.getEmpresaById(empresaId);
                if (empresa != null) {
                    String nomeEEndereco = "[" + empresa.getNome() + ", " + empresa.getEndereco() + "]";
                    empresasVinculadas.add(nomeEEndereco);
                }
            }
        }

        if (empresasVinculadas.isEmpty()) {
            return "{[]}";
        }

        return "{[" + String.join(", ", empresasVinculadas) + "]}";
    }

    public String getEmpresasDoUsuario(int donoId) throws Exception {
	    Usuario dono = usuarios.get(donoId);
	    List<Empresa> empresas = empresaService.getEmpresasDoUsuario(dono);
	    StringBuilder sb = new StringBuilder("{[");
	    for (int i = 0; i < empresas.size(); i++) {
	        Empresa empresa = empresas.get(i);
	        sb.append("[").append(empresa.getNome()).append(", ").append(empresa.getEndereco()).append("]");
	        if (i < empresas.size() - 1) {
	            sb.append(", ");
	        }
	    }
	    sb.append("]}");
	    return sb.toString();
	}
	
	public int getIdEmpresa(int donoId, String nome, int indice) throws IndiceInvalidoException, UsuarioNaoPodeCriarUmaEmpresaException, IndiceMaiorQueOEsperadoException, NomeInvalidoException, NaoExisteEmpresaComEsseNomeException {
	    Usuario dono = usuarios.get(donoId);
	    int idEmpresa = empresaService.getIdEmpresa(dono, nome, indice);
	    if (nome == null || nome.isEmpty()) {
	        throw new NomeInvalidoException();
	    }
	    if (indice < 0) {
	    	throw new IndiceInvalidoException();
	    }
	    return idEmpresa;
	}

    public Map<Integer, Usuario> getUsuarios() {
        return this.usuarios;
    }

    public String getAtributoEmpresa(int empresaId, String atributo) throws AtributoInvalidoException, EmpresaNaoCadastradaException {
    	if (!empresaService.empresaExiste(empresaId)) {
            throw new EmpresaNaoCadastradaException();
        }
    	if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }
        return empresaService.getAtributoEmpresa(empresaId, atributo);
    }
    
    public int criarProduto(int empresaId, String nome, float valor, String categoria) throws NomeInvalidoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoComEsseNomeJaExisteException {
        return produtoService.criarProduto(empresaId, nome, valor, categoria);
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria) throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoNaoEncontradoException, ValorInvalidoException, CategoriaInvalidaException, ProdutoNaoCadastradoException {
        produtoService.editarProduto(produtoId, nome, valor, categoria);
    }

    public String getProduto(int empresaId, String nome, String atributo) throws ProdutoNaoEncontradoException, AtributoInvalidoException, ProdutoNaoCadastradoException, AtributoNaoExisteException, EmpresaNaoCadastradaException {
    	if (atributo.equals("empresa")) {
    		Empresa empresa = empresaService.getEmpresaById(empresaId);
    		return empresa.getNome();
    	}
    	return produtoService.getProdutoAtributo(empresaId, nome, atributo);
    }

    public String listarProdutos(int empresaId) throws EmpresaNaoEncontradaException {
    	if (!empresaService.empresaExiste(empresaId)) {
            throw new EmpresaNaoEncontradaException();
        }
        return produtoService.listarProdutos(empresaId);
    }
    
    public int criarPedido(int clienteId, int empresaId) throws EmpresaNaoEncontradaException, PedidoJaExisteException, EmpresaNaoCadastradaException, DonoNaoPodeFazerPedidoException {
        Usuario cliente = usuarios.get(clienteId);
        if (cliente.getClass().equals(Dono.class)) {
        	throw new DonoNaoPodeFazerPedidoException();
        }
        	
        Empresa empresa = empresaService.getEmpresaById(empresaId);

        if (empresa == null) {
            throw new EmpresaNaoEncontradaException();
        }

        for (Pedido pedido : pedidos.values()) {
            if (pedido.getCliente().equals(cliente.getNome()) && pedido.getEmpresa().equals(empresa.getNome()) && pedido.getEstado().equals("aberto")) {
                throw new PedidoJaExisteException();
            }
        }

        Pedido novoPedido = new Pedido(cliente.getNome(), empresa.getNome());
        novoPedido.setEstado("aberto");
        pedidos.put(novoPedido.getNumero(), novoPedido);
        return novoPedido.getNumero();
    }

    public void adicionarPedido(Pedido pedido) {
        this.pedidos.put(pedido.getNumero(), pedido);
    }


    public int getNumeroPedido(int clienteId, int empresaId, int indice)
            throws PedidoNaoEncontradoException, IndiceInvalidoException, EmpresaNaoCadastradaException, UsuarioNaoCadastradoException {

        Usuario cliente = usuarios.get(clienteId);
        if (cliente == null) {
            throw new UsuarioNaoCadastradoException();
        }

        Empresa empresa = empresaService.getEmpresaById(empresaId);
        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }
        List<Pedido> pedidosDoCliente = pedidos.values().stream()
                .filter(p -> p.getCliente().equals(cliente.getNome()) && p.getEmpresa().equals(empresa.getNome()))
                .sorted(Comparator.comparingInt(Pedido::getNumero))
                .toList();

        if (pedidosDoCliente.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }
        if (indice < 0 || indice >= pedidosDoCliente.size()) {
            throw new IndiceInvalidoException();
        }

        return pedidosDoCliente.get(indice).getNumero();
    }


    public void adicionarProduto(int numeroPedido, int produtoId) throws ProdutoNaoEncontradoNaEmpresaException, EmpresaNaoCadastradaException, ProdutoNaoEncontradoException, EmpresaNaoEncontradaException, PedidoNaoEstaAbertoException, ProdutoNaoPertenceAEssaEmpresaException, NaoEPossivelAdicionarNoPedidoFechadoException {
        Pedido pedido = pedidos.get(numeroPedido);

        if (pedido == null) {
            throw new PedidoNaoEstaAbertoException();
        }
        
        if (pedido.getEstado() == "preparando") {
        	throw new NaoEPossivelAdicionarNoPedidoFechadoException();
        }

        Empresa empresa = empresaService.getEmpresaByName(pedido.getEmpresa());

        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }

        Produto produto = produtoService.getProdutoById(produtoId);

        if (produto == null) {
            throw new ProdutoNaoEncontradoNaEmpresaException();
        }
        
        if (produto.getEmpresaId() != empresa.getId()) {
        	throw new ProdutoNaoPertenceAEssaEmpresaException();
        }

        pedido.adicionarProduto(produto);
    }
    
    public String getPedidos(int numeroPedido, String atributo) throws AtributoNaoExisteException, AtributoInvalidoException {
        Pedido pedido = pedidos.get(numeroPedido);
        if (atributo == null) {
        	throw new AtributoInvalidoException();
        }
        switch (atributo) {
            case "cliente":
                return pedido.getCliente();
            case "empresa":
                return pedido.getEmpresa();
            case "estado":
                return pedido.getEstado();
            case "produtos":
            	 StringBuilder productNames = new StringBuilder();
            	 productNames.append("{[");
                 for (Produto produto : pedido.getProdutos()) {
                     productNames.append(produto.getNome()).append(", ");
                 }
                 if (productNames.length() > 2) {
                     productNames.delete(productNames.length() - 2, productNames.length());
                 }
                 productNames.append("]}");
                 return productNames.toString();
            case "valor":
            	return String.format(Locale.US, "%.2f", pedido.getValor());
            case "local":
            	throw new AtributoNaoExisteException();
            default:
                throw new AtributoInvalidoException();
        }
    }
    
    public void fecharPedido(int numeroPedido) throws PedidoNaoEncontradoException {
        Pedido pedido = pedidos.get(numeroPedido);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        pedido.setEstado("preparando");
    }
    
    public void removerProduto(int numeroPedido, String nomeProduto) throws ProdutoNaoEncontradoException, ProdutoInvalidoException, NaoEPossivelRemoverDeUmPedidoFechadoException {
    	if (nomeProduto == null) {
            throw new ProdutoInvalidoException();
        }
        Pedido pedido = pedidos.get(numeroPedido);
        
        if(pedido.getEstado().equals("preparando")) {
            throw new NaoEPossivelRemoverDeUmPedidoFechadoException();
        }
        
        pedido.removerProduto(nomeProduto);
    }

    public Produto getProdutoById(int produtoId) throws ProdutoNaoEncontradoException {
        return produtoService.getProdutoById(produtoId);
    }


    public void alterarFuncionamento(int mercado, String abre, String fecha) throws HorarioInvalidoException, EmpresaNaoCadastradaException, FormatoDeHoraInvalidoException, NaoEUmMercadoValidoException {
        empresaService.alterarFuncionamento(mercado, abre, fecha);
    }

    public void liberarPedido(int numero) throws PedidoNaoEncontradoException, PedidoJaLiberadoException, ProdutoNaoEstaSendoPreparadoException {
        Pedido pedido = pedidos.get(numero);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        if (pedido.getEstado().equals("pronto")) {
            throw new PedidoJaLiberadoException();
        }
        if (pedido.getEstado().equals("aberto")) {
            throw new ProdutoNaoEstaSendoPreparadoException();
        }

        pedido.setEstado("pronto");
    }

    public int obterPedido(int entregadorId) throws NenhumPedidoDisponivelException, EmpresaNaoEncontradaException, EmpresaNaoCadastradaException, NaoEUmEntregadorValidoException, UsuarioNaoEUmEntregadorException, EntregadorNaoEstaEmNenhumaEmpresaException, NaoExistePedidoParaEntregaException {
        if (!usuarios.containsKey(entregadorId) || !(usuarios.get(entregadorId) instanceof Entregador)) {
            throw new UsuarioNaoEUmEntregadorException();
        }

        Set<Integer> empresasDoEntregador = entregadoresPorEmpresa.keySet()
                .stream()
                .filter(empresaId -> entregadoresPorEmpresa.get(empresaId).contains(entregadorId))
                .collect(Collectors.toSet());

        if (empresasDoEntregador.isEmpty()) {
            throw new EntregadorNaoEstaEmNenhumaEmpresaException();
        }

        Pedido pedidoPrioritario = null;

        for (Pedido pedido : pedidos.values()) {
            if (pedido.getEstado().equals("pronto")) {
                int empresaId = empresaService.getEmpresaByName(pedido.getEmpresa()).getId();

                if (empresasDoEntregador.contains(empresaId)) {
                    Empresa empresa = empresaService.getEmpresaById(empresaId);

                    if (empresa instanceof Farmacia) {
                        return pedido.getNumero();
                    }

                    if (pedidoPrioritario == null) {
                        pedidoPrioritario = pedido;
                    }
                }
            }
        }

        if (pedidoPrioritario == null) {
            throw new NaoExistePedidoParaEntregaException();
        }

        return pedidoPrioritario.getNumero();
    }

    public int criarEntrega(int pedidoId, int entregadorId, String destino) throws PedidoNaoEncontradoException, NaoEUmEntregadorValidoException, PedidoNaoEstaProntoParaEntregaException, EntregadorAindaEmEntregaException, UsuarioNaoCadastradoException {
        Pedido pedido = pedidos.get(pedidoId);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        if (pedido.getEstado().equals("aberto")) {
            throw new PedidoNaoEstaProntoParaEntregaException();
        }

        if (!usuarios.containsKey(entregadorId) || !(usuarios.get(entregadorId) instanceof Entregador)) {
            throw new NaoEUmEntregadorValidoException();
        }

        for (Entrega entrega : entregas.values()) {
            if (entrega.getEntregador() == entregadorId) {
                throw new EntregadorAindaEmEntregaException();
            }
        }

        Usuario cliente = usuarios.get(getUsuarioByNome(pedido.getCliente()));
        if (cliente == null || !(cliente instanceof Cliente)) {
            throw new PedidoNaoEncontradoException();
        }

        if (destino == null || destino.isEmpty()) {
            destino = ((Cliente) cliente).getEndereco();
        }

        List<String> nomesProdutos = pedido.getProdutos()
                .stream()
                .map(Produto::getNome)
                .collect(Collectors.toList());

        Entrega entrega = new Entrega(pedido.getCliente(), pedido.getEmpresa(), pedidoId, entregadorId, destino, nomesProdutos);

        pedido.setEstado("entregando");

        entregas.put(entrega.getId(), entrega);
        return entrega.getId();
    }


    public String getEntrega(int entregaId, String atributo) throws EntregaNaoExisteException, AtributoInvalidoException, AtributoNaoExisteException {
        Entrega entrega = entregas.get(entregaId);

        if (entrega == null) {
            throw new EntregaNaoExisteException();
        }

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        switch (atributo.toLowerCase()) {
            case "cliente":
                return entrega.getCliente();
            case "empresa":
                return entrega.getEmpresa();
            case "pedido":
                return String.valueOf(entrega.getPedido());
            case "entregador":
                return usuarios.get(entrega.getEntregador()).getNome();
            case "destino":
                return entrega.getDestino();
            case "produtos":
                return "{[" + String.join(", ", entrega.getProdutos()) + "]}";
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public int getIdEntrega(int pedidoId) throws PedidoNaoEncontradoException, EntregaNaoExisteException, NaoExisteEntregaComEsseIdException {
        Pedido pedido = pedidos.get(pedidoId);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        for (Entrega entrega : entregas.values()) {
            if (entrega.getPedido() == pedidoId) {
                return entrega.getId();
            }
        }

        throw new NaoExisteEntregaComEsseIdException();
    }

    public void entregar(int entregaId) throws EntregaNaoExisteException, PedidoNaoEncontradoException, NaoExisteNadaPSerEntregueException {
        Entrega entrega = entregas.get(entregaId);

        if (entrega == null) {
            throw new NaoExisteNadaPSerEntregueException();
        }

        Pedido pedido = pedidos.get(entrega.getPedido());

        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        pedido.setEstado("entregue");

        entregas.remove(entregaId);
    }

    public Map<Integer, Set<Integer>> getEntregadoresPorEmpresa() {
        return entregadoresPorEmpresa;
    }
}
