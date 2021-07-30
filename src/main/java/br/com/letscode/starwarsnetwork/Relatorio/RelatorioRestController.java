package br.com.letscode.starwarsnetwork.Relatorio;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/relatorio")
@RestController
@RequiredArgsConstructor
public class RelatorioRestController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public Relatorio getRelatorio() throws IOException {
        return relatorioService.getRelatorio();
    }

}
