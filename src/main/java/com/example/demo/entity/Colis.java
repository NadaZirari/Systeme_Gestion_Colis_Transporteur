package com.example.demo.entity;

import com.example.demo.enums.ColisType;
import com.example.demo.enums.StatutColis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "colis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colis {

    @Id
    private String id;

    private ColisType type;
    private double poids;
    private String adresseDestination;

    private StatutColis statut;

    // Référence transporteur
    private String transporteurId;

    // FRAGILE uniquement
    private String instructionsManutention;

    // FRIGO uniquement
    private Double temperatureMin;
    private Double temperatureMax;
}

