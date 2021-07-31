package br.com.letscode.starwarsnetwork.inventario;


import br.com.letscode.starwarsnetwork.rebelde.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/inventario")
@RestController
@RequiredArgsConstructor
public class InventarioRestController {

    @Autowired
    private final InventarioService service;

    @PostMapping("/trade")
    @ResponseStatus(HttpStatus.CREATED)
    public void realizarTrade(@RequestBody Trade[] trade) throws IOException{
        service.realizarTrade(trade[0], trade[1]);
    }
}
