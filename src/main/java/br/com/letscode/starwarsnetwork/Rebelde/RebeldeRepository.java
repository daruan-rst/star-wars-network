package br.com.letscode.starwarsnetwork.Rebelde;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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


    public Rebelde adicionar(Rebelde rebelde) throws IOException {
       write(format(rebelde), StandardOpenOption.APPEND);
        return rebelde;
    }

    private Rebelde convert (String linha) {
        StringTokenizer token = new StringTokenizer(linha,";");
        Rebelde rebelde = new Rebelde();
        rebelde.setName(token.nextToken());
        rebelde.setAge(Integer.parseInt(token.nextToken()));
        rebelde.setGenre(GeneroEnum.valueOf(token.nextToken()));
        rebelde.setLocation();
        rebelde.isTraitor();
        return rebelde;
    }

    private String format (Rebelde rebelde){
        return String.format("%s;%s;%s;%s;%s;%s; \r\n",
                rebelde.getName(),
                rebelde.getAge(),
                rebelde.getGenre(),
                rebelde.getLocation(),
                rebelde.isTraitor());
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


}
