package com.example.demo.service;

import com.example.demo.dto.ColisResponseDTO;
import com.example.demo.dto.UpdateStatutColisDTO;
import com.example.demo.entity.Colis;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutColis;
import com.example.demo.exception.UnauthorizedAccessException;
import com.example.demo.mapper.ColisMapper;
import com.example.demo.repository.ColisRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColisServiceImplTest {

    @Mock
    private ColisRepository colisRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ColisMapper colisMapper;

    @InjectMocks
    private ColisServiceImpl colisService;

    private Colis colis;
    private User transporteur;
    private User admin;
    private ColisResponseDTO colisResponseDTO;

    @BeforeEach
    void setUp() {
        // Création d'un transporteur
        transporteur = new User();
        transporteur.setId("transporteur1");
        transporteur.setLogin("transporteur1");
        transporteur.setRole(Role.TRANSPORTEUR);
        transporteur.setActive(true);

        // Création d'un admin
        admin = new User();
        admin.setId("admin1");
        admin.setLogin("admin");
        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        // Création d'un colis
        colis = new Colis();
        colis.setId("colis1");
        colis.setTransporteurId("transporteur1");
        colis.setStatut(StatutColis.EN_ATTENTE);

        // Création d'un DTO de réponse
        colisResponseDTO = new ColisResponseDTO();
        colisResponseDTO.setId("colis1");
        colisResponseDTO.setStatut(StatutColis.EN_ATTENTE);
    }

    @Test
    void getColisByTransporteur_ShouldReturnColisList() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Colis> colisList = Arrays.asList(colis);
        Page<Colis> colisPage = new PageImpl<>(colisList, pageable, colisList.size());

        when(colisRepository.findByTransporteurId(anyString(), any(Pageable.class))).thenReturn(colisPage);
        when(colisMapper.toResponse(any(Colis.class))).thenReturn(colisResponseDTO);

        // Act
        Page<ColisResponseDTO> result = colisService.getColisByTransporteur("transporteur1", pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("colis1", result.getContent().get(0).getId());
        verify(colisRepository, times(1)).findByTransporteurId(anyString(), any(Pageable.class));
    }

    @Test
    void updateStatutColis_WhenUserIsTransporteur_ShouldUpdateStatus() {
        // Arrange
        when(colisRepository.findById(anyString())).thenReturn(Optional.of(colis));
        when(colisRepository.save(any(Colis.class))).thenReturn(colis);
        when(colisMapper.toResponse(any(Colis.class))).thenReturn(colisResponseDTO);

        // Act
        Colis result = colisService.updateStatutColis("colis1", StatutColis.EN_TRANSIT, transporteur);

        // Assert
        assertNotNull(result);
        assertEquals(StatutColis.EN_TRANSIT, result.getStatut());
        verify(colisRepository, times(1)).save(any(Colis.class));
    }

    @Test
    void updateStatutColis_WhenColisNotFound_ShouldThrowException() {
        // Arrange
        when(colisRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            colisService.updateStatutColis("inexistant", StatutColis.EN_TRANSIT, transporteur);
        });

        verify(colisRepository, never()).save(any(Colis.class));
    }

    @Test
    void updateStatutColis_WhenNotAuthorized_ShouldThrowException() {
        // Arrange
        User autreTransporteur = new User();
        autreTransporteur.setId("autreTransporteur");
        autreTransporteur.setRole(Role.TRANSPORTEUR);

        when(colisRepository.findById(anyString())).thenReturn(Optional.of(colis));

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () -> {
            colisService.updateStatutColis("colis1", StatutColis.EN_TRANSIT, autreTransporteur);
        });

        verify(colisRepository, never()).save(any(Colis.class));
    }

    @Test
    void updateStatutColis_WhenAdmin_ShouldUpdateAnyColis() {
        // Arrange
        when(colisRepository.findById(anyString())).thenReturn(Optional.of(colis));
        when(colisRepository.save(any(Colis.class))).thenReturn(colis);
        when(colisMapper.toResponse(any(Colis.class))).thenReturn(colisResponseDTO);

        // Act
        Colis result = colisService.updateStatutColis("colis1", StatutColis.LIVRE, admin);

        // Assert
        assertNotNull(result);
        assertEquals(StatutColis.LIVRE, result.getStatut());
        verify(colisRepository, times(1)).save(any(Colis.class));
    }
}