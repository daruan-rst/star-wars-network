package br.com.letscode.starwarsnetwork.Denuncia;

/*
 * Reportar rebelde como traidor: ao menos 3 denúncias
 * Modificar rebelde como traidor
 */

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class DenunciaService {

    @Autowired
    private DenunciaRepository denunciaRepository;

    public void save(Denuncia denuncia) throws IOException {
        denunciaRepository.save(denuncia);
    }



}
