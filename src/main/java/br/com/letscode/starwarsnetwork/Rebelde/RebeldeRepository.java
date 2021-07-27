package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Item;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class RebeldeRepository {


    private Path rebelPath;

    @PostConstruct
    public void init(){
        final String rebelPath = "src/main/java/br/com/letscode/starwarsnetwork/resources/rebeldes.csv";
        this.rebelPath = Paths.get(rebelPath);
    }


    public List<Rebelde> listAll() throws IOException {
        List<Rebelde> rebelde;
        try (BufferedReader br = Files.newBufferedReader(this.rebelPath)){
            rebelde = br.lines().filter(String::isEmpty)
                    .map(this::convert)
                    .collect(Collectors.toList());
        }
        return rebelde;
    }



    private Rebelde convert (String linha) {
        StringTokenizer token = new StringTokenizer(linha,";");
        Rebelde rebelde = new Rebelde();
        rebelde.setName(token.nextToken());
        rebelde.setAge(Integer.parseInt(token.nextToken()));
        rebelde.setGenre(GeneroEnum.valueOf(token.nextToken()));
//        rebelde.setLocation();
//        rebelde.isTraitor();
        return rebelde;
    }

    private String format (Rebelde rebelde){
        return String.format("%s;%s;%s; \r\n",
                rebelde.getName(),
                rebelde.getAge(),
                rebelde.getGenre());
//                rebelde.getLocation(),
//                rebelde.isTraitor());
    }

    private void write (String rebeldeString, StandardOpenOption option) throws IOException {
        try(BufferedWriter bf = Files.newBufferedWriter(rebelPath, option)){
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

    public void save(Rebelde rebelde)throws IOException{
        List<Rebelde> registeredRebeldes = listAll();

        List<Item> itens = rebelde.getInventario().getItens();

        for(int i = 0; i < itens.size(); i++){
            itens.get(i).adicionarPontos();
        }
        rebelde.getInventario().setItens(itens);

        registeredRebeldes.add(rebelde);
    }

    public Optional<Rebelde> findByID(long id) throws IOException{
        List<Rebelde> registeredRebeldes = listAll();
        return registeredRebeldes.stream().filter (rebelde -> rebelde.getId().equals(id)).findFirst();
    }

    public Optional<Rebelde> findByIdRestriction(long id)  throws IOException{
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
