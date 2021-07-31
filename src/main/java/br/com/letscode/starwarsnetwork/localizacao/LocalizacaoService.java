package br.com.letscode.starwarsnetwork.localizacao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LocalizacaoService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    public void atualizarLocalizacao(Localizacao localizacao, String id) throws IOException {
        localizacaoRepository.atualizarLocalizacao(localizacao, id);
    }
}
