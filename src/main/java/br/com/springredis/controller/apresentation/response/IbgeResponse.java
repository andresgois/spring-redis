package br.com.springredis.controller.apresentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IbgeResponse(
        Long id,
        String nome,
        Microrregiao microrregiao,

        @JsonProperty("regiao-imediata")
        RegiaoImediata regiaoImediata
) {

    public record Microrregiao(
            Long id,
            String nome,
            Mesorregiao mesorregiao
    ) {}

    public record Mesorregiao(
            Long id,
            String nome,
            UF uf
    ) {}

    public record RegiaoImediata(
            Long id,
            String nome,

            @JsonProperty("regiao-intermediaria")
            RegiaoIntermediaria regiaoIntermediaria
    ) {}

    public record RegiaoIntermediaria(
            Long id,
            String nome,
            UF uf
    ) {}

    public record UF(
            Long id,
            String sigla,
            String nome,
            Regiao regiao
    ) {}

    public record Regiao(
            Long id,
            String sigla,
            String nome
    ) {}
}
