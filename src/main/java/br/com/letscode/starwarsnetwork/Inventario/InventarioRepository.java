package br.com.letscode.starwarsnetwork.Inventario;

import br.com.letscode.starwarsnetwork.Rebelde.Rebelde;
import br.com.letscode.starwarsnetwork.Rebelde.RebeldeService;
import br.com.letscode.starwarsnetwork.Rebelde.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.*;


@Component
public class InventarioRepository {


    @Autowired
    private RebeldeService rebeldeService;


    public void realizarTrade(Trade ofertante, Trade receptor)throws IOException{
        Optional<Rebelde> rebeldeOfertante = rebeldeService.findByIdRestriction(ofertante.getIdRebelde());
        Optional<Rebelde> rebeldeReceptor = rebeldeService.findByIdRestriction(receptor.getIdRebelde());

        List<Item> itensTradeOfertante = validarItens(rebeldeOfertante.get().getInventario(), ofertante.getItens());
        List<Item> itensTradeReceptor = validarItens(rebeldeReceptor.get().getInventario(), receptor.getItens());

        if(itensTradeOfertante.isEmpty() || itensTradeReceptor.isEmpty()){
            return;
        }else{
            if(!validarPontos(itensTradeOfertante, itensTradeReceptor)){
                return;
            }else{
                rebeldeOfertante.get().setInventario
                        (adicionarItens(rebeldeOfertante.get().getInventario(), itensTradeReceptor));
                rebeldeOfertante.get().setInventario
                        (removerItens(rebeldeOfertante.get().getInventario(), itensTradeOfertante));

                rebeldeReceptor.get().setInventario
                        (adicionarItens(rebeldeReceptor.get().getInventario(), itensTradeOfertante));
                rebeldeReceptor.get().setInventario
                        (removerItens(rebeldeReceptor.get().getInventario(), itensTradeReceptor));

                rebeldeService.save(rebeldeOfertante.get());
                rebeldeService.save(rebeldeReceptor.get());

            }
        }
    }

    public List<Item> validarItens(List<Item> ofertante, List<Item> oferta){
        List<Item> itensProntos = new ArrayList<>();

        for(int i = 0; i < ofertante.size(); i++){

            for(int j = 0; j < oferta.size(); j++){
                String nome = ofertante.get(i).getName();
                int qtd = ofertante.get(i).getQnd();

                if(oferta.get(j).getName().equals(nome)){
                    if (oferta.get(j).getQnd() > qtd) {
                        return itensProntos = new ArrayList<>();
                    }else{
                        Item item = oferta.get(j);
                        item.setId(ofertante.get(i).getId());
                        item.adicionarPontos();
                        itensProntos.add(item);
                    }
                }else{
                    itensProntos.add(null);
                }
            }
        }
        itensProntos.removeIf(n -> (n == null));
        if(itensProntos.size() < oferta.size()){
            return itensProntos = new ArrayList<>();
        }

        return itensProntos;
    }

    public boolean validarPontos(List<Item> ofertante, List<Item> receptor){
        int pontosOfertante =  0, pontosReceptor = 0;

        for(Item it : ofertante){
            pontosOfertante += (it.getPoint() * it.getQnd());
        }
        for(Item it : receptor){
            pontosReceptor += (it.getPoint() * it.getQnd());
        }

        if(pontosOfertante != pontosReceptor){
            return false;
        }
        return true;
    }

    public List<Item> adicionarItens(List<Item> ofertante, List<Item> oferta){
        for (int i = 0; i < ofertante.size(); i++){
            for(int j = 0; j < oferta.size(); j++){
                if(ofertante.get(i).getName().equals(oferta.get(j).getName())){
                    ofertante.get(i).setQnd(ofertante.get(i).getQnd() + oferta.get(j).getQnd());
                }
            }
        }
        return ofertante;
    }

    public List<Item> removerItens(List<Item> ofertante, List<Item> oferta){
        for(int i = 0; i < ofertante.size(); i++){
            for(int j = 0; j < oferta.size(); j++){
                if(ofertante.get(i).getId() == oferta.get(j).getId()){
                    ofertante.get(i).setQnd(ofertante.get(i).getQnd() - oferta.get(j).getQnd());
                }
            }
        }
        return ofertante;
    }
}