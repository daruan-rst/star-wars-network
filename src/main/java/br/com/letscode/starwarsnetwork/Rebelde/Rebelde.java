package br.com.letscode.starwarsnetwork.Rebelde;

import br.com.letscode.starwarsnetwork.Inventario.Inventario;
import br.com.letscode.starwarsnetwork.Inventario.Item;
import jdk.jshell.Snippet;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
    private Inventario inventario;


    public void setLocation() {
        this.location = location;
    }
}