package com.example.demo.service;


import com.example.demo.entity.Colis;
import com.example.demo.entity.User;
import com.example.demo.enums.ColisType;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutColis;
import com.example.demo.enums.StatutTransporteur;
import com.example.demo.repository.ColisRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {

    private final ColisRepository colisRepository;
    private final UserRepository userRepository;

    @Override
    public Colis createColis(Colis colis) {
        colis.setStatut(StatutColis.EN_ATTENTE);
        return colisRepository.save(colis);
    }

    @Override
    public Colis updateColis(String id, Colis colis) {
        Colis existing = colisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colis not found"));

        existing.setPoids(colis.getPoids());
        existing.setAdresseDestination(colis.getAdresseDestination());
        existing.setInstructionsManutention(colis.getInstructionsManutention());
        existing.setTemperatureMin(colis.getTemperatureMin());
        existing.setTemperatureMax(colis.getTemperatureMax());

        return colisRepository.save(existing);
    }

    @Override
    public void deleteColis(String id) {
        colisRepository.deleteById(id);
    }

    @Override
    public Colis assignColisToTransporteur(
            String colisId,
            String transporteurId
    ) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis not found"));

        User transporteur = userRepository.findById(transporteurId)
                .orElseThrow(() -> new RuntimeException("Transporteur not found"));

        if (!transporteur.getSpecialite().name()
                .equals(colis.getType().name())) {
            throw new RuntimeException("Specialite incompatible");
        }

        colis.setTransporteurId(transporteurId);
        colis.setStatut(StatutColis.EN_TRANSIT);
        transporteur.setStatut(StatutTransporteur.EN_LIVRAISON);

        userRepository.save(transporteur);
        return colisRepository.save(colis);
    }

    @Override
    public Colis updateStatutColis(
            String colisId,
            StatutColis statut,
            User connectedUser
    ) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis not found"));

        if (connectedUser.getRole() == Role.TRANSPORTEUR &&
                !connectedUser.getId().equals(colis.getTransporteurId())) {
            throw new RuntimeException("Access denied");
        }

        colis.setStatut(statut);
        return colisRepository.save(colis);
    }

    @Override
    public Page<Colis> getAllColis(
            ColisType type,
            StatutColis statut,
            Pageable pageable
    ) {
        if (type != null) {
            return colisRepository.findByType(type, pageable);
        }
        if (statut != null) {
            return colisRepository.findByStatut(statut, pageable);
        }
        return colisRepository.findAll(pageable);
    }

    @Override
    public Page<Colis> getColisByTransporteur(
            String transporteurId,
            Pageable pageable
    ) {
        return colisRepository.findByTransporteurId(
                transporteurId,
                pageable
        );
    }

    @Override
    public Page<Colis> searchByAdresse(
            String adresse,
            Pageable pageable
    ) {
        return colisRepository.findByAdresseDestinationContainingIgnoreCase(
                adresse,
                pageable
        );
    }
}

