package com.example.demo.dto;

import com.example.demo.enums.StatutColis;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatutColisDTO {

    @NotNull
    private StatutColis statut;
}
