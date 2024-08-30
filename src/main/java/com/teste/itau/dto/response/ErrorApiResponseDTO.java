package com.teste.itau.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class
ErrorApiResponseDTO {

    @JsonProperty("erro")
    private String mensagem;
}
