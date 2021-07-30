package br.com.letscode.starwarsnetwork.Localizacao;

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
}
