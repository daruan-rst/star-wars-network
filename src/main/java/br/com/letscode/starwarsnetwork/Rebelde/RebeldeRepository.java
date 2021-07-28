package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Item;
import br.com.letscode.starwarsnetwork.Excecoes.NullPointerException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class RebeldeRepository {



    private Path path;
    @PostConstruct
    public void init(){

        try {
            String caminho = "src/main/resources/dados/rebeldes.csv";
            path = Paths.get(caminho);
            if (!path.toFile().exists()) {
                Files.createFile(path);
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public List<Rebelde> listAll() throws IOException {
        List<Rebelde> rebeldes;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            rebeldes = br.lines().filter(Objects::nonNull)
                    .filter(Predicate.not(String::isEmpty))
                    .map(this::convert).collect(Collectors.toList());
        }
        return rebeldes;
    }

    private String format(Rebelde rebelde) {
        return String.format("%s,%s,%d,%s,%d,%s\r\n",
                rebelde.getId(),
                rebelde.getName(),
                rebelde.getAge(),
                rebelde.getGenre(),
                rebelde.isTraitor(),
                rebelde.getLocation());
    }

    private Rebelde convert(String linha) {
        StringTokenizer token = new StringTokenizer(linha, ",");
        var rebelde = Rebelde.builder()
                .id(token.nextToken())
                .name(token.nextToken())
                .age(Integer.valueOf(token.nextToken()))
                .genre(GeneroEnum.valueOf(token.nextToken()))
                .isTraitor(Boolean.parseBoolean(token.nextToken()))
                .location(Localizacao.builder()
                        .latitude(Long.valueOf(token.nextToken()))
                        .longitude(Long.valueOf(token.nextToken()))
                        .galaxia(Long.valueOf(token.nextToken()))
                        .base(token.nextToken())
                        .build())
                //acessa um outro csv
                .build();

        return rebelde;
    }

    private void write (String rebeldeString, StandardOpenOption option) throws IOException {
        try(BufferedWriter bf = Files.newBufferedWriter(path, option)){
            bf.flush();
            bf.write(rebeldeString);
        }
    }

    public Optional<Rebelde> findByName(String name) throws IOException {
        List<Rebelde> registeredRebeldes = listAll();

        return registeredRebeldes.stream().filter (rebelde -> rebelde.getName().equals(name)).findFirst();
    }

    public Rebelde adicionar(Rebelde rebelde) throws IOException {
        write(format(rebelde), StandardOpenOption.APPEND);
        return rebelde;
    }

    public void confirmarTraicao(String idRebelde) {
        //TODO
    }

    public long qntDenunciaRebelde(String idRebelde) {
        //TODO
    }

    public void save(Rebelde rebelde)throws IOException{
        List<Rebelde> registeredRebeldes = listAll();

        List<Item> itens = rebelde.getInventario().getItens();

        for(int i = 0; i < itens.size(); i++){
            itens.get(i).adicionarPontos();
        }
        rebelde.getInventario().setItens(itens);

        registeredRebeldes.add(rebelde);
    }

    public Optional<Rebelde> findByID(String id) throws IOException{
        List<Rebelde> registeredRebeldes = listAll();
        return registeredRebeldes.stream().filter (rebelde -> rebelde.getId().equals(id)).findFirst();
    }

    public Optional<Rebelde> findByIdRestriction(String id)  throws IOException{
        Optional<Rebelde> rebelde = findByID(id);

        if(rebelde == null){
            throw new NullPointerException(" O rebelde não está cadastrado.");
        }else if(rebelde.get().isTraitor()){
            throw new NullPointerException(" O rebelde é um traidor.");
        }else{
            return rebelde;
        }
    }

    public void realizarTrade(Trade ofertante, Trade receptor)throws IOException{
        Optional<Rebelde> rebeldeOfertante = findByIdRestriction(ofertante.getIdRebelde());
        Optional<Rebelde> rebeldeReceptor = findByIdRestriction(receptor.getIdRebelde());

        List<Item> itensTradeOfertante = validarItens(rebeldeOfertante.get().getInventario().getItens(), ofertante.getItens());
        List<Item> itensTradeReceptor = validarItens(rebeldeReceptor.get().getInventario().getItens(), receptor.getItens());

        if(itensTradeOfertante.isEmpty() || itensTradeReceptor.isEmpty()){
            return;
        }else{
            if(!validarPontos(itensTradeOfertante, itensTradeReceptor)){
                return;
            }else{
                rebeldeOfertante.get().getInventario()
                        .setItens(adicionarItens(rebeldeOfertante.get().getInventario().getItens(), itensTradeReceptor));
                rebeldeOfertante.get().getInventario()
                        .setItens(removerItens(rebeldeOfertante.get().getInventario().getItens(), itensTradeOfertante));

                rebeldeReceptor.get().getInventario()
                        .setItens(adicionarItens(rebeldeReceptor.get().getInventario().getItens(), itensTradeOfertante));
                rebeldeReceptor.get().getInventario()
                        .setItens(removerItens(rebeldeReceptor.get().getInventario().getItens(), itensTradeReceptor));

                save(rebeldeOfertante.get());
                save(rebeldeReceptor.get());

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
