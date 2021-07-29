package br.com.letscode.starwarsnetwork.Denuncia;


import br.com.letscode.starwarsnetwork.Inventario.InventarioRepository;
import br.com.letscode.starwarsnetwork.Rebelde.GeneroEnum;
import br.com.letscode.starwarsnetwork.Rebelde.Localizacao;
import br.com.letscode.starwarsnetwork.Rebelde.Rebelde;
import br.com.letscode.starwarsnetwork.Rebelde.RebeldeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DenunciaRepository {

    @Autowired
    private RebeldeService rebeldeService;

    static private Path pathInventario;

    @PostConstruct
    public void init() {

        try {
            String caminho = "src/main/resources/dados/denuncias.csv";
            pathInventario = Paths.get(caminho);
            if (!pathInventario.toFile().exists()) {
                Files.createFile(pathInventario);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public List<Denuncia> getAllReports() throws IOException {
        List<Denuncia> denuncias;
        try (BufferedReader br = Files.newBufferedReader(pathInventario)) {
            denuncias = br.lines().filter(Objects::nonNull)
                    .filter(Predicate.not(String::isEmpty))
                    .map(linha -> {
                        try {
                            return convert(linha);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;}).collect(Collectors.toList());
        }
        return denuncias;
    }

    private String format(Denuncia denuncia) {
        return String.format("%s,%s\r\n",
                denuncia.getId(),
                denuncia.getIdAcusado());
    }

    private Denuncia convert(String linha) throws IOException {
        StringTokenizer token = new StringTokenizer(linha, ",");
        String id  = token.nextToken();
        var denuncia = Denuncia.builder()
                .id(id)
                .idAcusado(token.nextToken())
                .build();
        return denuncia;
    }

    private void write (String rebeldeString, StandardOpenOption option) throws IOException {
        try(BufferedWriter bf = Files.newBufferedWriter(pathInventario, option)){
            bf.flush();
            bf.write(rebeldeString);
        }
    }

    public Denuncia adicionar(Denuncia denuncia) throws IOException {
        write(format(denuncia), StandardOpenOption.APPEND);
        return denuncia;
    }


    public void save(Denuncia denuncia) throws IOException{

        List<Denuncia> registeredDenuncias = getAllReports();

        if(qtdDenunciaRebelde(denuncia.getIdAcusado()) >= 2){
            confirmarTraicao(denuncia.getIdAcusado());
            registeredDenuncias.add(denuncia);
            adicionar(denuncia);
        }else{
            registeredDenuncias.add(denuncia);
            adicionar(denuncia);
            qtdDenunciaRebelde(denuncia.getIdAcusado());
        }
    }

    public void confirmarTraicao(String idRebelde) throws IOException { rebeldeService.confirmarTraicao(idRebelde);}

    public long qtdDenunciaRebelde(String idRebelde) throws IOException{ return rebeldeService.qntDenunciaRebelde(idRebelde);}
}
