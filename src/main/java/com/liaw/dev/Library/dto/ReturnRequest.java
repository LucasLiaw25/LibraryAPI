package com.liaw.dev.Library.dto;

import jakarta.validation.constraints.NotBlank;

public record ReturnRequest(Long id, String registration, String isbn) {
}
