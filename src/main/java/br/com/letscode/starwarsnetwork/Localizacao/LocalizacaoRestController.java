package br.com.letscode.starwarsnetwork.Localizacao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/localizacao")
@RestController
@RequiredArgsConstructor
public class LocalizacaoRestController {

    @Autowired
    private LocalizacaoService localizacaoService;

    @PatchMapping("/atualizarLocalizacao/{id}")
    public void atualizarLocalizacao(@Valid @RequestBody Localizacao localizacao, @PathVariable String id) throws IOException {
        localizacaoService.atualizarLocalizacao(localizacao, id);
    }
}
