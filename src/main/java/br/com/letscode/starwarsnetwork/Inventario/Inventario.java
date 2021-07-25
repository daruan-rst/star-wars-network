package br.com.letscode.starwarsnetwork.Inventario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Inventario {
    private List<Item> itens;
}
