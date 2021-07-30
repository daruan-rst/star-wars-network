package br.com.letscode.starwarsnetwork.Inventario;

import br.com.letscode.starwarsnetwork.Rebelde.Rebelde;

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

public class InventarioRepository {

    static private Path pathInventario;

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
}