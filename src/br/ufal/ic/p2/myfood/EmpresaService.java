package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.Exception.AtributoInvalidoException;
import br.ufal.ic.p2.myfood.Exception.EmpresaComEsseNomeELocalJaExisteException;
import br.ufal.ic.p2.myfood.Exception.EmpresaComEsseNomeJaExisteException;
import br.ufal.ic.p2.myfood.Exception.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.myfood.Exception.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.myfood.Exception.IndiceInvalidoException;
import br.ufal.ic.p2.myfood.Exception.IndiceMaiorQueOEsperadoException;
import br.ufal.ic.p2.myfood.Exception.NaoExisteEmpresaComEsseNomeException;
import br.ufal.ic.p2.myfood.Exception.NomeInvalidoException;
import br.ufal.ic.p2.myfood.Exception.UsuarioNaoPodeCriarUmaEmpresaException;
import br.ufal.ic.p2.myfood.models.Cliente;
import br.ufal.ic.p2.myfood.models.Dono;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class EmpresaService {
    private Map<Integer, Empresa> empresas = new HashMap<>();
    private Map<String, List<Empresa>> nomeToEmpresas = new HashMap<>();
    private int nextId = 1;

    public void zerarEmpresas() {
    	this.empresas = new HashMap<Integer, Empresa>();
    	this.nextId = 1;
    }
    
    public int criarEmpresa(String tipoEmpresa, Usuario dono, String nome, String endereco, String tipoCozinha) throws EmpresaComEsseNomeELocalJaExisteException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeJaExisteException {
    	if (dono == null || (dono instanceof Cliente) || !(dono instanceof Dono)) {
            throw new UsuarioNaoPodeCriarUmaEmpresaException();
        }
        
        if (nomeToEmpresas.containsKey(nome)) {
            for (Empresa empresa : nomeToEmpresas.get(nome)) {
                if (empresa.getDono().equals(dono)) {
                    if (empresa.getEndereco().equals(endereco)) {
                        throw new EmpresaComEsseNomeELocalJaExisteException();
                    }
                }
            }
        }

        for (Empresa empresa : nomeToEmpresas.getOrDefault(nome, new ArrayList<>())) {
            if (empresa.getEndereco().equals(endereco)) {
                throw new EmpresaComEsseNomeJaExisteException();
            }
        }

        Empresa novaEmpresa = new Empresa(nextId++, nome, endereco, tipoCozinha, dono);
        empresas.put(novaEmpresa.getId(), novaEmpresa);
        nomeToEmpresas.computeIfAbsent(nome, k -> new ArrayList<>()).add(novaEmpresa);
        return novaEmpresa.getId();
    }

    public List<Empresa> getEmpresasDoUsuario(Usuario dono) throws UsuarioNaoPodeCriarUmaEmpresaException {
    	if(!(dono instanceof Dono)) {
    		throw new UsuarioNaoPodeCriarUmaEmpresaException();
    	}
        List<Empresa> resultado = new ArrayList<>();
        for (Empresa empresa : empresas.values()) {
            if (empresa.getDono().equals(dono)) {
                resultado.add(empresa);
            }
        }
        return resultado;
    }

    public Empresa getEmpresaById(int id) throws EmpresaNaoCadastradaException {
        Empresa empresa = empresas.get(id);
        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        } else if(!(empresas.containsKey(id))) {
        	throw new EmpresaNaoCadastradaException();
        }
        return empresa;
    }

    public String getAtributoEmpresa(int empresaId, String atributo) throws AtributoInvalidoException, EmpresaNaoCadastradaException {
        Empresa empresa = getEmpresaById(empresaId);
        switch (atributo) {
            case "nome":
                return empresa.getNome();
            case "endereco":
                return empresa.getEndereco();
            case "tipoCozinha":
                return empresa.getTipoCozinha();
            case "dono":
                return empresa.getDono().getNome();
            default:
                throw new AtributoInvalidoException();
        }
    }
    
    public int getIdEmpresa(Usuario dono, String nome, int indice) throws UsuarioNaoPodeCriarUmaEmpresaException, IndiceInvalidoException, IndiceMaiorQueOEsperadoException, NaoExisteEmpresaComEsseNomeException, NomeInvalidoException {
        List<Empresa> empresasDoDono = getEmpresasDoUsuario(dono);
        
        if (nome == null || nome.isEmpty()) {
        	throw new NomeInvalidoException();
        }
       
        List<Empresa> empresasComMesmoNome = empresasDoDono.stream()
                .filter(empresa -> empresa.getNome().equals(nome))
                .collect(Collectors.toList());
        
        if (empresasComMesmoNome.size() == 0) {
        	throw new NaoExisteEmpresaComEsseNomeException();
        }
        if (indice < 0) {
            throw new IndiceInvalidoException();
        }
        if (indice >= empresasComMesmoNome.size()) {
            throw new IndiceMaiorQueOEsperadoException();
        }
        
        return empresasComMesmoNome.get(indice).getId();
    }
    
    public boolean empresaExiste(int empresaId) {
    	return empresas.containsKey(empresaId);
    }

    public Empresa getEmpresaByName(String nome) throws EmpresaNaoEncontradaException {
        List<Empresa> empresa = nomeToEmpresas.get(nome);
        if (empresa == null) {
            throw new EmpresaNaoEncontradaException();
        }
        return empresa.get(0);
    }
    
    public List<Empresa> getEmpresas() {
        return new ArrayList<>(empresas.values());
    }
}
