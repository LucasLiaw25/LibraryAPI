package com.liaw.dev.Library.dto;

import jakarta.validation.constraints.NotBlank;

public record LoanRequest(
        @NotBlank(message = "Campo Obrigatório")
        String registration,
        @NotBlank(message = "Campo Obrigatório")
        String isbn
) {
}
