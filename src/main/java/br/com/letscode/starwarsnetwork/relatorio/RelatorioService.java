package br.com.letscode.starwarsnetwork.relatorio;

import br.com.letscode.starwarsnetwork.rebelde.Rebelde;
import br.com.letscode.starwarsnetwork.rebelde.RebeldeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class RelatorioService  {
    RebeldeRepository rebeldeRepository;

    public void qtdTraidores() throws IOException {
        List<Rebelde> rebeldes = rebeldeRepository.listAll();

        double traidores = rebeldes.stream()
                .mapToInt(rebelde -> rebelde.getDenuncia() >= 3 ? 1 : 0)
                .summaryStatistics()
                .getAverage();

        System.out.print("O percentual de traidores na resistência é:" + traidores * 100 + "% \n");
    }

    public void qtdRebeldes() throws IOException {
        List<Rebelde> rebeldes = rebeldeRepository.listAll();

        double traidores = rebeldes.stream()
                .mapToInt(rebelde -> rebelde.getDenuncia() < 3 ? 1 : 0)
                .summaryStatistics()
                .getAverage();

        System.out.print("O percentual de rebeldes na resistência é:" + traidores * 100 + "% \n");
    }

}
