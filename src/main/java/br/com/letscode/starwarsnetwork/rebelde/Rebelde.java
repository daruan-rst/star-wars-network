package br.com.letscode.starwarsnetwork.rebelde;

import br.com.letscode.starwarsnetwork.inventario.Item;
import br.com.letscode.starwarsnetwork.localizacao.Localizacao;
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
    private long denuncia = 0;
    private List<Item> inventario;

    public void ifPresent(Object remove) {
    }

    public void setLocation(long i, long i1, long i2, String terra) {
    }

    public void setInventario() {
    }
}