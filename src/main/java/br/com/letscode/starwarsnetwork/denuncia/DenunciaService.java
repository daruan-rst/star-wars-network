package br.com.letscode.starwarsnetwork.denuncia;

/*
 * Reportar rebelde como traidor: ao menos 3 denúncias
 * Modificar rebelde como traidor
 */

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    @Autowired
    private DenunciaRepository denunciaRepository;

    public void save(Denuncia denuncia) throws IOException {
        denunciaRepository.save(denuncia);
    }



}
