package br.com.letscode.starwarsnetwork.Rebelde;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping ("/rebelde")
@RestController
@RequiredArgsConstructor
public class RebeldeRestController {

    private final RebeldeService service;

    @GetMapping
    @ResponseStatus
    public List<Rebelde> listAll() throws IOException {
        return service.listAll();
    }

    @GetMapping("find")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Rebelde> findByName(@RequestParam String name) throws IOException {
        return service.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rebelde adicionar (@RequestBody Rebelde rebelde) throws IOException {
        return service.adicionar(rebelde);
    }


}
