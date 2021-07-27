package br.com.letscode.starwarsnetwork.Inventario;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    private Long id;
    private String name;
    private int point;
    private int qnd;

    public void adicionarPontos() {

        if (this.name.equalsIgnoreCase("Arma")) {
            this.point = 4;
        } else if (this.name.equalsIgnoreCase("Munição")) {
            this.point = 3;
        } else if (this.name.equalsIgnoreCase("Água")) {
            this.point = 2;
        } else if (this.name.equalsIgnoreCase("Comida")) {
            this.point = 1;
        }
    }


}
