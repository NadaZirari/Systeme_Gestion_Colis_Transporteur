package com.example.demo.controller;

import com.example.demo.dto.ColisCreateDTO;
import com.example.demo.dto.ColisResponseDTO;
import com.example.demo.dto.ColisUpdateDTO;
import com.example.demo.entity.Colis;
import com.example.demo.enums.ColisType;
import com.example.demo.enums.StatutColis;
import com.example.demo.mapper.ColisMapper;
import com.example.demo.service.ColisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/colis")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminColisController {

    private final ColisService colisService;
    private final ColisMapper colisMapper;

    @GetMapping
    public Page<ColisResponseDTO> getAll(
            @RequestParam(required = false) ColisType type,
            @RequestParam(required = false) StatutColis statut,
            Pageable pageable
    ) {
        return colisService.getAllColis(type, statut, pageable)
                .map(colisMapper::toResponse);
    }

    @PostMapping
    public ColisResponseDTO create(
            @Valid @RequestBody ColisCreateDTO dto
    ) {
        return colisMapper.toResponse(
                colisService.createColis(colisMapper.toEntity(dto))
        );
    }

    @PutMapping("/{id}")
    public ColisResponseDTO update(
            @PathVariable String id,
            @Valid @RequestBody ColisUpdateDTO dto
    ) {
        Colis colis = Colis.builder()
                .poids(dto.getPoids())
                .adresseDestination(dto.getAdresseDestination())
                .instructionsManutention(dto.getInstructionsManutention())
                .temperatureMin(dto.getTemperatureMin())
                .temperatureMax(dto.getTemperatureMax())
                .build();

        return colisMapper.toResponse(
                colisService.updateColis(id, colis)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        colisService.deleteColis(id);
    }

    @PostMapping("/{colisId}/assign/{transporteurId}")
    public ColisResponseDTO assign(
            @PathVariable String colisId,
            @PathVariable String transporteurId
    ) {
        return colisMapper.toResponse(
                colisService.assignColisToTransporteur(colisId, transporteurId)
        );
    }
}
