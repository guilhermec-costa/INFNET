package com.guilhermechina.onibusapi.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassagemRequestDTO {

    private String passageiro;
    private Integer assento;
    private String origem;
    private String destino;
    private LocalDate data;
    private String status;
}
