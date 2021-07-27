package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Inventario;
import br.com.letscode.starwarsnetwork.Inventario.Item;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Rebelde {

    @NotEmpty
    private Long id;
    private String name;
    private int age;
    private GeneroEnum genre;
    private Localizacao location;
    private boolean traitor;
    private Inventario inventario;


    public boolean isTraitor() {
        return false;
    }

    public void setLocation() {
        this.location = location;
    }
}