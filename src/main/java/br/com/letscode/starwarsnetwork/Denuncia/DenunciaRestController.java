package br.com.letscode.starwarsnetwork.Denuncia;

import br.com.letscode.starwarsnetwork.Rebelde.Rebelde;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/denuncia")
@RestController
@RequiredArgsConstructor
public class DenunciaRestController {

    @Autowired
    private DenunciaService denunciaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody Denuncia denuncia)throws IOException {
        denunciaService.save(denuncia);
    }

}
