package com.example.demo.repository;

import com.example.demo.entity.Colis;
import com.example.demo.enums.ColisType;
import com.example.demo.enums.StatutColis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ColisRepository extends MongoRepository<Colis, String> {

    Page<Colis> findByType(ColisType type, Pageable pageable);

    Page<Colis> findByStatut(StatutColis statut, Pageable pageable);

    Page<Colis> findByAdresseDestinationContainingIgnoreCase(
            String adresse,
            Pageable pageable
    );

    Page<Colis> findByTransporteurId(
            String transporteurId,
            Pageable pageable
    );

    Page<Colis> findByTransporteurIdAndStatut(
            String transporteurId,
            StatutColis statut,
            Pageable pageable
    );
}
