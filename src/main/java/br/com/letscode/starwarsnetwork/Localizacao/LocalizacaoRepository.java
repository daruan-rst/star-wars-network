package br.com.letscode.starwarsnetwork.Localizacao;

import br.com.letscode.starwarsnetwork.Rebelde.Rebelde;
import br.com.letscode.starwarsnetwork.Rebelde.RebeldeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class LocalizacaoRepository {

    @Autowired
    private RebeldeService rebeldeService;

    public void atualizarLocalizacao(Localizacao localizacao, String id) throws IOException {
        Optional<Rebelde> rebelde = rebeldeService.findByIdRestriction(id);

        rebelde.get().setLocation(localizacao);
        rebeldeService.save(rebelde.get());
    }
}
