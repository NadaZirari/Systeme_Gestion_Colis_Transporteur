package com.example.demo.controller;

import com.example.demo.dto.ColisResponseDTO;
import com.example.demo.dto.UpdateStatutColisDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedAccessException;
import com.example.demo.mapper.ColisMapper;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.ColisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/transporteur/colis")
@RequiredArgsConstructor
public class TransporteurColisController {

    private final ColisService colisService;
    private final ColisMapper colisMapper;

    @GetMapping
    public Page<ColisResponseDTO> myColis(Pageable pageable) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new UnauthorizedAccessException("Accès non autorisé. Veuillez vous connecter.");
            }
            
            Object principal = authentication.getPrincipal();
            if (!(principal instanceof CustomUserDetails)) {
                throw new UnauthorizedAccessException("Type d'authentification non supporté");
            }
            
            User user = ((CustomUserDetails) principal).getUser();
            if (user == null) {
                throw new UsernameNotFoundException("Utilisateur non trouvé");
            }
            
            log.info("Récupération des colis pour le transporteur: {}", user.getLogin());
            return colisService.getColisByTransporteur(user.getId(), pageable)
                    .map(colisMapper::toResponse);
                    
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des colis", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Une erreur est survenue lors de la récupération des colis", 
                e
            );
        }
    }

    @PutMapping("/{id}/statut")
    public ColisResponseDTO updateStatut(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatutColisDTO dto
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new UnauthorizedAccessException("Accès non autorisé. Veuillez vous connecter.");
            }
            
            Object principal = authentication.getPrincipal();
            if (!(principal instanceof CustomUserDetails)) {
                throw new UnauthorizedAccessException("Type d'authentification non supporté");
            }
            
            User user = ((CustomUserDetails) principal).getUser();
            if (user == null) {
                throw new UsernameNotFoundException("Utilisateur non trouvé");
            }
            
            log.info("Mise à jour du statut du colis {} par l'utilisateur {}", id, user.getLogin());
            return colisMapper.toResponse(
                colisService.updateStatutColis(id, dto.getStatut(), user)
            );
            
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès refusé à cette ressource", e);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour du statut du colis: {}", id, e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Une erreur est survenue lors de la mise à jour du statut du colis", 
                e
            );
        }
    }
}
