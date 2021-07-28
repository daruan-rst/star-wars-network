package br.com.letscode.starwarsnetwork.Inventario;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Inventario {

    private String id;
    private List<Item> itens;
}
