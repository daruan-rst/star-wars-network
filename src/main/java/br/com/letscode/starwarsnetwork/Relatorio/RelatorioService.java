package br.com.letscode.starwarsnetwork.Relatorio;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

//    public Relatorio getRelatorio() throws IOException {
//        return relatorioRepository.getRelatorio();
//    }
}
