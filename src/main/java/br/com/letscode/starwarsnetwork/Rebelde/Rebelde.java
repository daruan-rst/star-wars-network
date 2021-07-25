package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Inventario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Rebelde {
    /*Um rebelde deve ter um nome, idade, gênero, localização (latitude, longitude e nome, na galáxia, da base ao qual faz parte)*/

    @NotEmpty
    private String name;
    private int age;
    private GeneroEnum genre;
    private Localizacao location;
    private Inventario inventory;
    private boolean traitor;

}