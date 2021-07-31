package br.com.letscode.starwarsnetwork.localizacao;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Localizacao {

    private Long latitude;
    private Long longitude;
    private Long galaxia;
    private String base;

    @Override
    public String toString() {
        return latitude + "," + longitude + "," + galaxia + "," + base;
    }
}
