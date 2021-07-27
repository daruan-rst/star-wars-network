package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Item;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RebeldeService {

    private RebeldeRepository repository;

    public Rebelde adicionar(Rebelde rebelde) throws IOException {
        return repository.adicionar(rebelde);
    }

    public List<Rebelde> listAll() throws IOException {
        return repository.listAll();
    }

    public Optional<Rebelde> findByName(String name) throws IOException {
        return repository.findByName(name);
    }

    public void save(Rebelde rebelde)throws IOException{}


    public Optional<Rebelde> findByIdRestriction(long id) throws IOException{
        return repository.findByIdRestriction(id);
    }

    public void realizarTrade(Trade ofertante, Trade receptor)throws IOException{}

}
