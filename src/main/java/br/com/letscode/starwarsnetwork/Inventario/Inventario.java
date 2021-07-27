package br.com.letscode.starwarsnetwork.Inventario;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Inventario {

    private Long id;
    private List<Item> itens;
}
