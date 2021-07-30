package br.com.letscode.starwarsnetwork.Inventario;

import br.com.letscode.starwarsnetwork.Rebelde.Rebelde;
import br.com.letscode.starwarsnetwork.Rebelde.RebeldeService;
import br.com.letscode.starwarsnetwork.Rebelde.Trade;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InventarioRepository {

    static private Path pathInventario;

    @Autowired
    private RebeldeService rebeldeService;

    @PostConstruct
    public void init() {

        try {
            String caminho = "src/main/resources/dados/inventario.csv";
            pathInventario = Paths.get(caminho);
            if (!pathInventario.toFile().exists()) {
                Files.createFile(pathInventario);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public List<Inventario> listAllInventario() throws IOException {
        List<Inventario> inventarios;
        try (BufferedReader br = Files.newBufferedReader(pathInventario)) {
            inventarios = br.lines().filter(Objects::nonNull)
                    .filter(Predicate.not(String::isEmpty))
                    .map(linha -> inventarioConvert(linha) ).collect(Collectors.toList());
        }

    return inventarios;}

    public Optional<Inventario> findByIdInventario(String id) throws IOException{
        List<Inventario> registeredInventarios = listAllInventario();
        return registeredInventarios.stream().filter (inventario -> inventario.getId().equals(id)).findFirst();
    }

    public String format(Inventario inventario){
        return String.format("%s,%d,%d,%d,%d\r\n",
                inventario.getId(),
                inventario.getItens().get(1).getQnd(),
                inventario.getItens().get(2).getQnd(),
                inventario.getItens().get(3).getQnd(),
                inventario.getItens().get(4).getQnd());
    }

    private void write(String inventarioString, StandardOpenOption option) throws IOException{
        try(BufferedWriter bf = Files.newBufferedWriter(pathInventario, option)){
            bf.flush();
            bf.write(inventarioString);
        }
    }

    public Inventario adicionar(Inventario inventario) throws IOException{
        write(format(inventario), StandardOpenOption.APPEND);
        return inventario;
    }

    public static Inventario inventarioConvert(String linha) {
        StringTokenizer token = new StringTokenizer(linha, ",");
        /**
         * no inventario.csv decidi que, depois do identificador (id) , 4 inteiros separados por virgulas representariam,
         * em ordem, comida, agua, munição e arma. Os pontos de cada rebelde ficam gravados no int points -  que pode
         * passar a integrar como um atributo da classe Inventário. Segue abaixo um exemplo da linha do inventário.csv:
         * [ID],[quantidade de comida],[quantidade de água],[quantidade de munição],[quantidade de arma]
         */
        var id = token.nextToken();
        int[] qntdItensArray = new int[4];
        int points; // to be modified or removed
        for (int i = 0; i < 4; i++) {
            qntdItensArray[i] = Integer.parseInt((token.nextToken()));
            points =+ qntdItensArray[i]*(i+1);
        }
        return Inventario.builder()
                .id(id)
                .itens(arrayOfItensToItemList(qntdItensArray, id))
                .build();
    }

    private static List<Item> arrayOfItensToItemList(int[] qntdItensArray, String id){
    Item comida = new Item(id,"Comida",1,qntdItensArray[0]);
    Item agua = new Item(id,"Agua",2,qntdItensArray[1]);
    Item municao = new Item(id,"Municao",3,qntdItensArray[2]);
    Item armas = new Item(id,"Armas",4,qntdItensArray[3]);

    List<Item> itens = new ArrayList<>();
    itens.add(1,comida);
    itens.add(2,agua);
    itens.add(3,municao);
    itens.add(4,armas);

    return itens;}

    static public String getIdReturnInventarioLine(String id) throws IOException {
        BufferedReader bf = Files.newBufferedReader(pathInventario);
        String line  = bf.readLine();
        StringTokenizer token = new StringTokenizer(line, ",");
        var readID = token.nextToken();
        while (!readID.equals(id)||(line=bf.readLine())!=null){
            String[]linha = line.split(",");
            readID = linha[0];
            line=bf.readLine();
        }
        bf.close();
        return line; }


    public void realizarTrade(Trade ofertante, Trade receptor)throws IOException{
        Optional<Rebelde> rebeldeOfertante = rebeldeService.findByIdRestriction(ofertante.getIdRebelde());
        Optional<Rebelde> rebeldeReceptor = rebeldeService.findByIdRestriction(receptor.getIdRebelde());

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