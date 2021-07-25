package br.com.letscode.starwarsnetwork.Rebelde;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class Localizacao {

    private double latitude;
    private double longitude;
    private String galaxia;
    private String base;

}
