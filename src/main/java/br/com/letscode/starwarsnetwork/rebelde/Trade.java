package br.com.letscode.starwarsnetwork.rebelde;

import br.com.letscode.starwarsnetwork.inventario.Item;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Trade {
    private String idRebelde;
    private List<Item> itens;
}
