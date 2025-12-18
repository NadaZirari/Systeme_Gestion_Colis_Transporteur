package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ColisUpdateDTO {

    @Positive
    private double poids;

    @NotBlank
    private String adresseDestination;

    private String instructionsManutention;
    private Double temperatureMin;
    private Double temperatureMax;
}
