package br.com.letscode.starwarsnetwork.Inventario;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/inventario")
@RestController
@RequiredArgsConstructor
public class InventarioRestController {

    private final InventarioService service;

    @GetMapping
    public List<Inventario> listAll() throws IOException {
        return service.listAll();
    }

    @GetMapping("/id")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Inventario> findByIdInventario(@RequestParam String id) throws IOException {
        return service.findByIdInventario(id);
    }


}
