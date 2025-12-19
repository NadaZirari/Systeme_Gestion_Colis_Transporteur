package com.example.demo.service;

import com.example.demo.entity.Colis;
import com.example.demo.entity.User;
import com.example.demo.enums.ColisType;
import com.example.demo.enums.StatutColis;
import com.example.demo.dto.ColisResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ColisService {
        Colis createColis(Colis colis);

Colis updateColis(String id, Colis colis);

void deleteColis(String id);

Colis assignColisToTransporteur(
        String colisId,
        String transporteurId
);

Colis updateStatutColis(
        String colisId,
        StatutColis statut,
        User connectedUser
);

Page<Colis> getAllColis(
        ColisType type,
        StatutColis statut,
        Pageable pageable
);

Page<ColisResponseDTO> getColisByTransporteur(
        String transporteurId,
        Pageable pageable
);

Page<Colis> searchByAdresse(
        String adresse,
        Pageable pageable
);
}
