package br.com.letscode.starwarsnetwork.relatorio;

/*
 * Relatórios:
 * Porcentagem de traidores. Porcentagem de rebeldes. Quantidade média de cada tipo de recurso
 * por rebelde (Ex: 2 armas por rebelde). Pontos perdidos devido a traidores.
 */

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Relatorio {
    private double traitorReport;
    private double rebelReport;
    private float inventoryReport;
    private int lostPoints;
}