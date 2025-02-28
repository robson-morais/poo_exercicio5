package sistema;

import sistema.exceptions.CadastroException;
import sistema.exceptions.PesquisaException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaAsistenciaTecnica implements EncomendasInterface {

    private Map<Integer, Encomenda> encomendas;
    private GravadorDeDados gravador;

    SistemaAsistenciaTecnica(Map<Integer, Encomenda> encomendas){
        this.encomendas = encomendas;
        this.gravador = new GravadorDeDados();
    }

    public void cadastrarEncomenda (int id, Encomenda encomenda) throws CadastroException {
        if (!this.encomendas.containsKey(id)) {
            this.encomendas.put(id, encomenda);
            System.out.println("sistema.projeto.Encomenda " + id + " cadastrada com sucesso!");
        } else {
            throw new CadastroException("sistema.projeto.Encomenda já cadatrada no sistema");
        }
    }

    public void pesquisarParaAlterar (int pesquisaId, String novoStatus) throws PesquisaException {
        Encomenda encomendaEncontrada = this.encomendas.get(pesquisaId);
        if (this.encomendas.values().contains(encomendaEncontrada)) {
            encomendaEncontrada.setStatus(novoStatus);
        } else {
            throw new PesquisaException("Serviço não encontrado no sistema.");
        }

    }

    public List<Encomenda> consultarServicosPendentes () throws PesquisaException {
        List<Encomenda> encomendasEncontradas = new ArrayList<>();

        if (!this.encomendas.isEmpty()) {
            for (Encomenda enc: this.encomendas.values()) {
                if (enc.getStatus().equalsIgnoreCase("A fazer")){
                    encomendasEncontradas.add(enc);
                }
            } return encomendasEncontradas;
        } else {
            throw new PesquisaException("Não há serviços pendentes no sistema.");
        }
    }

    public List<Encomenda> consultarServicosProntos () throws PesquisaException {
        List<Encomenda> encomendasEncontradas2 = new ArrayList<>();

        if (!this.encomendas.isEmpty()) {
            for (Encomenda enc: this.encomendas.values()) {
                if (enc.getStatus().equalsIgnoreCase("Pronto")){
                    encomendasEncontradas2.add(enc);
                }
            } return encomendasEncontradas2;
        } else {
            throw new PesquisaException("Nenhum serviço com status 'Pronto' foi cadastrado no sistema.");
        }
    }

    @Override
    public void salvarDados(Map<Integer, Encomenda> encomendas) throws IOException {
        this.gravador.salvarDados(this.encomendas);
    }

    @Override
    public void recuperarDados() throws IOException {
        this.encomendas = this.gravador.recuperarDados();
    }

    // Necessária apenas para converter String em Enum
    public Categoria stringToEnum (String categoriaString) {
        Categoria ctg = null;
        for (Categoria cat: Categoria.values()) {
            if (categoriaString.toUpperCase().equals(cat.name())) {
                ctg = cat;
            }
        } return ctg;
    }

}
