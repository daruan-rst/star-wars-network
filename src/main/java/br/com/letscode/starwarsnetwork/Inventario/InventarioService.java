package br.com.letscode.starwarsnetwork.Inventario;
/*
* Negociar itens: ambos os lados devem ofeecer a mesma qnt de pontos
*/

import br.com.letscode.starwarsnetwork.Rebelde.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventarioService {

    @Autowired
    private InventarioRepository repository;

    public List<Inventario> listAll() throws IOException {
        return repository.listAllInventario();
    }

    public Optional<Inventario> findByIdInventario(String id) throws IOException{
        return repository.findByIdInventario(id);
    }

    public void realizarTrade(Trade ofertante, Trade receptor)throws IOException{
        repository.realizarTrade(ofertante, receptor);
    }

}
