package com.example.demo.dto;

import com.example.demo.enums.ColisType;
import com.example.demo.enums.StatutColis;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColisResponseDTO {

    private String id;
    private ColisType type;
    private double poids;
    private String adresseDestination;
    private StatutColis statut;
    private String transporteurId;

    private String instructionsManutention;
    private Double temperatureMin;
    private Double temperatureMax;
}
