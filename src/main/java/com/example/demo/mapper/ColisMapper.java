package com.example.demo.mapper;

import com.example.demo.entity.Colis;
import org.springframework.stereotype.Component;

@Component
public class ColisMapper {


    public Colis toEntity(ColisCreateDTO dto) {
        return Colis.builder()
                .type(dto.getType())
                .poids(dto.getPoids())
                .adresseDestination(dto.getAdresseDestination())
                .instructionsManutention(dto.getInstructionsManutention())
                .temperatureMin(dto.getTemperatureMin())
                .temperatureMax(dto.getTemperatureMax())
                .build();
    }

    public ColisResponseDTO toResponse(Colis colis) {
        return ColisResponseDTO.builder()
                .id(colis.getId())
                .type(colis.getType())
                .poids(colis.getPoids())
                .adresseDestination(colis.getAdresseDestination())
                .statut(colis.getStatut())
                .transporteurId(colis.getTransporteurId())
                .instructionsManutention(colis.getInstructionsManutention())
                .temperatureMin(colis.getTemperatureMin())
                .temperatureMax(colis.getTemperatureMax())
                .build();
    }
}
