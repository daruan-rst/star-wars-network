package br.com.letscode.starwarsnetwork.localizacao;

import br.com.letscode.starwarsnetwork.rebelde.Rebelde;
import br.com.letscode.starwarsnetwork.rebelde.RebeldeService;
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
