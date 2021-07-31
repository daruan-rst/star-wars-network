package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Item;
import br.com.letscode.starwarsnetwork.Inventario.InventarioRepository;
import br.com.letscode.starwarsnetwork.Excecoes.NullPointerException;
import br.com.letscode.starwarsnetwork.Localizacao.Localizacao;
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
public class RebeldeRepository {

    @Autowired
    private InventarioRepository inventarioRepository;

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
                    .map(linha -> {
                        try {
                            return convert(linha);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    return null;}).collect(Collectors.toList());
        }
        return rebeldes;
    }

    private String format(Rebelde rebelde) {
        String formato = String.format("%s,%s,%d,%s,%s,%d,%s,%s\r\n",
                rebelde.getId(),
                rebelde.getName(),
                rebelde.getAge(),
                rebelde.getGenre(),
                rebelde.isTraitor(),
                rebelde.getDenuncia(),
                rebelde.getLocation().toString().replace("(", "").trim().replace(")", "").trim(),
                rebelde.getInventario().toString().replace("[", "").trim().replace("]", "").trim());

        return formato;
    }

    private Rebelde convert(String linha) throws IOException {
        StringTokenizer token = new StringTokenizer(linha, ",");
        String id  = token.nextToken();
        var rebelde = Rebelde.builder()
                .id(id)
                .name(token.nextToken())
                .age(Integer.valueOf(token.nextToken()))
                .genre(GeneroEnum.valueOf(token.nextToken()))
                .isTraitor(Boolean.parseBoolean(token.nextToken()))
                .denuncia(Integer.valueOf(token.nextToken()))
                .location(Localizacao.builder()
                        .latitude(Long.valueOf(token.nextToken().trim()))
                        .longitude(Long.valueOf(token.nextToken().trim()))
                        .galaxia(Long.valueOf(token.nextToken().trim()))
                        .base(token.nextToken().trim())
                        .build())
                .build();
        List<Item> itens = new ArrayList<>();
        while(token.hasMoreTokens()){
            var item = Item.builder()
                    .id(token.nextToken().trim())
                    .name(token.nextToken().trim())
                    .qnd(Integer.parseInt(token.nextToken().trim()))
                    .point(Integer.parseInt(token.nextToken().trim()))
                    .build();
            itens.add(item);
        }
        rebelde.setInventario(itens);

        return rebelde;
    }

    private void write(String rebeldeString, StandardOpenOption option) throws IOException {
        try(BufferedWriter bf = Files.newBufferedWriter(path, option)){
            bf.flush();
            bf.write(rebeldeString);
        }
    }

    public Optional<Rebelde> findByName(String name) throws IOException {
        List<Rebelde> registeredRebeldes = listAll();

        return registeredRebeldes.stream().filter(rebelde -> rebelde.getName().equals(name)).findFirst();
    }

    public Rebelde adicionar(Rebelde rebelde) throws IOException {
        write(format(rebelde), StandardOpenOption.APPEND);
        return rebelde;
    }

    public void confirmarTraicao(String idRebelde) throws IOException {
        Optional<Rebelde> rebelde = findByIdRestriction(idRebelde);
        rebelde.get().setTraitor(true);
    }

    public long qntDenunciaRebelde(String idRebelde) throws IOException{
        Optional<Rebelde> rebelde = findByIdRestriction(idRebelde);

        rebelde.get().setDenuncia(rebelde.get().getDenuncia()+1);

        return rebelde.get().getDenuncia();
    }


    public void save(Rebelde rebelde)throws IOException{

        List<Item> itens = rebelde.getInventario();

        for(int i = 0; i < itens.size(); i++){
            itens.get(i).adicionarPontos();
        }
        rebelde.setInventario(itens);
        removerItemArquivo(rebelde.getId());
        adicionar(rebelde);
    }

    public void removerItemArquivo(String id)throws IOException{
        List<Rebelde> registeredRebeldes = listAll();
        List<Rebelde> rebeldesResultante = new ArrayList<>();
        for(Rebelde rebelde : registeredRebeldes){
            if(!rebelde.getId().equals(id)){
                rebeldesResultante.add(rebelde);
            }
        }
        eraseContent();
        reescreverArquivo(registeredRebeldes);
    }

    public void eraseContent() throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(path);
        writer.write("");
        writer.flush();
    }

    public void reescreverArquivo(List<Rebelde> rebeldes) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Rebelde rebeldeBuilder : rebeldes) {
            builder.append(format(rebeldeBuilder));
        }
        write(builder.toString(), StandardOpenOption.CREATE);
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

    public int totalTraidores(boolean traidor) throws IOException{
        List<Rebelde> registeredRebeldes = listAll();

        int count = 0;

        if(registeredRebeldes.stream().filter(rebelde -> rebelde.isTraitor()).equals(traidor)) {
           count++;
        }
        return count;
    }

    public int totalRebeldes() throws IOException{
        List<Rebelde> registeredRebeldes = listAll();

        int count = registeredRebeldes.size();
        return count;
    }

}
