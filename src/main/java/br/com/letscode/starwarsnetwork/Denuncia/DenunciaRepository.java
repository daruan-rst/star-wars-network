package br.com.letscode.starwarsnetwork.Denuncia;


import br.com.letscode.starwarsnetwork.Rebelde.RebeldeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DenunciaRepository {

    @Autowired
    private RebeldeService rebeldeService;

    public void save(Denuncia denuncia){
       //Criar um banco de dados para as denuncias
        //depois disso no rebelde precisa ver como confirmar a traicao
        //a questa da denuncia também
        List<Denuncia> registeredDenuncias = listAll();

        if(qtdDenunciaRebelde(denuncia.getIdAcusado()) >= 2){
            confirmarTraicao(denuncia.getIdAcusado());
            registeredDenuncias.add(denuncia);
        }else{
            registeredDenuncias.add(denuncia);
        }
    }

    public void confirmarTraicao(String idRebelde){ rebeldeService.confirmarTraicao(idRebelde);}

    public long qtdDenunciaRebelde(String idRebelde){ return rebeldeService.qntDenunciaRebelde(idRebelde);}
}
