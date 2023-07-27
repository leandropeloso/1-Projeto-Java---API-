package br.com.ecommit.chamado.services.dtos;

import jakarta.validation.constraints.NotBlank;

public record CalledRecordDto(@NotBlank String name, String cause, @NotBlank String description, @NotBlank String status) {

}

