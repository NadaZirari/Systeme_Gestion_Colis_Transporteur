package com.example.demo.controller;

import com.example.demo.dto.ColisResponseDTO;
import com.example.demo.dto.UpdateStatutColisDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.ColisMapper;
import com.example.demo.service.ColisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transporteur/colis")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TRANSPORTEUR')")
public class TransporteurColisController {

    private final ColisService colisService;
    private final ColisMapper colisMapper;

    @GetMapping
    public Page<ColisResponseDTO> myColis(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return colisService.getColisByTransporteur(user.getId(), pageable)
                .map(colisMapper::toResponse);
    }

    @PutMapping("/{id}/statut")
    public ColisResponseDTO updateStatut(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatutColisDTO dto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return colisMapper.toResponse(
                colisService.updateStatutColis(id, dto.getStatut(), user)
        );
    }
}
