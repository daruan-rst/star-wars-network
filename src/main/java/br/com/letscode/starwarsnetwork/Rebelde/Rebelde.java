package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Inventario;
import br.com.letscode.starwarsnetwork.Localizacao.Localizacao;
import lombok.*;

import javax.validation.constraints.NotEmpty;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Rebelde {

    @NotEmpty
    private String id;
    private String name;
    private int age;
    private GeneroEnum genre;
    private Localizacao location;
    private boolean isTraitor;
    private long denuncia = 0;
    private Inventario inventario;

}