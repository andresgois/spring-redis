package br.com.springredis.controller.apresentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record IbgeResponse (
        Long id,
        String nome,
        Microrregiao microrregiao,

        @JsonProperty("regiao-imediata")
        RegiaoImediata regiaoImediata
) implements Serializable{

    public record Microrregiao(
            Long id,
            String nome,
            Mesorregiao mesorregiao
    ) implements Serializable{}

    public record Mesorregiao(
            Long id,
            String nome,
            UF uf
    ) implements Serializable{}

    public record RegiaoImediata(
            Long id,
            String nome,

            @JsonProperty("regiao-intermediaria")
            RegiaoIntermediaria regiaoIntermediaria
    ) implements Serializable{}

    public record RegiaoIntermediaria(
            Long id,
            String nome,
            UF uf
    ) implements Serializable{}

    public record UF(
            Long id,
            String sigla,
            String nome,
            Regiao regiao
    )implements Serializable {}

    public record Regiao(
            Long id,
            String sigla,
            String nome
    ) implements Serializable{}
}
