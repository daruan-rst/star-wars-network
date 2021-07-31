package br.com.letscode.starwarsnetwork.denuncia;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Denuncia {
    private String id;
    private String idAcusado;
}
