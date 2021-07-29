package br.com.letscode.starwarsnetwork.Rebelde;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping ("/rebelde")
@RestController
@RequiredArgsConstructor
public class RebeldeRestController {

    private final RebeldeService service;

    @GetMapping
    public List<Rebelde> listAll() throws IOException {
        return service.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rebelde adicionar(@Valid @RequestBody Rebelde rebelde) throws IOException {
        return service.adicionar(rebelde);
    }

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Rebelde> findByName(@RequestParam String name) throws IOException {
        return service.findByName(name);
    }

    @GetMapping("/id")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Rebelde> findByIdRestriction(@RequestParam String id)throws IOException{
        return service.findByIdRestriction(id);
    }

    @PostMapping("/trade")
    @ResponseStatus(HttpStatus.CREATED)
    public void realizarTrade(@RequestBody Trade[] trade) throws IOException{
         service.realizarTrade(trade[0], trade[1]);
    }
}
