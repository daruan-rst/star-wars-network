package br.com.letscode.starwarsnetwork.Rebelde;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor

public class Localizacao {

    private Long latitude;
    private Long longitude;
    private Long galaxia;
    private String base;

}
