package br.com.letscode.starwarsnetwork.rebelde;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebeldeService {

    @Autowired
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

    public void save(Rebelde rebelde)throws IOException{
        repository.save(rebelde);
    }


    public Optional<Rebelde> findByIdRestriction(String id) throws IOException{
        return repository.findByIdRestriction(id);
    }


    public void confirmarTraicao(String idRebelde) throws IOException{
         repository.confirmarTraicao(idRebelde);
    }

    public long qntDenunciaRebelde(String idRebelde) throws IOException{
        return repository.qntDenunciaRebelde(idRebelde);
    }

    public int totalTraidores(boolean traidor) throws IOException{
        return  repository.totalTraidores(traidor);
    }

    public int totalRebeldes() throws IOException{
        return  repository.totalRebeldes();
    }

}
