package br.com.letscode.starwarsnetwork.Relatorio;

import br.com.letscode.starwarsnetwork.Rebelde.RebeldeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RelatorioRepository {

    @Autowired
    private RebeldeService rebeldeService;

    public Relatorio getRelatorio() throws IOException{
        Relatorio relatorio = new Relatorio();
        relatorio.setTraitorReport(calcularPorcentagemTraidores());
        relatorio.setRebelReport(calcularPorcentagemRebeldes());
        relatorio.setInventoryReport(calcularQuantidadeMediaRecursos);
        relatorio.setLostPoints(calcularPontosPerdidos);
        return relatorio;
    }

    public double calcularPorcentagemTraidores() throws IOException {
        double resultado = ((double) rebeldeService.totalTraidores(true)/100) * rebeldeService.totalRebeldes();
        return resultado;
    }

    public double calcularPorcentagemRebeldes() throws IOException {
        double resultado = ((double) rebeldeService.totalTraidores(false)/100) * rebeldeService.totalRebeldes();
        return resultado;

    }

    public double calcularQuantidadeMediaRecursos(){
        //TODO
        return 0;
    }

    public double calcularPontosPerdidos(){
        //TODO
        return 0;
    }


}
