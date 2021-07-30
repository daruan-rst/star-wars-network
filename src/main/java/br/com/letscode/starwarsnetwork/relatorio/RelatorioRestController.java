package br.com.letscode.starwarsnetwork.relatorio;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping ("/relatorio")
@AllArgsConstructor
public class RelatorioRestController {

    RelatorioService relatorioService;

    @GetMapping
    public void exibirRelatorio() throws IOException {
        relatorioService.qtdTraidores();
        relatorioService.qtdRebeldes();
    }
}
