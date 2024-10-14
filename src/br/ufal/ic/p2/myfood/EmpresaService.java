package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.Exception.*;
import br.ufal.ic.p2.myfood.models.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
    	this.nomeToEmpresas = new HashMap<String, List<Empresa>>();
    	this.nextId = 1;
    }
    
    public int criarEmpresa(String tipoEmpresa, Usuario dono, String nome, String endereco, String tipoCozinha) throws EmpresaComEsseNomeELocalJaExisteException, UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeJaExisteException {
    	if (dono == null || dono.getClass().equals(Cliente.class)|| !(dono.getClass().equals(Dono.class))) {
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

    public int criarEmpresa(String tipoEmpresa, Usuario dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws UsuarioNaoPodeCriarUmaEmpresaException, EmpresaComEsseNomeELocalJaExisteException, EmpresaComEsseNomeJaExisteException, FormatoDeHoraInvalidoException, HorarioInvalidoException, TipoDeEmpresaInvalidoException, NomeInvalidoException, EnderecoDaEmpresaInvalidoException, TipoDeMercadoInvalidoException {
        if (dono == null || dono.getClass().equals(Cliente.class)|| !(dono.getClass().equals(Dono.class))) {
            throw new UsuarioNaoPodeCriarUmaEmpresaException();
        }
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoDaEmpresaInvalidoException();
        }
        if (((abre == null || abre.isEmpty()) && !verificarHorario(fecha))){
            System.out.println("entrou");
            throw new FormatoDeHoraInvalidoException();
        }
        else if (abre == null || abre.isEmpty()) {
            throw new HorarioInvalidoException();
        }
        else if (fecha == null) {
            throw new HorarioInvalidoException();
        }
        else if(fecha.isEmpty() && verificarHorario(abre)) {
            throw new FormatoDeHoraInvalidoException();
        }
        else if (!verificarFormatoHora(abre) || !verificarFormatoHora(fecha)) {
            throw new FormatoDeHoraInvalidoException();
        }
        else if(!verificarHorarioValido(abre, fecha)) {
            throw new HorarioInvalidoException();
        }
        if(tipoEmpresa == null || !tipoEmpresa.equals("mercado")) {
            throw new TipoDeEmpresaInvalidoException();
        }
        if(tipoMercado == null) {
            throw new TipoDeMercadoInvalidoException();
        }
        if (nomeToEmpresas.containsKey(nome)) {
            for (Empresa empresa : nomeToEmpresas.get(nome)) {
                if (!empresa.getDono().equals(dono)) {
                    throw new EmpresaComEsseNomeJaExisteException();
                }
            }
        }
        for (Empresa empresa : nomeToEmpresas.getOrDefault(nome, new ArrayList<>())) {
            if (empresa.getEndereco().equals(endereco)) {
                throw new EmpresaComEsseNomeELocalJaExisteException();
            }
        }
        Mercado mercado = new Mercado(nextId++, nome, endereco, dono, abre, fecha, tipoMercado);
        empresas.put(mercado.getId(), mercado);
        nomeToEmpresas.computeIfAbsent(nome, k -> new ArrayList<>()).add(mercado);
        return mercado.getId();
    }

    public boolean verificarFormatoHora(String hora) {
        String regex = "^\\d{2}:\\d{2}$";
        return hora.matches(regex);
    }

    public boolean verificarHorario(String hora) {
        String regex = "^([01]\\d|2[0-3]):([0-5]\\d)$";
        return hora.matches(regex);
    }

    public boolean verificarHorarioValido(String abre, String fecha) {
        try {
            LocalTime horaAbre = LocalTime.parse(abre);
            LocalTime horaFecha = LocalTime.parse(fecha);
            return horaAbre.isBefore(horaFecha);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public List<Empresa> getEmpresasDoUsuario(Usuario dono) throws UsuarioNaoPodeCriarUmaEmpresaException {
    	if(dono.getClass().equals(Cliente.class)) {
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
                if (empresa instanceof Empresa) {
                    return empresa.getTipoCozinha();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "dono":
                return empresa.getDono().getNome();
            default:
                if (empresa instanceof Mercado) {
                    Mercado mercado = (Mercado) empresa;
                    switch (atributo) {
                        case "abre":
                            return mercado.getAbre();
                        case "fecha":
                            return mercado.getFecha();
                        case "tipoMercado":
                            return mercado.getTipoMercado();
                        default:
                            throw new AtributoInvalidoException();
                    }
                } else {
                    throw new AtributoInvalidoException();
                }
        }
    }

    public int getIdEmpresa(Usuario dono, String nome, int indice) throws UsuarioNaoPodeCriarUmaEmpresaException, IndiceInvalidoException, IndiceMaiorQueOEsperadoException, NaoExisteEmpresaComEsseNomeException, NomeInvalidoException {
        List<Empresa> empresasDoDono = getEmpresasDoUsuario(dono);
        
        if (nome == null || nome.isEmpty()) {
        	throw new NomeInvalidoException();
        }
       
        List<Empresa> empresasComMesmoNome = empresasDoDono.stream()
                .filter(empresa -> empresa.getNome().equals(nome))
                .toList();
        if (indice < 0) {
            throw new IndiceInvalidoException();
        }
        if (empresasComMesmoNome.isEmpty()) {
        	throw new NaoExisteEmpresaComEsseNomeException();
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
    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EmpresaNaoCadastradaException, FormatoDeHoraInvalidoException, HorarioInvalidoException, NaoEUmMercadoValidoException {

        Empresa empresa = getEmpresaById(mercado);

        if (abre == null || abre.isEmpty()) {
            throw new HorarioInvalidoException();
        }
        if (fecha == null || fecha.isEmpty()) {
            throw new HorarioInvalidoException();
        }
        if (!(empresa instanceof Mercado)) {
            throw new NaoEUmMercadoValidoException();
        }

        if (!verificarFormatoHora(abre) || !verificarFormatoHora(fecha)) {
            throw new FormatoDeHoraInvalidoException();
        }

        if (!verificarHorarioValido(abre, fecha)) {
            throw new HorarioInvalidoException();
        }

        ((Mercado) empresa).setAbre(abre);
        ((Mercado) empresa).setFecha(fecha);
    }

}
